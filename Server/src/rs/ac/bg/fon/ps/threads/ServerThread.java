/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import rs.ac.bg.fon.ps.users.Users;

/**
 *
 * @author nikol
 */
public class ServerThread extends Thread{
    
    ServerSocket serverSocket;

    public ServerThread() throws IOException {
        serverSocket=new ServerSocket(9000);
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
}
