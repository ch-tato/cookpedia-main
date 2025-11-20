package com.cookpedia.servlet;

import com.cookpedia.dao.CategoryDAO;
import com.cookpedia.dao.RecipeDAO;
import com.cookpedia.dao.IngredientDAO;
import com.cookpedia.dao.RecipeIngredientDAO;
import com.cookpedia.dao.StepDAO;

import com.cookpedia.model.Category;
import com.cookpedia.model.Ingredient;
import com.cookpedia.model.Recipe;
import com.cookpedia.model.Step;
import com.cookpedia.model.RecipeIngredient;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/add-recipe")
@MultipartConfig
public class AddRecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.getAllCategories();
        request.setAttribute("categories", categories);

        RequestDispatcher rd = request.getRequestDispatcher("recipeadd.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String recipeName = request.getParameter("recipeName");
        String description = request.getParameter("description");
        String cookingTimeStr = request.getParameter("cookingTime");
        String difficulty = request.getParameter("difficulty");
        String categoryIdStr = request.getParameter("categoryId");

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = (Integer) session.getAttribute("userId");

        if(recipeName == null || recipeName.isEmpty()){
            request.setAttribute("error", "Recipe name is empty");
            request.getRequestDispatcher("recipeadd.jsp").forward(request, response);
            return;
        }

        int cookingTime = 0;
        int categoryId = 0;

        try {
            cookingTime = Integer.parseInt(cookingTimeStr);
            categoryId = Integer.parseInt(categoryIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number input");
            request.getRequestDispatcher("recipeadd.jsp").forward(request, response);
            return;
        }

        Part filePart = request.getPart("image");
        String fileName = "";
        if(filePart != null && filePart.getSize() > 0){
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String uploadPath = request.getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()){
                boolean created = uploadDir.mkdirs();
                if(!created){
                    throw new IOException("Failed to create upload directory");
                }
            }
            filePart.write(uploadPath + File.separator + fileName);
        }

        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        recipe.setDescription(description);
        recipe.setCookingTime(cookingTime);
        recipe.setDifficulty(difficulty);
        recipe.setCategoryId(categoryId);
        recipe.setUserId(userId);
        recipe.setImageUrl(fileName);

        RecipeDAO recipeDAO;
        try {
            recipeDAO = new RecipeDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error!");
            return;
        }

        int recipeId = recipeDAO.insertRecipe(recipe);
        if (recipeId == 0){
            throw new ServletException("Failed to insert recipe");
        }
        String[] ingredientNames = request.getParameterValues("ingredientName");
        String[] quantities = request.getParameterValues("quantity");

        IngredientDAO ingDAO = new IngredientDAO();
        RecipeIngredientDAO riDAO = new RecipeIngredientDAO();

        if(ingredientNames != null && quantities != null){
            for(int i = 0; i < ingredientNames.length; i++){
                String name = ingredientNames[i].trim();
                String inputQty = quantities[i].trim();
                if(name.isEmpty() || inputQty.isEmpty()) continue;

                String quantity = "";
                String unit = "";
                int firstSpace = inputQty.indexOf(' ');
                if(firstSpace > 0){
                    quantity = inputQty.substring(0, firstSpace);
                    unit = inputQty.substring(firstSpace + 1);
                } else {
                    quantity = inputQty;
                    unit = "";
                }

                int ingredientId = ingDAO.insertIngredient(name, unit);
                if (ingredientId == 0){
                    throw new ServletException("Failed to insert ingredient: "  + name);
                }
                String qty = quantity + " " + unit;
                riDAO.insertRecipeIngredient(recipeId, ingredientId, qty);
            }
        }

        String[] steps = request.getParameterValues("instruction");
        StepDAO stepDAO = new StepDAO();
        if(steps != null){
            for(int i = 0; i < steps.length; i++){
                String instruction = steps[i].trim();
                if(instruction.isEmpty()) continue;
                stepDAO.insertStep(recipeId, i+1, instruction);

            }
        }
        response.sendRedirect(request.getContextPath() + "/my-recipes");
    }
}
