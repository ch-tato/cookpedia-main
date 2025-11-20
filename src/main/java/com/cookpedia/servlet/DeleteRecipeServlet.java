package com.cookpedia.servlet;

import com.cookpedia.dao.RecipeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-recipe")
    public class DeleteRecipeServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            int id = Integer.parseInt(request.getParameter("id"));

            RecipeDAO recipeDAO;
            try {
                recipeDAO = new RecipeDAO();
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error!");
                return;
            }

            recipeDAO.deleteRecipe(id);

            response.sendRedirect(request.getContextPath() + "/my-recipes");
        }
    }
