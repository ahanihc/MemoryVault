<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - MemoryVault</title>

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
                <li><a href="index.jsp">Home</a></li>
                <li><a href="login.jsp" class="nav-btn">Login</a></li>
                <li><a href="register.jsp">Register</a></li>
            </ul>
        </nav>

    </div>

</header>

<section class="auth-container">

    <div class="auth-card">

        <h1 class="auth-title">
            Welcome Back
        </h1>

        <% if (request.getParameter("error") != null) { %>
            <p class="error">
                Invalid email or password.
            </p>
        <% } %>

        <% if (request.getParameter("success") != null) { %>
            <p class="success">
                Registration successful. Please login.
            </p>
        <% } %>

        <form action="login" method="post">

            <div class="input-group">

                <label for="email">
                    Email
                </label>

                <input type="email"
                       id="email"
                       name="email"
                       placeholder="Enter your email"
                       required>

            </div>

            <div class="input-group">

                <label for="password">
                    Password
                </label>

                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Enter your password"
                       required>

            </div>

            <button type="submit" class="auth-btn">
                Login
            </button>

        </form>

        <div class="auth-link">

            Don't have an account?

            <a href="register.jsp">
                Register here
            </a>

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