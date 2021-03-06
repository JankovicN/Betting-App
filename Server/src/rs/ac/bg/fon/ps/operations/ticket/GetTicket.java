/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.ticket;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetTicket extends AbstractGenericOperation{

    Ticket selectedTicket;

    public GetTicket() {
        selectedTicket = new Ticket();
    }

    public Ticket getTicket() {
        return selectedTicket;
    }
    
    
    
    @Override
    protected void executeOperation(Object param) throws Exception {
    
        int ticketID = (int) param;
        selectedTicket.setTicketID(ticketID);
        ArrayList<Ticket> ticket = (ArrayList<Ticket>) repository.getJoinForTicket(selectedTicket, new Bet(), new Odds(), new Game(), new BetType());
        if(ticket!=null)
            selectedTicket=ticket.get(0);
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }
    
    
    
}
