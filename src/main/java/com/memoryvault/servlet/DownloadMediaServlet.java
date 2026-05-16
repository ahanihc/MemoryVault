package com.memoryvault.servlet;

import com.memoryvault.dao.MediaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;

@WebServlet("/downloadMedia")
public class DownloadMediaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int mediaId = Integer.parseInt(request.getParameter("id"));

        try {
            MediaDAO mediaDAO = new MediaDAO();
            ResultSet rs = mediaDAO.getMediaStream(mediaId);

            if (rs != null && rs.next()) {
                String fileName = rs.getString("file_name");
                String fileType = rs.getString("file_type");
                InputStream inputStream = rs.getBinaryStream("file_data");

                response.setContentType(fileType);
                response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

                OutputStream outputStream = response.getOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}