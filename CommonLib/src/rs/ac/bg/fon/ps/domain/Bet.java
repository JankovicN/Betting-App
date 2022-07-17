/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author nikol
 */
public class Bet implements GeneralDomainObject {

    private int betID;
    private Ticket ticket;
    private double betOdds;
    private boolean passed;
    private Odds odds;
    
    public Bet() {
        this.ticket = new Ticket();
        this.odds = new Odds();
        this.passed = false;
    }

    public Bet(int betID, Ticket ticket, double betOdds, boolean passed, Odds odds) {
        this.betID = betID;
        this.ticket = ticket;
        this.betOdds = betOdds;
        this.passed = passed;
        this.odds = odds;
    }

    public Odds getOdds() {
        return odds;
    }

    public void setOdds(Odds odds) {
        this.odds = odds;
    }

    public int getBetID() {
        return betID;
    }

    public void setBetID(int betID) {
        this.betID = betID;
    }

    public double getBetOdds() {
        return betOdds;
    }

    public void setBetOdds(double betOdds) {
        this.betOdds = betOdds;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.betID;
        hash = 43 * hash + Objects.hashCode(this.ticket);
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.betOdds) ^ (Double.doubleToLongBits(this.betOdds) >>> 32));
        hash = 43 * hash + (this.passed ? 1 : 0);
        hash = 43 * hash + Objects.hashCode(this.odds);
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
        final Bet other = (Bet) obj;
        if (this.betID != other.betID) {
            return false;
        }
        if (Double.doubleToLongBits(this.betOdds) != Double.doubleToLongBits(other.betOdds)) {
            return false;
        }
        if (this.passed != other.passed) {
            return false;
        }
        if (!Objects.equals(this.ticket, other.ticket)) {
            return false;
        }
        return Objects.equals(this.odds, other.odds);
    }

    @Override
    public String toString() {
        return "Ticket: " + ticket.getTicketID() + "  Passed: " + passed + "\nGame: " + odds.getGame().toString() + "   Type: " + odds.getType().toString() + "  Odds: " + betOdds;
    }

    @Override
    public String getTableName() {
        return "bet";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "ticketID, betOdds, passed, game, type";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return addAlias("ticketID") + ", " + addAlias("betOdds") + ", " + addAlias("passed") + ", " + addAlias("game") + ", " + addAlias("types");
    }

    @Override
    public String getDeleteCondition() {
        return this.addAlias(getTicket().getPrimaryKeyColumnName()) + "=" + this.getTicket().getPrimaryKey();
    }

    @Override
    public String getUpdateCondition() {
        return this.addAlias(getTicket().getPrimaryKeyColumnName()) + "=" + this.getTicket().getPrimaryKey();
    }

    @Override
    public String getUpdateValues(GeneralDomainObject gdo) {
        Bet updatedBet = (Bet) gdo;
        return addAlias("passed") + "=" + updatedBet.isPassed();
    }

    @Override
    public String getInsertValues() {
        return "(" + this.getTicket().getTicketID() + ","
                + this.getOdds().getOdds()+ ","
                + "0 ,"
                + this.getOdds().getGame().getGameID() + ","
                + this.getOdds().getType().getTypeID()
                + ")";
    }

    @Override
    public String getAlias() {
        return "b";
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
        return "game";
    }

    @Override
    public String getSecondForeignKey() {
        return "type";
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "betID";
    }

    @Override
    public String getSecondPrimarykeyColumnName() {
        return "ticketID";
    }

    @Override
    public List<GeneralDomainObject> readResultSet(ResultSet rs) throws Exception {
        List<GeneralDomainObject> list = new ArrayList<>();

        if (rs.next()) {
            do {
                Team home = new Team();
                home.setTeamID(rs.getInt(home.addAlias("teamID")));
                home.setTeamName(rs.getString(home.addAlias("teamName")));

                Team away = new Team();
                away.setTeamID(rs.getInt(away.getSecondAlias() + ".teamID"));
                away.setTeamName(rs.getString(away.getSecondAlias() + ".teamName"));

                Game game = new Game();
                game.setGameID(rs.getInt(game.addAlias("gameID")));
                game.setDateOfPlay(rs.getDate(game.addAlias("dateOfPlay")));
                game.setHome(home);
                game.setHomeGoals(rs.getInt(game.addAlias("homeGoals")));
                game.setAway(away);
                game.setAwayGoals(rs.getInt(game.addAlias("awayGoals")));
                game.setIsOver(rs.getBoolean(game.addAlias("isOver")));

                BetType bt = new BetType();
                bt.setTypeID(rs.getInt(bt.addAlias("typeID")));
                bt.setTypeName(rs.getString(bt.addAlias("typeName")));

                Odds o = new Odds();
                o.setGame(game);
                o.setType(bt);
                o.setOdds(rs.getDouble(o.addAlias("odds")));

                User u = new User();
                u.setName(rs.getString(u.addAlias("name")));
                u.setSurname(rs.getString(u.addAlias("surname")));
                u.setUsername(rs.getString(u.addAlias("username")));
                u.setPassword(rs.getString(u.addAlias("password")));
                u.setRole(Role.valueOf(rs.getString(u.addAlias("role")).toUpperCase()));
                u.setUserID(rs.getInt(u.addAlias("userID")));

                Ticket t = new Ticket();
                t.setTicketID(rs.getInt(t.addAlias("ticketID")));
                t.setWin(rs.getBoolean(t.addAlias("win")));
                t.setWager(rs.getBigDecimal(t.addAlias("wager")));
                t.setCombinedOdds(rs.getDouble(t.addAlias("combinedOdds")));
                t.setPotentialWin(rs.getBigDecimal(t.addAlias("potentialWin")));
                t.setDate(rs.getDate(t.addAlias("date")));
                t.setState(rs.getString(t.addAlias("state")));
                t.setUser(u);

                Bet b = new Bet();
                b.setBetID(rs.getInt(b.addAlias("betID")));
                b.setTicket(t);
                b.setBetOdds(rs.getDouble(b.addAlias("betOdds")));
                b.setPassed(rs.getBoolean(b.addAlias("passed")));
                b.setOdds(o);
                
                list.add(b);
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
                Odds odds = new Odds();
                odds = (Odds) odds.readResultSet(rs).get(0);

                Ticket t = new Ticket();
                t = (Ticket) t.readResultSet(rs).get(0);

                Bet b = new Bet();
                b.setBetID(rs.getInt(addAlias("betID")));
                b.setTicket(t);
                b.setBetOdds(rs.getDouble(addAlias("betOdds")));
                b.setPassed(rs.getBoolean(addAlias("passed")));
                b.setOdds(odds);

            } while (rs.next());
            return list;
        } else {
            return null;
        }
    }

    @Override
    public String getSelectCondition() {
        return this.addAlias(getTicket().getPrimaryKeyColumnName()) + "=" + this.getTicket().getPrimaryKeyColumnNameWithAlias();
    }

    @Override
    public int getPrimaryKey() {
        return this.getBetID();
    }

    @Override
    public String getForeignKeyWithAlias() {
        return addAlias(getForeignKey());
    }

    @Override
    public String getSecondForeignKeyWithAlias() {
        return addAlias(getSecondForeignKey());
    }

    @Override
    public String getPrimaryKeyColumnNameWithAlias() {
        return addAlias(getPrimaryKeyColumnName());
    }

    @Override
    public String getSecondPrimarykeyColumnNameWithAlias() {
        return addAlias(getSecondPrimarykeyColumnName());
    }

    @Override
    public String addAlias(String column) {
        return this.getAlias() + "." + column;
    }

    @Override
    public int getSecondPrimaryKey() {
        return this.getTicket().getTicketID();
    }
}
