/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.users;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.threads.HandleClientThread;

/**
 *
 * @author nikol
 */
public class Users {
    
    private static Users instance;
    private User admin;
    private List<HandleClientThread> clientThreads;

    public Users() {
        
        admin= new User();
        clientThreads= new ArrayList<>();
    }
    
    public static Users getInstance() {
        if(instance==null)
            instance= new Users();
        return instance;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<User> getClients() {
        List<User> clients= new ArrayList<>();
        for (HandleClientThread ct : clientThreads) {
            clients.add(ct.getClient());
        }
        return clients;
    }

    public List<HandleClientThread> getClientThreads() {
        return clientThreads;
    }
    

    public void addClientThread(HandleClientThread client){
        this.clientThreads.add(client);
    }
    
    public void setClientThreads(List<HandleClientThread> clients) {
        this.clientThreads = clients;
    }
    
    
}
