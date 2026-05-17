package com.memoryvault.scheduler;

import com.memoryvault.util.DBConnection;

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
                        System.out.println("Database connection failed. Scheduler skipped.");
                        Thread.sleep(60000);
                        continue;
                    }

                    String sql =
                            "SELECT capsule_id " +
                            "FROM capsules " +
                            "WHERE unlock_date <= DATE_ADD(NOW(), INTERVAL 330 MINUTE) " +
                            "AND is_unlocked = FALSE";

                    PreparedStatement ps = con.prepareStatement(sql);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {

                        int capsuleId = rs.getInt("capsule_id");

                        String updateSql =
                                "UPDATE capsules " +
                                "SET is_unlocked = TRUE " +
                                "WHERE capsule_id=?";

                        PreparedStatement updatePs =
                                con.prepareStatement(updateSql);

                        updatePs.setInt(1, capsuleId);

                        updatePs.executeUpdate();

                        updatePs.close();

                        System.out.println("Capsule unlocked: " + capsuleId);
                    }

                    rs.close();
                    ps.close();
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