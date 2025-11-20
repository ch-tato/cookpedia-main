<%@ page import="java.util.List" %>
<%@ page session="true" %>
<%
    if (request.getSession(false) == null || request.getSession(false).getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Change Password</title>

    <link rel="stylesheet" href="resource/css/style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/bg-anim-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/nav-footer-style.css?v=<%= System.currentTimeMillis() %>">

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
            text-align: center;
            margin-bottom: 25px;
        }

        .error-box {
            background: #ffe0e0;
            padding: 12px;
            border-left: 4px solid #d90000;
            margin-bottom: 12px;
            color: #a40000;
        }

        .form-label {
            display: block;
            margin-top: 12px;
            font-weight: 600;
        }

        input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-top: 6px;
            border: 1px solid #ccc;
        }

        .profile-actions {
            margin-top: 25px;
            display: flex;
            justify-content: center;
        }

        .btn {
            padding: 12px 22px;
            cursor: pointer;
            font-size: var(--step--1);
            font-weight: 600;
            border: none;
            transition: 0.25s;
        }

        /* ORANGE PRIMARY BUTTON */
        .btn-orange {
            background: var(--brand-orange);
            color: white;
        }
        .btn-orange:hover { opacity: 0.85; }

        /* BACK BUTTON */
        .btn-back {
            width: 100%;
            padding: 15px 0;
            background: var(--brand-silver);
            color: black;
            font-weight: 700;
            font-size: var(--step-0);
            border: none;
            cursor: pointer;
            transition: 0.25s;
        }
        .btn-back:hover { opacity: 0.75; }

        .back-wrapper {
            max-width: 900px;
            margin: 0 auto 80px;
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

    <h2 class="profile-header">Change Password</h2>

    <%
        List<String> errors = (List<String>) request.getAttribute("errors");
        if (errors != null) {
            for (String e : errors) {
    %>
    <div class="error-box"><%= e %></div>
    <%
            }
        }
    %>

    <form action="change-password" method="post">

        <label class="form-label">Old Password</label>
        <input type="password" name="oldPassword" required>

        <label class="form-label">New Password</label>
        <input type="password" name="newPassword" required>

        <label class="form-label">Confirm New Password</label>
        <input type="password" name="confirmPassword" required>

        <div class="profile-actions">
            <button type="submit" class="btn btn-orange">Update Password</button>
        </div>
    </form>
</div>
<div class="back-wrapper">
    <a href="profile"><button class="btn-back">Back to Profile</button></a>
</div>

<%@ include file="/WEB-INF/components/footer.jsp" %>

</body>
</html>
