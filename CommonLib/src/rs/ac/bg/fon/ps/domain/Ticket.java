/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.domain;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author nikol
 */
public class Ticket implements GeneralDomainObject {

    private int ticketID;
    private BigDecimal wager;
    private double combinedOdds;
    private BigDecimal potentialWin;
    private Date date;
    private boolean win;
    private String state;
    private User user;
    private List<Bet> listOfBets;

    public Ticket() {
        this.state = "unproccesed";
        this.listOfBets = new ArrayList<>();
        this.user = new User();
        this.combinedOdds = 1;
        this.potentialWin = BigDecimal.ZERO;
        this.win = false;
        this.date = new Date();
    }

    public Ticket(int ticketID, BigDecimal wager, double combinedOdds, BigDecimal potentialWin, User user, Date date, boolean win, String state, List<Bet> listOfBets) {
        this.ticketID = ticketID;
        this.wager = wager;
        this.combinedOdds = combinedOdds;
        this.potentialWin = potentialWin;
        this.user = user;
        this.date = date;
        this.win = win;
        this.state = state;
        this.listOfBets = listOfBets;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public BigDecimal getWager() {
        return wager;
    }

    public void setWager(BigDecimal wager) {
        this.wager = wager;
    }

    public double getCombinedOdds() {
        return combinedOdds;
    }

    public void setCombinedOdds(double combinedOdds) {
        this.combinedOdds = combinedOdds;
    }

    public BigDecimal getPotentialWin() {
        return potentialWin;
    }

    public void setPotentialWin(BigDecimal potentialWin) {
        this.potentialWin = potentialWin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public List<Bet> getListOfBets() {
        return listOfBets;
    }

    public void setListOfBets(List<Bet> listOfBets) {
        this.listOfBets = listOfBets;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.ticketID;
        hash = 79 * hash + Objects.hashCode(this.wager);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.combinedOdds) ^ (Double.doubleToLongBits(this.combinedOdds) >>> 32));
        hash = 79 * hash + Objects.hashCode(this.potentialWin);
        hash = 79 * hash + Objects.hashCode(this.user);
        hash = 79 * hash + Objects.hashCode(this.date);
        hash = 79 * hash + (this.win ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.state);
        hash = 79 * hash + Objects.hashCode(this.listOfBets);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ticket other = (Ticket) obj;
        if (this.ticketID != other.ticketID) {
            return false;
        }
        if (Double.doubleToLongBits(this.combinedOdds) != Double.doubleToLongBits(other.combinedOdds)) {
            return false;
        }
        if (this.win != other.win) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.wager, other.wager)) {
            return false;
        }
        if (!Objects.equals(this.potentialWin, other.potentialWin)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return Objects.equals(this.listOfBets, other.listOfBets);
    }

    @Override
    public String toString() {
        return "TicketID: " + ticketID + "  Played on: " + date + "  Passed: " + win + "\n"
                + "Wager: " + wager + "  Combined odds: " + combinedOdds + "  Potential win: " + potentialWin;
    }

    @Override
    public String getTableName() {
        return "ticket";
    }

    @Override
    public int getPrimaryKey() {
        return ticketID;
    }

    @Override
    public String getColumnNamesForInsert() {
        return " win, wager, combinedOdds, potentialWin, date, state, playedByUser";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return addAlias("win") + ", "
                + addAlias("wager") + ", "
                + addAlias("combinedOdds") + ", "
                + addAlias("potentialWin") + ", "
                + addAlias("date") + ","
                + addAlias("state") + ","
                + addAlias("playedByUser");
    }

    @Override
    public String getDeleteCondition() {
        return this.getPrimaryKeyColumnNameWithAlias() + "=" + this.getTicketID();
    }

    @Override
    public String getUpdateCondition() {
        return this.getPrimaryKeyColumnNameWithAlias() + "=" + this.getTicketID();
    }

    @Override
    public String getUpdateValues(GeneralDomainObject gdo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "  date= \'" + sdf.format(date) + "\',  win= " + win + ", state = \'" + state + "\'";
    }

    @Override
    public String getInsertValues() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "(" + this.isWin() + ","
                + this.getWager() + ","
                + this.getCombinedOdds() + ","
                + this.getPotentialWin() + ","
                + "'" + sdf.format(date) + "',"
                + "\'unprocessed\',"
                + +this.getUser().getPrimaryKey() + ")";
    }

    @Override
    public String getAlias() {
        return "ti";
    }

    @Override
    public String getSecondAlias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getValuesForDoubleJoin() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getForeignKey() {
        return "playedByUser";
    }

    @Override
    public String getSecondForeignKey() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "ticketID";
    }

    @Override
    public String getSecondPrimarykeyColumnName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GeneralDomainObject> readResultSet(ResultSet rs) throws Exception {

        List<GeneralDomainObject> list = new ArrayList<>();

        if (rs.next()) {
            do {

                User u = new User();
                u.setName(rs.getString(u.addAlias("name")));
                u.setSurname(rs.getString(u.addAlias("surname")));
                u.setUsername(rs.getString(u.addAlias("username")));
                u.setPassword(rs.getString(u.addAlias("password")));
                u.setRole(Role.valueOf(rs.getString(u.addAlias("role")).toUpperCase()));
                u.setUserID(rs.getInt(u.addAlias("userID")));

                Ticket t = new Ticket();
                t.setTicketID(rs.getInt("ticketID"));
                t.setWin(rs.getBoolean("win"));
                t.setWager(rs.getBigDecimal("wager"));
                t.setCombinedOdds(rs.getDouble("combinedOdds"));
                t.setPotentialWin(rs.getBigDecimal("potentialWin"));
                t.setDate(rs.getDate("date"));
                t.setState(rs.getString("state"));
                t.setUser(u);

                list.add(t);
            } while (rs.next());
            return list;
        } else {
            return null;
        }

    }

    @Override
    public List<GeneralDomainObject> readResultSetBasic(ResultSet rs) throws Exception {

        List<GeneralDomainObject> list = new ArrayList<>();

        if (rs.next()) {
            do {
                Ticket t = new Ticket();
                t.setTicketID(rs.getInt(addAlias("ticketID")));
                t.setWin(rs.getBoolean(addAlias("win")));
                t.setWager(rs.getBigDecimal(addAlias("wager")));
                t.setCombinedOdds(rs.getDouble(addAlias("combinedOdds")));
                t.setPotentialWin(rs.getBigDecimal(addAlias("potentialWin")));
                t.setDate(rs.getDate(addAlias("date")));
                t.setState(rs.getString(addAlias("state")));
                System.out.println("ticket -> " + t);
                list.add(t);
            } while (rs.next());
            return list;
        } else {
            return null;
        }

    }

    @Override
    public String getSelectCondition() {
        return getPrimaryKeyColumnNameWithAlias() + "=" + getPrimaryKey();
    }

    @Override
    public String getForeignKeyWithAlias() {
        return addAlias(getForeignKey());
    }

    @Override
    public String getSecondForeignKeyWithAlias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getPrimaryKeyColumnNameWithAlias() {
        return addAlias(getPrimaryKeyColumnName());
    }

    @Override
    public String getSecondPrimarykeyColumnNameWithAlias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String addAlias(String column) {
        return this.getAlias() + "." + column;
    }

    @Override
    public int getSecondPrimaryKey() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getProcessCondition() {
        return getPrimaryKeyColumnName() + "=" + ticketID + " AND state = unprocessed";
    }

    public List<GeneralDomainObject> readResultSetForUpdate(ResultSet rs) throws SQLException {

        List<GeneralDomainObject> list = new ArrayList<>();

        if (rs.next()) {
            Ticket t = new Ticket();
            t.setTicketID(rs.getInt(t.addAlias("ticketID")));
            t.setWin(rs.getBoolean(t.addAlias("win")));
            t.setWager(rs.getBigDecimal(t.addAlias("wager")));
            t.setCombinedOdds(rs.getDouble(t.addAlias("combinedOdds")));
            t.setPotentialWin(rs.getBigDecimal(t.addAlias("potentialWin")));
            t.setDate(rs.getDate(t.addAlias("date")));
            t.setState(rs.getString(t.addAlias("state")));
            ArrayList<Bet> listOfTicketBets = new ArrayList<>();
            System.out.println("ticket -> " + t);
            do {
                Game game = new Game();
                game.setGameID(rs.getInt(game.addAlias("gameID")));
                game.setIsOver(rs.getBoolean(game.addAlias("isOver")));

                BetType bt = new BetType();

                Odds o = new Odds();
                o.setGame(game);
                o.setType(bt);
                o.setOdds(rs.getDouble(o.addAlias("odds")));

                Bet b = new Bet();
                b.setBetID(rs.getInt(b.addAlias("betID")));
                b.setTicket(t);
                b.setBetOdds(rs.getDouble(b.addAlias("betOdds")));
                b.setPassed(rs.getBoolean(b.addAlias("passed")));
                b.setOdds(o);
                listOfTicketBets.add(b);
            } while (rs.next());
            t.setListOfBets(listOfTicketBets);
            list.add(t);
            return list;
        } else {
            return null;
        }
    }

}
