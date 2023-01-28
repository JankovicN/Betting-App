/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations.User;

import java.util.ArrayList;
import rs.ac.bg.fon.ps.domain.GeneralDomainObject;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author nikol
 */
public class GetAllUsers extends AbstractGenericOperation {

    private ArrayList<User> listOfUsers;

    public GetAllUsers() {
        listOfUsers = new ArrayList<>();
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ArrayList<User> list = (ArrayList<User>) repository.getAll(new User());

        if (list != null) {
            this.listOfUsers = list;
        }
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof User)) {
            throw new Exception("Invalid data for User!");
        }
    }

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

}
