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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
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
    private ArrayList<Ticket> listOfTickets;

    public ControllerMain(FormMain formMain) {
        this.formMain = formMain;
        listOfTickets = new ArrayList<>();
    }

    public FormMain getFormMain() {
        return this.formMain;
    }

    public void openForm() throws Exception {
        System.out.println("Opened Form Main ");
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
        try {
            Request request = new Request(Operations.GET_USER_TICKETS, null);
            Response response = Communication.getInstance().sendRequest(request, "Request for tickets played by user is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {

                ArrayList<Ticket> listOfTickets = (ArrayList<Ticket>) response.getResult();
                System.out.println("list size " + listOfTickets.size());
//                for (Ticket ticket : listOfTickets) {
//                    if (!ticket.getState().equals("processed")) {
//                        listOfTickets.remove(ticket);
//                    }
//                }
                tmpt.setListOfTickets(listOfTickets);
                this.listOfTickets = listOfTickets;
                table.setModel(tmpt);
                centerTableText(table);
                table.setRowSelectionAllowed(true);
                formMain.setTablePlayedTicket(table);
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formMain, "Sistem ne moze da ucita tikete!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void fillTable() {
        JTable table = formMain.getTablePlayedTicket();
        TableModelPlayedTickets tmpt = new TableModelPlayedTickets();
        try {
            Request request = new Request(Operations.GET_USER_TICKETS, null);
            Response response = Communication.getInstance().sendRequest(request, "Request for tickets played by user is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {

                ArrayList<Ticket> listOfTickets = (ArrayList<Ticket>) response.getResult();
                System.out.println("list size " + listOfTickets.size());
//                for (Ticket ticket : listOfTickets) {
//                    if (!ticket.getState().equals("processed")) {
//                        listOfTickets.remove(ticket);
//                    }
//                }
                tmpt.setListOfTickets(listOfTickets);
                this.listOfTickets = listOfTickets;
                table.setModel(tmpt);
                centerTableText(table);
                table.setRowSelectionAllowed(true);
                formMain.setTablePlayedTicket(table);
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formMain, "Sistem ne moze da ucita tikete!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void filterTable() {

        try {
            fillTable();
            String dateString = formMain.getDate();
            if (dateString.isBlank()) {
                setupTable();
                return;
            }
            JTable table = formMain.getTablePlayedTicket();
            TableModelPlayedTickets tmpt = (TableModelPlayedTickets) table.getModel();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Date date = sdf.parse(dateString);
            listOfTickets.removeIf(ticket -> !sdf.format(ticket.getDate()).equals(dateString));
            
            if (listOfTickets.size() > 0) {
                JOptionPane.showMessageDialog(formMain, "Sistem je nasao tikete po zadatoj vrednosti");
            } else {
                JOptionPane.showMessageDialog(formMain, "Sistem nije nasao tikete po zadatoj vrednosti", "Invalid input!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(formMain, "Sistem ne moze da nadje tiket po zadatoj vrednosti", "Invalid input!", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(formMain, "Sistem ne moze da ucita tikete", "Invalid input!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void viewTicket() {
        try {
            JTable table = formMain.getTablePlayedTicket();
            TableModelPlayedTickets tmpt = (TableModelPlayedTickets) table.getModel();

            Ticket ticket = tmpt.getTicket(table.getSelectedRow());
            Request request = new Request(Operations.GET_TICKET_WITH_BETS, ticket);
            Response response = Communication.getInstance().sendRequest(request, "Request for ticket is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {

                ticket = (Ticket) response.getResult();
                Controller.getInstance().openFormViewTicket(ticket);
                JOptionPane.showMessageDialog(formMain, "Sistem je ucitao tiket");
            } else {
                throw response.getException();
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formMain, " No ticket selected! ", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formMain, "Error displaying selected ticket! \n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void playNewTicket() throws Exception {
        formMain.setVisible(false);
        Controller.getInstance().openFormPlayTicket();
    }

    private void centerTableText(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }
}
