
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class DatabaseOperations {
    
    public static void createUser(Connection c, String n, String u, String p) {
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
    public static boolean existsUserName(Connection c, String username) {
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
    
    public static boolean correctLogin(Connection c, String username, String password){
        Statement stmt = null;
        String query = "SELECT * FROM user WHERE username = \""+username+"\" AND password = \""+password+"\"";
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
