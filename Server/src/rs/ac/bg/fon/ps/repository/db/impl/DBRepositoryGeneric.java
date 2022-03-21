/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.domain.GenericDomainObject;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DBRepository;

/**
 *
 * @author nikol
 */
public class DBRepositoryGeneric implements DBRepository<GenericDomainObject>{

    /**
     *
     * @param param
     * @throws Exception
     */
    @Override
    public void add(GenericDomainObject param)throws Exception{
    
        try {
            Connection connection= DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb= new StringBuilder();
            sb.append("INSERT INTO")
                    .append(param.getTableName())
                    .append(" (").append(param.getColumnNamesForInsert()).append(") ")
                    .append(" VALUES (")
                    .append(param.getInsertValues()).append(")");
            String query = sb.toString();
            System.out.println(query);
            Statement statement= connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBRepositoryGeneric.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error while adding object to database!");
        }
    }

    /**
     *
     * @param param
     * @throws Exception
     */
    @Override
    public void edit(GenericDomainObject param) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ")
                    .append(param.getTableName()).append(" SET ")
                    .append(param.getUpdateValues(param))
                    .append(" WHERE ")
                    .append(param.getUpdateCondition());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Error updating object in database!");
        }
    }

    /**
     *
     * @param param
     * @throws Exception
     */
    @Override
    public void delete(GenericDomainObject param) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ")
                    .append(param.getTableName())
                    .append(" WHERE ")
                    .append(param.getDeleteCondition());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Error while deleting from database!");
        }
    
    }

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public List<GenericDomainObject> getAll(GenericDomainObject param) throws Exception {
        try {
            String query = "SELECT * from " + param.getTableName();
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return param.readResultSet(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public int addReturnKey(GenericDomainObject param) throws Exception {
    try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(param.getTableName())
                    .append(" (").append(param.getColumnNamesForInsert()).append(")")
                    .append(" VALUES (")
                    .append(param.getInsertValues())
                    .append(")");
            String query = sb.toString();
            System.out.println(query);
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            
            ResultSet rsKey = statement.getGeneratedKeys();
            
            if(rsKey.next()){
                int id = rsKey.getInt(1);
                rsKey.close();
                statement.close();
                
                return id;
            }

            throw new Exception();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom dodavanja u bazu!");
        }
    }
    
    
}
