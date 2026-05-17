<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memoryvault.model.User" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Capsule - MemoryVault</title>

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
                <li><a href="dashboard.jsp">Dashboard</a></li>
                <li><a href="createCapsule.jsp" class="nav-btn">Create Capsule</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </nav>

    </div>

</header>

<section class="auth-container">

    <div class="auth-card create-card">

        <h1 class="auth-title">
            Create a New Memory Capsule
        </h1>

        <% if (request.getParameter("error") != null) { %>
            <p class="error">
                Capsule creation failed. Please try again.
            </p>
        <% } %>

        <form action="createCapsule" method="post" enctype="multipart/form-data">

            <div class="input-group">
                <label for="title">Capsule Title</label>
                <input type="text"
                       id="title"
                       name="title"
                       placeholder="Example: Open this on graduation day"
                       required>
            </div>

            <div class="input-group">
                <label for="secretLetter">Your Secret Letter</label>
                <textarea id="secretLetter"
                          name="secretLetter"
                          rows="8"
                          placeholder="Write something for your future self..."
                          required></textarea>
            </div>

            <div class="input-group">
                <label for="unlockDate">Unlock Date and Time</label>
                <input type="datetime-local"
                       id="unlockDate"
                       name="unlockDate"
                       required>
            </div>

            <div class="input-group">
                <label for="capsuleType">Capsule Type</label>
                <select id="capsuleType"
                        name="capsuleType"
                        required>
                    <option value="PERSONAL">Personal</option>
                    <option value="FAMILY">Family</option>
                    <option value="FRIENDS">Friends</option>
                    <option value="TEAM">Team</option>
                    <option value="FUTURE_SELF">Future Self</option>
                </select>
            </div>

            <div class="input-group">
                <label for="mediaFile">Upload Image / Audio / Video</label>
                <input type="file"
                       id="mediaFile"
                       name="mediaFile"
                       accept="image/*,audio/*,video/*">
            </div>

            <button type="submit" class="auth-btn">
                Create Capsule
            </button>

        </form>

        <div class="auth-link">
            <a href="dashboard.jsp">Back to Dashboard</a>
        </div>

    </div>

</section>

<footer class="home-footer">
    <p>
        Made with &#127807; for future memories — MemoryVault
    </p>
</footer>

</body>
</html>