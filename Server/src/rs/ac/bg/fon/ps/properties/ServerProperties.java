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

    public ServerProperties(){
        
        try {
            properties=new Properties();
            properties.load(new FileInputStream("config/serverconfig.properties"));
        } catch (FileNotFoundException ex) {

            //TODO if there is no file
            System.out.println("ServerProperties FileNotFoundException: \n" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ServerProperties IOException: \n" + ex.getMessage());
        }catch (Exception ex) {
            System.out.println("ServerProperties Exception: \n" + ex.getMessage());
        }
    }
    
    public String getPort(){
        
        return properties.getProperty(ServerConstants.SERVER_PORT);
    }
}
