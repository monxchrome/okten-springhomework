package owu.springhomework.com.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public void sendMail(String to, String text, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(text);
        message.setTo(to);
        message.setFrom(mailFrom + "@gmail.com");
        mailSender.send(message);
    }
}
