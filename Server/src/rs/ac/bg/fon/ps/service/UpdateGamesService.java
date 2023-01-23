/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.constants.BetTypes;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Ticket;

/**
 *
 * @author nikol
 */
public class UpdateGamesService {

    private Date currentDate;
    private List<Game> games;
    private List<Ticket> tickets;
    private List<Bet> bets;

    public UpdateGamesService() {
        currentDate = new Date();
        games = new ArrayList<>();
        bets = new ArrayList<>();
        tickets = new ArrayList<>();
    }

    public void update(){
        try {
            updateDate();
            updateGames();
            updateBets();
            updateTickets();
        } catch (Exception ex) {
            System.out.println("UpdateGamesService: Error updating games and tickets!");
        }
    }

    public void getStartedGames() throws Exception {
        games = Controller.getInstance().getNotFinishedGames();
    }

    private void getProcessedBets() throws Exception, Exception {
        bets= Controller.getInstance().getProcessedBets();
    }

    private void getProcessedTickets() throws Exception {
        tickets = Controller.getInstance().getProcessedTickets();
    }

    private void updateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        currentDate = calendar.getTime();
    }

    private void updateFullTimeResult(List<Game> games) throws Exception {
        for (Game game : games) {
            if (game.getState().equals("IP") && game.getDateOfPlay().after(currentDate)) {
                game.setState("FT");
                game.setGoals();
                Controller.getInstance().updateGame(game);
            }
        }
    }

    private List<Game> updateState(List<Game> games) throws Exception {
        for (Game game : games) {
            if (game.getState().equals("NS")) {
                game.setState("IP");
                Controller.getInstance().updateGame(game);
            }
        }
        return games;
    }

    private void updateGames() throws Exception {
        getStartedGames();
        games=updateState(games);
        updateFullTimeResult(games);
    }

    public void updateBets() throws Exception {
        getProcessedBets();
        for (Bet bet : bets) {
            if (updateBet(bet)) {
                bet.setState("✓");
            } else {
                bet.setState("X");
                if (!bet.getTicket().getState().equals("X")) {
                    bet.getTicket().setState("X");
                    Controller.getInstance().updateTicket(bet.getTicket());
                }
            }
            Controller.getInstance().updateBet(bet);
        }
    }

    private void updateTickets() throws Exception {
        getProcessedTickets();
        for (Ticket ticket : tickets) {
            String state = "✓";
            for (Bet b : ticket.getListOfBets()) {
                if (b.getState().equals("X")) {
                    state = "X";
                    break;
                } else if (b.getState().equals("processed")) {
                    state = "processed";
                    break;
                }
            }
            if (state.equals("✓")) {
                ticket.setState("✓");
                Controller.getInstance().updateTicket(ticket);
            }
        }
    }

    private boolean updateBet(Bet bet) {
        int homeGoals = bet.getOdds().getGame().getHomeGoals();
        int awayGoals = bet.getOdds().getGame().getAwayGoals();
        switch (bet.getOdds().getType().getTypeID()) {
            case BetTypes.HOME:
                if (homeGoals > awayGoals) {
                    return true;
                }
                return false;
            case BetTypes.AWAY:
                if (homeGoals < awayGoals) {
                    return true;
                }
                return false;
            case BetTypes.DRAW:
                if (homeGoals == awayGoals) {
                    return true;
                }
                return false;
            case BetTypes.HOME_DRAW:
                if (homeGoals >= awayGoals) {
                    return true;
                }
                return false;
            case BetTypes.HOME_AWAY:
                if (homeGoals != awayGoals) {
                    return true;
                }
                return false;
            case BetTypes.DRAW_AWAY:
                if (homeGoals <= awayGoals) {
                    return true;
                }
                return false;
            case BetTypes.GOALS_THREE_OR_MORE:
                if (homeGoals + awayGoals >= 3) {
                    return true;
                }
                return false;
            case BetTypes.GOALS_TWO_TO_FOUR:
                if (homeGoals + awayGoals >= 2 && homeGoals + awayGoals <= 4) {
                    return true;
                }
                return false;
            case BetTypes.GOALS_ZERO_TO_TWO:
                if (homeGoals + awayGoals >= 0 && homeGoals + awayGoals <= 2) {
                    return true;
                }
                return false;
            case BetTypes.GG:
                if (homeGoals >= 1 && awayGoals >= 1) {
                    return true;
                }
                return false;
            case BetTypes.NG:
                if ((homeGoals != 0 && awayGoals == 0) || (awayGoals != 0 && homeGoals == 0)) {
                    return true;
                }
                return false;
            default:
                throw new AssertionError();
        }
    }
}
