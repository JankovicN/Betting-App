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
import rs.ac.bg.fon.ps.domain.Ticket;

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
            sb.append("INSERT INTO ")
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
                    .append(param.getTableName() + " " + param.getAlias()).append(" SET ")
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
    
    @Override
    public void update(GeneralDomainObject param, String condition) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ")
                    .append(param.getTableName()+ " " + param.getAlias()).append(" SET ")
                    .append(param.getUpdateValues(param))
                    .append(" WHERE ")
                    .append(condition);
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
            String query = "SELECT * from " + param.getTableName() + " " + param.getAlias();
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
    public List<GeneralDomainObject> search(GeneralDomainObject param, String condition) throws Exception {
        try {
            String query = "SELECT * from " + param.getTableName() + " " + param.getAlias()
                    + " WHERE " + condition;
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
                    + " WHERE " + param.getForeignKeyWithAlias() + "=" + foreign.getPrimaryKeyColumnNameWithAlias();
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
    public List<GeneralDomainObject> getAllJoinCondition(GeneralDomainObject game, GeneralDomainObject team, String condition) throws Exception {

        try {
            String query = "SELECT * FROM " + game.getTableName() + " " + game.getAlias()
                    + " JOIN " + team.getTableName() + " " + team.getAlias() + " ON " + game.getForeignKeyWithAlias() + "=" + team.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getSecondAlias() + " ON " + game.getSecondForeignKeyWithAlias() + "=" + team.getSecondAlias() + "." + team.getPrimaryKeyColumnName()
                    + " WHERE " + condition;
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return game.readResultSet(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }
    @Override
    public List<GeneralDomainObject> getAllJoinConditionBasic(GeneralDomainObject ticket, GeneralDomainObject user, String condition) throws Exception {

        try {
            String query = "SELECT * FROM " + ticket.getTableName() + " " + ticket.getAlias()
                    + " JOIN " + user.getTableName() + " " + user.getAlias() + " ON " + ticket.getForeignKeyWithAlias() + "=" + user.getPrimaryKeyColumnNameWithAlias()
                    + " WHERE " + condition;
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return (new Ticket()).readResultSetUser(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

    @Override
    public List<GeneralDomainObject> getAllJoin(GeneralDomainObject odds, GeneralDomainObject game, GeneralDomainObject team, GeneralDomainObject type) throws Exception {

        try {
            String query = "SELECT * FROM " + odds.getTableName() + " " + odds.getAlias()
                    + " JOIN " + game.getTableName() + " " + game.getAlias() + " ON " + odds.getPrimaryKeyColumnNameWithAlias() + "=" + game.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + type.getTableName() + " " + type.getAlias() + " ON " + odds.getSecondPrimarykeyColumnNameWithAlias() + "=" + type.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getAlias() + " ON " + game.getForeignKeyWithAlias() + "=" + team.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getSecondAlias() + " ON " + game.getSecondForeignKeyWithAlias() + "=" + team.getSecondAlias() + "." + team.getPrimaryKeyColumnName();
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return odds.readResultSet(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

    @Override
    public List<GeneralDomainObject> getAllJoinCondition(GeneralDomainObject odds, GeneralDomainObject game, GeneralDomainObject team, GeneralDomainObject type, String condition) throws Exception {

        try {
            String query = "SELECT * FROM " + odds.getTableName() + " " + odds.getAlias()
                    + " JOIN " + game.getTableName() + " " + game.getAlias() + " ON " + odds.getPrimaryKeyColumnNameWithAlias() + "=" + game.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + type.getTableName() + " " + type.getAlias() + " ON " + odds.getSecondPrimarykeyColumnNameWithAlias() + "=" + type.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getAlias() + " ON " + game.getForeignKeyWithAlias() + "=" + team.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getSecondAlias() + " ON " + game.getSecondForeignKeyWithAlias() + "=" + team.getSecondAlias() + "." + team.getPrimaryKeyColumnName()
                    + " WHERE " + condition;

            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return odds.readResultSet(rs);
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
            System.out.println(query);
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
            System.out.println(query);
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
    public List<GeneralDomainObject> getAllJoin(GeneralDomainObject bet, GeneralDomainObject odds, GeneralDomainObject match, GeneralDomainObject type, GeneralDomainObject team, GeneralDomainObject ticket, GeneralDomainObject user) throws Exception {

        try {
            String query = "SELECT * FROM " + bet.getTableName() + " " + bet.getAlias()
                    + " JOIN " + odds.getTableName() + " " + odds.getAlias() + " ON " + bet.getForeignKeyWithAlias() + "=" + odds.getPrimaryKeyColumnNameWithAlias()
                    + " AND " + bet.getSecondForeignKeyWithAlias() + "=" + odds.getSecondPrimarykeyColumnNameWithAlias()
                    + " JOIN " + match.getTableName() + " " + match.getAlias() + " ON " + odds.getPrimaryKeyColumnNameWithAlias() + "=" + match.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + type.getTableName() + " " + type.getAlias() + " ON " + odds.getSecondPrimarykeyColumnNameWithAlias() + "=" + type.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getAlias() + " ON " + match.getForeignKeyWithAlias() + "=" + team.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getSecondAlias() + " ON " + match.getSecondForeignKeyWithAlias() + "=" + team.getSecondAlias() + "." + team.getPrimaryKeyColumnName()
                    + " JOIN " + ticket.getTableName() + " " + ticket.getAlias() + " ON " + bet.getSecondPrimarykeyColumnNameWithAlias()+ "=" + ticket.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + user.getTableName() + " " + user.getAlias() + " ON " + ticket.getForeignKeyWithAlias()+ "=" + user.getPrimaryKeyColumnNameWithAlias()
                    + " WHERE " + bet.getSecondPrimarykeyColumnNameWithAlias()
                    + "=" + ticket.getPrimaryKey();
            System.out.println(query);
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
    public List<GeneralDomainObject> getJoinForTicket( GeneralDomainObject ticket, GeneralDomainObject bet, GeneralDomainObject odds, GeneralDomainObject match, GeneralDomainObject type) throws Exception {

        try {
            String query = "SELECT * FROM " +ticket.getTableName() + " " + ticket.getAlias() +", "+  bet.getTableName() + " " + bet.getAlias()
                    + " JOIN " + odds.getTableName() + " " + odds.getAlias() + " ON " + bet.getForeignKeyWithAlias() + "=" + odds.getPrimaryKeyColumnNameWithAlias()
                    + " AND " + bet.getSecondForeignKeyWithAlias() + "=" + odds.getSecondPrimarykeyColumnNameWithAlias()
                    + " JOIN " + match.getTableName() + " " + match.getAlias() + " ON " + odds.getPrimaryKeyColumnNameWithAlias() + "=" + match.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + type.getTableName() + " " + type.getAlias() + " ON " + odds.getSecondPrimarykeyColumnNameWithAlias() + "=" + type.getPrimaryKeyColumnNameWithAlias()
                    + " WHERE " + bet.getSecondPrimarykeyColumnNameWithAlias() + "=" + ticket.getPrimaryKey() 
                    + " AND " + ticket.getPrimaryKeyColumnNameWithAlias() + " = " + ticket.getPrimaryKey();
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return ((Ticket)ticket).readResultSetForUpdate(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }
    
    @Override
    public List<GeneralDomainObject> getAllJoin(GeneralDomainObject bet, GeneralDomainObject odds, GeneralDomainObject match, GeneralDomainObject type, GeneralDomainObject team, GeneralDomainObject ticket, GeneralDomainObject user, String condition) throws Exception {

        try {
            String query = "SELECT * FROM " + bet.getTableName() + " " + bet.getAlias()
                    + " JOIN " + odds.getTableName() + " " + odds.getAlias() + " ON " + bet.getForeignKeyWithAlias() + "=" + odds.getPrimaryKeyColumnNameWithAlias()
                    + " AND " + bet.getSecondForeignKeyWithAlias() + "=" + odds.getSecondPrimarykeyColumnNameWithAlias()
                    + " JOIN " + match.getTableName() + " " + match.getAlias() + " ON " + odds.getPrimaryKeyColumnNameWithAlias() + "=" + match.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + type.getTableName() + " " + type.getAlias() + " ON " + odds.getSecondPrimarykeyColumnNameWithAlias() + "=" + type.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getAlias() + " ON " + match.getForeignKeyWithAlias() + "=" + team.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + team.getTableName() + " " + team.getSecondAlias() + " ON " + match.getSecondForeignKeyWithAlias() + "=" + team.getSecondAlias() + "." + team.getPrimaryKeyColumnName()
                    + " JOIN " + ticket.getTableName() + " " + ticket.getAlias() + " ON " + bet.getSecondPrimarykeyColumnNameWithAlias()+ "=" + ticket.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + user.getTableName() + " " + user.getAlias() + " ON " + ticket.getForeignKeyWithAlias()+ "=" + user.getPrimaryKeyColumnNameWithAlias()
                    + " WHERE " + condition;
            System.out.println(query);
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
    public List<GeneralDomainObject> getJoinForTicketCondition(GeneralDomainObject ticket, GeneralDomainObject bet, GeneralDomainObject odds, GeneralDomainObject match, GeneralDomainObject type, String condition) throws Exception {
        
        try {
            String query = "SELECT * FROM " +ticket.getTableName() + " " + ticket.getAlias()
                    + " JOIN " + bet.getTableName() + " " + bet.getAlias() + " ON " + bet.getSecondPrimarykeyColumnNameWithAlias() + "=" + ticket.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + odds.getTableName() + " " + odds.getAlias() + " ON " + bet.getForeignKeyWithAlias() + "=" + odds.getPrimaryKeyColumnNameWithAlias()
                    + " AND " + bet.getSecondForeignKeyWithAlias() + "=" + odds.getSecondPrimarykeyColumnNameWithAlias()
                    + " JOIN " + match.getTableName() + " " + match.getAlias() + " ON " + odds.getPrimaryKeyColumnNameWithAlias() + "=" + match.getPrimaryKeyColumnNameWithAlias()
                    + " JOIN " + type.getTableName() + " " + type.getAlias() + " ON " + odds.getSecondPrimarykeyColumnNameWithAlias() + "=" + type.getPrimaryKeyColumnNameWithAlias()
                    + " WHERE " + condition;
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return ((Ticket)ticket).readResultSetForUpdate(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

    @Override
    public void updateJoin(GeneralDomainObject param, GeneralDomainObject foreign, String condition) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ")
                    .append(param.getAlias()).append(" SET ")
                    .append(param.getUpdateValues(param))
                    .append(" FROM ")
                    .append(param.getTableName() + param.getAlias())
                    .append(" INNER JOIN ")
                    .append(foreign.getTableName() + foreign.getAlias())
                    .append(" ON ")
                    .append(param.getSecondPrimarykeyColumnNameWithAlias()+" = "+foreign.getPrimaryKeyColumnNameWithAlias())
                    .append(" WHERE ")
                    .append(condition);
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

    @Override
    public List<GeneralDomainObject> getJoinForBet(GeneralDomainObject bet, GeneralDomainObject ticket, String ticketCondition, String betCondition) throws Exception {
        try {
            String query = "SELECT * FROM " + bet.getTableName() + " " + bet.getAlias()
                    + " JOIN " + ticket.getTableName() + " " + ticket.getAlias() + " ON " + bet.getSecondPrimarykeyColumnNameWithAlias() + "=" + ticket.getPrimaryKeyColumnNameWithAlias()
                    + " WHERE " + ticketCondition+ " AND  "+ betCondition;
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return bet.readResultSetBasic(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error getting all objects from database!");
        }
    }

}
