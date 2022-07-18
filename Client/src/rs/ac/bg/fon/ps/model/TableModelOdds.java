/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Odds;

/**
 *
 * @author nikol
 */
public class TableModelOdds extends AbstractTableModel {

    private ArrayList<Odds> listOfOdds;

    private String[] columns = new String[]{"Type", "Odds"};

    public TableModelOdds() {
        listOfOdds = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return listOfOdds.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Odds odd = listOfOdds.get(rowIndex);

        switch (columnIndex) {
            case 0 -> {
                return odd.getType();
            }
            case 1 -> {
                return odd.getOdds();
            }
            default ->
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public Odds getOdd(int rowIndex) {
        return listOfOdds.get(rowIndex);
    }

    public ArrayList<Odds> getListOfOdds() {
        return listOfOdds;
    }

    public void setListOfOdds(ArrayList<Odds> listOfOdds) {
        this.listOfOdds = listOfOdds;
        fireTableDataChanged();
    }
}
