/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.FormGame;

/**
 *
 * @author nikol
 */
public class ControllerEditGame {

    private final FormGame formEditGame;
    private Game game;
    private ArrayList<Odds> listOfOdds;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public ControllerEditGame(FormGame formGame, Game selectedGame) {
        this.formEditGame = formGame;
        this.game = selectedGame;
        listOfOdds = new ArrayList<>();
    }

    public FormGame getFormEditGame() {
        return formEditGame;
    }

    public Game getGame() {
        return game;
    }

    public void openForm() {
        if (game != null) {
            System.out.println("Opened Form Edit Game");
            formEditGame.setVisible(true);
            setupForm();
        }
    }

    private void setupForm() {

        setComboboxItem(formEditGame.getCmbHomeTeam(), game.getHome());
        setComboboxItem(formEditGame.getCmbAwayTeam(), game.getAway());

        formEditGame.getLblTitle().setText("UPDATE GAME");
        formEditGame.getBtnAddOdds().setText("EDIT ODDS");
        formEditGame.getBtnConfirmTeams().setVisible(false);
        formEditGame.getBtnCreateTeam().setVisible(false);
        String date = sdf.format(game.getDateOfPlay());
        formEditGame.getTxtDate().setText(date);
        formEditGame.getTxtHomeGoals().setText("0");
        formEditGame.getTxtAwayGoals().setText("0");

    }

    private void setComboboxItem(JComboBox cmb, Team team) {
        cmb.removeAll();
        cmb.addItem(team);
        cmb.setEnabled(false);
    }

    void openEditOddsDialog() {
        try {
            Controller.getInstance().openDialogEditOdds();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formEditGame, ex.getMessage(), "Error opening DialogEditOdds!", JOptionPane.ERROR_MESSAGE);
        }
    }

    void updateOdds(ArrayList<Odds> listOfOdds) {
        this.listOfOdds = listOfOdds;
    }

    public void saveChanges() {

        String homeGoals = formEditGame.getTxtHomeGoals().getText();
        String awayGoals = formEditGame.getTxtAwayGoals().getText();
        if (!homeGoals.equals("0")) {
            game.setHomeGoals(Integer.parseInt(homeGoals));
        }
        if (!awayGoals.equals("0")) {
            game.setAwayGoals(Integer.parseInt(awayGoals));
        }

        String dateString = formEditGame.getTxtDate().getText();
        if(!dateString.equals(sdf.format(game.getDateOfPlay()))){
            try {
                Date date = sdf.parse(dateString);
                game.setDateOfPlay(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(formEditGame, ex.getMessage(), "Invalid date format!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        game.setIsOver(isOver());
        
        if(isOver()){
            
            // SVE OVO SE DESAVA U NOVOM THREADU
            
            // GET ALL BETS FOR GAME
            
            // GO THROUGH BETS AND CHECK IF BET HAS PASSED
            
                // YES: GET ALL BETS FOR TICKET
                    // UPDATE BET: passed -> true
                    // CHECK IF ALL BETS HAVE PASSED
                        // YES: UPDATE TICKET: processed -> completed && win -> true
                            
                
                // NO: 
                    // UPDATE BET: passed -> false
                    // UPDATE TICKET: processed -> completed && win -> false
            
        }else{
            // UPDATE ODDS
        }

        // UPDATE GAME
        
    }
    
    private boolean isOver(){
        return formEditGame.getRbtnOver().isSelected();
    }
}
