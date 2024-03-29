/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.User;

import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class LoginUser extends AbstractGenericOperation{
    
    User user;

    public LoginUser() {
        this.user=new User();
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
    
        User user = (User) param;
        List<User> users =(List<User>)repository.search(user);
        
        if(users==null){
            throw new Exception("Invalid username or password!");
        }
        this.user=users.get(0);
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof User)) {
            throw new Exception("Invalid data for User!");
        }
    }
    public User getUser(){
        return this.user;
    }
}
