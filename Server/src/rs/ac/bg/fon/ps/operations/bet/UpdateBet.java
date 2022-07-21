/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.bet;

import rs.ac.bg.fon.ps.domain.Bet;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class UpdateBet extends AbstractGenericOperation{

    @Override
    protected void executeOperation(Object param) throws Exception {
        Bet bet = (Bet) param;
        repository.edit(bet);
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }
    
}
