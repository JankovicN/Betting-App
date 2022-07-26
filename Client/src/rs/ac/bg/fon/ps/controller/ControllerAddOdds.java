/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.model.TableModelAddOdds;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.DialogAddOdds;

/**
 *
 * @author nikol
 */
public class ControllerAddOdds {

    private final DialogAddOdds dialogAddOdds;
    private final Game game;
    private ArrayList<Odds> listOfOdds;

    public ControllerAddOdds(DialogAddOdds dialogAddOdds, Game game) {
        this.dialogAddOdds = dialogAddOdds;
        this.game = game;
        this.listOfOdds = new ArrayList<>();
    }

    public void openDialogAddOdds() throws Exception {
        System.out.println("Opened Dialog Add Odds ");
        this.dialogAddOdds.setVisible(true);
        setupDialogAddOdds();
    }

    public ArrayList<Odds> getOdds() {
        return listOfOdds;
    }

    private void setupDialogAddOdds() {
        try {
            dialogAddOdds.setlblGame(game.toString());
            setupTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialogAddOdds.getParent(), "Error setting up window for adding odds!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetOdds() {
        listOfOdds = new ArrayList<>();
    }

    private void setupTable() throws Exception {
        JTable table = dialogAddOdds.getTblOdds();
        TableModelAddOdds tmo = new TableModelAddOdds();
        if (listOfOdds.isEmpty()) {
            try {
                Request request = new Request(Operations.GET_BETTYPE, null);
                Response response = Communication.getInstance().sendRequest(request, "Request for bet types sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {

                    ArrayList<BetType> listOfBetTypes = (ArrayList<BetType>) response.getResult();

                    for (BetType betType : listOfBetTypes) {
                        Odds o = new Odds(game, betType, 1.0);
                        listOfOdds.add(o);
                    }
                } else {
                    throw response.getException();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialogAddOdds.getParent(), "Error getting bet types from server!\n" + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        tmo.setListOfOdds(listOfOdds);
        table.setModel(tmo);
        dialogAddOdds.setTblOdds(table);
    }

    private void setupTable(ArrayList<Odds> gameOdds) throws Exception {
        JTable table = dialogAddOdds.getTblOdds();
        TableModelAddOdds tmo = new TableModelAddOdds();
        if (listOfOdds.isEmpty()) {
            try {
                Request request = new Request(Operations.GET_BETTYPE, null);
                Response response = Communication.getInstance().sendRequest(request, "Request for bet types sent..");

                if (response.getResponseType().equals(ResponseType.SUCCESS)) {

                    ArrayList<BetType> listOfBetTypes = (ArrayList<BetType>) response.getResult();

                    for (BetType betType : listOfBetTypes) {
                        Odds o = new Odds(game, betType, 1.0);
                        for (Odds go : gameOdds) {
                            if (go.getType().getTypeID() == o.getType().getTypeID()) {
                                o.setOdds(go.getOdds());
                                break;
                            }
                        }
                        listOfOdds.add(o);
                    }
                } else {
                    throw response.getException();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialogAddOdds.getParent(), "Error getting bet types from server!\n" + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        tmo.setListOfOdds(listOfOdds);
        table.setModel(tmo);
        dialogAddOdds.setTblOdds(table);
    }

    public void openDialogEditOdds() throws Exception {
        try {
            this.dialogAddOdds.setVisible(true);
            setupDialogEditOdds();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialogAddOdds.getParent(), "Error setting up window for editing odds!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupDialogEditOdds() {

        dialogAddOdds.setlblGame(game.toString());

        try {
            ArrayList<Odds> gameOdds;
            Request request = new Request(Operations.GET_ODDS, game);
            Response response = Communication.getInstance().sendRequest(request, "Request for games is sent..");

            if (response.getResponseType().equals(ResponseType.SUCCESS)) {
                gameOdds = (ArrayList<Odds>) response.getResult();
                setupTable(gameOdds);
            } else {
                throw response.getException();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialogAddOdds.getParent(), "Error getting odds from server!\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
