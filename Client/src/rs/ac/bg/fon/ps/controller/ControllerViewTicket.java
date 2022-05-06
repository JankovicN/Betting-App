/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.view.form.FormViewTicket;

/**
 *
 * @author nikol
 */
public class ControllerViewTicket {
    
    private FormViewTicket formViewTicket;
    private Ticket ticket;

    public ControllerViewTicket(FormViewTicket formViewTicket, Ticket ticket) {
        this.formViewTicket=formViewTicket;
        this.ticket=ticket;
    }

    public FormViewTicket getFormViewTicket() {
        return formViewTicket;
    }

    public Ticket getTicket() {
        return ticket;
    }
    
    public void openForm() {
        this.formViewTicket.setVisible(true);
    }
    
}
