/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import rs.ac.bg.fon.ps.view.form.FormAllGames;

/**
 *
 * @author nikol
 */
public class ControllerAllGames {

    private FormAllGames formAllGames;

    private ArrayList<Game> listOfGames;

    public ControllerAllGames(FormAllGames formAllGames) {
        this.formAllGames = formAllGames;
    }

    public FormAllGames getFormAllGames() {
        return formAllGames;
    }

    public void openForm() {
        System.out.println("Opened Form All Games");
        formAllGames.setVisible(true);
        setupForm();
    }

    private void setupForm() {
        JTable tableGames = formAllGames.getTblGames();
        TableModelGames tmg = new TableModelGames();
        getGames();
        tmg.setListOfGames(listOfGames);
        tableGames.setModel(tmg);
    }

    private void getGames() {
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
            JOptionPane.showMessageDialog(formAllGames, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openDialogAlterGame() throws Exception {

        Game game = getSelectedGame();
        if (game != null) {
            Controller.getInstance().openDialogAlterGame();
        }

    }

    public Game getSelectedGame() {
        int selected = formAllGames.getTblGames().getSelectedRow();
        if (selected != -1) {
            return listOfGames.get(selected);
        } else {
            JOptionPane.showMessageDialog(formAllGames, "No game seleceted\n Please select a game!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void filterByTeamName() {

        ArrayList<Game> filteredList = new ArrayList<>();
        String teamName = formAllGames.getTxtTeam().getText().toLowerCase();
        for (Game g : listOfGames) {
            if (g.getAway().getTeamName().toLowerCase().contains(teamName) || g.getHome().getTeamName().toLowerCase().contains(teamName)) {
                addGameToList(filteredList, g);
            }
        }
        if (filteredList.size() == 0) {
            JOptionPane.showMessageDialog(formAllGames, "Sistem ne moze da nađe utakmicu po zadatoj vrednosti", "Invalid input", JOptionPane.ERROR_MESSAGE);
        } else {
            setupTableGames(filteredList);
        }
    }

    public void setupTableGames(ArrayList<Game> listOfGames) {
        JTable tableGames = formAllGames.getTblGames();
        TableModelGames tmg = new TableModelGames();
        tmg.setListOfGames(listOfGames);
        tableGames.setModel(tmg);
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

    public void filterByDate() {

        try {
            getGames();
            if (formAllGames.getRbAllTime().isSelected()) {
                setupTableGames(listOfGames);
            } else {
                Date today = new Date();
                ArrayList<Game> filteredList = new ArrayList<>();
                if (formAllGames.getRbToday().isSelected()) {
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
                if (filteredList.size() == 0) {
                    JOptionPane.showMessageDialog(formAllGames, "Sistem ne moze da nađe utakmicu po zadatoj vrednosti", "Invalid input", JOptionPane.ERROR_MESSAGE);
                } else {
                    setupTableGames(filteredList);
        }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(formAllGames, "Error filtering games", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void refreshTable() {
        setupForm();
    }


}
