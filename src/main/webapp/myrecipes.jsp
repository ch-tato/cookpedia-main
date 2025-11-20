<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.cookpedia.model.Recipe" %>
<%@ page import="com.cookpedia.dao.RecipeDAO"%>
<%@ page import="com.cookpedia.dao.RecipeVoteDAO"%>
<%@ page import="com.cookpedia.dao.UserDAO" %>
<%@ page import="com.cookpedia.model.User" %>

<%@ page session="true" %>
<%
    HttpSession sessionObj = request.getSession(false);

    Integer userId = (sessionObj != null)
                     ? (Integer) sessionObj.getAttribute("userId")
                     : null;

    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String username = (String) sessionObj.getAttribute("Username");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cookpedia</title>
    <link rel="stylesheet" href="resource/css/style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/nav-footer-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/myrecipes-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/card-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/bg-anim-style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

    <%@ include file="/WEB-INF/components/navbar.jsp" %>

    <div class="circle-background"></div>
    <section class="my-recipes-section">
        <div class="page-header">
            <h1>Hello <%= username %></h1>
            <p class="subtitle">Here are the recipes you've added</p>
            <a href="add-recipe">
                <div class="add-btn-wrapper"> Add New Recipe + </div>
            </a>
        </div>

        <div class="card-parent">

            <%
                List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
                RecipeVoteDAO voteDao = new RecipeVoteDAO();

                if(recipes != null && !recipes.isEmpty()){
                    for(Recipe r : recipes){
                        int up = voteDao.countUpvotes(r.getId());
                        int down = voteDao.countDownvotes(r.getId());
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
                        <div>VIEW <span>RECIPE</span></div>
                    </a>
                    <h3 class="card-hover__title"><%=r.getName()%></h3>
                    <p class="card-hover__chip" style="background-color: var(--brand-gray);">difficulty: <%=r.getDifficulty()%></p>
                    <p class="card-hover__chip" style="background-color: var(--brand-gray);">Time: <%=r.getCookingTime()%> min</p>
                    <p class="card-hover__chip" style="background-color: var(--brand-gray);">vote: <%= up - down %></p>
                    <p class="card-hover__text"><%=r.getDescription()%></p>
                </div>
                <div class="card-actions">
                    <a href="recipeedit.jsp?id=<%= r.getId() %>" class="btn-edit">Edit</a>
                    <a href="<%= request.getContextPath() %>/delete-recipe?id=<%= r.getId() %>"
                       onclick="return confirm('Are you sure?');" class="btn-delete">
                       Delete
                    </a>
                </div>
            </div>

            <%
                    }
                } else {
            %>
                <p class="no-recipes">You haven't added any recipes yet.</p>
            <%
                }
            %>

        </div>

        <div class="back-wrapper">
            <a href="#" class="back-link">
            <p>▴</p>
            <p>Back To Top</p>
            </a>
        </div>

    </section>

    <%@ include file="/WEB-INF/components/footer.jsp" %>
    <script src="resource/js/script.js"></script>

</body>

</html>
