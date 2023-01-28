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
public class UpdateTicket extends AbstractGenericOperation{

    @Override
    protected void executeOperation(Object param) throws Exception {
        Ticket ticket = (Ticket) param;
        repository.edit(ticket);
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Ticket)) {
            throw new Exception("Invalid data for Ticket!");
        }
    }
    
}
