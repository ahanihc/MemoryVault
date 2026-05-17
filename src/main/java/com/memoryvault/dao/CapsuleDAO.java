package com.memoryvault.dao;

import com.memoryvault.model.Capsule;
import com.memoryvault.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CapsuleDAO {

    public int createCapsule(Capsule capsule) {
    int capsuleId = 0;

    String sql = "INSERT INTO capsules(user_id, title, secret_letter, unlock_date, capsule_type, is_unlocked, email_sent) " +
                 "VALUES (?, ?, ?, ?, ?, FALSE, FALSE)";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setInt(1, capsule.getUserId());
        ps.setString(2, capsule.getTitle());
        ps.setString(3, capsule.getSecretLetter());
        ps.setString(4, capsule.getUnlockDate());
        ps.setString(5, capsule.getCapsuleType());

        int rows = ps.executeUpdate();
        System.out.println("Capsule insert rows: " + rows);

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            capsuleId = rs.getInt(1);
            System.out.println("Created capsule ID: " + capsuleId);
        }

    } catch (Exception e) {
        System.out.println("Capsule creation failed in DAO");
        e.printStackTrace();
    }

    return capsuleId;
}
    public List<Capsule> getCapsulesByUser(int userId) {
        List<Capsule> capsules = new ArrayList<>();

        String sql =
                "SELECT *, IF(unlock_date <= DATE_ADD(NOW(), INTERVAL 5 HOUR 30 MINUTE), TRUE, FALSE) AS unlocked_status " +
                "FROM capsules WHERE user_id=? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Capsule capsule = new Capsule();

                capsule.setCapsuleId(rs.getInt("capsule_id"));
                capsule.setUserId(rs.getInt("user_id"));
                capsule.setTitle(rs.getString("title"));
                capsule.setSecretLetter(rs.getString("secret_letter"));
                capsule.setUnlockDate(rs.getString("unlock_date"));
                capsule.setCapsuleType(rs.getString("capsule_type"));
                capsule.setUnlocked(rs.getBoolean("unlocked_status"));

                capsules.add(capsule);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return capsules;
    }

    public Capsule getCapsuleById(int capsuleId, int userId) {
        Capsule capsule = null;

        String sql =
                "SELECT *, IF(unlock_date <= DATE_ADD(NOW(), INTERVAL 5 HOUR 30 MINUTE), TRUE, FALSE) AS unlocked_status " +
                "FROM capsules WHERE capsule_id=? AND user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, capsuleId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                capsule = new Capsule();

                capsule.setCapsuleId(rs.getInt("capsule_id"));
                capsule.setUserId(rs.getInt("user_id"));
                capsule.setTitle(rs.getString("title"));
                capsule.setSecretLetter(rs.getString("secret_letter"));
                capsule.setUnlockDate(rs.getString("unlock_date"));
                capsule.setCapsuleType(rs.getString("capsule_type"));
                capsule.setUnlocked(rs.getBoolean("unlocked_status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return capsule;
    }

    public boolean updateUnlockStatus(int capsuleId) {
        String sql =
                "UPDATE capsules SET is_unlocked = TRUE " +
                "WHERE capsule_id=? AND unlock_date <= DATE_ADD(NOW(), INTERVAL 5 HOUR 30 MINUTE)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, capsuleId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}