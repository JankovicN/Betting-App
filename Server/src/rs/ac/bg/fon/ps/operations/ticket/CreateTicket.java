/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.ticket;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class CreateTicket extends AbstractGenericOperation{

    private Ticket ticket;

    public CreateTicket() {
        this.ticket = new Ticket();
    }

    public Ticket getTicket() {
        return ticket;
    }
    
    
    @Override
    protected void executeOperation(Object param) throws Exception {
        
        ArrayList<Bet> listOfBets = (ArrayList<Bet>) param;
        ticket = listOfBets.get(0).getTicket();
        
        int ticketId =repository.addReturnKey(ticket);
        ticket.setTicketID(ticketId);
        for (Bet b : listOfBets) {
            b.setTicket(ticket);
            repository.add(b);
        }
        
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }
    
}
