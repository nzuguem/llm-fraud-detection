package me.nzuguem.fraud.detection.services.email;

import dev.langchain4j.agent.tool.Tool;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailService {

    private final Mailer mailer;

    public EmailService(Mailer mailer) {
        this.mailer = mailer;
    }

    @Tool("Send this given content to this given customer email by email")
    public void sendAnEmail(String emailContent, String customerEmail) {
        this.mailer.send(Mail.withText(
                customerEmail,
                "Fraud Suspicion",
                emailContent));
    }
}
