package com.memoryvault.dao;

import com.memoryvault.model.Capsule;
import com.memoryvault.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CapsuleDAO {

    public int createCapsule(Capsule capsule) {
        int capsuleId = 0;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO capsules(user_id, title, secret_letter, unlock_date, capsule_type) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, capsule.getUserId());
            ps.setString(2, capsule.getTitle());
            ps.setString(3, capsule.getSecretLetter());
            ps.setString(4, capsule.getUnlockDate());
            ps.setString(5, capsule.getCapsuleType());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                capsuleId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return capsuleId;
    }

    public List<Capsule> getCapsulesByUser(int userId) {
        List<Capsule> capsules = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT *, IF(unlock_date <= NOW(), TRUE, FALSE) AS unlocked_status FROM capsules WHERE user_id=? ORDER BY created_at DESC";

            PreparedStatement ps = con.prepareStatement(sql);
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

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT *, IF(unlock_date <= NOW(), TRUE, FALSE) AS unlocked_status FROM capsules WHERE capsule_id=? AND user_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
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

    public void updateUnlockStatus(int capsuleId) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE capsules SET is_unlocked = TRUE WHERE capsule_id=? AND unlock_date <= NOW()";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, capsuleId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}