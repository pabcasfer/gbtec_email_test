package com.gbtec.email.business.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "email_receivers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailReceiverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_id")
    private EmailEntity email;

    @NonNull
    private String emailTo;

    @NonNull
    private Boolean hidden;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailReceiverEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(emailTo, that.emailTo) && Objects.equals(hidden, that.hidden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emailTo, hidden);
    }

    @Override
    public String toString() {
        return "EmailReceiverEntity{" +
                "id=" + id +
                ", emailTo='" + emailTo + '\'' +
                ", hidden=" + hidden +
                '}';
    }

    public boolean sameInfo(EmailReceiverEntity receiver) {
        return receiver.emailTo.equals(this.emailTo) && receiver.hidden.equals(this.hidden);
    }
}
