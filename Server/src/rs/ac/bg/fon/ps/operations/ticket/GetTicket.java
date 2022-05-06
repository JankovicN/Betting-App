/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.ticket;

import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetTicket extends AbstractGenericOperation{

    private Ticket ticket;

    public GetTicket() {
        this.ticket=new Ticket();
    }
    
    @Override
    protected void executeOperation(Object param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }

    public Ticket getTicket() {
        return this.ticket;
    }
    
}
