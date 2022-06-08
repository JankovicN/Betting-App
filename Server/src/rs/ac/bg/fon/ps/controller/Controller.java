/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;
import rs.ac.bg.fon.ps.operations.User.GetAllUsers;
import rs.ac.bg.fon.ps.operations.User.LoginUser;
import rs.ac.bg.fon.ps.operations.team.AddTeam;
import rs.ac.bg.fon.ps.operations.team.GetTeams;
import rs.ac.bg.fon.ps.operations.ticket.GetTicket;
import rs.ac.bg.fon.ps.operations.ticket.GetUserTickets;
import rs.ac.bg.fon.ps.view.form.FormServer;

/**
 *
 * @author nikol
 */
public class Controller {

    private static Controller instance;
    private FormServer formServer;
    private final ArrayList<User> loggedInUsers;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    
    public Controller() {
        loggedInUsers= new ArrayList<>();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    public void setFormServer(FormServer formServer) {
        this.formServer = formServer;
    }
    
    public boolean isLoggedIn(User user){
        return loggedInUsers.contains(user);
    }

    public User login(User client) throws Exception {
        User user = client;
        AbstractGenericOperation operation = new LoginUser();
        operation.execute(user);
        user = ((LoginUser) operation).getUser();
        loginUser(user);
        return user;
    }

    public ArrayList<Ticket> getUserTickets(User client) throws Exception {
        User user = client;
        AbstractGenericOperation operation = new GetUserTickets();
        operation.execute(user);
        ArrayList<Ticket> listOfTickets = ((GetUserTickets) operation).getList();
        return listOfTickets;
    }

    public Ticket getTicket(Ticket ticket) throws Exception {
        Ticket requestedTicket = ticket;
        AbstractGenericOperation operation = new GetTicket();
        operation.execute(requestedTicket);
        requestedTicket=((GetTicket)operation).getTicket();
        return requestedTicket;
    }

    public ArrayList<Team> getTeams() throws Exception {
        
        AbstractGenericOperation operation = new GetTeams();
        operation.execute(new Team());
        ArrayList<Team> listOfTeams=((GetTeams)operation).getListOfTeams();
        return listOfTeams;
    }


    public Team addTeam(String teamName) throws Exception{
        Team newTeam = new Team();
        newTeam.setTeamName(teamName);
        AbstractGenericOperation operation = new AddTeam();
        operation.execute(newTeam);
        newTeam.setTeamID(((AddTeam)operation).getTeamID());
        return newTeam; 
    }

    public ArrayList<User> getAllUsers() throws Exception{
        AbstractGenericOperation operation = new GetAllUsers();
        operation.execute(new User());
        ArrayList<User> listOfUsers=((GetAllUsers)operation).getListOfUsers();
        return listOfUsers;
    }

    public void loginUser(User user){
        String date = sdf.format(new Date());
        user.setDateOfLogin(date);
        loggedInUsers.add(user);
        formServer.getTmu().loginUser(user);
    }
    
    public void logoutUser(User client) {
        loggedInUsers.remove(client);
        formServer.getTmu().logoutUser(client);
    }
    
}
