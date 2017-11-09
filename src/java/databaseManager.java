
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class databaseManager {
    private final String dbName = "luner_lander";
    private final String username = "andreu";
    private final String password = "1234";
    private final String jdbcDriver = "com.mysql.jdbc.Driver";
    private final String dbAddress = "jdbc:mysql://localhost:3306/";
    private Connection connection;
    
    public databaseManager(){
        
    }
    
    public void openConnection() throws ClassNotFoundException, SQLException{
        Class.forName(jdbcDriver);
        connection = DriverManager.getConnection(dbAddress+dbName, username, password);
    }
    
    public void closeConnection() throws SQLException{
        connection.close();
    }
    
    
}
