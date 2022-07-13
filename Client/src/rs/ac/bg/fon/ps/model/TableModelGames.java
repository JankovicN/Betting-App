/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Game;

/**
 *
 * @author nikol
 */
public class TableModelGames extends AbstractTableModel{

    private ArrayList<Game> listOfGames;
    private String[] columns = new String[]{"Game", "Date of Play"};
    
    public TableModelGames(){
        this.listOfGames = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return listOfGames.size();
    }

    @Override
    public int getColumnCount() {
       return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Game game = listOfGames.get(rowIndex);
        
        switch (columnIndex) {
            case 0 -> {
                return game.toString();
            }
            case 1 -> {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                return sdf.format(game.getDateOfPlay());
            }
            default ->
                throw new AssertionError();
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    public Game getGame(int rowIndex){
        return listOfGames.get(rowIndex);
    }
    
    public ArrayList<Game> getListOfGames(){
        return this.listOfGames;
    }
    
    public void setListOfGames(ArrayList<Game> listOfGames){
        this.listOfGames = listOfGames;
        fireTableDataChanged();
    }
    
}
