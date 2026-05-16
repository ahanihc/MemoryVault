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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

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
            response.sendRedirect("dashboard.jsp?notfound=1");
            return;
        }

        if (!capsule.isUnlocked()) {
            request.setAttribute("capsule", capsule);
            request.setAttribute("locked", true);
            request.getRequestDispatcher("viewCapsule.jsp").forward(request, response);
            return;
        }

        MediaDAO mediaDAO = new MediaDAO();
        List<MediaFile> mediaFiles = mediaDAO.getMediaByCapsule(capsuleId);

        request.setAttribute("capsule", capsule);
        request.setAttribute("mediaFiles", mediaFiles);
        request.setAttribute("locked", false);

        request.getRequestDispatcher("viewCapsule.jsp").forward(request, response);
    }
}