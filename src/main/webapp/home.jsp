<%--
  Created by IntelliJ IDEA.
  User: MQ DANISH A
  Date: 11/12/2025
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.cookpedia.dao.RecipeDAO" %>
<%@ page import="com.cookpedia.dao.UserDAO" %>
<%@ page import="com.cookpedia.dao.CategoryDAO" %>
<%@ page import="com.cookpedia.model.User" %>
<%@ page import="com.cookpedia.model.Recipe" %>
<%@ page import="com.cookpedia.model.Category" %>
<%@ page import="java.util.List" %>

<%
    RecipeDAO recipeDao = new RecipeDAO();
    List<Recipe> allRecipes = recipeDao.getAllRecipes();

    String searchQuery = request.getParameter("search");
     if(searchQuery != null && !searchQuery.trim().isEmpty()){
            allRecipes = recipeDao.searchRecipesByTitle(searchQuery.trim());
     } else {
            allRecipes = recipeDao.getAllRecipes();
     }

    String sort = request.getParameter("sort");
    if(searchQuery != null && !searchQuery.trim().isEmpty()){
        allRecipes = recipeDao.searchRecipesByTitle(searchQuery.trim());
    } else {
        allRecipes = recipeDao.getAllRecipes();
    }

    if(sort != null) {
        switch(sort) {
            case "ASC":
                allRecipes.sort((a,b) -> a.getName().compareToIgnoreCase(b.getName()));
                break;
            case "DESC":
                allRecipes.sort((a,b) -> b.getName().compareToIgnoreCase(a.getName()));
                break;
            case "UPVOTE":
                allRecipes.sort((a,b) -> recipeDao.getTotalUpvotes(b.getId()) - recipeDao.getTotalUpvotes(a.getId()));
                break;
        }
    }
%>

<%
    CategoryDAO categoryDao = new CategoryDAO();
    List<Category> categories = categoryDao.getAllCategories();

    String categoryFilter = request.getParameter("category");

    if(categoryFilter != null && !categoryFilter.isEmpty()){
        int catIdFilter = Integer.parseInt(categoryFilter);
        allRecipes.removeIf(r -> r.getCategoryId() != catIdFilter);
    }
%>


<%@ page session="true" %>
<%
    HttpSession sessionObj = request.getSession(false);
    String username = null;

    if (sessionObj != null) {
        username = (String) sessionObj.getAttribute("Username");
    }

    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Cookpedia</title>
</head>
<body>
<h1>Hello, <%= username %></h1>
<p>This is Home Page</p>
<a href="add-recipe">Add New Recipe</a><br><br>
<a href="my-recipes">My Recipes</a><br><br>
<a href="profile.jsp">Profile</a>

<h2>All Recipes</h2>

<form method="get" action="home.jsp" style="margin-bottom:20px;">
    <input type="text" name="search" placeholder="Search recipes by title..."
           value="<%= searchQuery != null ? searchQuery : "" %>">
    <button type="submit">Search</button>
    <% if(searchQuery != null && !searchQuery.trim().isEmpty()){ %>
        <a href="home.jsp" style="margin-left:10px; padding:4px 8px; background:#ccc; color:black; text-decoration:none; border-radius:4px;">
            Cancel
        </a>
    <% } %>
</form>

<form method="get" action="home.jsp" style="margin-bottom:20px;">
    <input type="hidden" name="search" value="<%= searchQuery != null ? searchQuery : "" %>">
    <select name="sort" onchange="this.form.submit()">
        <option value="">-- Sort Recipes --</option>
        <option value="ASC" <%= "ASC".equals(sort) ? "selected" : "" %>>Recipe A-Z</option>
        <option value="DESC" <%= "DESC".equals(sort) ? "selected" : "" %>>Recipe Z-A</option>
        <option value="UPVOTE" <%= "UPVOTE".equals(sort) ? "selected" : "" %>>Most Upvoted</option>
    </select>
</form>

<form method="get" action="home.jsp" style="margin-bottom:20px;">
    <input type="hidden" name="search" value="<%= searchQuery != null ? searchQuery : "" %>">
    <input type="hidden" name="sort" value="<%= sort != null ? sort : "" %>">
    <select name="category" onchange="this.form.submit()">
        <option value="">-- All Categories --</option>
        <% for(Category c : categories) { %>
            <option value="<%= c.getId() %>"
                <%= (categoryFilter != null && categoryFilter.equals(String.valueOf(c.getId()))) ? "selected" : "" %>>
                <%= c.getName() %>
            </option>
        <% } %>
    </select>
</form>

<% if (allRecipes != null && !allRecipes.isEmpty()) { %>
    <ul>
    <% UserDAO userDao = new UserDAO(); %>

    <% for (Recipe r : allRecipes) {
        int up = recipeDao.getTotalUpvotes(r.getId());
        int down = recipeDao.getTotalDownvotes(r.getId());
        User recipeOwner = userDao.getUserById(r.getUserId());
    %>

    <li style="margin-bottom: 20px;">
        <a href="recipe-detail.jsp?id=<%= r.getId() %>">
            <strong><%= r.getName() %></strong>
        </a><br>

        <% if (r.getImageUrl() != null && !r.getImageUrl().isEmpty()) { %>
            <img src="<%= request.getContextPath() %>/uploads/<%= r.getImageUrl() %>"
                 style="width:150px; border-radius: 8px;">
        <% } else { %>
            <em>No image</em>
        <% } %>

        <br>
        <%= r.getDescription() %><br>

        <strong>By: <%= recipeOwner != null ? recipeOwner.getUsername() : "Unknown" %></strong><br>

        <span style="color:green; font-weight:bold;">Upvotes: <%= up %></span> |
        <span style="color:red; font-weight:bold;">Downvotes: <%= down %></span>
    </li>

    <% } %>
    </ul>
<% } else { %>
    <p>No recipes available.</p>
<% } %>

</body>
</html>
