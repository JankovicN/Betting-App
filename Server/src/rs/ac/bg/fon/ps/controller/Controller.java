/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;
import rs.ac.bg.fon.ps.operations.User.LoginUser;
import rs.ac.bg.fon.ps.operations.ticket.GetTicket;
import rs.ac.bg.fon.ps.operations.ticket.GetUserTickets;

/**
 *
 * @author nikol
 */
public class Controller {

    private static Controller instance;

    public Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public User login(User client) throws Exception {
        User user = client;
        AbstractGenericOperation operation = new LoginUser();
        operation.execute(user);
        user = ((LoginUser) operation).getUser();
        return user;
    }

    public ArrayList<Ticket> getUserTickets(User client) throws Exception {
        User user = client;
        AbstractGenericOperation operation = new GetUserTickets();
        operation.execute(user);
        ArrayList<Ticket> listOfTickets = ((GetUserTickets) operation).getList();
        return listOfTickets;
    }

    public Ticket getTicket(Ticket ticket) throws Exception {
        Ticket requestedTicket = ticket;
        AbstractGenericOperation operation = new GetTicket();
        operation.execute(requestedTicket);
        requestedTicket=((GetTicket)operation).getTicket();
        return requestedTicket;
    }

}
