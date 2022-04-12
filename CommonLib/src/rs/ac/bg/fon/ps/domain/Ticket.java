/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.domain;

import java.math.BigDecimal;
import java.sql.ResultSet;
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
    private double oddsSum;
    private BigDecimal totalWin;
    private Date date;
    private boolean win;
    private String state;
    private User user;
    private List<Bet> listOfBets;

    public Ticket() {
        state = "unproccesed";
        listOfBets = new ArrayList<>();
    }

    public Ticket(int ticketID, BigDecimal wager, double oddsSum, BigDecimal totalWin, User user, Date date, boolean win, String state, List<Bet> listOfBets) {
        this.ticketID = ticketID;
        this.wager = wager;
        this.oddsSum = oddsSum;
        this.totalWin = totalWin;
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

    public double getOddsSum() {
        return oddsSum;
    }

    public void setOddsSum(double oddsSum) {
        this.oddsSum = oddsSum;
    }

    public BigDecimal getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(BigDecimal totalWin) {
        this.totalWin = totalWin;
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
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.oddsSum) ^ (Double.doubleToLongBits(this.oddsSum) >>> 32));
        hash = 79 * hash + Objects.hashCode(this.totalWin);
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
        if (Double.doubleToLongBits(this.oddsSum) != Double.doubleToLongBits(other.oddsSum)) {
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
        if (!Objects.equals(this.totalWin, other.totalWin)) {
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
                + "Wager: " + wager + "  Odds sum: " + oddsSum + "  Total win: " + totalWin;
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
        return "ticketID, win, wager, oddsSum, totalWin, date, state, playedByUser";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return getAlias() + ".ticketID, " + getAlias() + ".win, " + getAlias() + ".wager, " + getAlias() + ".oddsSum, " + getAlias() + ".totalWin, " + getAlias() + ".date," + getAlias() + ".state," + getAlias() + ".playedByUser";
    }

    @Override
    public String getDeleteCondition() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getUpdateCondition() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getUpdateValues(GeneralDomainObject gdo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getInsertValues() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<GeneralDomainObject> readResultSetBasic(ResultSet rs) throws Exception {

        List<GeneralDomainObject> list = new ArrayList<>();

        if (rs.next()) {
            do {
                Ticket t = new Ticket();
                t.setTicketID(rs.getInt("ticketID"));
                t.setWin(rs.getBoolean("win"));
                t.setWager(rs.getBigDecimal("wager"));
                t.setOddsSum(rs.getDouble("oddsSum"));
                t.setTotalWin(rs.getBigDecimal("totalWin"));
                t.setDate(rs.getDate("date"));
                t.setState(rs.getString("state"));
            } while (rs.next());
            return list;
        } else {
            return null;
        }

    }

    @Override
    public String getSelectCondition() {
        return " ticketID=" + ticketID;
    }
}
