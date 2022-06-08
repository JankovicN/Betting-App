/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.properties.ServerProperties;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.users.Users;

/**
 *
 * @author nikol
 */
public class ServerThread extends Thread{
    
    ServerSocket serverSocket;
    String port;

    public ServerThread() throws IOException {
        readConfigProperties();
        serverSocket=new ServerSocket(Integer.parseInt(port));
        System.out.println("Waiting for connection...");
    }

    @Override
    public void run() {

        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                HandleClientThread clientThread= new HandleClientThread(this, socket);
                clientThread.start();
                Users.getInstance().addClientThread(clientThread);
                System.out.println("logged in");
            }
        } catch (Exception e) {
        }

    }

    public void stopServer() throws IOException {
    
        for (HandleClientThread client : Users.getInstance().getClientThreads()) {
            client.getSocket().close();
        }
        serverSocket.close();   
    }
    
    private void readConfigProperties() {

        try {
            ServerProperties properties = new ServerProperties();
            port = properties.getPort();
        } catch (IOException e) {
            Logger.getLogger(DBConnectionFactory.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
