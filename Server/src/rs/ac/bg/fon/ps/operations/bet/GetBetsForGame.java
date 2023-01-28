/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.bet;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.domain.Game;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.domain.Ticket;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetBetsForGame extends AbstractGenericOperation {

    ArrayList<Bet> listOfBets;

    public GetBetsForGame() {
        listOfBets = new ArrayList<>();
    }

    public ArrayList<Bet> getListOfBets() {
        return listOfBets;
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        int gameID =  (int) param;
        ArrayList<Bet> betsForGame = (ArrayList<Bet>) repository.getAllJoin(new Bet(),new Odds(), new Game(), new BetType(), new Team(), new Ticket(),new User(), (new Bet()).getSelectConditionForGame(gameID));
        System.out.println(betsForGame);
        if(betsForGame!=null){
            listOfBets=betsForGame;
        }
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Integer) || (int)param<0) {
            throw new Exception("Invalid Game id!");
        }
    }

}
