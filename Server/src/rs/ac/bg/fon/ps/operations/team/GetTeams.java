/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.team;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetTeams extends AbstractGenericOperation  {

    private ArrayList<Team> listOfTeams;

    public GetTeams() {
        this.listOfTeams= new ArrayList<>();
    }
    public ArrayList<Team> getListOfTeams() {
        return this.listOfTeams;
    }
    
    @Override
    protected void executeOperation(Object param) throws Exception {
        ArrayList<Team> listOfTeams= (ArrayList<Team>) repository.getAll(new Team());
        
        if(listOfTeams!=null){
           this.listOfTeams=listOfTeams;
        }
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Team)) {
            throw new Exception("Invalid data for Team!");
        }
    }

    
}
