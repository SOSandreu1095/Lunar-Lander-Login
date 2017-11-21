/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class loginServlet extends HttpServlet {

    @Override
    public void init() {
        DatabaseManager db = new DatabaseManager();
        Connection c = db.doConnection();

        if (DatabaseOperations.existenTabalas(c)) {
            System.out.println("LAS TABLAS EXISTEN");
        } else {
            System.out.println("HAY QUE CREAR LAS TABLAS");
            //EJECUTAR SCRIPT
            DatabaseOperations.crearTablas(c);
        }

        db.closeConnection();
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Handles the HTTP <code>GET</code> method. Will check if exists the
     * cookies of the username / password If the cookies exists, will check it
     * if their values are the correct login in the database, if are correct it
     * will redirect to the game, if not it will redirect to the login screen
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cookieUser = "username";
        String cookiePass = "password";
        String u = null;
        String p = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            //Recorremos los cookies y buscamos el nuestro
            for (int i = 0; i < cookies.length; i++) {
                //Get the username
                if (cookieUser.equals(cookies[i].getName())) {
                    u = cookies[i].getValue();
                }
                //Get the password
                if (cookiePass.equals(cookies[i].getName())) {
                    p = cookies[i].getValue();
                }
            }
            DatabaseManager db = new DatabaseManager();
            Connection c = db.doConnection();

            //Si el usuario esta conectado correctamente, cargaremos sus configuraciones
            if (DatabaseOperations.correctLogin(c, u, p)) {

                try {
                    c.close();
                    db.closeConnection();
                    request.setAttribute("username", u);
                } catch (SQLException ex) {
                    Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }
            try {
                c.close();
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method. Check if the information about
     * the username and the password is correct If it's correct,it will redirect
     * to the game screen If it is not correct it will warn it and will keep in
     * the login screen
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DatabaseManager db = null;
        Connection c = null;
        PrintWriter pw;

        try {
            db = new DatabaseManager();
            c = db.doConnection();
            //Mirar si un usuario determinado existe, para no duplicarlo
            if (DatabaseOperations.correctLogin(c, username, password)) {
                //Creamos los cookies de inicio
                response.addCookie(createCookie("username", username, 60));
                response.addCookie(createCookie("password", password, 60));
                response.setContentType("application/json");
                pw = response.getWriter();
                pw.println("{\"mess\":\"Correct Login\"}");
            } else {
                response.setContentType("application/json");
                pw = response.getWriter();
                pw.println("{\"mess\":\"Wrong Username / password\"}");
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

    private Cookie createCookie(String name, String value, int maxAgeSec) {
        Cookie c = new Cookie(name, value);
        c.setMaxAge(maxAgeSec); //Store cookie for 1 min
        return c;
    }

}
