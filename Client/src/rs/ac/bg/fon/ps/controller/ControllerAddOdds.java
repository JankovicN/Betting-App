/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import rs.ac.bg.fon.communication.Communication;
import rs.ac.bg.fon.ps.communication.Request;
import rs.ac.bg.fon.ps.communication.Response;
import rs.ac.bg.fon.ps.communication.ResponseType;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.model.TableModelOdds;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.DialogAddOdds;

/**
 *
 * @author nikol
 */
public class ControllerAddOdds {

    private final DialogAddOdds dialogAddOdds;
    private Game game;
    private ArrayList<Odds> listOfOdds;

    public ControllerAddOdds(DialogAddOdds dialogAddOdds, Game game) {
        this.dialogAddOdds = dialogAddOdds;
        this.game = game;
        this.listOfOdds = new ArrayList<>();
    }

    public void openForm() throws Exception {
        this.dialogAddOdds.setVisible(true);
        setupDialog();
    }

    public ArrayList<Odds> getOdds() {
        return listOfOdds;
    }

    private void setupDialog() throws Exception {

        dialogAddOdds.setlblGame(game.toString());

        JTable table = dialogAddOdds.getTblOdds();
        TableModelOdds tmo = new TableModelOdds();
        if (listOfOdds.isEmpty()) {
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
        }
        tmo.setListOfOdds(listOfOdds);
        table.setModel(tmo);
        dialogAddOdds.setTblOdds(table);

    }

    public void resetOdds() {
        listOfOdds= new ArrayList<>();
    }

}
