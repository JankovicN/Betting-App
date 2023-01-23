/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Bet;

/**
 *
 * @author nikol
 */
public class TableModelViewTicket extends AbstractTableModel{
    
    private ArrayList<Bet> listOfBets;

    private String[] columns = new String[]{"Game", "Type", "Odds", "State"};

    public TableModelViewTicket(){
        listOfBets = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return listOfBets.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bet bet = listOfBets.get(rowIndex);

        switch (columnIndex) {
            case 0 -> {
                return bet.getOdds().getGame().toString();
            }
            case 1 -> {
                return bet.getOdds().getType().toString();
            }
            case 2 -> {
                return bet.getBetOdds();
            }
            case 3 -> {
                System.out.println(bet.getOdds().getGame().getState());
                if (!bet.getOdds().getGame().getState().equals("FT")){
                    return "?";
                } else {
                    if (bet.getState().equals("✓")) {
                        return "WIN";
                    } else {
                        return "X";
                    }
                }
            }
            default ->
                throw new AssertionError();
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void addBet(Bet bet){
        listOfBets.add(bet);
        fireTableDataChanged();
    }
    
    public void removeBet(int rowIndex){
        listOfBets.remove(rowIndex);
        fireTableDataChanged();
    }
    
    public void updateBet(Bet bet){
        for (Bet b : listOfBets) {
            if(b.getOdds().getGame().getGameID()==bet.getOdds().getGame().getGameID()){
                b=bet;
                fireTableDataChanged();
                return;
            }
        }
    }
    
    public void setListOfBets(ArrayList<Bet> listOfBets){
        this.listOfBets = listOfBets;
        fireTableDataChanged();
    }
}
