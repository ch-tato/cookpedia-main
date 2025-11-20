<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cookpedia.model.Category" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cookpedia</title>
    <link rel="stylesheet" href="resource/css/style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/recipeadd-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/nav-footer-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/bg-anim-style.css?v=<%= System.currentTimeMillis() %>">
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

    <div class="container">
        <h2 class="title">Add New Recipe</h2>

        <form action="${pageContext.request.contextPath}/add-recipe"
              method="post" enctype="multipart/form-data" class="form-card">
            <div class="input-group">
                <label>Recipe Name</label>
                <input type="text" name="recipeName" required>
            </div>

            <div class="input-group">
                <label>Description</label>
                <textarea name="description" rows="4" required></textarea>
            </div>

            <div class="input-group">
                <label>Cooking Time (minutes)</label>
                <input type="number" min="0" name="cookingTime" required>
            </div>

            <div class="input-group">
                <label>Difficulty Level</label>
                <select name="difficulty" required>
                    <option value="Easy">Easy</option>
                    <option value="Medium">Medium</option>
                    <option value="Hard">Hard</option>
                </select>
            </div>

            <div class="input-group">
                <label>Category</label>
                <select name="categoryId" required>
                    <%
                        List<Category> categories = (List<Category>) request.getAttribute("categories");
                        if (categories != null) {
                            for (Category c : categories) {
                    %>
                    <option value="<%= c.getId() %>"><%= c.getName() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <div class="input-group">
                <div id="dropzone" class="dropzone">
                    <p>Drag & Drop image here, or click to upload</p>
                    <img id="previewImg" style="display:none;" />
                </div>

                <input type="file" id="imageInput" name="image" accept="image/*" style="display:none;">

                <button type="button" id="cancelImageBtn" class="cancel-btn" style="display:none;">
                    Remove Image
                </button>
            </div>

            <h3 class="subtitle">Ingredients</h3>
            <div id="ingredients">
                <div class="ingredient-row">
                    <input type="text" name="ingredientName" placeholder="Ingredient name" required />
                    <input type="text" name="quantity" placeholder="Quantity (ex: 100 grams)" required />
                </div>
            </div>
            <button type="button" class="add-btn" onclick="addIngredient()">+ Add Ingredient</button>

            <h3 class="subtitle">Steps</h3>
            <div id="steps">
                <div class="step-row">
                    <textarea name="instruction" placeholder="Step instruction" required></textarea>
                </div>
            </div>
            <button type="button" class="add-btn" onclick="addStep()">+ Add Step</button>

            <button class="submit-btn" type="submit">Save Recipe</button>
            <button type="button" onclick="history.back()" class="back-arrow">Back</button>
        </form>
    </div>

    <%@ include file="/WEB-INF/components/footer.jsp" %>
    <script src="resource/js/script.js"></script>
    <script src="resource/js/recipeadd.js"></script>
</body>
</html>
