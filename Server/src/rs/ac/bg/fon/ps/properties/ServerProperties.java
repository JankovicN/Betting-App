/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Provider;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author nikol
 */
public class ServerProperties {
    
    private Properties properties;

    public ServerProperties() throws IOException {
        
        try {
            properties=new Properties();
            properties.load(new FileInputStream("config/serverconfig.properties"));
        } catch (FileNotFoundException e) {
            
            //TODO if there is no file
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public String getPort(){
        
        return properties.getProperty(ServerConstants.SERVER_PORT);
    }
}
