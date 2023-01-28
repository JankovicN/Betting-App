/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.DialogAlterGame;

/**
 *
 * @author nikol
 */
public class ControllerAlterGame {

    private ArrayList<Team> listOfTeams;
    private Game game;
    private DialogAlterGame dialogAlterGame;

    public ControllerAlterGame(Game game, DialogAlterGame dialogAlterGame) {
        this.game = game;
        this.dialogAlterGame = dialogAlterGame;
    }

    public void openForm() throws Exception {
        System.out.println("Opened Dialog Alter Game");
        this.dialogAlterGame.setVisible(true);
        setupDialog();
    }

    private void setupDialog() {

        fillCmbs();
        setCmbTeams();
        setDate();

    }

    private void fillCmbs() {
        try {

            Request request = new Request(Operations.GET_TEAMS, null);
            Response response = Communication.getInstance().sendRequest(request, "Request for all teams is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                ArrayList<Team> listOfTeams = (ArrayList<Team>) response.getResult();

                JComboBox cmbHomeTeam = dialogAlterGame.getCmbHomeTeam();
                JComboBox cmbAwayTeam = dialogAlterGame.getCmbAwayTeam();
                if (listOfTeams == null || listOfTeams.isEmpty()) {
                    cmbAwayTeam.addItem("No teams avaliable");
                    cmbHomeTeam.addItem("No teams avaliable");
                } else {
                    cmbAwayTeam.removeAll();
                    cmbHomeTeam.removeAll();
                    this.listOfTeams = listOfTeams;
                    for (Team team : this.listOfTeams) {
                        cmbAwayTeam.addItem(team);
                        cmbHomeTeam.addItem(team);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error filling CMB: \n" + e.getMessage());
        }
    }

    private void setCmbTeams() {
        dialogAlterGame.getCmbHomeTeam().setSelectedItem(game.getHome());
        dialogAlterGame.getCmbAwayTeam().setSelectedItem(game.getAway());
    }

    private void setDate() {
        Date date = game.getDateOfPlay();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dialogAlterGame.getTxtDateDay().setText(String.valueOf(day));
        int month = calendar.get(Calendar.MONTH) + 1;
        dialogAlterGame.getTxtDateMonth().setText(String.valueOf(month));
        int year = calendar.get(Calendar.YEAR);
        dialogAlterGame.getTxtDateYear().setText(String.valueOf(year));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        dialogAlterGame.getTxtDateHour().setText(String.valueOf(hour));
        int minute = calendar.get(Calendar.MINUTE);
        dialogAlterGame.getTxtDateMinutes().setText(String.valueOf(minute));
    }

    public void addDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            String day = dialogAlterGame.getTxtDateDay().getText();
            String month = dialogAlterGame.getTxtDateMonth().getText();
            String year = dialogAlterGame.getTxtDateYear().getText();
            String hours = dialogAlterGame.getTxtDateHour().getText();
            String minutes = dialogAlterGame.getTxtDateMinutes().getText();
            String dateString = day + "." + month + "." + year + " " + hours + ":" + minutes;
            Date date = sdf.parse(dateString);
            game.setDateOfPlay(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(dialogAlterGame, "Invalid date format!\n Date must be in format dd.MM.yyyy HH:mm\n dd - days, MM - months yyyy - years\n HH - hours, mm - minutes", "Invalid input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean compareTeams() {
        return !((Team) dialogAlterGame.getCmbHomeTeam().getSelectedItem()).getTeamName().equals(((Team) dialogAlterGame.getCmbAwayTeam().getSelectedItem()).getTeamName());
    }

    public boolean validateConfirmTeams() {
        return game.getHome().getTeamName() != null && game.getAway().getTeamName() != null;
    }

    public void updateGame(){
        createGame();
        try {
                Request request = new Request(Operations.UPDATE_GAME, game);
                Response response = Communication.getInstance().sendRequest(request, "Request for updating game is sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                    dialogAlterGame.dispose();
                    Controller.getInstance().getControllerAllGames().refreshTable();
                } else {
                    throw response.getException();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialogAlterGame, "Error updating game!\n" + ex.getMessage(), "Error updating game ", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    private void createGame() {
        try {
            if (validate()) {
                game.setHome((Team) dialogAlterGame.getCmbHomeTeam().getSelectedItem());
                game.setAway((Team) dialogAlterGame.getCmbAwayTeam().getSelectedItem());
                addDate();
            } else {
                JOptionPane.showMessageDialog(dialogAlterGame, "You must choose two different teams!", "Information", JOptionPane.OK_OPTION);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialogAlterGame, "Error confirming teams!\n Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkDate() {
        return game.getDateOfPlay().after(new Date());
    }

    private boolean validate() {
        return checkDate() && validateConfirmTeams() && compareTeams();
    }

}
