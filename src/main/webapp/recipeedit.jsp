<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.cookpedia.model.Recipe" %>
<%@ page import="com.cookpedia.model.DetailIngredient" %>
<%@ page import="com.cookpedia.model.Step" %>
<%@ page import="com.cookpedia.dao.RecipeDAO" %>
<%@ page import="com.cookpedia.dao.IngredientDAO" %>
<%@ page import="com.cookpedia.dao.RecipeIngredientDAO" %>
<%@ page import="com.cookpedia.dao.StepDAO" %>
<%@ page import="com.cookpedia.dao.CategoryDAO" %>
<%@ page import="com.cookpedia.model.Category" %>
<%@ page import="java.util.List" %>

<%
    if(session == null || session.getAttribute("userId") == null){
        response.sendRedirect("login.jsp");
        return;
    }

    String recipeIdStr = request.getParameter("id");
    if(recipeIdStr == null){
        out.println("Invalid Recipe ID");
        return;
    }

    int recipeId = Integer.parseInt(recipeIdStr);

    RecipeDAO recipeDao = new RecipeDAO();
    Recipe recipe = recipeDao.getRecipeById(recipeId);
    if(recipe == null){
        out.println("Recipe not found!");
        return;
    }

    CategoryDAO catDao = new CategoryDAO();
    List<Category> categories = catDao.getAllCategories();

    IngredientDAO ingDao = new IngredientDAO();
    List<DetailIngredient> ingredients = ingDao.getIngredientDetailsByRecipeId(recipeId);

    StepDAO stepDao = new StepDAO();
    List<Step> steps = stepDao.getStepsByRecipeId(recipeId);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Recipe - <%= recipe.getName() %></title>
    <link rel="stylesheet" href="resource/css/style.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="resource/css/nav-footer-style.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="resource/css/bg-anim-style.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="resource/css/recipeadd-style.css?v=<%=System.currentTimeMillis()%>">
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
        <h2 class="title">Edit Recipe: <%= recipe.getName() %></h2>

        <form action="update-recipe" method="post" enctype="multipart/form-data" class="form-card">
            <input type="hidden" name="recipeId" value="<%= recipe.getId() %>">

            <div class="input-group">
                <label>Recipe Name</label>
                <input type="text" name="recipeName" value="<%= recipe.getName() %>" required>
            </div>

            <div class="input-group">
                <label>Description</label>
                <textarea name="description" rows="4" required><%= recipe.getDescription() %></textarea>
            </div>

            <div class="input-group">
                <label>Cooking Time (minutes)</label>
                <input type="number" name="cookingTime" value="<%= recipe.getCookingTime() %>" required>
            </div>

            <div class="input-group">
                <label>Difficulty</label>
                <select name="difficulty" required>
                    <option value="Easy" <%= recipe.getDifficulty().equals("Easy") ? "selected" : "" %>>Easy</option>
                    <option value="Medium" <%= recipe.getDifficulty().equals("Medium") ? "selected" : "" %>>Medium</option>
                    <option value="Hard" <%= recipe.getDifficulty().equals("Hard") ? "selected" : "" %>>Hard</option>
                </select>
            </div>

            <div class="input-group">
                <label>Category</label>
                <select name="categoryId" required>
                    <% for (Category c : categories) { %>
                        <option value="<%= c.getId() %>"
                            <%= c.getId() == recipe.getCategoryId() ? "selected" : "" %>>
                            <%= c.getName() %>
                        </option>
                    <% } %>
                </select>
            </div>

            <div class="input-group">
                <label>Change Image</label>

                <div id="dropzone" class="dropzone">
                    <% if (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) { %>
                        <img id="previewImg" src="uploads/<%= recipe.getImageUrl() %>" />
                    <% } else { %>
                        <p>Drag & Drop image here, or click to upload</p>
                        <img id="previewImg" style="display:none;" />
                    <% } %>
                </div>

                <input type="file" id="imageInput" name="image" accept="image/*" style="display:none;">

                <button type="button" id="cancelImageBtn"
                        class="cancel-btn"
                        style="<%= (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) ? "" : "display:none;" %>">
                    Remove Image
                </button>
            </div>

            <h3 class="subtitle">Ingredients</h3>
                <div id="ingredients">

                    <% if (ingredients != null && !ingredients.isEmpty()) {
                        for (DetailIngredient ing : ingredients) { %>

                            <div class="ingredient-row">
                                <input type="text" name="ingredientName[]" value="<%= ing.getName() %>" required />
                                <input type="text" name="quantity[]" value="<%= ing.getQuantity() %>" required />
                                <button type="button" class="remove-btn" onclick="this.parentElement.remove()">X</button>
                            </div>

                    <% }} else { %>

                        <div class="ingredient-row">
                            <input type="text" name="ingredientName[]" placeholder="Ingredient name" required />
                            <input type="text" name="quantity[]" placeholder="Quantity (ex: 2 cups)" required />
                        </div>

                    <% } %>

                </div>

                <button type="button" class="add-btn" onclick="addIngredient()">+ Add Ingredient</button>

                <h3 class="subtitle">Steps</h3>
                <div id="steps">

                    <% if (steps != null && !steps.isEmpty()) {
                        for (Step st : steps) { %>
                            <div class="step-row">
                                <input type="hidden" name="stepId[]" value="<%= st.getId() %>" />
                                <textarea name="instruction[]" required><%= st.getInstruction() %></textarea>
                                <button type="button" class="remove-btn" onclick="this.parentElement.remove()">X</button>
                            </div>

                    <% }} else { %>

                        <div class="step-row">
                            <textarea name="instruction[]" placeholder="Step instruction" required></textarea>
                        </div>

                    <% } %>

                </div>

                <button type="button" class="add-btn" onclick="addStep()">+ Add Step</button>

            <button class="submit-btn" type="submit">Update Recipe</button>
            <button type="button" onclick="history.back()" class="back-arrow">Back</button>
        </form>
    </div>

    <%@ include file="/WEB-INF/components/footer.jsp" %>
    <script src="resource/js/script.js"></script>
    <script src="resource/js/recipeadd.js"></script>
    <script src="resource/js/recipeedit.js"></script>
</body>
</html>
