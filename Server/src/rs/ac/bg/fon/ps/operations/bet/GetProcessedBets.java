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
public class GetProcessedBets  extends AbstractGenericOperation {

    ArrayList<Bet> listOfBets;

    public GetProcessedBets() {
        listOfBets = new ArrayList<>();
    }

    public ArrayList<Bet> getListOfBets() {
        return listOfBets;
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        ArrayList<Bet> processedBets = 
                (ArrayList<Bet>) repository.getAllJoin(new Bet(),
                        new Odds(), new Game(), new BetType(), new Team(), new Ticket(),new User(), 
                        (new Bet()).getProcessedAndFTCondition());
        if(processedBets!=null){
            listOfBets=processedBets;
        }
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }

}
