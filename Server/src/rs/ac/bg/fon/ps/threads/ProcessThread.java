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

    ProcessThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {

        try {
            while (!serverSocket.isClosed()) {

                Controller.getInstance().processTickets();
                sleep(60000);
            }
        } catch (Exception e) {
        }

    }

}
