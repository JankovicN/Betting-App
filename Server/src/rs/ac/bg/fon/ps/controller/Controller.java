/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;
import rs.ac.bg.fon.ps.operations.User.LoginUser;

/**
 *
 * @author nikol
 */
public class Controller {
    
    private static Controller instance;

    public Controller() {
    }
    
    public static Controller getInstance(){
        if(instance==null){
            instance= new Controller();
        }
        return instance;
    }

    public User login(User client) throws Exception {
        User user = client;
        AbstractGenericOperation operation = new LoginUser();
        operation.execute(user);
        user = ((LoginUser)operation).getUser();
        return user;
    }
    
}
