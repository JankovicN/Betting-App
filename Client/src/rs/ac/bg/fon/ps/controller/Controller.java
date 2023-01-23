/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.form.DialogCreateTeam;
import rs.ac.bg.fon.ps.view.form.DialogGameOdds;
import rs.ac.bg.fon.ps.view.form.FormGame;
import rs.ac.bg.fon.ps.view.form.FormLogin;
import rs.ac.bg.fon.ps.view.form.FormMain;
import rs.ac.bg.fon.ps.view.form.DialogViewTicket;
import rs.ac.bg.fon.ps.view.form.FormCancelTicket;
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
    private ControllerGameOdds controllerGameOdds;
    private ControllerPlayTicket controllerPlayTicket;
    private ControllerConfirmTicket controllerConfirmTicket;
    private ControllerCancelTicket controllerCancelTicket;
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

    public void openFormCancelTicket() throws Exception {
        this.controllerCancelTicket = new ControllerCancelTicket(new FormCancelTicket());
        this.controllerCancelTicket.openForm();
    }

    public ControllerCancelTicket getControllerCancelTicket() {
        return controllerCancelTicket;
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

    public ControllerPlayTicket getControllerPlayTicket() {
        return controllerPlayTicket;
    }

    public void openFormPlayTicket() throws Exception {
        this.controllerPlayTicket = new ControllerPlayTicket(new FormPlayTicket());
        this.controllerPlayTicket.openForm();
    }

    public ControllerConfirmTicket getControllerConfirmTicket() {
        return controllerConfirmTicket;
    }

    public void openDialogConfirmTicket() throws Exception {
        if (this.controllerPlayTicket.getTicket().getWager().compareTo(BigDecimal.ZERO) == 1 && !this.controllerPlayTicket.getTicket().getListOfBets().isEmpty()) {
            this.controllerConfirmTicket = new ControllerConfirmTicket(new DialogViewTicket(this.controllerPlayTicket.getFormPlayTicket(), false), controllerPlayTicket.getTicket());
            this.controllerConfirmTicket.openForm();
        }
    }

    public void confirmGame() {
        this.controllerCreateGame.createOdds();
    }

    void openDialogGameOdds() throws Exception {
        this.controllerGameOdds = new ControllerGameOdds(new DialogGameOdds(this.controllerPlayTicket.getFormPlayTicket(), false), controllerPlayTicket.getSelectedGame());
        this.controllerGameOdds.openForm();
    }

    public ControllerGameOdds getControllerGameOdds() {
        return controllerGameOdds;
    }

}
