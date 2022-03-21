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
    
    Properties properties;

    public DBProperties() throws IOException {
        
        try {
            properties=new Properties();
            properties.load(new FileInputStream("dbconfig.properties"));
        } catch (FileNotFoundException ex) {
            
            //TODO if there is no file
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getURL() {
       return properties.getProperty(DBConstants.URL);
    }
    
    public String getUsername() {
       return properties.getProperty(DBConstants.USERNAME);
    }
     
    public String getPassword() {
       return properties.getProperty(DBConstants.PASSWORD);
    }
    
    
}
