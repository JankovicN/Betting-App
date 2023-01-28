/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.team;

import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class AddTeam extends AbstractGenericOperation {

    private int teamID=-1;
    
    
    @Override
    protected void executeOperation(Object param) throws Exception {
        Team newTeam = (Team) param;
        teamID = repository.addReturnKey(newTeam);
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Team)) {
            throw new Exception("Invalid data for Team!");
        }
    }

    public int getTeamID() {
        return teamID;
    }

}
