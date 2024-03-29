/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import java.net.ServerSocket;
import rs.ac.bg.fon.ps.controller.Controller;

/**
 *
 * @author nikol
 */
public class ProcessThread extends Thread {

    ServerSocket serverSocket;

    public ProcessThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {

        try {
            while (!serverSocket.isClosed()) {
                
                Controller.getInstance().processTickets();
                sleep(2*60000);
            }
            System.out.println("ProcessThread: ServerSocket is closed!");
        } catch (Exception e) {
            System.out.println("Error in process thread: \n"+e.getMessage());
        }

    }

}
