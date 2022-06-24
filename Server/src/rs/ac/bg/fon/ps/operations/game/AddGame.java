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
public class AddGame extends AbstractGenericOperation {

    int gameID = -1;

    @Override
    protected void executeOperation(Object param) throws Exception {
        Game game = (Game) param;
        gameID = repository.addReturnKey(game);
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }

    public int getGameID() {
        return gameID;
    }

}
