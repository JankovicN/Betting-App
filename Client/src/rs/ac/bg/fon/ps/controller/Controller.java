/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.form.FormLogin;
import rs.ac.bg.fon.ps.view.form.FormMain;

/**
 *
 * @author nikol
 */
public class Controller {
    
    private static Controller instance;
    private ControllerLogin controllerLogin;
    private ControllerMain controllerMain;
    private User currentUser;

    public Controller() {
    }
    
    public static Controller getInstance(){
    
        if(instance==null){
            instance=new Controller();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
    public void operFormLogin(){
        this.controllerLogin=new ControllerLogin(new FormLogin());
        this.controllerLogin.openForm();
    }
    
    public ControllerLogin getControllerLogin(){
        return controllerLogin;
    }

    public void openMainForm() throws Exception {
        this.controllerMain=new ControllerMain(new FormMain());
        this.controllerMain.openForm();
    }

    public ControllerMain getControllerMain() {
        return controllerMain;
    }
}
