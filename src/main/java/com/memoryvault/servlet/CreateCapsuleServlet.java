package com.memoryvault.servlet;

import com.memoryvault.dao.CapsuleDAO;
import com.memoryvault.dao.MediaDAO;
import com.memoryvault.model.Capsule;
import com.memoryvault.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/createCapsule")
@MultipartConfig(maxFileSize = 1024 * 1024 * 100)
public class CreateCapsuleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String secretLetter = request.getParameter("secretLetter");
        String unlockDate = request.getParameter("unlockDate");
        String capsuleType = request.getParameter("capsuleType");

        if (unlockDate != null) {
            unlockDate = unlockDate.replace("T", " ") + ":00";
        }

        Capsule capsule = new Capsule();

        capsule.setUserId(user.getUserId());
        capsule.setTitle(title);
        capsule.setSecretLetter(secretLetter);
        capsule.setUnlockDate(unlockDate);
        capsule.setCapsuleType(capsuleType);

        CapsuleDAO capsuleDAO = new CapsuleDAO();
        int capsuleId = capsuleDAO.createCapsule(capsule);

        if (capsuleId > 0) {
            Part filePart = request.getPart("mediaFile");

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                String fileType = filePart.getContentType();
                InputStream fileData = filePart.getInputStream();

                MediaDAO mediaDAO = new MediaDAO();
                mediaDAO.saveMedia(capsuleId, fileName, fileType, fileData);
            }

            response.sendRedirect("dashboard.jsp?created=1");
        } else {
            response.sendRedirect("createCapsule.jsp?error=1");
        }
    }
}