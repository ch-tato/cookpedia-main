package com.cookpedia.servlet;

import com.cookpedia.dao.RecipeDAO;
import com.cookpedia.dao.IngredientDAO;
import com.cookpedia.dao.RecipeIngredientDAO;
import com.cookpedia.dao.StepDAO;

import com.cookpedia.model.Recipe;

import com.cookpedia.model.Step;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/update-recipe")
@MultipartConfig
public class UpdateRecipeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = (Integer) session.getAttribute("userId");

        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        String recipeName = request.getParameter("recipeName");
        String description = request.getParameter("description");
        int cookingTime = Integer.parseInt(request.getParameter("cookingTime"));
        String difficulty = request.getParameter("difficulty");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Part filePart = request.getPart("image");
        String fileName = null;
        if(filePart != null && filePart.getSize() > 0){
            String contentType = filePart.getContentType();
            if(!contentType.startsWith("image/")){
                request.setAttribute("error", "File must be an image!");
                request.getRequestDispatcher("recipeedit.jsp?id=" + recipeId)
                        .forward(request, response);
                return;
            }

            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = request.getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(uploadPath + File.separator + fileName);
        }

        RecipeDAO recipeDAO;
        try {
            recipeDAO = new RecipeDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error!");
            return;
        }

        Recipe recipe = recipeDAO.getRecipeById(recipeId);
        if(recipe == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recipe not found");
            return;
        }

        recipe.setName(recipeName);
        recipe.setDescription(description);
        recipe.setCookingTime(cookingTime);
        recipe.setDifficulty(difficulty);
        recipe.setCategoryId(categoryId);
        if(fileName != null) recipe.setImageUrl(fileName);
        recipeDAO.updateRecipe(recipe);

        String[] ingredientNames = request.getParameterValues("ingredientName[]");
        String[] quantities = request.getParameterValues("quantity[]");

        IngredientDAO ingDAO = new IngredientDAO();
        RecipeIngredientDAO riDAO = new RecipeIngredientDAO();
        riDAO.deleteIngredientsByRecipeId(recipeId);
        for(int i = 0; i < ingredientNames.length; i++){
            String name = ingredientNames[i].trim();
            String qtyUnit = quantities[i].trim();

            if(name.isEmpty() || qtyUnit.isEmpty()) continue;

            String quantity = "", unit = "";
            int firstSpace = qtyUnit.indexOf(' ');
            if(firstSpace > 0){
                quantity = qtyUnit.substring(0, firstSpace);
                unit = qtyUnit.substring(firstSpace + 1);
            } else {
                quantity = qtyUnit;
            }

            int ingId = ingDAO.insertIngredient(name, unit);
            riDAO.insertRecipeIngredient(recipeId, ingId, qtyUnit);
        }

        String[] instructions = request.getParameterValues("instruction[]");
        String[] stepIds = request.getParameterValues("stepId[]");

        StepDAO stepDAO = new StepDAO();

        List<Step> oldSteps = stepDAO.getStepsByRecipeId(recipeId);
        for (Step oldStep : oldSteps) {
            boolean existsInForm = false;
            if(stepIds != null){
                for (String sid : stepIds) {
                    if (Integer.parseInt(sid) == oldStep.getId()) {
                        existsInForm = true;
                        break;
                    }
                }
            }
            if (!existsInForm) {
                stepDAO.deleteStep(oldStep.getId());
            }
        }

        if (instructions != null) {
            for (int i = 0; i < instructions.length; i++) {
                String instr = instructions[i].trim();
                if (instr.isEmpty()) continue;

                Step s = new Step();
                s.setRecipeId(recipeId);
                s.setInstruction(instr);
                s.setStepNumber(i+1);

                if (stepIds != null && i < stepIds.length && !stepIds[i].isEmpty()) {
                    s.setId(Integer.parseInt(stepIds[i]));
                    stepDAO.updateStep(s);
                } else {
                    stepDAO.insertStep(recipeId, i+1, instr);
                }
            }
        }


        response.sendRedirect(request.getContextPath() + "/my-recipes");
    }
}
