/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.form.DialogAddOdds;
import rs.ac.bg.fon.ps.view.form.DialogCreateTeam;
import rs.ac.bg.fon.ps.view.form.DialogGameOdds;
import rs.ac.bg.fon.ps.view.form.FormGame;
import rs.ac.bg.fon.ps.view.form.FormLogin;
import rs.ac.bg.fon.ps.view.form.FormMain;
import rs.ac.bg.fon.ps.view.form.DialogViewTicket;
import rs.ac.bg.fon.ps.view.form.FormPlayTicket;

/**
 *
 * @author nikol
 */
public class Controller {

    private static Controller instance;
    private ControllerLogin controllerLogin;
    private ControllerMain controllerMain;
    private ControllerViewTicket controllerViewTicket;
    private ControllerCreateGame controllerCreateGame;
    private ControllerCreateTeam controllerCreateTeam;
    private ControllerAddOdds controllerAddOdds;
    private ControllerGameOdds controllerGameOdds;
    private ControllerPlayTicket controllerPlayTicket;
    private User currentUser;

    public Controller() {
    }

    public static Controller getInstance() {

        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void operFormLogin() {
        this.controllerLogin = new ControllerLogin(new FormLogin());
        this.controllerLogin.openForm();
    }

    public ControllerLogin getControllerLogin() {
        return controllerLogin;
    }

    public void openFormMain() throws Exception {
        if (this.controllerMain == null) {
            this.controllerMain = new ControllerMain(new FormMain());
        }
        this.controllerMain.openForm();
    }

    public ControllerMain getControllerMain() {
        return controllerMain;
    }

    public void openFormViewTicket(Ticket ticket) throws Exception {
        this.controllerViewTicket = new ControllerViewTicket(new DialogViewTicket(this.controllerMain.getFormMain(), false), ticket);
        this.controllerViewTicket.openForm();
    }

    public ControllerViewTicket getControllerViewTicket() {
        return controllerViewTicket;
    }

    public void openFormCreateGame() throws Exception {
        this.controllerCreateGame = new ControllerCreateGame(new FormGame());
        this.controllerCreateGame.openForm();
    }

    public ControllerCreateGame getControllerCreateGame() {
        return controllerCreateGame;
    }

    public void openDialogCreateTeam() throws Exception {
        this.controllerCreateTeam = new ControllerCreateTeam(new DialogCreateTeam(this.controllerCreateGame.getFormCreateGame(), false), controllerCreateGame.getTeams());
        this.controllerCreateTeam.openForm();
    }

    public ControllerCreateTeam getControllerCreateTeam() {
        return controllerCreateTeam;
    }

    public ControllerAddOdds getControllerAddOdds() {
        return controllerAddOdds;
    }

    public void openDialogAddOdds() throws Exception {
        if (this.controllerAddOdds == null) {
            this.controllerAddOdds = new ControllerAddOdds(new DialogAddOdds(this.controllerCreateGame.getFormCreateGame(), false), controllerCreateGame.getGame());
        }
        this.controllerAddOdds.openForm();
    }

    public ControllerGameOdds getControllerGameOdds() {
        return controllerGameOdds;
    }

    public void openDialogGameOdds() throws Exception {
        this.controllerGameOdds = new ControllerGameOdds(new DialogGameOdds(this.controllerPlayTicket.getFormPlayTicket(), false), controllerPlayTicket.getSelectedGame());
        this.controllerGameOdds.openForm();
    }

    public ControllerPlayTicket getControllerPlayTicket() {
        return controllerPlayTicket;
    }

    public void openFormPlayTicket() throws Exception {
        this.controllerPlayTicket = new ControllerPlayTicket(new FormPlayTicket());
        this.controllerPlayTicket.openForm();
    }

}
