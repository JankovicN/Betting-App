/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.Operations;

/**
 *
 * @author nikol
 */
public class HandleClientThread extends Thread {

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
        this.client = new User();
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {

        Response response = new Response();
        try {
            while (true) {
                Request request = (Request) receiver.receive();
                processResponse(request, response);
            }
        } catch (Exception e) {
            //TODO 
        }
    }

    private void processResponse(Request request, Response response) throws Exception {

        switch (request.getOperation()) {

            case Operations.LOGIN:
                login(request, response);
            case Operations.GET_USER_TICKETS:
                getUserTickets(request, response);
        }
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    private void login(Request request, Response response) throws Exception {

        try {
            User user = (User) request.getArgument();
            setClient(Controller.getInstance().login(user));
            System.out.println("Login successful!");
            response.setResult(client);
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        sender.send(response);
    }

    private void getUserTickets(Request request, Response response) throws Exception {
        try {
            
            ArrayList<Ticket> listOfTickets = Controller.getInstance().getUserTickets(client);

            System.out.println("Requst for list of played tickets was successful!");
            response.setResult(listOfTickets);
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        sender.send(response);
    }
}
