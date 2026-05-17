<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memoryvault.model.User" %>
<%@ page import="com.memoryvault.model.Capsule" %>
<%@ page import="com.memoryvault.dao.CapsuleDAO" %>
<%@ page import="java.util.List" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    CapsuleDAO capsuleDAO = new CapsuleDAO();

    List<Capsule> capsules = capsuleDAO.getCapsulesByUser(user.getUserId());

    List<Capsule> notifications = capsuleDAO.getNewUnlockedNotifications(user.getUserId());

    if (notifications != null) {
        for (Capsule notifyCapsule : notifications) {
            capsuleDAO.markNotificationSeen(notifyCapsule.getCapsuleId());
        }
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Dashboard - MemoryVault</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<header class="home-header">
    <div class="home-container nav-wrapper">

        <h1 class="logo-text">
            &#10024; Memory<span>Vault</span>
        </h1>

        <nav>
            <ul class="nav-menu">
                <li><a href="dashboard.jsp" class="nav-btn">Dashboard</a></li>
                <li><a href="createCapsule.jsp">Create Capsule</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </nav>

    </div>
</header>

<section class="dashboard">

    <div class="welcome-card">
        <h1>Welcome, <%= user.getName() %></h1>
        <p>Your cozy hub for creating and managing future memories.</p>

        <br>

        <a href="createCapsule.jsp" class="btn btn-primary">
            Create New Capsule
        </a>
    </div>

    <h2 class="page-title">Your Capsules</h2>

    <div class="capsule-grid">

        <%
            if (capsules == null || capsules.size() == 0) {
        %>

            <div class="capsule-card">
                <h3 class="capsule-title">No capsules yet</h3>
                <p>Create your first memory capsule today.</p>

                <a href="createCapsule.jsp" class="view-btn">
                    Create Capsule
                </a>
            </div>

        <%
            } else {
                for (Capsule capsule : capsules) {
        %>

            <div class="capsule-card">

                <h3 class="capsule-title">
                    <%= capsule.getTitle() %>
                </h3>

                <p><b>Type:</b> <%= capsule.getCapsuleType() %></p>
                <p><b>Unlock Date:</b> <%= capsule.getUnlockDate() %></p>

                <% if (capsule.isUnlocked()) { %>
                    <span class="success">Unlocked</span>
                <% } else { %>
                    <span class="locked">Locked</span>
                <% } %>

                <br><br>

                <a class="view-btn"
                   href="viewCapsule?id=<%= capsule.getCapsuleId() %>">
                    View Capsule
                </a>

            </div>

        <%
                }
            }
        %>

    </div>

</section>

<% if (notifications != null && notifications.size() > 0) { %>

<div class="unlock-popup" id="unlockPopup">

    <div class="unlock-popup-card">

        <h2>Your capsule is ready!</h2>

        <p>
            You have <%= notifications.size() %> capsule(s) ready to unlock.
        </p>

        <div class="popup-capsule-list">

            <%
                for (Capsule notifyCapsule : notifications) {
            %>

                <p>
                    <b><%= notifyCapsule.getTitle() %></b>
                </p>

            <%
                }
            %>

        </div>

        <a href="dashboard.jsp" class="view-btn">
            View Capsules
        </a>

        <button onclick="closePopup()" class="popup-close-btn">
            Later
        </button>

    </div>

</div>

<script>
    function closePopup() {
        document.getElementById("unlockPopup").style.display = "none";
    }
</script>

<% } %>

<footer class="home-footer">
    <p>Turning moments into future nostalgia— MemoryVault</p>
</footer>

</body>
</html>