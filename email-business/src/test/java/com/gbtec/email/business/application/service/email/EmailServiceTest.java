package com.gbtec.email.business.application.service.email;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailException;
import com.gbtec.email.business.application.model.EmailExceptionType;
import com.gbtec.email.business.application.model.EmailReceiverEntity;
import com.gbtec.email.business.testutils.model.EmailEntityMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EmailServiceTest {
    @Autowired
    private EmailService service;

    @Test
    public void multipleCreate() throws EmailException {
        final EmailEntity email1 = EmailEntityMother.draft();
        final EmailEntity email2 = EmailEntityMother.draft();

        service.create(Arrays.asList(email1, email2));

        validateCreatedEmail(email1);
        validateCreatedEmail(email2);
    }

    @Test
    public void createRepeatedUuid() {
        final EmailEntity email1 = EmailEntityMother.draft();
        final EmailEntity email2 = EmailEntityMother.draftFromAddressWithoutReceivers(email1.getUuid());

        final EmailException thrownException = Assertions.assertThrows(EmailException.class, () -> service.create(Arrays.asList(email1, email2)));

        Assertions.assertEquals(EmailExceptionType.UUID_ALREADY_IN_USE, thrownException.getType());

        validateNotCreatedEmail(email1);
        validateNotCreatedEmail(email2);
    }

    private void validateCreatedEmail(EmailEntity email) {
        final Optional<EmailEntity> insertedEmailOpt = service.findByUuid(email.getUuid());
        Assertions.assertTrue(insertedEmailOpt.isPresent());
        final EmailEntity insertedEmail = insertedEmailOpt.get();
        Assertions.assertNotNull(insertedEmail.getId());
        Assertions.assertEquals(email.getFrom(), insertedEmail.getFrom());
        Assertions.assertEquals(email.getState(), insertedEmail.getState());
        Assertions.assertEquals(email.getBody(), insertedEmail.getBody());
        Assertions.assertEquals(email.getReceivers().size(), insertedEmail.getReceivers().size());
        Assertions.assertTrue(insertedEmail.getReceivers().stream().map(EmailReceiverEntity::getId).allMatch(Objects::nonNull));
    }

    private void validateNotCreatedEmail(EmailEntity email) {
        Assertions.assertFalse(service.findByUuid(email.getUuid()).isPresent());
    }
}