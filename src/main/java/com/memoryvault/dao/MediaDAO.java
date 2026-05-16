package com.memoryvault.dao;

import com.memoryvault.model.MediaFile;
import com.memoryvault.util.DBConnection;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MediaDAO {

    public boolean saveMedia(int capsuleId, String fileName, String fileType, InputStream fileData) {
        boolean status = false;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO media_files(capsule_id, file_name, file_type, file_data) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, capsuleId);
            ps.setString(2, fileName);
            ps.setString(3, fileType);
            ps.setBlob(4, fileData);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public List<MediaFile> getMediaByCapsule(int capsuleId) {
        List<MediaFile> files = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT media_id, capsule_id, file_name, file_type FROM media_files WHERE capsule_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, capsuleId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MediaFile media = new MediaFile();

                media.setMediaId(rs.getInt("media_id"));
                media.setCapsuleId(rs.getInt("capsule_id"));
                media.setFileName(rs.getString("file_name"));
                media.setFileType(rs.getString("file_type"));

                files.add(media);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return files;
    }

    public ResultSet getMediaStream(int mediaId) {
        ResultSet rs = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT file_name, file_type, file_data FROM media_files WHERE media_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, mediaId);

            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }
}