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
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DBRepository;
import rs.ac.bg.fon.ps.domain.GeneralDomainObject;

/**
 *
 * @author nikol
 */
public class DBRepositoryGeneric implements DBRepository<GeneralDomainObject> {

    /**
     *
     * @param param
     * @throws Exception
     */
    @Override
    public void add(GeneralDomainObject param) throws Exception {

        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO")
                    .append(param.getTableName())
                    .append(" (").append(param.getColumnNamesForInsert()).append(") ")
                    .append(" VALUES ")
                    .append(param.getInsertValues());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
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
    public void edit(GeneralDomainObject param) throws Exception {
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
    public void delete(GeneralDomainObject param) throws Exception {
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
    public List<GeneralDomainObject> getAll(GeneralDomainObject param) throws Exception {
        try {
            String query = "SELECT * from " + param.getTableName() +" "+ param.getAlias();
            System.out.println(query);
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
    public int addReturnKey(GeneralDomainObject param) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(param.getTableName())
                    .append(" (").append(param.getColumnNamesForInsert()).append(")")
                    .append(" VALUES ")
                    .append(param.getInsertValues());
            String query = sb.toString();
            System.out.println(query);
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            ResultSet rsKey = statement.getGeneratedKeys();

            if (rsKey.next()) {
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

    @Override
    public List<GeneralDomainObject> search(GeneralDomainObject param) throws Exception {
        try {
            String query = "SELECT * from " + param.getTableName() + " " + param.getAlias()
                    + " WHERE " + param.getSelectCondition();
            System.out.println(query);

            Connection connection = DBConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return param.readResultSet(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

    @Override
    public List<GeneralDomainObject> searchByForeignKey(GeneralDomainObject param, GeneralDomainObject foreign) throws Exception {
        try {
            String query = "SELECT * from " + param.getTableName() + " " + param.getAlias()
                    + " WHERE " + param.getForeignKeyWithAlias()+ "=" + foreign.getPrimaryKeyColumnNameWithAlias();
            System.out.println(query);

            Connection connection = DBConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return param.readResultSet(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

    @Override
    public List<GeneralDomainObject> searchByForeignKeyBasic(GeneralDomainObject param, GeneralDomainObject foreign) throws Exception {
        try {
            String query = "SELECT * from " + param.getTableName() + " " + param.getAlias()
                    + " WHERE " + param.getForeignKeyWithAlias() + "=" + foreign.getPrimaryKey();
            System.out.println(query);

            Connection connection = DBConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return param.readResultSetBasic(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

    @Override
    public List<GeneralDomainObject> getAllJoin(GeneralDomainObject bet, GeneralDomainObject odds, GeneralDomainObject match, GeneralDomainObject type, GeneralDomainObject team) throws Exception {

        try {
            String query = "SELECT * FROM " + bet.getTableName() + " " + bet.getAlias()
                    + " JOIN " + odds.getTableName() + " " + odds.getAlias() + " ON " + bet.getForeignKeyWithAlias() + "=" + odds.getPrimaryKeyColumnNameWithAlias()
                    + " AND " + bet.getSecondForeignKeyWithAlias() + "=" + odds.getSecondPrimarykeyColumnNameWithAlias()
                    + " JOIN " + match.getTableName() + " " + match.getAlias() + " ON " + odds.getPrimaryKeyColumnNameWithAlias() + "=" + match.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + type.getTableName() + " " + type.getAlias() + " ON " + odds.getSecondPrimarykeyColumnNameWithAlias() + "=" + type.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getAlias() + " ON " + match.getForeignKeyWithAlias() + "=" + team.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getSecondAlias() + " ON " + match.getSecondForeignKeyWithAlias() + "=" + team.getSecondAlias() + "." + team.getPrimaryKeyColumnName();
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return bet.readResultSet(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

    @Override
    public List<GeneralDomainObject> getAllJoin(GeneralDomainObject bet, GeneralDomainObject odds, GeneralDomainObject match, GeneralDomainObject type, GeneralDomainObject team, GeneralDomainObject ticket) throws Exception {

        try {
            String query = "SELECT * FROM " + bet.getTableName() + " " + bet.getAlias()
                    + " JOIN " + odds.getTableName() + " " + odds.getAlias() + " ON " + bet.getForeignKeyWithAlias() + "=" + odds.getPrimaryKeyColumnNameWithAlias()
                    + " AND " + bet.getSecondForeignKeyWithAlias() + "=" + odds.getSecondPrimarykeyColumnNameWithAlias()
                    + " JOIN " + match.getTableName() + " " + match.getAlias() + " ON " + odds.getPrimaryKeyColumnNameWithAlias() + "=" + match.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + type.getTableName() + " " + type.getAlias() + " ON " + odds.getSecondPrimarykeyColumnNameWithAlias() + "=" + type.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getAlias() + " ON " + match.getForeignKeyWithAlias() + "=" + team.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getSecondAlias() + " ON " + match.getSecondForeignKeyWithAlias() + "=" + team.getSecondAlias() + "." + team.getPrimaryKeyColumnName()
                    + " WHERE " + bet.getSecondPrimarykeyColumnNameWithAlias()
                    + "=" + ticket.getPrimaryKey();
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return bet.readResultSet(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }

    }

}
