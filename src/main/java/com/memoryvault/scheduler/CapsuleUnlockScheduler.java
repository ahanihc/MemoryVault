package com.memoryvault.scheduler;

import com.memoryvault.util.DBConnection;
import com.memoryvault.util.EmailUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CapsuleUnlockScheduler {

    public static void startScheduler() {

        Thread schedulerThread = new Thread(() -> {

            while (true) {

                try {
                    Connection con = DBConnection.getConnection();

                    if (con == null) {
                        System.out.println("Database connection failed.");
                        Thread.sleep(60000);
                        continue;
                    }

                    String sql =
                            "SELECT c.capsule_id, c.title, u.email " +
                            "FROM capsules c " +
                            "JOIN users u ON c.user_id = u.user_id " +
                            "WHERE c.unlock_date <= DATE_ADD(NOW(), INTERVAL 330 MINUTE) " +
                            "AND c.email_sent = FALSE";

                    PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        int capsuleId = rs.getInt("capsule_id");
                        String title = rs.getString("title");
                        String email = rs.getString("email");

                        String updateSql =
                                "UPDATE capsules SET is_unlocked = TRUE, email_sent = TRUE " +
                                "WHERE capsule_id=?";

                        PreparedStatement updatePs = con.prepareStatement(updateSql);
                        updatePs.setInt(1, capsuleId);
                        updatePs.executeUpdate();

                        EmailUtility.sendUnlockEmail(email, title);

                        System.out.println("Capsule unlocked and email sent: " + capsuleId);
                    }

                    con.close();

                    Thread.sleep(60000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        schedulerThread.setDaemon(true);
        schedulerThread.start();

        System.out.println("MemoryVault scheduler started...");
    }
}