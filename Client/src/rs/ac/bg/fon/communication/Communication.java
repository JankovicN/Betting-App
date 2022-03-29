/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.communication;

import java.io.IOException;
import java.net.Socket;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.Sender;

/**
 *
 * @author nikol
 */
public class Communication {
    
    private static Communication instance;
    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;

    public Communication() throws Exception {
        this.socket = new Socket("localhost", 9000);
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket);
    }
    
    public static Communication getInstance() throws Exception{
        if(instance==null){
            instance = new Communication();
        }
        return instance;
    }
    
    public Response sendRequest(Request request, String info) throws Exception{
        
        sender.send(request);
        System.out.println(info);
        return (Response) new Receiver(socket).receive();
    }
    
    public Response login(Request request) throws Exception{
    
        sender.send(request);
        System.out.println("Login request sent..");
        return (Response) new Receiver(socket).receive();
    }
    
}
