package com.memoryvault.scheduler;

import com.memoryvault.util.DBConnection;
import com.memoryvault.util.EmailUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

public class CapsuleUnlockScheduler {

    public static void startScheduler() {

        Timer timer = new Timer(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkUnlockedCapsules();
            }
        }, 0, 60000);
    }

    private static void checkUnlockedCapsules() {

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT c.capsule_id, c.title, c.created_at, u.name, u.email " +
                    "FROM capsules c " +
                    "JOIN users u ON c.user_id = u.user_id " +
                    "WHERE c.unlock_date <= NOW() " +
                    "AND c.email_sent = FALSE";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int capsuleId = rs.getInt("capsule_id");
                String title = rs.getString("title");
                String createdAt = rs.getString("created_at");
                String name = rs.getString("name");
                String email = rs.getString("email");

                EmailUtil.sendUnlockEmail(email, name, title, createdAt, capsuleId);

                String updateSql =
                        "UPDATE capsules SET is_unlocked = TRUE, email_sent = TRUE WHERE capsule_id=?";

                PreparedStatement updatePs = con.prepareStatement(updateSql);
                updatePs.setInt(1, capsuleId);
                updatePs.executeUpdate();

                System.out.println("Capsule unlocked and email sent: " + title);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}