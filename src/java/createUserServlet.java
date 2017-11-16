/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class createUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DatabaseManager db = null;
        Connection c = null;
        PrintWriter pw;

        try {
            db = new DatabaseManager();
            c = db.doConnection();
            //Mirar si un usuario determinado existe, para no duplicarlo
            if (!DatabaseOperations.existsUserName(c, username)) {
                DatabaseOperations.createUser(c, name, username, password);
                response.setContentType("application/json");
                pw = response.getWriter();
                pw.println("{\"mess\":\"User created succesfully\"}");
            } else {
                response.setContentType("application/json");
                pw = response.getWriter();
                pw.println("{\"mess\":\"The user already exists\"}");
            }
        } catch (Exception ex) {
            response.setContentType("application/json");
            pw = response.getWriter();
            pw.println("{\"error\":\"Error during the creation\"}");
        } finally {
            try {
                c.close();
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
