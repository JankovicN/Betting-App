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
import rs.ac.bg.fon.ps.model.TableModelOdds;
import rs.ac.bg.fon.ps.operations.Operations;
import rs.ac.bg.fon.ps.view.form.DialogGameOdds;

/**
 *
 * @author nikol
 */
public class ControllerGameOdds {

    private final DialogGameOdds dialogGameOdds;
    private ArrayList<Odds> listOfOdds;
    private Game game;

    public ControllerGameOdds(DialogGameOdds dialogGameOdds, Game game) {
        this.dialogGameOdds = dialogGameOdds;
        this.listOfOdds = new ArrayList<>();
        this.game = game;
    }

    public void openForm() throws Exception {
        System.out.println("Opened Dialog Game Odds ");
        this.dialogGameOdds.setVisible(true);
        setupDialog();
    }

    private void setupDialog() throws Exception {
        dialogGameOdds.getLblGame().setText(game.toString());

        JTable table = dialogGameOdds.getTblOdds();
        TableModelOdds tmo = new TableModelOdds();

        setOdds();

        tmo.setListOfOdds(listOfOdds);
        table.setModel(tmo);
        dialogGameOdds.setTblOdds(table);
    }

    public void setOdds() throws Exception {
        Request request = new Request(Operations.GET_ODDS, game);
        Response response = Communication.getInstance().sendRequest(request, "Request for odds sent..");

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            this.listOfOdds = (ArrayList<Odds>) response.getResult();
        } else {
            throw response.getException();
        }
    }
    
    private Odds getSelectedOdds() {
        JTable table = dialogGameOdds.getTblOdds();
        int row = table.getSelectedRow();
        return listOfOdds.get(row);
    }
    
    public void confirmOdds(){
    
        if(dialogGameOdds.getTblOdds().getSelectedRow()!=-1){
            Controller.getInstance().getControllerPlayTicket().addBet(getSelectedOdds());
        }else{
            JOptionPane.showMessageDialog(dialogGameOdds, "No odds selected..");
        }
        dialogGameOdds.dispose();
    }

}
