package com.cookpedia.servlet;

import com.cookpedia.dao.RecipeVoteDAO;
import com.cookpedia.model.RecipeVote;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        String type = request.getParameter("type");

        RecipeVoteDAO voteDao = new RecipeVoteDAO();
        RecipeVote existing = voteDao.getVoteByUserAndRecipe(userId, recipeId);

        if (existing == null) {
            RecipeVote v = new RecipeVote();
            v.setUserId(userId);
            v.setRecipeId(recipeId);
            v.setType(type);
            voteDao.insertVote(v);
        } else {
            if (existing.getType().equals(type)) {
                voteDao.deleteVote(existing.getId());
            } else {
                existing.setType(type);
                voteDao.updateVote(existing);
            }
        }

        response.sendRedirect("recipedetail.jsp?id=" + recipeId);
    }
}
