/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import java.net.Socket;
import java.util.ArrayList;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.Operations;

/**
 *
 * @author nikol
 */
public class HandleClientThread extends Thread {

    private final ServerThread serverThread;
    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private User client;

    public HandleClientThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
        this.client = new User();
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {

        try {
            while (true) {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                try {
                    processResponse(request, response);
                    response.setResponseType(ResponseType.SUCCESS);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    response.setResponseType(ResponseType.ERROR);
                    response.setException(ex);
                }
                sender.send(response);
            }
        } catch (Exception e) {
            System.out.println("Client " + client.getUsername() + " logged out!");
            Controller.getInstance().logoutUser(client);
        }
    }

    private void processResponse(Request request, Response response) throws Exception {

        switch (request.getOperation()) {

            case Operations.LOGIN:
                login(request, response);
                break;
            case Operations.GET_USER_TICKETS:
                getUserTickets(request, response);
                break;
            case Operations.GET_TICKET_WITH_BETS:
                getTicketWithBets(request, response);
                break;
            case Operations.GET_TEAMS:
                getTeams(request, response);
                break;
            case Operations.ADD_TEAM:
                addTeam(request, response);
                break;
            case Operations.GET_BETTYPE:
                getBetType(request, response);
                break;
            case Operations.CREATE_ODDS:
                createOdds(request, response);
                break;
            case Operations.CREATE_GAME:
                createGame(request, response);
                break;
            case Operations.GET_ODDS:
                getOddsForGame(request, response);
                break;
            case Operations.GET_GAMES_NOT_STARTED:
                getGamesNotStarted(request, response);
                break;
            case Operations.GET_ACTIVE_GAMES:
                getActiveGames(request, response);
                break;
            case Operations.CREATE_TICKET:
                createTicket(request, response);
                break;
            case Operations.UPDATE_GAME:
                updateGame(request, response);
                break;
            case Operations.GET_BETS_FOR_GAME:
                getBetsForGame(request, response);
                break;
            case Operations.GET_TICKET:
                getTicket(request, response);
                break;
            case Operations.UPDATE_TICKET:
                updateTicket(request, response);
                break;
            case Operations.UPDATE_BET:
                updateBet(request, response);
                break;
            case Operations.GET_UNPROCESSED_TICKETS:
                getUnprocessedTickets(request, response);
                break;
            case Operations.CANCEL_TICKET:
                cancelTicket(request, response);
                break;

        }
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    private void login(Request request, Response response) throws Exception {

        User user = (User) request.getArgument();
        setClient(Controller.getInstance().login(user));
        System.out.println("Login successful!");
        response.setResult(client);
    }

    private void getUserTickets(Request request, Response response) throws Exception {

        ArrayList<Ticket> listOfTickets = Controller.getInstance().getUserTickets(client);
        System.out.println("Request for list of played tickets was successful!");
        response.setResult(listOfTickets);
    }

    private void getTicketWithBets(Request request, Response response) throws Exception {

        Ticket requestedTicket = (Ticket) request.getArgument();
        Ticket ticket = Controller.getInstance().getTicketWithBets(requestedTicket);
        System.out.println("Requst for ticket was successful!");
        response.setResult(ticket);
    }

    private void getTeams(Request request, Response response) throws Exception {

        ArrayList<Team> listOfTeams = Controller.getInstance().getTeams();
        System.out.println("Request for teams was successful!");
        response.setResult(listOfTeams);
    }

    private void addTeam(Request request, Response response) throws Exception {

        Team newTeam = Controller.getInstance().addTeam((String) request.getArgument());
        System.out.println("Request for adding team was successful!");
        response.setResult(newTeam);
    }

    private void getBetType(Request request, Response response) throws Exception {
        ArrayList<BetType> listOfBetTypes = Controller.getInstance().getBetTypes();
        System.out.println("Request for bet types was successful!");
        response.setResult(listOfBetTypes);
    }

    private void createOdds(Request request, Response response) throws Exception {
        Controller.getInstance().createOdds((ArrayList<Odds>) request.getArgument());
        System.out.println("Request for rreating odds was successful!");
    }

    private void createGame(Request request, Response response) throws Exception {
        Game newGame = Controller.getInstance().addGame((ArrayList<Odds>) request.getArgument());
        System.out.println("Request for adding game was successful!");
        response.setResult(newGame);
    }

    private void getOddsForGame(Request request, Response response) throws Exception {
        ArrayList<Odds> listOfOdds = Controller.getInstance().getOddsForGame((Game) request.getArgument());
        System.out.println("Request for game odds was successful");
        response.setResult(listOfOdds);
    }

    private void getGamesNotStarted(Request request, Response response) throws Exception {
        ArrayList<Game> listOfGames = Controller.getInstance().getGamesNotStarted();
        System.out.println("Request for games was successful");
        response.setResult(listOfGames);
    }

    private void createTicket(Request request, Response response) throws Exception {
        Ticket ticket = Controller.getInstance().createTicket((ArrayList<Bet>) request.getArgument());
        System.out.println("Request for creting ticket was successful");
        response.setResult(ticket);
    }

    private void updateGame(Request request, Response response) throws Exception {
        Controller.getInstance().updateGame((Game) request.getArgument());
        System.out.println("Request for updating game was successful");
    }

    private void getBetsForGame(Request request, Response response) throws Exception {
        ArrayList<Bet> listOfBets = Controller.getInstance().getBetsForGame((int) request.getArgument());
        System.out.println("Request for placed bets for game was successful");
        response.setResult(listOfBets);
    }

    private void getTicket(Request request, Response response) throws Exception {
        Ticket ticket = Controller.getInstance().getTicket((int) request.getArgument());
        System.out.println("Request for ticket by id was successful");
        response.setResult(ticket);
    }

    private void updateTicket(Request request, Response response) throws Exception {
        Controller.getInstance().updateTicket((Ticket) request.getArgument());
        System.out.println("Request for updating Ticket was successful");
    }

    private void updateBet(Request request, Response response) throws Exception {
        Controller.getInstance().updateBet((Bet) request.getArgument());
        System.out.println("Request for updating Bet was successful");
    }

    private void getActiveGames(Request request, Response response) throws Exception {
        ArrayList<Game> listOfGames = Controller.getInstance().getActiveGames();
        System.out.println("Request for active games was successful");
        response.setResult(listOfGames);
    }

    private void getUnprocessedTickets(Request request, Response response) throws Exception {
        ArrayList<Ticket> listOfTickets = Controller.getInstance().getUnprocessedTickets();
        System.out.println("Request for unprocessed tickets was successful");
        response.setResult(listOfTickets);
    }

    private void cancelTicket(Request request, Response response) throws Exception {
        Controller.getInstance().cancelTicket((Ticket) request.getArgument());
        System.out.println("Request for canceling Ticket was successful");
    }
}
