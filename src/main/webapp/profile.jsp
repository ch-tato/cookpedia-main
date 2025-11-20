<%@ page import="com.cookpedia.dao.UserDAO" %>
<%@ page import="com.cookpedia.model.User" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    UserDAO dao = new UserDAO();
    User user = dao.getUserById(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>My Profile - Cookpedia</title>

    <link rel="stylesheet" href="resource/css/style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/nav-footer-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/bg-anim-style.css?v=<%= System.currentTimeMillis() %>">


    <style>
        .profile-container {
            max-width: 900px;
            margin: 120px auto 20px;
            padding: 40px;
            background: var(--brand-gray);
            box-shadow: 0 4px 12px #00000025;
        }

        .profile-header {
            font-size: var(--step-3);
            font-weight: 700;
            color: var(--brand-black);
            margin-bottom: 20px;
            text-align: center;
        }

        .profile-info {
            margin: 20px 0;
            font-size: var(--step-0);
        }

        .profile-info p {
            margin-bottom: 15px;
        }

        .password-field {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .profile-actions {
            margin-top: 25px;
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 12px 22px;
            cursor: pointer;
            font-size: var(--step--1);
            border: none;
            transition: 0.25s;
            font-weight: 600;
        }

        .btn-gray {
            background: var(--brand-silver);
            color: var(--brand-black);
        }
        .btn-gray:hover {
            opacity: 0.7;
        }

        .logout-wrapper {
            max-width: 900px;
            margin: 0 auto 80px;
        }

        .btn-logout {
            width: 100%;
            padding: 15px 0;
            background: #db1f24;
            color: white;
            font-weight: 700;
            font-size: var(--step-0);
            border: none;
            cursor: pointer;
            transition: 0.25s;
        }
        .btn-logout:hover {
            background: #b91a1f;
        }

        .success-msg {
            padding: 10px;
            margin-bottom: 10px;
            background: #d9ffde;
            color: #067a0c;
            text-align: center;
        }
    </style>
</head>

<body>
<%@ include file="/WEB-INF/components/navbar.jsp" %>
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

<div class="profile-container">

    <h2 class="profile-header">Your Profile</h2>

    <% if (request.getAttribute("success") != null) { %>
    <div class="success-msg"><%= request.getAttribute("success") %></div>
    <% } %>

    <div class="profile-info">
        <p><strong>Username:</strong> <%= user.getUsername() %></p>
        <p><strong>Email:</strong> <%= user.getEmail() %></p>

        <p class="password-field">
            <strong>Password:</strong>
            <span id="passwordText">********</span>
        </p>
    </div>

    <div class="profile-actions">
        <a href="edit_profile.jsp" class="btn btn-gray">Edit Profile</a>
        <a href="change_password.jsp" class="btn btn-gray">Change Password</a>
    </div>
</div>

<div class="logout-wrapper">
    <a href="logout">
        <button class="btn-logout">Logout</button>
    </a>
</div>

<%@ include file="/WEB-INF/components/footer.jsp" %>

</body>
</html>
