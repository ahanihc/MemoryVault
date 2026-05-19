<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memoryvault.model.Capsule" %>
<%@ page import="com.memoryvault.model.MediaFile" %>
<%@ page import="java.util.List" %>

<%
    Capsule capsule = (Capsule) request.getAttribute("capsule");
    Boolean locked = (Boolean) request.getAttribute("locked");

    if (capsule == null) {
        response.sendRedirect("dashboard.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Capsule - MemoryVault</title>

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
                <li><a href="createCapsule.jsp">Create Capsule</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </nav>

    </div>

</header>

<section class="dashboard">

    <div class="capsule-view">

        <h1 class="page-title">
            <%= capsule.getTitle() %>
        </h1>

        <p>
            <b>Capsule Type:</b> <%= capsule.getCapsuleType() %>
        </p>

        <p>
            <b>Unlock Date:</b> <%= capsule.getUnlockDate() %>
        </p>

        <br>

        <% if (locked != null && locked) { %>

            <div class="capsule-card">

                <h2 class="capsule-title">
                    This capsule is still locked
                </h2>

                <p>
                    Your memory is safely stored. Come back after the unlock date.
                </p>

                <br>

                <span class="locked">
                    Locked
                </span>

            </div>

        <% } else { %>

            <span class="success">
                Unlocked
            </span>

            <br><br>

            <div class="capsule-card">

                <h2 class="capsule-title">
                    Your Secret Letter
                </h2>

                <div class="letter">
                    <%= capsule.getSecretLetter() %>
                </div>

            </div>

            <br>

            <div class="capsule-card">

                <h2 class="capsule-title">
                    Attached Memories
                </h2>

                <%
                    List<MediaFile> mediaFiles =
                            (List<MediaFile>) request.getAttribute("mediaFiles");

                    if (mediaFiles == null || mediaFiles.size() == 0) {
                %>

                    <p>No media uploaded for this capsule.</p>

                <%
                    } else {
                        for (MediaFile media : mediaFiles) {
                            String type = media.getFileType();
                %>

                    <div class="media-box">

                        <p>
                            <b><%= media.getFileName() %></b>
                        </p>

                        <% if (type != null && type.startsWith("image")) { %>

                            <img src="downloadMedia?id=<%= media.getMediaId() %>"
                                 class="media-preview"
                                 alt="Capsule Image">

                        <% } else if (type != null && type.startsWith("audio")) { %>

                            <audio controls class="media-preview">
                                <source src="downloadMedia?id=<%= media.getMediaId() %>"
                                        type="<%= type %>">
                            </audio>

                        <% } else if (type != null && type.startsWith("video")) { %>

                            <video controls class="media-preview video-preview">
                                <source src="downloadMedia?id=<%= media.getMediaId() %>"
                                        type="<%= type %>">
                            </video>

                        <% } else { %>

                            <a href="downloadMedia?id=<%= media.getMediaId() %>"
                               class="view-btn">
                                Download File
                            </a>

                        <% } %>

                    </div>

                <%
                        }
                    }
                %>

            </div>

        <% } %>

        <br>

        <a href="dashboard.jsp" class="view-btn">
            Back to Dashboard
        </a>

    </div>

</section>

<footer class="home-footer">

    <p>
        Made with &#127807; for future memories — MemoryVault
    </p>

</footer>

</body>
</html>