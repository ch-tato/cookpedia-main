package com.cookpedia.servlet;

import com.cookpedia.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session==null || session.getAttribute("userId")==null){
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = (Integer) session.getAttribute("userId");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        List<String> errors = new ArrayList<>();

        if (newPassword.length() < 8) {
            errors.add("New password must be at least 8 characters long.");
        }

        if (!newPassword.equals(confirmPassword)) {
            errors.add("New password and confirmation password do not match.");
        }

        if (oldPassword.equals(newPassword)) {
            errors.add("New password cannot be the same as your current password.");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("change_password.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        boolean success = dao.changePassword(userId, oldPassword, newPassword);

        if (!success) {
            errors.add("Old password is incorrect.");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("change_password.jsp").forward(request, response);
            return;
        }
        request.setAttribute("success", "Password changed successfully!");
        request.getRequestDispatcher("profile.jsp").forward(request, response);
        response.sendRedirect("profile.jsp?success=password");
    }
}
