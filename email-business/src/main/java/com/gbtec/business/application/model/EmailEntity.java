package com.gbtec.business.application.model;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "email")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
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
    private List<EmailReceiverEntity> receivers;

    @CreationTimestamp
    private Timestamp creationTime;

    @UpdateTimestamp
    private Timestamp lastModifiedTime;

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
}
