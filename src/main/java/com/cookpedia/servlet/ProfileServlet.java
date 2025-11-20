package com.cookpedia.servlet;

import com.cookpedia.dao.UserDAO;
import com.cookpedia.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session==null || session.getAttribute("userId")==null){
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer)session.getAttribute("userId");
        UserDAO dao = new UserDAO();
        User user = dao.getUserById(userId);
        if(user==null){
            response.sendRedirect("login.jsp");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
