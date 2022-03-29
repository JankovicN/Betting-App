/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.FormLogin;

/**
 *
 * @author nikol
 */
public class ControllerLogin {
    
    private final FormLogin formLogin;

    public ControllerLogin(FormLogin formLogin) {
        this.formLogin = formLogin;
    }
    
    public void openForm(){
        formLogin.setVisible(true);
    }
    
    public void validateForm(){
        return;
    }
    
    public User login() throws Exception{
        validateForm();
        
        User user=new User();
        user.setUsername(formLogin.getUsername());
        user.setPassword(formLogin.getPassword());
        
        Request request = new Request(Operations.LOGIN, user);
        Response response = Communication.getInstance().sendRequest(request, "Login request sent...");
        
        if(response.getResponseType().equals(ResponseType.SUCCESS)){
            
            user=(User) response.getResult();
            Controller.getInstance().setCurrentUser(user);
            return user;
        }else{
            throw response.getException();
        }
    }
}
