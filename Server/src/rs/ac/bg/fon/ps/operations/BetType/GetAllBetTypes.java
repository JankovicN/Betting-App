/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.BetType;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.BetType;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetAllBetTypes extends AbstractGenericOperation{
    
    ArrayList<BetType> listOfBetTypes;

    public GetAllBetTypes() {
        this.listOfBetTypes = new ArrayList<>();
    }

    public ArrayList<BetType> getListOfBetTypes() {
        return listOfBetTypes;
    }
       
    @Override
    protected void executeOperation(Object param) throws Exception {
         ArrayList<BetType> list = (ArrayList<BetType>) repository.getAll((BetType)param);

        if (list != null) {
            this.listOfBetTypes = list;
        }
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof BetType)) {
            throw new Exception("Invalid data for User!");
        }
    }
    
}
