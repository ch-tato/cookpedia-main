package com.cookpedia.test;

import com.cookpedia.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/test-db")
public class DBTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.println("Connecting to database...");
            try (Connection conn = DBConnection.getConnection()) {
                out.println("✅ Database connection successful!");
            } catch (Exception e) {
                e.printStackTrace(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
