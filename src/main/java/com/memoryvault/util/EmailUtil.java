package com.memoryvault.util;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    private static final String FROM_EMAIL = "memoryvault.team@gmail.com";
    private static final String APP_PASSWORD = "hnjy kjeo flka ycek";

    public static void sendUnlockEmail(String toEmail, String userName, String capsuleTitle, String createdAt, int capsuleId) {

    String capsuleLink = "http://localhost:8080/memoryvault/viewCapsule?id=" + capsuleId;

    String subject = "MemoryVault: Your Capsule is Ready to Unlock!";

    String body =
            "Hello " + userName + ",\n\n" +
            "A memory capsule created by you is now ready to unlock.\n\n" +
            "Capsule Title: " + capsuleTitle + "\n" +
            "Created On: " + createdAt + "\n\n" +
            "Open your capsule here:\n" +
            capsuleLink + "\n\n" +
            "Regards,\n" +
            "MemoryVault Team";

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}