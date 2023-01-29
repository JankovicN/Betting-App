/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.repository.db;

import static java.awt.image.ImageObserver.ERROR;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.properties.DBProperties;

/**
 *
 * @author nikol
 */
public class DBConnectionFactory {

    private static DBConnectionFactory instance;
    private Connection connection;
    private String url, username, password;

    public static DBConnectionFactory getInstance() {

        if (instance == null) {
            instance = new DBConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                readConfigProperties();

                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
            }
            return connection;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Promenite config properties!", "Greska", ERROR);
        }
        return null;
    }

    private void readConfigProperties() {

        try {
            DBProperties properties = new DBProperties();
            url = properties.getURL();
            username = properties.getUsername();
            password = properties.getPassword();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Promenite config properties!", "Greska", ERROR);
            System.out.println("DBConnectionFactory Exception: \n" + e.getMessage());
        }
    }
}
