/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.odds;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetOddsForGame extends AbstractGenericOperation {

    private ArrayList<Odds> listOfOdds;

    public GetOddsForGame() {
        listOfOdds = new ArrayList<>();
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Game game = (Game) param;
        Odds odd = new Odds();
        odd.setGame(game);

        ArrayList<Odds> list = (ArrayList<Odds>) repository.getAllJoinCondition(new Odds(), new Game(), new Team(), new BetType(), odd.getGameCondition());

        if (list != null) {
            listOfOdds = list;
        }
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Game)) {
            throw new Exception("Invalid data for Game!");
        }
        Game game = (Game) param;
        if(game.getGameID()<=0){
            throw new Exception("Invalid id for Game!");
        }
    }

    public ArrayList<Odds> getListOfOdds() {
        return listOfOdds;
    }

}
