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
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetProcessedTickets extends AbstractGenericOperation {
    
    private ArrayList<Ticket> listOfTickets;

    public GetProcessedTickets() {
        listOfTickets=new ArrayList<>();
    }
    
    public ArrayList<Ticket> getListOfTickets(){
        return listOfTickets;
    }
    
    @Override
    protected void executeOperation(Object param) throws Exception {
        
        ArrayList<Ticket> list =(ArrayList<Ticket>) repository.getJoinForTicketCondition(new Ticket(),new Bet(), new Odds(), new Game(), new BetType(), (new Ticket()).getProcessedCondition());
        
        if(list!=null){
            listOfTickets=list;
        }
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }
}
