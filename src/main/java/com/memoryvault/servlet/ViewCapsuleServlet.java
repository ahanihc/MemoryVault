package com.memoryvault.servlet;

import com.memoryvault.dao.CapsuleDAO;
import com.memoryvault.dao.MediaDAO;
import com.memoryvault.model.Capsule;
import com.memoryvault.model.MediaFile;
import com.memoryvault.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/viewCapsule")
public class ViewCapsuleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            if (session == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int capsuleId = Integer.parseInt(request.getParameter("id"));

            CapsuleDAO capsuleDAO = new CapsuleDAO();

            capsuleDAO.updateUnlockStatus(capsuleId);

            Capsule capsule = capsuleDAO.getCapsuleById(capsuleId, user.getUserId());

            if (capsule == null) {
                response.sendRedirect("dashboard.jsp");
                return;
            }

            boolean locked = !capsule.isUnlocked();

            MediaDAO mediaDAO = new MediaDAO();
            List<MediaFile> mediaFiles = mediaDAO.getMediaByCapsule(capsuleId);

            request.setAttribute("capsule", capsule);
            request.setAttribute("locked", locked);
            request.setAttribute("mediaFiles", mediaFiles);

            request.getRequestDispatcher("viewCapsule.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard.jsp");
        }
    }
}