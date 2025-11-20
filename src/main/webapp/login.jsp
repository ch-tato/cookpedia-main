<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cookpedia</title>
    <link rel="stylesheet" href="resource/css/style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/register-login-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/bg-anim-style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>
    <div class="area" >
        <ul class="circles">
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div >
    <div class="form-parent">
        <button type="button" onclick="history.back()" class="back-arrow">Back</button>
        <h1>Login</h1>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
        <div style="color: red; margin-bottom: 10px;">
            <%= error %>
        </div>
        <% } %>
        <form action="login" method="post">
            <input type="email" name="email" placeholder="Email" required><br>
            <input type="password" name="password" placeholder="Password" required><br>
            <button type="submit" class="submit">Login</button>
        </form>
        <p>Don't have an account?<a href="register.jsp">Register</a></p>
    </div>
</body>
</html>