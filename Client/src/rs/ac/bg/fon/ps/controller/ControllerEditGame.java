/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.FormGame;

/**
 *
 * @author nikol
 */
public class ControllerEditGame {

    private final FormGame formEditGame;
    private Game game;
    private ArrayList<Odds> listOfOdds;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public ControllerEditGame(FormGame formGame, Game selectedGame) {
        this.formEditGame = formGame;
        this.game = selectedGame;
        listOfOdds = new ArrayList<>();
    }

    public FormGame getFormEditGame() {
        return formEditGame;
    }

    public Game getGame() {
        return game;
    }

    public void openForm() {
        if (game != null) {
            System.out.println("Opened Form Edit Game");
            formEditGame.setVisible(true);
            setupForm();
        }
    }

    private void setupForm() {

        setComboboxItem(formEditGame.getCmbHomeTeam(), game.getHome());
        setComboboxItem(formEditGame.getCmbAwayTeam(), game.getAway());

        formEditGame.getLblTitle().setText("UPDATE GAME");
        formEditGame.getBtnAddOdds().setText("EDIT ODDS");
        formEditGame.getBtnConfirmTeams().setVisible(false);
        formEditGame.getBtnCreateTeam().setVisible(false);
        formEditGame.getRbtnOver().setSelected(game.isIsOver());
        String date = sdf.format(game.getDateOfPlay());
        formEditGame.getTxtDate().setText(date);
        formEditGame.getTxtHomeGoals().setText(String.valueOf(game.getHomeGoals()));
        formEditGame.getTxtAwayGoals().setText(String.valueOf(game.getAwayGoals()));

    }

    private void setComboboxItem(JComboBox cmb, Team team) {
        cmb.removeAll();
        cmb.addItem(team);
        cmb.setEnabled(false);
    }

    void openEditOddsDialog() {
        try {
            Controller.getInstance().openDialogEditOdds();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formEditGame, ex.getMessage(), "Error opening DialogEditOdds!", JOptionPane.ERROR_MESSAGE);
        }
    }

    void updateOdds(ArrayList<Odds> listOfOdds) {
        this.listOfOdds = listOfOdds;
    }

    public void saveChanges() {

        int homeGoals = Integer.parseInt(formEditGame.getTxtHomeGoals().getText());
        int awayGoals = Integer.parseInt(formEditGame.getTxtAwayGoals().getText());
        if (homeGoals>=0 && awayGoals>=0) {
            game.setHomeGoals(homeGoals);
            game.setAwayGoals(awayGoals);
        }else{
            JOptionPane.showMessageDialog(formEditGame,"Invalid home/away goal input!\nGoals cannot be negative", "Invalid input!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String dateString = formEditGame.getTxtDate().getText();
        if (!dateString.equals(sdf.format(game.getDateOfPlay()))) {
            try {
                Date date = sdf.parse(dateString);
                game.setDateOfPlay(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(formEditGame, "Invalid date format!\n Date must be in format dd.MM.yyyy HH:mm\n dd - days, MM - months yyyy - years\n HH - hours, mm - minutes", "Invalid date format!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        game.setIsOver(isOver());
        updateGame();

        if (isOver()) {

            try {
                updateBet();

                // SVE OVO SE DESAVA U NOVOM THREADU
                // GET ALL BETS FOR GAME
                // GO THROUGH BETS AND CHECK IF BET HAS PASSED
                // YES: GET ALL BETS FOR TICKET
                // UPDATE BET: passed -> true
                // CHECK IF ALL BETS HAVE PASSED
                // YES: UPDATE TICKET: processed -> completed && win -> true
                // NO:
                // UPDATE BET: passed -> false
                // UPDATE TICKET: processed -> completed && win -> false
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(formEditGame, ex.getMessage(), "Error updating game! ", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // UPDATE ODDS
            System.out.println("not over");
        }

        // UPDATE GAME
    }

    private boolean isOver() {
        return formEditGame.getRbtnOver().isSelected();
    }

    private void updateBet() throws Exception {

        new Thread() {
            @Override
            public void run() {
                try {

                    ArrayList<Bet> listOfBets = getBetsForGame();
                    for (Bet b : listOfBets) {

                        b.setPassed(checkBet(b));
                        updateBet(b);
                        Ticket ticket = getTicket(b.getTicket().getTicketID());
                        System.out.println(ticket);
                        if (ticket.getState().equals("processed")) {
                            if (b.isPassed()) {
                                if (checkTicketBets((ArrayList<Bet>) ticket.getListOfBets())) {
                                    ticket.setState("completed");
                                    ticket.setWin(true);
                                    updateTicket(ticket);
                                }
                            } else {
                                ticket.setState("completed");
                                ticket.setWin(false);
                                updateTicket(ticket);
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(formEditGame, ex.getMessage(), "Error updating bets! ", JOptionPane.ERROR_MESSAGE);
                }
            }

            private boolean checkBet(Bet b) {
                String betType = b.getOdds().getType().getTypeName();
                switch (betType) {
                    case "1":
                        return game.getHomeGoals() > game.getAwayGoals();
                    case "X":
                        return game.getHomeGoals() == game.getAwayGoals();
                    case "2":
                        return game.getHomeGoals() < game.getAwayGoals();
                    case "1X":
                        return game.getHomeGoals() >= game.getAwayGoals();
                    case "X2":
                        return game.getHomeGoals() <= game.getAwayGoals();
                    case "12":
                        return game.getHomeGoals() != game.getAwayGoals();
                    case "3+":
                        return game.getHomeGoals() + game.getAwayGoals() > 2;
                    case "2-4":
                        int totalGoals = game.getHomeGoals() + game.getAwayGoals();
                        return totalGoals > 1 && totalGoals < 5;
                    case "0-2":
                        return game.getHomeGoals() + game.getAwayGoals() < 3;
                    case "GG":
                        return game.getHomeGoals() > 0 && game.getAwayGoals() > 0;
                    case "NG":
                        return (game.getHomeGoals() > 0 && game.getAwayGoals() == 0) || (game.getHomeGoals() == 0 && game.getAwayGoals() > 0);
                    default:
                        return false;
                }
            }

            private ArrayList<Bet> getBetsForGame() throws Exception {
                Request request = new Request(Operations.GET_BETS_FOR_GAME, game.getGameID());
                Response response = Communication.getInstance().sendRequest(request, "GET_BETS_FOR_GAME: Request for bets placed on game is sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                    return (ArrayList<Bet>) response.getResult();

                } else {
                    throw response.getException();
                }
            }

            private Ticket getTicket(int ticketID) throws Exception {
                Request request = new Request(Operations.GET_TICKET, ticketID);
                Response response = Communication.getInstance().sendRequest(request, "GET_TICKET: Request for bets placed on ticket is sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                    return (Ticket) response.getResult();

                } else {
                    throw response.getException();
                }
            }

            private boolean checkTicketBets(ArrayList<Bet> listOfTicketBets) {
                System.out.println("checkTicketBets: "+listOfTicketBets);
                for (Bet b : listOfTicketBets) {
                    if (!b.getOdds().getGame().isIsOver()) {
                        return false;
                    } else if (!b.isPassed()) {
                        return false;
                    }
                }
                return true;
            }

            private void updateTicket(Ticket ticket) throws Exception {
                Request request = new Request(Operations.UPDATE_TICKET, ticket);
                Response response = Communication.getInstance().sendRequest(request, "Request for bets placed on game is sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                    System.out.println("Ticket updated successfully!");
                } else {
                    throw response.getException();
                }
            }

            private void updateBet(Bet bet) throws Exception {
                Request request = new Request(Operations.UPDATE_BET, bet);
                Response response = Communication.getInstance().sendRequest(request, "Request for bets placed on game is sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                    System.out.println("Bet updated successfully!");
                } else {
                    throw response.getException();
                }
            }

        }.start();
    }

    private void updateGame() {
        try {
            Request request = new Request(Operations.UPDATE_GAME, game);
            Response response = Communication.getInstance().sendRequest(request, "UPDATE_GAME: Request for updating game is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                System.out.println("Game updated successfully!");
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formEditGame, ex.getMessage(), "Error updating game! ", JOptionPane.ERROR_MESSAGE);
        }
    }
}
