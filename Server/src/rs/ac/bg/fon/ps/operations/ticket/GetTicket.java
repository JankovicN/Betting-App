/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.ticket;

import java.util.ArrayList;
import javax.sound.sampled.FloatControl.Type;
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
public class GetTicket extends AbstractGenericOperation {

    private Ticket ticket;

    public GetTicket() {
        this.ticket = new Ticket();
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Ticket t = (Ticket) param;
        ArrayList<Bet> list = (ArrayList<Bet>) repository.getAllJoin(new Bet(), new Odds(), new Game(), new BetType(), new Team(), t, new User());

        for (Bet bet : list) {
            t.getListOfBets().add(bet);
        }
        this.ticket = t;
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

}
