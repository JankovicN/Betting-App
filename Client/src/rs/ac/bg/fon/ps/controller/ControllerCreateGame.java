/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
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
public class ControllerCreateGame {

    private final FormGame formCreateGame;
    private ArrayList<Team> teams;
    private Game game;
    private ArrayList<Odds> listOfOdds;

    public ControllerCreateGame(FormGame formCreateGame) {
        this.formCreateGame = formCreateGame;
        this.teams = new ArrayList<>();
        this.game = new Game();
        this.listOfOdds = new ArrayList<>();
    }

    public void openForm() throws Exception {
        formCreateGame.setVisible(true);
        setupForm();
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }

    public void setupForm() throws Exception {
        JLabel lblHomeGoals = formCreateGame.getLblHomeGoals();
        lblHomeGoals.setVisible(false);
        JLabel lblAwayGoals = formCreateGame.getLblAwayGoals();
        lblAwayGoals.setVisible(false);
        JLabel lblOver = formCreateGame.getLblOver();
        lblOver.setVisible(false);
        JLabel lblScore = formCreateGame.getLblScore();
        lblScore.setVisible(false);

        JTextField txtHomeGoals = formCreateGame.getTxtHomeGoals();
        txtHomeGoals.setVisible(false);
        JTextField txtAwayGoals = formCreateGame.getTxtAwayGoals();
        txtAwayGoals.setVisible(false);
        JRadioButton rbtnOver = formCreateGame.getRbtnOver();
        rbtnOver.setVisible(false);

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
        } else {
            throw response.getException();
        }

    }

    public FormGame getFormCreateGame() {
        return formCreateGame;
    }

    public void setOdds(ArrayList<Odds> listOfOdds) {
        this.listOfOdds = listOfOdds;
    }

    public Game getGame() {
        if (game.getHome().getTeamName() == null || game.getAway().getTeamName() == null) {
            return null;
        }
        return game;
    }

    public void confirmTeams() {
        this.game = new Game();
        game.setHome((Team) formCreateGame.getCmbHomeTeam().getSelectedItem());
        game.setAway((Team) formCreateGame.getCmbAwayTeam().getSelectedItem());
        listOfOdds = new ArrayList<>();
        if (Controller.getInstance().getControllerAddOdds() != null) {
            Controller.getInstance().getControllerAddOdds().resetOdds();
        }
    }

    public boolean compareTeams() {
        return !((Team) formCreateGame.getCmbHomeTeam().getSelectedItem()).getTeamName().equals(((Team) formCreateGame.getCmbAwayTeam().getSelectedItem()).getTeamName());
    }

    public boolean validateConfirmTeams() {
        return game.getHome().getTeamName() != null && game.getAway().getTeamName() != null;
    }

    public boolean validateCreateGame() {
        return !listOfOdds.isEmpty();
    }

    public boolean addDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse(formCreateGame.getTxtDate().getText());
            game.setDateOfPlay(date);
            return true;
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(formCreateGame, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void createOdds() {

        

        if (addDate() && validateCreateGame() && validateConfirmTeams() && compareTeams()) {

            Iterator<Odds> iter = listOfOdds.iterator();
            while (iter.hasNext()) {
                Odds o = iter.next();
                if (o.getOdds() == 1.0) {
                    iter.remove();
                }
            }
            try {
                Request request = new Request(Operations.CREATE_GAME, game);
                Response response = Communication.getInstance().sendRequest(request, "Request for creating game is sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                    game.setGameID(((Game) response.getResult()).getGameID());
                } else {
                    throw response.getException();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(formCreateGame, ex.getMessage(), "Error creating game ", JOptionPane.ERROR_MESSAGE);
            }

            try {
                Request request = new Request(Operations.CREATE_ODDS, listOfOdds);
                Response response = Communication.getInstance().sendRequest(request, "Request for creating odds is sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                    formCreateGame.dispose();
                    Controller.getInstance().getControllerMain().openForm();

                } else {
                    throw response.getException();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(formCreateGame, ex.getMessage(), "Error creating odds ", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(formCreateGame, "Please confirm teams and add odds to create game! :D", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
