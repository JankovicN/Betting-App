/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.model.TableModelActiveTickets;
import rs.ac.bg.fon.ps.model.TableModelGames;
import rs.ac.bg.fon.ps.model.TableModelPlayedTickets;
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
            JOptionPane.showMessageDialog(formCancelTicket, "Sistem ne moze da ucita tikete!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void setupTable() {
        getUnprocessedTickets();
        JTable tableTickets = formCancelTicket.getTblActiveTickets();
        tmat.setListOfTickets(listOfTickets);
        tableTickets.setModel(tmat);
    }

    public void cancelTicket() {

        JTable tableTickets = formCancelTicket.getTblActiveTickets();
        Ticket ticket = tmat.getTicket(tableTickets.getSelectedRow());

        try {
            Request request = new Request(Operations.CANCEL_TICKET, ticket);
            Response response = Communication.getInstance().sendRequest(request, "CANCEL_TICKET: Request canceling ticket is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                JOptionPane.showMessageDialog(formCancelTicket, "Sistem je stornirao tiket!");
                setupTable();
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formCancelTicket, "Sistem ne moze d astornira tiket!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void filterTable() {
        getUnprocessedTickets();
        String dateString = formCancelTicket.getDate();
        if (dateString.isBlank()) {
            tmat.setListOfTickets(listOfTickets);
            return;
        }
        try {
            JTable table = formCancelTicket.getTblActiveTickets();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Date date = sdf.parse(dateString);
            listOfTickets.removeIf(ticket -> !sdf.format(ticket.getDate()).equals(dateString));
            JTable tableTickets = formCancelTicket.getTblActiveTickets();
            tmat.setListOfTickets(listOfTickets);
            tableTickets.setModel(tmat);

            if (listOfTickets.size() > 0) {
                JOptionPane.showMessageDialog(formCancelTicket, "Sistem je nasao tikete po zadatoj vrednosti");
            } else {
                JOptionPane.showMessageDialog(formCancelTicket, "Sistem nije nasao tikete po zadatoj vrednosti", "Tickets", JOptionPane.ERROR_MESSAGE);
            };

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(formCancelTicket, "Sistem ne moz da nadje tiket po zadatoj vrednosti", "Invalid input!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

}
