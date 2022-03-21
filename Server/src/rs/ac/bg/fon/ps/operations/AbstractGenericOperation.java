/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations;

import java.sql.SQLException;
import rs.ac.bg.fon.ps.repository.Repository;
import rs.ac.bg.fon.ps.repository.db.DBRepository;
import rs.ac.bg.fon.ps.repository.db.impl.DBRepositoryGeneric;

/**
 *
 * @author nikol
 */
public abstract class AbstractGenericOperation {
    
    protected final Repository repository;

    public AbstractGenericOperation() {
        this.repository = new DBRepositoryGeneric();
    }

    public final void execute(Object param) throws Exception{
    
        try {
            precondicions(param);
            startTransaction();
            executeOperation(param);
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction();
            throw e;
        }finally{
            disconnect();
        }
    }

    private void disconnect() throws SQLException {
        ((DBRepository)repository).disconnect();
    }

    private void rollbackTransaction() throws SQLException {
        ((DBRepository)repository).rollback();
    }

    private void commitTransaction() throws SQLException {
        ((DBRepository)repository).commit();
    }

    protected abstract void executeOperation(Object param) throws Exception;

    private void startTransaction() throws SQLException {
        ((DBRepository)repository).connect();
    }

    protected abstract void precondicions(Object param) throws Exception;
    
    
}
