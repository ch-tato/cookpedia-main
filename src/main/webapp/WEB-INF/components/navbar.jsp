<%
    String usn = (String) session.getAttribute("Username");
    boolean isLoggedIn = (usn != null);
%>

<nav class="navbar">
    <div class="logo">
        <img src="resource/img/5526265.jpg" alt="logo" />
        <a href="<%= request.getContextPath() %>/">
            <h1>CookPedia</h1>
        </a>
    </div>
    <ul>
        <li><a href="<%= request.getContextPath() %>/search.jsp">Search</a></li>
        <% if (!isLoggedIn) { %>
            <li><a href="<%= request.getContextPath() %>/login">Login</a></li>
            <li><a href="<%= request.getContextPath() %>/add-recipe">New Recipe</a></li>
        <% } else { %>
            <li><a href="<%= request.getContextPath() %>/my-recipes">My Recipes</a></li>
            <li><a href="<%= request.getContextPath() %>/add-recipe">New Recipe</a></li>
            <li><a href="<%= request.getContextPath() %>/profile">Profile</a></li>
        <% } %>
    </ul>
    <div class="hamburger">
        <span class="line"></span>
        <span class="line"></span>
        <span class="line"></span>
    </div>
</nav>
<div class="menubar">
    <ul>
        <li><a href="<%= request.getContextPath() %>/search.jsp">Search</a></li>
        <% if (!isLoggedIn) { %>
            <li><a href="<%= request.getContextPath() %>/login">Login</a></li>
            <li><a href="<%= request.getContextPath() %>/add-recipe">New Recipe</a></li>
        <% } else { %>
            <li><a href="<%= request.getContextPath() %>/my-recipes">My Recipes</a></li>
            <li><a href="<%= request.getContextPath() %>/add-recipe">New Recipe</a></li>
            <li><a href="<%= request.getContextPath() %>/profile">Profile</a></li>
        <% } %>
    </ul>
</div>