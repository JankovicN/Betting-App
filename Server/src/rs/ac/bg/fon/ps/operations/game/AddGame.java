/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.game;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class AddGame extends AbstractGenericOperation {

    int gameID = -1;

    @Override
    protected void executeOperation(Object param) throws Exception {
        ArrayList<Odds> odds = (ArrayList<Odds>) param;
        Game game = odds.get(0).getGame();
        gameID = repository.addReturnKey(game);
        for (Odds odd : odds) {
            odd.getGame().setGameID(gameID);
            repository.add(odd);
        }
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof ArrayList)) {
            throw new Exception("Invalid data for List of odds for game!");
        }
        ArrayList<Odds> odds = (ArrayList<Odds>) param;
        if (odds.get(0) instanceof Odds) {
            throw new Exception("Invalid data for Odds!");
        }
        
        Game game =odds.get(0).getGame();
        if (game instanceof Game) {
            throw new Exception("Invalid data for Game!");
        }
        
        for (Odds odd : odds) {
            if(odd.getOdds()<1){
                throw new Exception("Invalid data for Odds value!");
            }
        }
        if(game.getHome() == null ){
            throw new Exception("Invalid data for Home Team!");
        }
        if(game.getAway()== null ){
            throw new Exception("Invalid data for Away Team!");
        }
        if(game.getAway().getTeamName().equals(game.getHome().getTeamName())){
            throw new Exception("Home and Away must be different for Game!");
        }
    }

}
