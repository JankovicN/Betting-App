/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.model.TableModelNewTicket;
import rs.ac.bg.fon.ps.model.TableModelPlayedTickets;
import rs.ac.bg.fon.ps.model.TableModelViewTicket;
import rs.ac.bg.fon.ps.view.form.DialogViewTicket;

/**
 *
 * @author nikol
 */
public class ControllerViewTicket {

    private DialogViewTicket dialogViewTicket;
    private Ticket ticket;

    public ControllerViewTicket(DialogViewTicket dialogViewTicket, Ticket ticket) {
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
        dialogViewTicket.getBtnPlayTicket().setVisible(false);
        if (!ticket.getState().equals("completed")) {
            dialogViewTicket.getLblPassed().setText("");
        } else {
            if (ticket.isWin()) {
                dialogViewTicket.getLblPassed().setText("WIN");
                dialogViewTicket.getLblPassed().setForeground(Color.GREEN);
            } else {
                dialogViewTicket.getLblPassed().setText("LOST");
                dialogViewTicket.getLblPassed().setForeground(Color.RED);
            }
        }
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

}
