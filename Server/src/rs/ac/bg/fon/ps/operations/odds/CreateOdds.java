/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.odds;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Odds;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class CreateOdds extends AbstractGenericOperation{

    @Override
    protected void executeOperation(Object param) throws Exception {
        ArrayList<Odds> listOfOdds = (ArrayList<Odds>) param;
        for (Odds o : listOfOdds) {
            repository.add(o);
        }
    }

    @Override
    protected void precondicions(Object param) throws Exception {
        return;
    }

    
}
