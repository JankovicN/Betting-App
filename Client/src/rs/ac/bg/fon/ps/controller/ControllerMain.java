/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Role;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.model.TableModelPlayedTickets;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.FormMain;

/**
 *
 * @author nikol
 */
public class ControllerMain {

    private final FormMain formMain;

    public ControllerMain(FormMain formMain) {
        this.formMain = formMain;
    }

    public FormMain getFormMain() {
        return this.formMain;
    }

    public void openForm() throws Exception {
        formMain.setVisible(true);
        setupTable();
    }

    public void validateForm() {
        return;
    }

    public void setupTable() throws Exception {

        formMain.setupMenuBar();
        formMain.setlblUser(Controller.getInstance().getCurrentUser().getUsername());

        JTable table = formMain.getTablePlayedTicket();
        TableModelPlayedTickets tmpt = new TableModelPlayedTickets();

        Request request = new Request(Operations.GET_USER_TICKETS, null);
        Response response = Communication.getInstance().sendRequest(request, "Request for tickets played by user is sent..");

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {

            ArrayList<Ticket> listOfTickets = (ArrayList<Ticket>) response.getResult();

            for (Ticket ticket : listOfTickets) {
                if (!ticket.getState().equals("processed")) {
                    listOfTickets.remove(ticket);
                }
            }
            tmpt.setListOfTickets(listOfTickets);
            table.setModel(tmpt);
            table.setRowSelectionAllowed(true);
            formMain.setTablePlayedTicket(table);
        } else {
            throw response.getException();
        }

    }

    public void filterTable() throws ParseException {

        String dateString = formMain.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        JTable table = formMain.getTablePlayedTicket();
        TableModelPlayedTickets tmpt = (TableModelPlayedTickets) table.getModel();

        Date date = sdf.parse(dateString);
        ArrayList<Ticket> listOfTickets = tmpt.getListOfTickets();
        for (Ticket ticket : listOfTickets) {
            if (!ticket.getDate().equals(date)) {
                listOfTickets.remove(ticket);
            }
        }
        tmpt.setListOfTickets(listOfTickets);

    }

    public void viewTicket() throws Exception {

        JTable table = formMain.getTablePlayedTicket();
        TableModelPlayedTickets tmpt = (TableModelPlayedTickets) table.getModel();

        Ticket ticket = tmpt.getTicket(table.getSelectedRow());
        Request request = new Request(Operations.GET_TICKET, ticket);
        Response response = Communication.getInstance().sendRequest(request, "Request for ticket is sent..");

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {

            ticket = (Ticket) response.getResult();
            Controller.getInstance().openFormViewTicket(ticket);
        } else {
            throw response.getException();
        }

    }
}
