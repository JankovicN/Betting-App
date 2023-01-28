/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.model.TableModelAddOdds;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.FormGame;

/**
 *
 * @author nikol
 */
public class ControllerCreateGame {

    private final FormGame formCreateGame;
    private ArrayList<Team> teams;
    private Game game;
    private ArrayList<Odds> listOfOdds;
    TableModelAddOdds tmao = new TableModelAddOdds();

    public ControllerCreateGame(FormGame formCreateGame) {
        this.formCreateGame = formCreateGame;
        this.teams = new ArrayList<>();
        this.game = new Game();
        this.listOfOdds = new ArrayList<>();
    }

    public void openForm() throws Exception {
        System.out.println("Opened Form Create Game ");
        formCreateGame.setVisible(true);
        setupForm();
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }

    public void setupForm() throws Exception {

        Request request = new Request(Operations.GET_TEAMS, null);
        Response response = Communication.getInstance().sendRequest(request, "Request for all teams is sent..");

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            ArrayList<Team> listOfTeams = (ArrayList<Team>) response.getResult();

            JComboBox cmbHomeTeam = formCreateGame.getCmbHomeTeam();
            JComboBox cmbAwayTeam = formCreateGame.getCmbAwayTeam();
            if (listOfTeams == null || listOfTeams.isEmpty()) {
                cmbAwayTeam.addItem("No teams avaliable");
                cmbHomeTeam.addItem("No teams avaliable");
            } else {
                cmbAwayTeam.removeAll();
                cmbHomeTeam.removeAll();
                teams = listOfTeams;
                for (Team team : listOfTeams) {
                    cmbAwayTeam.addItem(team);
                    cmbHomeTeam.addItem(team);
                }
            }
            request = new Request(Operations.GET_BETTYPE, null);
            response = Communication.getInstance().sendRequest(request, "Request for bet types sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {

                ArrayList<BetType> listOfBetTypes = (ArrayList<BetType>) response.getResult();

                for (BetType betType : listOfBetTypes) {
                    Odds o = new Odds(game, betType, 1.0);
                    listOfOdds.add(o);
                }
            } else {
                throw response.getException();
            }
            JTable table = formCreateGame.getTblOdds();
            tmao.setListOfOdds(listOfOdds);
            table.setModel(tmao);
            formCreateGame.setTblOdds(table);
        } else {
            throw response.getException();
        }

    }

    public FormGame getFormCreateGame() {
        return formCreateGame;
    }

    public Game getGame() {
        if (game.getHome().getTeamName() == null || game.getAway().getTeamName() == null) {
            return null;
        }
        return game;
    }

    public boolean compareTeams() {
        return !((Team) formCreateGame.getCmbHomeTeam().getSelectedItem()).getTeamName().equals(((Team) formCreateGame.getCmbAwayTeam().getSelectedItem()).getTeamName());
    }

    public boolean validateConfirmTeams() {
        return game.getHome().getTeamName() != null && game.getAway().getTeamName() != null;
    }

    public boolean validateGameOdds() {
        for (Odds odd : listOfOdds) {
            if (odd.getOdds() < 1) {
                return false;
            }
        }
        return !listOfOdds.isEmpty();
    }

    public void addDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            String day = formCreateGame.getTxtDateDay().getText();
            String month = formCreateGame.getTxtDateMonth().getText();
            String year = formCreateGame.getTxtDateYear().getText();
            String hours = formCreateGame.getTxtDateHour().getText();
            String minutes = formCreateGame.getTxtDateMinutes().getText();
            String dateString = day + "." + month + "." + year + " " + hours + ":" + minutes;
            Date date = sdf.parse(dateString);
            game.setDateOfPlay(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(formCreateGame, "Invalid date format!\n Date must be in format dd.MM.yyyy HH:mm\n dd - days, MM - months yyyy - years\n HH - hours, mm - minutes", "Invalid input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createOdds() {
        createGame();
        if (validate()) {

            Iterator<Odds> iter = listOfOdds.iterator();
            while (iter.hasNext()) {
                Odds o = iter.next();
                if (o.getOdds() == 1.0) {
                    iter.remove();
                }else{
                    o.setGame(game);
                }
            }
            try {
                Request request = new Request(Operations.CREATE_GAME, listOfOdds);
                Response response = Communication.getInstance().sendRequest(request, "Request for creating game is sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                    Controller.getInstance().getControllerMain().openForm();
                    JOptionPane.showMessageDialog(formCreateGame, "Game saved!\n");
                    formCreateGame.dispose();
                } else {
                    throw response.getException();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(formCreateGame, "Error creating game!\n" + ex.getMessage(), "Error creating game ", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(formCreateGame, "Sistem ne moze da sacuva utakmicu", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void createGame() {
        try {
            if (compareTeams()) {
                this.game = new Game();
                game.setHome((Team) formCreateGame.getCmbHomeTeam().getSelectedItem());
                game.setAway((Team) formCreateGame.getCmbAwayTeam().getSelectedItem());
                listOfOdds = tmao.getListOfOdds();
                addDate();
            } else {
                JOptionPane.showMessageDialog(formCreateGame, "You must choose two different teams!", "Information", JOptionPane.OK_OPTION);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formCreateGame, "Error confirming teams!\n Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validate() {
        return checkDate() && validateGameOdds() && validateConfirmTeams() && compareTeams();
    }

    private boolean checkDate() {
        return game.getDateOfPlay().after(new Date());
    }

}
