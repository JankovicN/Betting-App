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
        System.out.println("Opened Dialog Create Team ");
        this.dialogCreateTeam.setVisible(true);
    }

    public boolean addTeam(String teamName) {

        if(teamName.length()==0){
            
                JOptionPane.showMessageDialog(dialogCreateTeam, "Sistem ne moze da zapamti tim!", "Information", JOptionPane.OK_OPTION);
                return false;
        }
        for (Team team : teams) {
            if (team.getTeamName().equals(teamName)) {
                JOptionPane.showMessageDialog(dialogCreateTeam, "Tim vec postoji!", "Information", JOptionPane.OK_OPTION);
                return false;
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
                JOptionPane.showMessageDialog(dialogCreateTeam, "Sistem je zapamtio tim!");
                return true;

            } else {
                throw response.getException();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialogCreateTeam, "Error adding team!\n" + ex.getMessage(), "Error adding team! ", JOptionPane.ERROR_MESSAGE);
                return false;
        }
    }

}
