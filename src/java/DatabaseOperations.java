
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        String query = "SELECT * FROM user WHERE username = \"" + username + "\"";
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

    public static boolean correctLogin(Connection c, String username, String password) {
        Statement stmt = null;
        String query = "SELECT * FROM user WHERE username = \"" + username + "\" AND password = \"" + password + "\"";
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

    public static void cargarConfiguraciones(Connection c, String u) {
        Statement stmt = null;
        String username = null, password = null;
        String query = "SELECT * FROM user WHERE username = \"" + username + "\" AND password = \"" + password + "\"";
        System.out.println(query);
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) { //Cada vez que entremos aqui le pondremos una configuraciones

            }
        } catch (SQLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean existenTabalas(Connection c) {
        Statement stmt = null;
        String query = "SELECT * FROM user";
        try {
            stmt = c.createStatement();
            stmt.executeQuery(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("");
            return false;
        }
    }

    public static void crearTablas(Connection c) {

        String queryConf = "CREATE TABLE `configuration` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `user_id` int(11) NOT NULL,\n"
                + "  `config_name` varchar(20) NOT NULL,\n"
                + "  `dificultad` varchar(10) NOT NULL,\n"
                + "  `modelo_nave` varchar(10) NOT NULL,\n"
                + "  `modelo_luna` varchar(10) NOT NULL,\n"
                + "    PRIMARY key (`id`),\n"
                + "    FOREIGN key (`user_id`) REFERENCES `User`(`id`)\n"
                + ") AUTO_INCREMENT=1;";
        String queryUser = "CREATE TABLE `user` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `name` varchar(20) NOT NULL,\n"
                + "  `username` varchar(20) NOT NULL,\n"
                + "  `password` varchar(255) NOT NULL,\n"
                + "PRIMARY key (`id`)\n"
                + ")AUTO_INCREMENT=1;";

        String queryScore = "CREATE TABLE `score` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `conf_id` int(11) NOT NULL,\n"
                + "  `speed` float NOT NULL,\n"
                + "  `fuel` float NOT NULL,\n"
                + "  `init_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                + "  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',\n"
                + "    PRIMARY KEY (`id`),\n"
                + "    FOREIGN KEY  (`conf_id`) REFERENCES `configuration`(`id`)\n"
                + ") AUTO_INCREMENT=1;";

        try {

            System.out.println("INTENTANDO CREAR");
            PreparedStatement ps = c.prepareStatement(queryUser);
            ps.executeUpdate();

            ps = c.prepareStatement(queryConf);
            ps.executeUpdate();

            ps = c.prepareStatement(queryScore);
            ps.executeUpdate();

            System.out.println("CREADO TODO");

        } catch (SQLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Configuracion> getConfiguraciones(Connection c, String username) {
        ArrayList<Configuracion> listConf = new ArrayList<>();

        Statement stmt = null;
        String query = "SELECT *\n"
                + "FROM user a\n"
                + "INNER JOIN configuration b\n"
                + "ON (a.id = b.user_id AND a.name=\"" + username + "\");";
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            while (rs.next()) { //Cada vez que entremos aqui le pondremos una configuraciones
                listConf.add(new Configuracion(rs.getString("config_name"), rs.getString("dificultad"), rs.getString("modelo_nave"), rs.getString("modelo_luna")));
                System.out.println("CONFIGURACION FINDED");
            }
        } catch (SQLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listConf;
    }

    public static int getUid(Connection c, String username) {
        Statement stmt = null;
        String query = "SELECT id FROM user WHERE (username=\"" + username + "\");";
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            if (rs.next()) { //Cada vez que entremos aqui le pondremos una configuraciones
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public static void insertConfiguration(Connection c, String username, Configuracion conf) {
        try {
            int uid = getUid(c, username);
            
            String query = "INSERT INTO `configuration`(`user_id`, `config_name`, `dificultad`, `modelo_nave`, `modelo_luna`) "
                    + "VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, conf.getNombre());
            preparedStatement.setString(3, conf.getDificultad());
            preparedStatement.setString(4, conf.getModeloNave());
            preparedStatement.setString(5, conf.getModeloLuna());
            // execute insert SQL stetement
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
