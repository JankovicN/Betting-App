/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;
import rs.ac.bg.fon.ps.operations.BetType.GetAllBetTypes;
import rs.ac.bg.fon.ps.operations.User.GetAllUsers;
import rs.ac.bg.fon.ps.operations.User.LoginUser;
import rs.ac.bg.fon.ps.operations.bet.GetBetsForGame;
import rs.ac.bg.fon.ps.operations.bet.GetProcessedBets;
import rs.ac.bg.fon.ps.operations.bet.UpdateBet;
import rs.ac.bg.fon.ps.operations.game.AddGame;
import rs.ac.bg.fon.ps.operations.game.GetActiveGames;
import rs.ac.bg.fon.ps.operations.game.GetGamesNotFinished;
import rs.ac.bg.fon.ps.operations.game.GetGamesNotStarted;
import rs.ac.bg.fon.ps.operations.game.UpdateGame;
import rs.ac.bg.fon.ps.operations.odds.CreateOdds;
import rs.ac.bg.fon.ps.operations.odds.GetOddsForGame;
import rs.ac.bg.fon.ps.operations.team.AddTeam;
import rs.ac.bg.fon.ps.operations.team.GetTeams;
import rs.ac.bg.fon.ps.operations.ticket.CancelTicket;
import rs.ac.bg.fon.ps.operations.ticket.CreateTicket;
import rs.ac.bg.fon.ps.operations.ticket.GetProcessedTickets;
import rs.ac.bg.fon.ps.operations.ticket.GetTicket;
import rs.ac.bg.fon.ps.operations.ticket.GetTicketWithBets;
import rs.ac.bg.fon.ps.operations.ticket.GetUnprocessedTickets;
import rs.ac.bg.fon.ps.operations.ticket.GetUserTickets;
import rs.ac.bg.fon.ps.operations.ticket.ProcessTickets;
import rs.ac.bg.fon.ps.operations.ticket.UpdateTicket;
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
        System.out.println(listOfTickets);
        return listOfTickets;
    }

    public Ticket getTicketWithBets(Ticket ticket) throws Exception {
        Ticket requestedTicket = ticket;
        AbstractGenericOperation operation = new GetTicketWithBets();
        operation.execute(requestedTicket);
        requestedTicket=((GetTicketWithBets)operation).getTicket();
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

    public ArrayList<BetType> getBetTypes() throws Exception {
        AbstractGenericOperation operation = new GetAllBetTypes();
        operation.execute(new BetType());
        ArrayList<BetType> listOfBetTypes=((GetAllBetTypes)operation).getListOfBetTypes();
        return listOfBetTypes;
    }

    public void createOdds(ArrayList<Odds> arrayList) throws Exception {
        AbstractGenericOperation operation = new CreateOdds();
        operation.execute(arrayList);
    }

    public Game addGame(ArrayList<Odds> odds) throws Exception {
        AbstractGenericOperation operation = new AddGame();
        operation.execute(odds);
        return odds.get(0).getGame();
    }

    public ArrayList<Odds> getOddsForGame(Game game) throws Exception {
        AbstractGenericOperation operation = new GetOddsForGame();
        operation.execute(game);
        return ((GetOddsForGame)operation).getListOfOdds();
    }

    public ArrayList<Game> getGamesNotStarted() throws Exception {
        AbstractGenericOperation operation = new GetGamesNotStarted();
        operation.execute(null);
        return ((GetGamesNotStarted) operation).getListOfGames();
        
    }

    public Ticket createTicket(ArrayList<Bet> listOfBets) throws Exception {
        AbstractGenericOperation operation = new CreateTicket();
        operation.execute(listOfBets);
        return((CreateTicket) operation).getTicket();
    }

    public void updateGame(Game game) throws Exception {
        AbstractGenericOperation operation = new UpdateGame();
        operation.execute(game);
    }

    public ArrayList<Bet> getBetsForGame(int gameID) throws Exception {
        AbstractGenericOperation operation = new GetBetsForGame();
        operation.execute(gameID);
        return ((GetBetsForGame) operation).getListOfBets();
    }

    public Ticket getTicket(int ticketID) throws Exception {
        AbstractGenericOperation operation = new GetTicket();
        operation.execute(ticketID);
        return ((GetTicket) operation).getTicket();
    }

    public void updateTicket(Ticket ticket) throws Exception {
        AbstractGenericOperation operation = new UpdateTicket();
        operation.execute(ticket);
    }

    public void updateBet(Bet bet) throws Exception {
        AbstractGenericOperation operation = new UpdateBet();
        operation.execute(bet);
    }

    public ArrayList<Game> getActiveGames() throws Exception {
        AbstractGenericOperation operation = new GetActiveGames();
        operation.execute(null);
        return ((GetActiveGames) operation).getListOfGames();
    }

    public ArrayList<Ticket> getUnprocessedTickets() throws Exception {
        AbstractGenericOperation operation = new GetUnprocessedTickets();
        operation.execute(new Ticket());
        return ((GetUnprocessedTickets) operation).getListOfTickets();
    }

    public void cancelTicket(Ticket ticket) throws Exception {
        AbstractGenericOperation operation = new CancelTicket();
        operation.execute(ticket);
    }

    public void processTickets() throws Exception {
        AbstractGenericOperation operation = new ProcessTickets();
        operation.execute(new Ticket());
    }
    
    public ArrayList<Game> getNotFinishedGames() throws Exception {
        AbstractGenericOperation operation = new GetGamesNotFinished();
        operation.execute(null);
        return ((GetGamesNotFinished) operation).getListOfGames();
    }
    
    public ArrayList<Bet> getProcessedBets()throws Exception {
        AbstractGenericOperation operation = new GetProcessedBets();
        operation.execute(new Bet());
        return ((GetProcessedBets) operation).getListOfBets();
    }

    public List<Ticket> getProcessedTickets() throws Exception {
        AbstractGenericOperation operation = new GetProcessedTickets();
        operation.execute(new Ticket());
        return ((GetProcessedTickets) operation).getListOfTickets();
    }

}
