/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import rs.ac.bg.fon.ps.service.UpdateGamesService;

/**
 *
 * @author nikol
 */
public class UpdateGamesThread extends Thread {

    ServerSocket serverSocket;
    private UpdateGamesService updateGamesService;

    public UpdateGamesThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        updateGamesService = new UpdateGamesService();
    }

    @Override
    public void run() {

        try {
            sleep(3000);
            while (!serverSocket.isClosed()) {
                updateGamesService.update();

                sleep(2 * 60000);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
