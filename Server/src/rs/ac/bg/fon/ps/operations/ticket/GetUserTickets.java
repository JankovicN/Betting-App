/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.ticket;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetUserTickets extends AbstractGenericOperation {

    ArrayList<Ticket> listOfTickets;

    public GetUserTickets() {
        listOfTickets=new ArrayList<>();
    }
    
    public ArrayList<Ticket> getList(){
        return listOfTickets;
    }
    
    @Override
    protected void executeOperation(Object param) throws Exception {
        
        User user = (User) param;
        ArrayList<Ticket> list =(ArrayList<Ticket>) repository.searchByForeignKeyBasic(new Ticket(), user);
        
        if(list!=null){
            listOfTickets=list;
        }
        
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }

}
