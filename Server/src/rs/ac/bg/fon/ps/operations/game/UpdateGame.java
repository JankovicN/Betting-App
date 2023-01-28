/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.game;

import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class UpdateGame extends AbstractGenericOperation{

    @Override
    protected void executeOperation(Object param) throws Exception {
        Game game = (Game) param;
        repository.edit(game);
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
        if(game.getHome() == null ){
            throw new Exception("Invalid data for home Team!");
        }
        if(game.getAway()== null ){
            throw new Exception("Invalid data for away Team!");
        }
        if(game.getAway().getTeamName().equals(game.getHome().getTeamName())){
            throw new Exception("Home and away must be different for game!");
        }
    }
    
}
