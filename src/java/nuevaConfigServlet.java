/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class nuevaConfigServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String username = request.getParameter("username");
            String nombre = request.getParameter("nombre");
            String dif = request.getParameter("dificultad");
            String nav = request.getParameter("modeloNave");
            String lun = request.getParameter("modeloLuna");

            Configuracion conf = new Configuracion();
            conf.setNombre(nombre);
            conf.setDificultad(dif);
            conf.setModeloNave(nav);
            conf.setModeloLuna(lun);
            
            DatabaseManager db = new DatabaseManager();
            Connection c = db.doConnection();
            try {
                //Realizar el insert
                DatabaseOperations.insertConfiguration(c, username, conf);

                db.closeConnection();
                c.close();
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.println("{\"mess\":\"Configuracion guardada correctamente\"}");
            } catch (IOException e) {
                db.closeConnection();
                c.close();
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.println("{\"error\":\"Ha sido imposible guardar los datos\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.println("{\"error\":\"Ha sido imposible guardar los datos\"}");

        }
    }

}
