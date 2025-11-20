package com.anoop.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    @Autowired
    private JavaMailSender mailSender;

    public Boolean sendEmailForTempPassword(String toAddress, String subject, String body) {
        Boolean isSent = false;
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(mimeMessage);
            isSent = true;
            return isSent;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return isSent;
    }
}
