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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cookieName = "tipo";
        String cookieValue = "jsp";
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            //Recorremos los cookies y buscamos el nuestro
            for (int i = 0; i < cookies.length; i++) {
                //Si es el tipo que queremos y tiene el valor deseado, redireccionaremos al index directamente
                if (cookieName.equals(cookies[i].getName()) && cookieValue.equals(cookies[i].getValue())) {
                    //Para redireccionar
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                }
            }

        }
        //Iriamos al login y crearemos un cookie para ir directamente al jsp la proxima vez
        //Si no hay cookies le redireccionamos al login
        Cookie userCookie = new Cookie(cookieName, cookieValue);
        userCookie.setMaxAge(60); //Store cookie for 1 min
        response.addCookie(userCookie);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.html");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            if (!existsUser(c, username)) {
                createUser(c, name, username, password);
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

    /**
     * 
     * @param c
     * @param n
     * @param u
     * @param p 
     */
    public void createUser(Connection c, String n, String u, String p) {
        try {
            String query = "INSERT INTO user (name, username, password) VALUES (?, ?, ?)";
            PreparedStatement prepstmt = c.prepareStatement(query);
            prepstmt.setString(1, n);
            prepstmt.setString(2, u);
            prepstmt.setString(3, p);
            prepstmt.execute();

            //Can't use ResultSet after close prepared statement, we have to print or fill object here...
            prepstmt.close();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * 
     * @param c
     * @param username
     * @return 
     */
    private boolean existsUser(Connection c, String username) {
        Statement stmt = null;
        String query = "SELECT * FROM user WHERE username = \""+username+"\"";
        System.out.println(query);
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) { //Si entra aqui es porque ya existe ese usuario
                return true;
            } else {
                return false; //Si no existe devuelve false
            }
        } catch (SQLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }
}
