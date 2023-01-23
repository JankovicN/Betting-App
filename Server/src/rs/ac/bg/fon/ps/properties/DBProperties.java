/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikol
 */
public class DBProperties {

    private Properties properties;

    public DBProperties() {

        try {
            properties = new Properties();
            properties.load(new FileInputStream("config/dbconfig.properties"));
        } catch (FileNotFoundException ex) {

            //TODO if there is no file
            System.out.println("DBProperties FileNotFoundException: \n" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("DBProperties IOException: \n" + ex.getMessage());
        }catch (Exception ex) {
            System.out.println("DBProperties Exception: \n" + ex.getMessage());
        }
    }

    public String getURL() {
        return properties.getProperty(DBConstants.DB_URL);
    }

    public String getUsername() {
        return properties.getProperty(DBConstants.DB_USERNAME);
    }

    public String getPassword() {
        return properties.getProperty(DBConstants.DB_PASSWORD);
    }

}
