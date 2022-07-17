/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void playTicket() throws Exception {

        Request request = new Request(Operations.CREATE_TICKET, ticket.getListOfBets());
        Response response = Communication.getInstance().sendRequest(request, "Request for creating ticket is sent..");

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            ticket = (Ticket) response.getResult();
            proccesTicket();
            JOptionPane.showMessageDialog(dialogViewTicket, "Ticket played successfully");
            dialogViewTicket.dispose();
            Controller.getInstance().openFormMain();

        } else {
            JOptionPane.showMessageDialog(dialogViewTicket, "Error playing ticket!", "Error ", JOptionPane.ERROR_MESSAGE);
            throw response.getException();
        }

    }

    private void proccesTicket() {

        Date date = new Date();
        long fiveMins = 300000;
        long dateOfPlay = ticket.getListOfBets().get(0).getOdds().getGame().getDateOfPlay().getTime();
        if (dateOfPlay - date.getTime() < fiveMins) {
            fiveMins = dateOfPlay - date.getTime();
        }
        long sleepTime = fiveMins;

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(sleepTime);
                    
                    Request request = new Request(Operations.PROCESS_TICKET, ticket);
                    Response response = Communication.getInstance().sendRequest(request, "Request for processing ticket is sent...");
                    if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                        System.out.println("Ticket processed successfully!");
                    } else {
                        System.out.println("Error while processing ticket!");
                        throw response.getException();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

}
