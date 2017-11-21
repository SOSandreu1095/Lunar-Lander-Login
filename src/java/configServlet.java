/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class configServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        DatabaseManager db = new DatabaseManager();
        Connection c = db.doConnection();
        System.out.println(username);
        ArrayList<Configuracion> listConf = DatabaseOperations.getConfiguraciones(c, username);
        System.out.println("Confs: "+listConf.size());
        try {
            if (listConf.size() > 0) {
                Gson gson = new Gson();
                String jsonInString = gson.toJson(listConf); //a String

                db.closeConnection();
                c.close();
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.println(jsonInString);

            } else {
                db.closeConnection();
                c.close();
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.println("No hay configuraciones para este usuario");
            }
        } catch (SQLException ex) {
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.println("{\"error\":\"Problema al cerrar la conexion\"}");
        }

    }

}
