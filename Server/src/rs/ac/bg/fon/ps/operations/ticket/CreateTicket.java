/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class CreateTicket extends AbstractGenericOperation {

    private Ticket ticket;

    public CreateTicket() {
        this.ticket = new Ticket();
    }

    public Ticket getTicket() {
        return ticket;
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        ticket = (Ticket) param;
        ArrayList<Bet> listOfBets = (ArrayList<Bet>) ticket.getListOfBets();

        int ticketId = repository.addReturnKey(ticket);
        ticket.setTicketID(ticketId);
        for (Bet b : listOfBets) {
            b.setTicket(ticket);
            repository.add(b);
        }

    }

    @Override
    protected void preconditions(Object param) throws Exception {

        if (param == null || !(param instanceof Ticket)) {
            throw new Exception("Invalid data for Ticket!");
        }
        Ticket newTicket = (Ticket) param;
        if (newTicket.getWager().compareTo(BigDecimal.valueOf(1000000)) == 1) {
            throw new Exception("Exceded maximum  number of bets!");
        }
        if (newTicket.getCombinedOdds() < 1 || newTicket.getCombinedOdds() > 1000000) {
            throw new Exception("Invalid odds for ticket!");
        }
        
        ArrayList<Bet> listOfBets = (ArrayList<Bet>) newTicket.getListOfBets();
        if (listOfBets.size() > 50) {
            throw new Exception("Exceded maximum  number of bets!");
        }

    }

}
