/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.model.TableModelViewTicket;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.DialogViewTicket;

/**
 *
 * @author nikol
 */
public class ControllerConfirmTicket {

    private DialogViewTicket dialogViewTicket;
    private Ticket ticket;

    public ControllerConfirmTicket(DialogViewTicket dialogViewTicket, Ticket ticket) {
        this.dialogViewTicket = dialogViewTicket;
        this.ticket = ticket;
    }

    public DialogViewTicket getDialogViewTicket() {
        return dialogViewTicket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void openForm() {
        System.out.println("Opened Confirm Ticket ");
        this.dialogViewTicket.setVisible(true);
        setupForm();
    }

    private void setupForm() {

        setupTable();
        dialogViewTicket.getLblTicketID().setText(String.valueOf(ticket.getTicketID()));
        dialogViewTicket.getLblDate().setText(String.valueOf(ticket.getDate()));
        dialogViewTicket.getLblPassed().setVisible(false);

        dialogViewTicket.getLblWager().setText(String.valueOf(ticket.getWager()));
        dialogViewTicket.getLblCombinedOdds().setText(String.valueOf(ticket.getCombinedOdds()));
        dialogViewTicket.getLblPotentialWin().setText(String.valueOf(ticket.getPotentialWin()));

    }

    private void setupTable() {

        TableModelViewTicket tmvt = new TableModelViewTicket();
        tmvt.setListOfBets((ArrayList<Bet>) ticket.getListOfBets());
        JTable table = dialogViewTicket.getTblMatches();
        table.setModel(tmvt);
        centerTableText(table);
    }

    private void centerTableText(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }

    public void playTicket() {
        try {
            Request request = new Request(Operations.CREATE_TICKET, ticket.getListOfBets());
            Response response = Communication.getInstance().sendRequest(request, "Request for creating ticket is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                ticket = (Ticket) response.getResult();
                JOptionPane.showMessageDialog(dialogViewTicket, "Ticket played successfully");
                dialogViewTicket.dispose();
                Controller.getInstance().openFormMain();

            } else {
                JOptionPane.showMessageDialog(dialogViewTicket, "Error playing ticket!", "Error ", JOptionPane.ERROR_MESSAGE);
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialogViewTicket, "Error playing ticket!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


}
