/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.model.TableModelGames;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.FormViewGames;

/**
 *
 * @author nikol
 */
public class ControllerViewGames {

    private final FormViewGames formViewGames;
    private ArrayList<Game> listOfGames;

    public ControllerViewGames(FormViewGames formViewGames) {
        this.formViewGames = formViewGames;
        listOfGames = new ArrayList();
    }

    public FormViewGames getFormViewGames() {
        return formViewGames;
    }

    public void openForm() throws Exception {
        System.out.println("Opened Form View Games");
        formViewGames.setVisible(true);
        setupForm();
    }

    private void setupForm() throws Exception {

        JTable tableGames = formViewGames.getTblGames();
        TableModelGames tmg = new TableModelGames();
        getGames();
        tmg.setListOfGames(listOfGames);
        tableGames.setModel(tmg);
    }

    public void getGames() throws Exception {
        Request request = new Request(Operations.GET_GAMES_NOT_STARTED, null);
        Response response = Communication.getInstance().sendRequest(request, "Request for games is sent..");

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            listOfGames = (ArrayList<Game>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void filter() {

        try {
            ArrayList<Game> filteredList = new ArrayList<>();
            String teamName = formViewGames.getTxtTeam().getText().toLowerCase();
            String date = formViewGames.getTxtDate().getText().toLowerCase() + ".2022";
            if (teamName.isBlank() && date.isBlank()) {
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            for (Game g : listOfGames) {
                if (!teamName.isBlank() && date.isBlank()) {
                    if (checkTeamFilter(g, teamName)) {
                        addGameToList(filteredList, g);
                    }
                } else if (teamName.isBlank() && !date.isBlank()) {
                    String dateString = sdf.format(g.getDateOfPlay());
                    Date gameDate = sdf.parse(dateString);
                    Date filterDate = sdf.parse(date);
                    if (checkDateFilter(gameDate, filterDate)) {
                        addGameToList(filteredList, g);
                    }
                } else {
                    String dateString = sdf.format(g.getDateOfPlay());
                    Date gameDate = sdf.parse(dateString);
                    Date filterDate = sdf.parse(date);
                    if (checkDateFilter(gameDate, filterDate) && checkTeamFilter(g, teamName)) {
                        addGameToList(filteredList, g);
                    }
                }
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formViewGames, "Invalid date format!\n Date must be in format dd.MM.yyyy\n dd - days, MM - months yyyy - years", "Invalid date", JOptionPane.ERROR_MESSAGE);
        } 

    }

    private boolean checkTeamFilter(Game game, String teamName) {
        return game.getAway().getTeamName().toLowerCase().contains(teamName) || game.getHome().getTeamName().toLowerCase().contains(teamName);
    }

    private boolean checkDateFilter(Date gameDate, Date date) {
        return gameDate.equals(date);
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

    public Game getSelectedGame() {
        int selected = formViewGames.getTblGames().getSelectedRow();
        if (selected != -1) {
            return listOfGames.get(selected);
        } else {
            JOptionPane.showMessageDialog(formViewGames, "No game seleceted\n Please select a game", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void openFormEditGame() {
        Game game = getSelectedGame();
        if (game != null) {
            Controller.getInstance().openFormEditGame();
        }
    }

}
