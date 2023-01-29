/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import static java.awt.image.ImageObserver.ERROR;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.properties.ServerProperties;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.users.Users;

/**
 *
 * @author nikol
 */
public class ServerThread extends Thread {

    ServerSocket serverSocket;
    String port;
    ProcessThread processThread;
    UpdateGamesThread updateGamesThread;

    public ServerThread() throws IOException {
        try{
        readConfigProperties();
        serverSocket = new ServerSocket(Integer.parseInt(port));
        System.out.println("Waiting for connection...");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Promenite config properties!", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void run() {

        try {
            initializeThreads();
            startThreads();
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                HandleClientThread clientThread = new HandleClientThread(this, socket);
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

        ServerProperties properties = new ServerProperties();
        port = properties.getPort();
    }

    private void initializeThreads() throws InterruptedException {
        processThread = new ProcessThread(serverSocket);
        updateGamesThread = new UpdateGamesThread(serverSocket);
    }

    private void startThreads() {
        processThread.start();
        updateGamesThread.start();
    }
}
