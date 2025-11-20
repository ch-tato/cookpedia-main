<%@ page session="true" %>
<%
    if (session == null || session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer userId = (Integer) session.getAttribute("userId");
    com.cookpedia.dao.UserDAO dao = new com.cookpedia.dao.UserDAO();
    com.cookpedia.model.User user = dao.getUserById(userId);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Edit Profile</title>

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
            text-align: center;
            margin-bottom: 25px;
        }

        .form-label {
            display: block;
            margin-top: 12px;
            font-weight: 600;
        }

        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 12px;
            margin-top: 6px;
            border: 1px solid #ccc;
        }

        .error-msg {
            background: #ffe0e0;
            padding: 12px;
            border-left: 4px solid #d90000;
            margin-bottom: 10px;
            color: #a40000;
        }

        .profile-actions {
            margin-top: 20px;
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

    <h2 class="profile-header">Edit Profile</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="error-msg"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="edit-profile" method="post">
        <label class="form-label">Username</label>
        <input type="text" name="username" value="<%= user.getUsername() %>" required>

        <label class="form-label">Email</label>
        <input type="email" name="email" value="<%= user.getEmail() %>" required>

        <div class="profile-actions">
            <button type="submit" class="btn btn-orange">Save Changes</button>
        </div>
    </form>
</div>
<div class="back-wrapper">
    <a href="profile"><button class="btn-back">Back to Profile</button></a>
</div>


<%@ include file="/WEB-INF/components/footer.jsp" %>

</body>
</html>
