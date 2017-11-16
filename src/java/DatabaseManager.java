
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class DatabaseManager {

    private final String user = "andreu";
    private final String password = "1234";
    private final String url = "jdbc:mysql://localhost:3306/lunar_lander";
    private Connection conn = null;

    public Connection doConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to " + conn.toString());
            }
            return conn;
        } catch (SQLException e) {
            System.out.println("Wrong sql_url, sql_username or sql_password");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        try {
            conn.close();
            System.out.println("Conexión cerrada");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fallo al cerrar la conexión");
        }
    }
}
