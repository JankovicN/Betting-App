/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.game;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetGamesNotStarted extends AbstractGenericOperation {

    ArrayList<Game> listOfGames;

    public GetGamesNotStarted() {
        this.listOfGames = new ArrayList<>();
    }

    public ArrayList<Game> getListOfGames() {
        return listOfGames;
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        ArrayList<Game> list = (ArrayList<Game>) repository.getAllJoinCondition((Game) param, new Team(), new Game().getNotStartedCondition());

        if (list != null) {
            listOfGames = list;
        }
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Game)) {
            throw new Exception("Invalid data for Game!");
        }
    }

}
