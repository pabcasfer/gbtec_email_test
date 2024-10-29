package com.gbtec.email.business.application.repository.email;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailEntityFilter;
import com.gbtec.email.business.application.model.EmailReceiverEntity;
import com.gbtec.email.business.application.model.EmailState;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmailRepositoryCustomImpl implements EmailRepositoryCustom {
    @Autowired
    @Lazy
    private EmailRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EmailEntity insert(EmailEntity email) {
        email.getReceivers().forEach(receiver -> receiver.setEmail(email));
        return this.repository.save(email);
    }

    @Override
    public EmailEntity update(EmailEntity persistedEmail, EmailEntity updatedEmail) {
        persistedEmail.setFrom(updatedEmail.getFrom());
        persistedEmail.setBody(updatedEmail.getBody());
        persistedEmail.setState(updatedEmail.getState());

        final Set<EmailReceiverEntity> receiversToRemove = persistedEmail.getReceivers().stream()
                .filter(r -> updatedEmail.getReceivers().stream().noneMatch(r2 -> r2.sameInfo(r)))
                .collect(Collectors.toSet());
        persistedEmail.getReceivers().removeAll(receiversToRemove);

        updatedEmail.getReceivers().stream()
                .filter(r -> persistedEmail.getReceivers().stream().noneMatch(r2 -> r2.sameInfo(r)))
                .forEach(r -> {
                    r.setEmail(persistedEmail);
                    persistedEmail.getReceivers().add(r);
                });

        return this.repository.save(persistedEmail);
    }

    @Override
    public List<EmailEntity> find(EmailEntityFilter filter) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<EmailEntity> query = criteriaBuilder.createQuery(EmailEntity.class);

        final Root<EmailEntity> root = query.from(EmailEntity.class);
        final Expression<Long> uuids = root.get("uuid");
        final Expression<String> froms = root.get("from");
        final Expression<EmailState> state = root.get("state");
        final Join<EmailEntity, EmailReceiverEntity> receivers = root.join("receivers", JoinType.INNER);
        final Path<String> emailTo = receivers.get("emailTo");
        final Path<Boolean> hidden = receivers.get("hidden");
        final Path<String> body = root.get("body");

        final List<Predicate> predicates = new ArrayList<>();

        filter.getUuids().ifPresent(uuidsFilterValue -> predicates.add(uuids.in(uuidsFilterValue)));
        filter.getFroms().ifPresent(fromsFilterValue -> predicates.add(froms.in(fromsFilterValue)));
        filter.getStates().ifPresent(stateFilterValues -> predicates.add(state.in(stateFilterValues)));
        filter.getBody().ifPresent(bodyFilterValue -> predicates.add(criteriaBuilder.like(body, "%" + bodyFilterValue + "%")));

        if(filter.getTos().isPresent()) {
            final List<String> tos = filter.getTos().getValue();
            predicates.addAll(tos.stream().map(to -> criteriaBuilder.and(criteriaBuilder.equal(emailTo, to), criteriaBuilder.equal(hidden, false))).toList());
        }

        if(filter.getCcs().isPresent()) {
            final List<String> ccs = filter.getCcs().getValue();
            predicates.addAll(ccs.stream().map(cc -> criteriaBuilder.and(criteriaBuilder.equal(emailTo, cc), criteriaBuilder.equal(hidden, true))).toList());
        }

        query.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        final Session session = entityManager.unwrap(Session.class);
        return session.createQuery(query).getResultList();
    }
}
