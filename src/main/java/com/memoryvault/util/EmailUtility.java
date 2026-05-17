package com.memoryvault.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtility {

    public static void sendUnlockEmail(String toEmail, String capsuleTitle) {

        final String fromEmail = System.getenv("EMAIL_USER");
        final String password = System.getenv("EMAIL_PASSWORD");

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("Your MemoryVault Capsule is Unlocked!");

            message.setText(
                    "Hello,\n\n" +
                    "Your capsule \"" + capsuleTitle + "\" is now unlocked.\n\n" +
                    "Login to MemoryVault to view it.\n\n" +
                    "— MemoryVault Team"
            );

            Transport.send(message);

            System.out.println("Unlock email sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}