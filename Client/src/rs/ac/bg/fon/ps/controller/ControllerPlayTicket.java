/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.model.TableModelGames;
import rs.ac.bg.fon.ps.model.TableModelNewTicket;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.FormPlayTicket;

/**
 *
 * @author nikol
 */
public class ControllerPlayTicket {

    private final FormPlayTicket formPlayTicket;

    private ArrayList<Game> listOfGames;
    private ArrayList<Bet> listOfBets;
    private Ticket ticket;
    private TableModelNewTicket tmnt;

    public ControllerPlayTicket(FormPlayTicket formPlayTicket) {
        this.formPlayTicket = formPlayTicket;
        this.listOfBets = new ArrayList<>();
        this.ticket = new Ticket();
        this.tmnt = new TableModelNewTicket();
    }

    public FormPlayTicket getFormPlayTicket() {
        return formPlayTicket;
    }

    public void openForm() throws Exception {

        System.out.println("Opened Form Play Ticket");
        formPlayTicket.setVisible(true);
        setupForm();
    }

    private void setupForm() throws Exception {

        this.ticket.setUser(Controller.getInstance().getCurrentUser());
        this.ticket.setListOfBets(listOfBets);
        formPlayTicket.setupWager();

        JTable tableGames = formPlayTicket.getTblGames();
        TableModelGames tmg = new TableModelGames();
        getGames();
        tmg.setListOfGames(listOfGames);
        tableGames.setModel(tmg);

        JTable tableTicket = formPlayTicket.getTblTicket();
        tableTicket.setModel(tmnt);

    }

    public void getGames() {
        try {
            Request request = new Request(Operations.GET_ACTIVE_GAMES, null);
            Response response = Communication.getInstance().sendRequest(request, "Request for games is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                listOfGames = (ArrayList<Game>) response.getResult();
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formPlayTicket, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addBet(Odds odd) {

        for (Bet b : listOfBets) {
            if (b.getOdds().getGame().getGameID() == odd.getGame().getGameID()) {
                updateOdds(b.getBetOdds(), odd.getOdds());
                b.setOdds(odd);
                b.setBetOdds(odd.getOdds());
                updateBet(b);
                return;
            }
        }
        Bet bet = new Bet();
        bet.setBetOdds(odd.getOdds());
        bet.setOdds(odd);
        bet.setTicket(ticket);
        listOfBets.add(bet);
        addBetToTable(bet);
        addOdd(bet.getBetOdds());
    }

    private void updateOdds(double currentOdds, double updatedOdds) {
        ticket.setCombinedOdds(ticket.getCombinedOdds() / currentOdds * updatedOdds);
        System.out.println(String.format("%,.2f", ticket.getCombinedOdds()));
        formPlayTicket.getLblCombinedOdds().setText(String.format("%,.2f", ticket.getCombinedOdds()));

        if (ticket.getWager() != null) {
            BigDecimal combined = BigDecimal.valueOf(ticket.getCombinedOdds());
            ticket.setPotentialWin(combined.multiply(ticket.getWager()));
            formPlayTicket.getTxtWager().setText("");
            formPlayTicket.getLblPotentialWin().setText("-");
        }
    }

    private void addOdd(double odds) {
        ticket.setCombinedOdds(ticket.getCombinedOdds() * odds);
        System.out.println(String.format("%,.2f", ticket.getCombinedOdds()));
        formPlayTicket.getLblCombinedOdds().setText(String.format("%,.2f", ticket.getCombinedOdds()));

        if (ticket.getWager() != null) {
            BigDecimal combined = BigDecimal.valueOf(ticket.getCombinedOdds());
            ticket.setPotentialWin(combined.multiply(ticket.getWager()));
            formPlayTicket.getTxtWager().setText("");
            formPlayTicket.getLblPotentialWin().setText("-");
        }
    }

    private void removeOdds(int selectedRow) {
        ticket.setCombinedOdds(ticket.getCombinedOdds() / listOfBets.get(selectedRow).getBetOdds());
        formPlayTicket.getLblCombinedOdds().setText(String.format("$,.2f", ticket.getCombinedOdds()));
        formPlayTicket.getTxtWager().setText("");
        formPlayTicket.getLblPotentialWin().setText("-");
        listOfBets.remove(selectedRow);
    }

    private void updateBet(Bet bet) {
        tmnt.updateBet(bet);
    }

    private void addBetToTable(Bet bet) {
        tmnt.addBet(bet);
        formPlayTicket.getTblTicket().setModel(tmnt);
    }

    public void removeBetFromTable() {
        int selected = formPlayTicket.getTblTicket().getSelectedRow();
        if (selected != -1) {
            tmnt.removeBet(selected);
            removeOdds(selected);
            JOptionPane.showMessageDialog(formPlayTicket, "Bet removed");
        } else {
            JOptionPane.showMessageDialog(formPlayTicket, "No bet seleceted!\n Please select the bet you want to remove!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSelected(JTable table) {
        return table.getSelectedRow() != -1;
    }

    public Game getSelectedGame() {
        int selected = formPlayTicket.getTblGames().getSelectedRow();
        if (selected != -1) {
            return listOfGames.get(selected);
        } else {
            JOptionPane.showMessageDialog(formPlayTicket, "No game seleceted\n Please select a game!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void openDialogOdds() throws Exception {

        Game game = getSelectedGame();
        if (game != null) {
            Controller.getInstance().openDialogGameOdds();
        }

    }

    public void filterByTeamName() {

        ArrayList<Game> filteredList = new ArrayList<>();
        String teamName = formPlayTicket.getTxtTeam().getText().toLowerCase();
        for (Game g : listOfGames) {
            if (g.getAway().getTeamName().toLowerCase().contains(teamName) || g.getHome().getTeamName().toLowerCase().contains(teamName)) {
                addGameToList(filteredList, g);
            }
        }
        setupTableGames(filteredList);
    }

    public void setupTableGames(ArrayList<Game> listOfGames) {
        JTable tableGames = formPlayTicket.getTblGames();
        TableModelGames tmg = new TableModelGames();
        tmg.setListOfGames(listOfGames);
        tableGames.setModel(tmg);
    }

    public void filterByDate() {

        try {
            getGames();
            if (formPlayTicket.getRbAllTime().isSelected()) {
                setupTableGames(listOfGames);
            } else {
                Date today = new Date();
                ArrayList<Game> filteredList = new ArrayList<>();
                if (formPlayTicket.getRbToday().isSelected()) {
                    for (Game g : listOfGames) {
                        if (g.getDateOfPlay().equals(today)) {
                            addGameToList(filteredList, g);
                        }
                    }
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    Calendar c = Calendar.getInstance();
                    c.setTime(today);
                    c.add(Calendar.DATE, 1);
                    Date tommorow = c.getTime();

                    c.add(Calendar.DATE, 1);
                    Date afterTommorow = c.getTime();

                    for (Game g : listOfGames) {
                        if (sdf.format(g.getDateOfPlay()).equals(sdf.format(today))
                                || sdf.format(g.getDateOfPlay()).equals(sdf.format(tommorow))
                                || sdf.format(g.getDateOfPlay()).equals(sdf.format(afterTommorow))) {
                            addGameToList(filteredList, g);
                        }
                    }
                }
                setupTableGames(filteredList);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formPlayTicket, "Error filtering games", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updatePotentialWin() {
        try {

            double wager = Double.parseDouble(formPlayTicket.getTxtWager().getText());
            ticket.setWager(BigDecimal.valueOf(wager));
            BigDecimal potentialWin = BigDecimal.valueOf(wager * ticket.getCombinedOdds());
            ticket.setPotentialWin(potentialWin);
            DecimalFormat df = new DecimalFormat("#,###.00");
            formPlayTicket.getLblPotentialWin().setText(df.format(potentialWin));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(formPlayTicket, "Wager must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addGameToList(ArrayList<Game> list, Game game) {

        Team home = new Team();
        home.setTeamID(game.getHome().getTeamID());
        home.setTeamName(game.getHome().getTeamName());

        Team away = new Team();
        away.setTeamID(game.getAway().getTeamID());
        away.setTeamName(game.getAway().getTeamName());

        Game g = new Game();
        g.setAway(away);
        g.setHome(home);
        g.setDateOfPlay(game.getDateOfPlay());
        g.setGameID(game.getGameID());

        list.add(g);

    }

    public Ticket getTicket() {
        return ticket;
    }

}
