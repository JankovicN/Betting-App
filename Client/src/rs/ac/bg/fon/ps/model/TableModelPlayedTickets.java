/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Ticket;

/**
 *
 * @author nikol
 */
public class TableModelPlayedTickets extends AbstractTableModel {

    ArrayList<Ticket> listOfTickets;
    String[] columns = new String[]{"TicketID", "Played on", "Wager", "Odds", "Potential win", "Win"};

    public TableModelPlayedTickets() {
        listOfTickets = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return listOfTickets.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Ticket ticket = listOfTickets.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return ticket.getTicketID();
            case 1:
                return ticket.getDate();
            case 2:
                return ticket.getWager();
            case 3:
                return ticket.getCombinedOdds();
            case 4:
                return ticket.getPotentialWin();
            case 5:
                return ticket.getPotentialWin();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void addTicket(Ticket ticket) {
        listOfTickets.add(ticket);
        fireTableDataChanged();
    }

    public Ticket getTicket(int index) {
        return listOfTickets.get(index);
    }

    public ArrayList<Ticket> getListOfTickets() {
        return listOfTickets;
    }

    public void setListOfTickets(ArrayList<Ticket> listOfTickets) {
        this.listOfTickets = listOfTickets;
        fireTableDataChanged();
    }
}
