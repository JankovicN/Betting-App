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
    protected void precondicions(Object param) throws Exception {
        return;
    }

}
