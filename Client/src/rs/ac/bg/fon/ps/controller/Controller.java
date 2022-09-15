/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.form.DialogAddOdds;
import rs.ac.bg.fon.ps.view.form.DialogCreateTeam;
import rs.ac.bg.fon.ps.view.form.DialogGameOdds;
import rs.ac.bg.fon.ps.view.form.FormGame;
import rs.ac.bg.fon.ps.view.form.FormLogin;
import rs.ac.bg.fon.ps.view.form.FormMain;
import rs.ac.bg.fon.ps.view.form.DialogViewTicket;
import rs.ac.bg.fon.ps.view.form.FormCancelTicket;
import rs.ac.bg.fon.ps.view.form.FormPlayTicket;
import rs.ac.bg.fon.ps.view.form.FormViewGames;

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
    private ControllerConfirmTicket controllerConfirmTicket;
    private ControllerViewGames controllerViewGames;
    private ControllerEditGame controllerEditGame;
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

    public ControllerAddOdds getControllerAddOdds() {
        return controllerAddOdds;
    }
    public void setControllerAddOddsToNull() {
        controllerAddOdds = null;
    }

    public void openDialogAddOdds() throws Exception {
        if (this.controllerAddOdds == null) {
            this.controllerAddOdds = new ControllerAddOdds(new DialogAddOdds(this.controllerCreateGame.getFormCreateGame(), false), controllerCreateGame.getGame());
        }
        this.controllerAddOdds.openDialogAddOdds();
    }
    
    public void openDialogEditOdds() throws Exception{
        if (this.controllerAddOdds == null) {
            this.controllerAddOdds = new ControllerAddOdds(new DialogAddOdds(this.controllerEditGame.getFormEditGame(), true), controllerEditGame.getGame());
        }
        this.controllerAddOdds.openDialogEditOdds();
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
    
    public ControllerConfirmTicket getControllerConfirmTicket() {
        return controllerConfirmTicket;
    }

    public void openDialogConfirmTicket() throws Exception {
        this.controllerConfirmTicket = new ControllerConfirmTicket(new DialogViewTicket(this.controllerPlayTicket.getFormPlayTicket(), false), controllerPlayTicket.getTicket());
        this.controllerConfirmTicket.openForm();
    }

    public ControllerViewGames getControllerViewGames() {
        return controllerViewGames;
    }

    public void openFormViewGames() throws Exception{
        this.controllerViewGames = new ControllerViewGames(new FormViewGames());
        this.controllerViewGames.openForm();
    }
    
    public ControllerEditGame getControllerEditGame(){
        return controllerEditGame;
    }
    
    public void openFormEditGame(){
        Game game= controllerViewGames.getSelectedGame();
        if(game!=null){
            this.controllerEditGame = new ControllerEditGame(new FormGame(), game);
            this.controllerEditGame.openForm();
        }
        
        
    }
    
    public void openAddOrEditOddsDialog(){
        if (controllerEditGame!=null && controllerEditGame.getFormEditGame().isVisible()) {
            Controller.getInstance().getControllerEditGame().openEditOddsDialog();
        } else {
            Controller.getInstance().getControllerCreateGame().openAddOddsDialog();
        }
    }
    
    public void confirmAddOrEditOdds(){
        ArrayList<Odds> listOfOdds=controllerAddOdds.getOdds();
        if (controllerEditGame!=null && controllerEditGame.getFormEditGame().isVisible()) {
            Controller.getInstance().getControllerEditGame().updateOdds(listOfOdds);
        } else {
            Controller.getInstance().getControllerCreateGame().setOdds(listOfOdds);
        }
    }
    
    public void confirmAddOrEditGame(){
        if (controllerEditGame!=null && controllerEditGame.getFormEditGame().isVisible()) {
            Controller.getInstance().getControllerEditGame().saveChanges();
        } else {
            Controller.getInstance().getControllerCreateGame().createOdds();
        }
    }
    
}
