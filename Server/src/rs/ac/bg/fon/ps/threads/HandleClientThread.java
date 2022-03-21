/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import java.net.Socket;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.Operations;

/**
 *
 * @author nikol
 */
public class HandleClientThread extends Thread{
    private ServerThread serverThread;
    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    private User client;

    public HandleClientThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        this.client=new User();
    }


    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
    
        Response response = new Response();
        try {
            while(true){
                Request request = (Request)receiver.receive();
                obradiOdgovor(request, response);
            }
        } catch (Exception e) {
            //TODO 
        }
    }

    private void obradiOdgovor(Request request, Response response) throws Exception {

        switch(request.getOperation()){
        
            
        }
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
