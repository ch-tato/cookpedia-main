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

@WebServlet("/edit-profile")
public class EditProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session==null || session.getAttribute("userId")==null){
            response.sendRedirect("login.jsp");
            return;
        }
        int userID = (Integer)session.getAttribute("userId");
        String newUsername = request.getParameter("username");
        String newEmail = request.getParameter("email");

        UserDAO dao = new UserDAO();
        if(newUsername==null || newEmail==null || newUsername.isBlank() || newEmail.isBlank()){
            request.setAttribute("error", "Fields cannot be empty");
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
            return;
        }
        User currentUser = dao.getUserById(userID);
        if (currentUser == null) {
            request.setAttribute("error", "User not found.");
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
            return;
        }
        if (newUsername.equals(currentUser.getUsername()) && newEmail.equals(currentUser.getEmail())) {
            request.setAttribute("error", "No changes detected.");
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
            return;
        }
        if (dao.isUsernameUsedByOthers(newUsername, userID)) {
            request.setAttribute("error", "Username is already taken.");
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
            return;
        }
        if (dao.isEmailUsedByOthers(newEmail, userID)) {
            request.setAttribute("error", "Email is already registered.");
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
            return;
        }
        boolean ok = dao.updateUserProfile(userID, newUsername, newEmail);
        if(ok){
            User updatedUser = dao.getUserById(userID);
            session.setAttribute("user", updatedUser);
            session.setAttribute("userId", updatedUser.getId());
            session.setAttribute("Username", updatedUser.getUsername());
            session.setAttribute("Email", updatedUser.getEmail());
            request.setAttribute("success", "Profile updated successfully!");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            response.sendRedirect("profile");
        }
        else{
            request.setAttribute("error", "Failed to update profile");
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
        }
    }
}
