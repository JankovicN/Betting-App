/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.DialogCreateTeam;

/**
 *
 * @author nikol
 */
public class ControllerCreateTeam {

    private final DialogCreateTeam dialogCreateTeam;
    ArrayList<Team> teams;

    public ControllerCreateTeam(DialogCreateTeam dialogCreateTeam, ArrayList<Team> teams) {
        this.dialogCreateTeam = dialogCreateTeam;
        this.teams = teams;
    }

    public void openForm() {
        this.dialogCreateTeam.setVisible(true);
    }
    
        public void addTeam(String teamName) {

        for (Team team : teams) {
            if (team.getTeamName().equals(teamName)) {
                JOptionPane.showMessageDialog(dialogCreateTeam, "Team already exists!", "Create team", JOptionPane.OK_OPTION);
                return;
            }
        }
        try {
            Request request = new Request(Operations.ADD_TEAM, teamName);
            Response response = Communication.getInstance().sendRequest(request, "Request for tickets played by user is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                Team newTeam = (Team) response.getResult();

                JComboBox cmbHomeTeam = Controller.getInstance().getControllerCreateGame().getFormCreateGame().getCmbHomeTeam();
                JComboBox cmbAwayTeam = Controller.getInstance().getControllerCreateGame().getFormCreateGame().getCmbAwayTeam();

                cmbAwayTeam.addItem(newTeam);
                cmbHomeTeam.addItem(newTeam);

            } else {
                throw response.getException();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialogCreateTeam, ex.getMessage(), "Error adding team! ", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
