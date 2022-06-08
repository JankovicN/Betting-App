/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
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

    public ControllerCreateGame(FormGame formCreateGame) {
        this.formCreateGame = formCreateGame;
        this.teams = new ArrayList<>();
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

                cmbAwayTeam.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Team awayTeam = (Team) cmbAwayTeam.getSelectedItem();
                        cmbHomeTeam.removeAll();
                        ArrayList<Team> homeTeams = teams;
                        homeTeams.remove(awayTeam);
                    }

                });

//                cmbAwayTeam.addItemListener((ItemEvent e) -> {
//                    Team awayTeam = (Team) cmbAwayTeam.getSelectedItem();
//                    cmbHomeTeam.removeAll();
//                    ArrayList<Team> homeTeams = teams;
//                    homeTeams.remove(awayTeam);
//                });
                cmbHomeTeam.addItemListener((ItemEvent e) -> {
                    Team homeTeam = (Team) cmbHomeTeam.getSelectedItem();
                    cmbAwayTeam.removeAll();
                    ArrayList<Team> awayTeams = teams;
                    awayTeams.remove(homeTeam);
                });
            }
        } else {
            throw response.getException();
        }

    }

    public void addGame(String teamName) {

        for (Team team : teams) {
            if (team.getTeamName().equals(teamName)) {
                JOptionPane.showMessageDialog(formCreateGame, "Team already exists!", "Create team", JOptionPane.OK_OPTION);
                return;
            }
        }
        try {
            Request request = new Request(Operations.ADD_TEAM, teamName);
            Response response = Communication.getInstance().sendRequest(request, "Request for tickets played by user is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                Team newTeam = (Team) response.getResult();

                JComboBox cmbHomeTeam = formCreateGame.getCmbHomeTeam();
                JComboBox cmbAwayTeam = formCreateGame.getCmbAwayTeam();

                cmbAwayTeam.addItem(newTeam);
                cmbHomeTeam.addItem(newTeam);

            } else {
                throw response.getException();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formCreateGame, ex.getMessage(), "Error adding team! ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public FormGame getFormCreateGame() {
        return formCreateGame;
    }

}
