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

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cookpedia</title>
    <link rel="stylesheet" href="resource/css/style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/search-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/card-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/nav-footer-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/bg-anim-style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>
    <%@ include file="/WEB-INF/components/navbar.jsp" %>
    <div class="circle-background"></div>
    <section class="filter-section">
        <h1>Search Recipes</h1>
        <div class="filter-bar">
            <div class="top-filter">
                <form method="get" action="search.jsp">
                    <input type="text" name="search" placeholder="Search recipes by title..." value="<%= searchQuery != null ? searchQuery : "" %>">
                    <button type="submit">Search</button>
                </form>
            </div>
            <div class="bottom-filter">
                <form method="get" action="search.jsp">
                    <input type="hidden" name="search" value="<%= searchQuery != null ? searchQuery : "" %>">
                    <select name="sort" onchange="this.form.submit()">
                        <option value="">-- Sort Recipes --</option>
                        <option value="ASC" <%= "ASC".equals(sort) ? "selected" : "" %>>Recipe A-Z</option>
                        <option value="DESC" <%= "DESC".equals(sort) ? "selected" : "" %>>Recipe Z-A</option>
                        <option value="UPVOTE" <%= "UPVOTE".equals(sort) ? "selected" : "" %>>Most Upvoted</option>
                    </select>
                </form>
                <form method="get" action="search.jsp">
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
            </div>
        </div>
    </section>
    <section class="recipes">
        <div class="card-parent">
        <%
            if (allRecipes == null || allRecipes.isEmpty()) {
        %>
                <p>No recipe available.</p>
        <%
            } else {
                for (Recipe r : allRecipes) {
                    int up = recipeDao.getTotalUpvotes(r.getId());
                    int down = recipeDao.getTotalDownvotes(r.getId());
        %>
            <div class="card-hover">
                <div class="card-hover__placeholder">
                    <% if(r.getImageUrl() != null && !r.getImageUrl().isEmpty()){ %>
                        <img src="uploads/<%= r.getImageUrl() %>" alt="<%= r.getName() %>">
                    <% } else { %>
                        <img src="uploads/no-image.png" alt="No image">
                    <% } %>
                </div>
                <div class="card-hover__content">
                    <a href="recipedetail.jsp?id=<%= r.getId() %>" class="card-hover__link">
                        <div>START <span>COOKING</span>!</div>
                    </a>
                    <h3 class="card-hover__title"><%=r.getName()%></h3>
                    <p class="card-hover__chip" style="background-color: var(--brand-gray);">difficulty: <%=r.getDifficulty()%></p>
                    <p class="card-hover__chip" style="background-color: var(--brand-gray);">Time: <%=r.getCookingTime()%> min</p>
                    <p class="card-hover__chip" style="background-color: var(--brand-gray);">vote: <%= up - down %></p>
                    <p class="card-hover__text"><%=r.getDescription()%></p>
                </div>
            </div>
        <%
                }
            }
        %>
        </div>
    </section>

    <%@ include file="/WEB-INF/components/footer.jsp" %>
    <script src="resource/js/script.js"></script>
</body>
</html>
