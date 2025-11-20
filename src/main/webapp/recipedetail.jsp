<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.cookpedia.dao.RecipeDAO" %>
<%@ page import="com.cookpedia.dao.StepDAO" %>
<%@ page import="com.cookpedia.dao.IngredientDAO" %>
<%@ page import="com.cookpedia.dao.UserDAO" %>

<%@ page import="com.cookpedia.model.Recipe" %>
<%@ page import="com.cookpedia.model.Step" %>
<%@ page import="com.cookpedia.model.DetailIngredient" %>
<%@ page import="com.cookpedia.model.User" %>

<%@ page import="java.util.List" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String idParam = request.getParameter("id");
    if (idParam == null) {
        out.println("Invalid Recipe ID");
        return;
    }

    int recipeId = Integer.parseInt(idParam);


    RecipeDAO recipeDao = new RecipeDAO();
    StepDAO stepDao = new StepDAO();
    IngredientDAO ingredientDao = new IngredientDAO();

    Recipe recipe = recipeDao.getRecipeById(recipeId);
    List<Step> steps = stepDao.getStepsByRecipeId(recipeId);
    List<DetailIngredient> ingredients = ingredientDao.getIngredientDetailsByRecipeId(recipeId);

    UserDAO userDao = new UserDAO();
    User owner = userDao.getUserById(recipe.getUserId());


    if (recipe == null) {
        out.println("Recipe not found!");
        return;
    }

    int upvotes = recipeDao.getTotalUpvotes(recipeId);
    int downvotes = recipeDao.getTotalDownvotes(recipeId);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cookpedia - <%= recipe.getName() %></title>
    <link rel="stylesheet" href="resource/css/style.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="resource/css/nav-footer-style.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="resource/css/bg-anim-style.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="resource/css/recipedetail-style.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>
    <%@ include file="/WEB-INF/components/navbar.jsp" %>

    <div class="area">
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
    </div>

    <div class="container">
        <h2 class="title"><%= recipe.getName() %></h2>
        <div class="image_placeholder">
            <% if (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) { %>
                <img src="<%= request.getContextPath() %>/uploads/<%= recipe.getImageUrl() %>">
            <% } %>
        </div>

        <div class="info_container">
            <p><strong>Created By:</strong> <%= owner != null ? owner.getUsername() : "Unknown" %></p>

            <form action="<%= request.getContextPath() %>/vote" method="post" style="margin-bottom:20px;">
                <input type="hidden" name="recipeId" value="<%= recipeId %>">
                <button type="submit" name="type" value="Upvote" class="add-btn" style="background:var(--brand-green);">Upvote (<%= upvotes %>)</button>
                <button type="submit" name="type" value="Downvote" class="add-btn" style="background:var(--brand-orange);">Downvote (<%= downvotes %>)</button>
            </form>

            <p><%= recipe.getDescription() %></p>
        </div>

        <h3 class="subtitle">Ingredients</h3>
        <div class="ingredients-list">
            <% if (ingredients != null && !ingredients.isEmpty()) { %>
                <% for (DetailIngredient ing : ingredients) { %>
                    <div class="ingredient-row">
                        <span><%= ing.getQuantity() %> — <%= ing.getName() %></span>
                    </div>
                <% } %>
            <% } else { %>
                <p>No ingredients listed.</p>
            <% } %>
        </div>

        <h3 class="subtitle">Steps</h3>
        <div class="steps-list">
            <% if (steps != null && !steps.isEmpty()) { %>
                <% for (Step s : steps) { %>
                    <div class="step-row">
                        <span><%= s.getInstruction() %></span>
                    </div>
                <% } %>
            <% } else { %>
                <p>No steps listed.</p>
            <% } %>
        </div>

        <button type="button" onclick="history.back()" class="back-arrow">Back</button>
    </div>

    <%@ include file="/WEB-INF/components/footer.jsp" %>
    <script src="resource/js/script.js"></script>
</body>
</html>