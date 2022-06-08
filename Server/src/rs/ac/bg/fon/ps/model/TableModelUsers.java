/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.User;

/**
 *
 * @author nikol
 */
public class TableModelUsers extends AbstractTableModel {

    ArrayList<User> listOfUsers;
    ArrayList<User> listOfActiveUsers;
    String[] columns = new String[]{"UserID", "Name", "Surname", "Username", "Role", "Online", "Last login"};

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");

    public TableModelUsers() throws Exception {
        listOfUsers = Controller.getInstance().getAllUsers();
        listOfActiveUsers = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return listOfUsers.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        User user = listOfUsers.get(rowIndex);

        switch (columnIndex) {
            case 0 -> {
                return user.getUserID();
            }
            case 1 -> {
                return user.getName();
            }
            case 2 -> {
                return user.getSurname();
            }
            case 3 -> {
                return user.getUsername();
            }
            case 4 -> {
                return user.getRole();
            }
            case 5 -> {
                return isActive(user);
            }
            case 6 -> {
                return getLoginDate(user);
            }
            default ->
                throw new AssertionError();

        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void loginUser(User u) {
        listOfActiveUsers.add(u);
        fireTableDataChanged();
    }

    public void logoutUser(User client) {
        listOfActiveUsers.remove(client);
        fireTableDataChanged();
    }

    public String isActive(User user) {
        for (User u : listOfActiveUsers) {
            if (u.equals(user)) {
                return "Active";
            }
        }
        return "Inactive";
    }

    public String getLoginDate(User user) {
        for (User u : listOfActiveUsers) {
            if (u.equals(user)) {
                user.setDateOfLogin(u.getDateOfLogin());
                return u.getDateOfLogin();
            }
        }
        return user.getDateOfLogin();
    }
}
