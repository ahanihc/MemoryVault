package com.memoryvault.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtility {

    public static boolean sendUnlockEmail(String toEmail, String capsuleTitle) {

        final String fromEmail = System.getenv("EMAIL_USER");
        final String appPassword = System.getenv("EMAIL_PASSWORD");

        try {
            Properties props = new Properties();

            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");

            props.put("mail.smtp.connectiontimeout", "10000");
            props.put("mail.smtp.timeout", "10000");
            props.put("mail.smtp.writetimeout", "10000");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, appPassword);
                }
            });

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("Your MemoryVault Capsule is Ready to Unlock");

            message.setText(
                    "Hello,\n\n" +
                    "Your MemoryVault capsule \"" + capsuleTitle + "\" is now ready to unlock.\n\n" +
                    "Login to MemoryVault and open your capsule.\n\n" +
                    "Regards,\nMemoryVault Team"
            );

            Transport.send(message);

            System.out.println("Unlock email sent successfully to " + toEmail);
            return true;

        } catch (Exception e) {
            System.out.println("Email sending failed");
            e.printStackTrace();
            return false;
        }
    }
}