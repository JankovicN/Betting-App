/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.model.TableModelActiveTickets;
import rs.ac.bg.fon.ps.model.TableModelGames;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.FormCancelTicket;

/**
 *
 * @author nikol
 */
public class ControllerCancelTicket {

    private final FormCancelTicket formCancelTicket;
    private ArrayList<Ticket> listOfTickets;
    TableModelActiveTickets tmat = new TableModelActiveTickets();
        
    public ControllerCancelTicket(FormCancelTicket formCancelTicket) {
        this.formCancelTicket = formCancelTicket;
        listOfTickets = new ArrayList<>();
    }

    public void openForm() {
        System.out.println("Opened Form Main ");
        formCancelTicket.setVisible(true);
        setupForm();
    }

    private void setupForm() {
        setupTable();
    }

    private void getUnprocessedTickets() {
        try {
            Request request = new Request(Operations.GET_UNPROCESSED_TICKETS, new Ticket());
            Response response = Communication.getInstance().sendRequest(request, "GET_UNPROCESSED_TICKETS: Request for getting unprocessed tickets is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                listOfTickets = (ArrayList<Ticket>) response.getResult();

            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formCancelTicket, "Error canceling ticket!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void setupTable() {
        getUnprocessedTickets();
        JTable tableTickets = formCancelTicket.getTblActiveTickets();
        tmat.setListOfTickets(listOfTickets);
        tableTickets.setModel(tmat);
    }

    public void cancelTicket(){
    
        JTable tableTickets = formCancelTicket.getTblActiveTickets();
        Ticket ticket = tmat.getTicket(tableTickets.getSelectedRow());
        
        try {
            Request request = new Request(Operations.CANCEL_TICKET, ticket);
            Response response = Communication.getInstance().sendRequest(request, "CANCEL_TICKET: Request canceling ticket is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                JOptionPane.showMessageDialog(formCancelTicket, "Ticket canceled succesffuly!","Success", JOptionPane.OK_OPTION);
                setupTable();
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formCancelTicket, "Error canceling ticket!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
    
}
