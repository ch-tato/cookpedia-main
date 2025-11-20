<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.cookpedia.dao.RecipeDAO" %>
<%@ page import="com.cookpedia.model.Recipe" %>
<%@ page import="java.util.*" %>

<%
    RecipeDAO recipeDAO = null;
    List<Recipe> allRecipes = null;

    try {
        recipeDAO = new RecipeDAO();
        allRecipes = recipeDAO.getAllRecipes();
    } catch (Exception e) {
        e.printStackTrace();
    }

    Map<Integer, Integer> upvoteMap = new HashMap<>();

    if (allRecipes != null) {
        for (Recipe r : allRecipes) {
            upvoteMap.put(r.getId(), recipeDAO.getTotalUpvotes(r.getId()));
        }
    }

    if (allRecipes != null) {
        Collections.sort(allRecipes, new Comparator<Recipe>() {
            public int compare(Recipe a, Recipe b) {
                int upA = upvoteMap.get(a.getId());
                int upB = upvoteMap.get(b.getId());
                return Integer.compare(upB, upA);
            }
        });
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cookpedia</title>
    <link rel="stylesheet" href="resource/css/style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/nav-footer-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/card-style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="resource/css/index-style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

    <%@ include file="/WEB-INF/components/navbar.jsp" %>
    <section class="header">
        <div class="header-parent">
            <div class="header1">
                <h1>CookPedia</h1>
            </div>
            <div class="header-plate">
                <img src="resource/img/plate.png" alt="tuna-salad">
                <div class="circle sliding-element"></div>
            </div>
            <div class="header2">
                <p>Get ready to cook and create! Dive into our extensive recipe library
                    and find inspiration for your next meal, from weeknight dinners to
                    special occasion treats. Explore the world of flavor.
                    Ready to share your own unique dishes with fellow food enthusiasts?
                    Simply Log In above and start adding your favorite recipes today.
                </p>
                <a href="#popular">
                    <button class="button-discover" type="button">
                        Discover Recipes
                    </button>
                </a>
                 <a href="<%= request.getContextPath() %>/login">
                    <button class="button-login" type="button">
                        Login
                    </button>
                </a>
            </div>
        </div>
    </section>

    <section id="popular" class="popular">
		<h2>Popular Recipes</h2>
		<div class="card-parent">
		<%
            if (allRecipes == null || allRecipes.isEmpty()) {
        %>
                <p>No recipe available.</p>
        <%
            } else {
                int counter = 0;
                for (Recipe r : allRecipes) {

                    if (counter >= 6) break;
                    counter++;

                    int up = recipeDAO.getTotalUpvotes(r.getId());
                    int down = recipeDAO.getTotalDownvotes(r.getId());
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

		<a href="search.jsp" class="more-btn">
		    <div>Discover more recipes</div>
		</a>
	</section>
    <%@ include file="/WEB-INF/components/footer.jsp" %>
    <script src="resource/js/script.js"></script>

</body>
</html>
