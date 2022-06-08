/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.view.form.DialogViewTicket;

/**
 *
 * @author nikol
 */
public class ControllerViewTicket {
    
    private DialogViewTicket dialogViewTicket;
    private Ticket ticket;

    public ControllerViewTicket(DialogViewTicket dialogViewTicket, Ticket ticket) {
        this.dialogViewTicket=dialogViewTicket;
        this.ticket=ticket;
    }

    public DialogViewTicket getDialogViewTicket() {
        return dialogViewTicket;
    }

    public Ticket getTicket() {
        return ticket;
    }
    
    public void openForm() {
        this.dialogViewTicket.setVisible(true);
    }
    
}
