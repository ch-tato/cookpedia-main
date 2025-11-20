package com.cookpedia.servlet;

import com.cookpedia.dao.RecipeDAO;
import com.cookpedia.model.Recipe;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/my-recipes")
public class MyRecipesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("userId") == null){
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = (Integer) session.getAttribute("userId");

        RecipeDAO recipeDAO;

        try {
            recipeDAO = new RecipeDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error!");
            return;
        }

        List<Recipe> recipes = recipeDAO.getRecipesByUserId(userId);
        request.setAttribute("recipes", recipes);

        RequestDispatcher rd = request.getRequestDispatcher("/myrecipes.jsp");
        rd.forward(request, response);
    }
}