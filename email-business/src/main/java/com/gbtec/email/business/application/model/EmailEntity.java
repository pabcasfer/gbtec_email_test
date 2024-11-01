package com.gbtec.email.business.application.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity(name = "email")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "email")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "uuid")
    private Long uuid;

    @Column(name = "email_from")
    @NonNull
    private String from;

    @OneToMany(mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
    @NonNull
    @Builder.Default
    private List<EmailReceiverEntity> receivers = new ArrayList<>();

    @CreationTimestamp
    private Instant creationTime;

    // NOTE: We may want to persist the last time an user modified the email instead updating it every time we update
    // the entity
    @UpdateTimestamp
    private Instant lastModifiedTime;

    @NonNull
    private EmailState state;

    private String body;

    public List<String> shownReceivers() {
        return receivers.stream()
                .filter(emailReceiverEntity -> !emailReceiverEntity.getHidden())
                .map(EmailReceiverEntity::getEmailTo)
                .collect(Collectors.toList());
    }

    public List<String> hiddenReceivers() {
        return receivers.stream()
                .filter(EmailReceiverEntity::getHidden)
                .map(EmailReceiverEntity::getEmailTo)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailEntity email)) return false;
        return Objects.equals(id, email.id) && Objects.equals(uuid, email.uuid) && Objects.equals(from, email.from)
                && Objects.equals(receivers, email.receivers) && Objects.equals(creationTime, email.creationTime)
                && Objects.equals(lastModifiedTime, email.lastModifiedTime) && state == email.state
                && Objects.equals(body, email.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, from, receivers, creationTime, lastModifiedTime, state, body);
    }

    @Override
    public String toString() {
        return "EmailEntity{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", from='" + from + '\'' +
                ", receivers=" + receivers +
                ", creationTime=" + creationTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", state=" + state +
                ", body='" + body + '\'' +
                '}';
    }
}
