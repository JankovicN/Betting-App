/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author nikol
 */
public class Match implements GeneralDomainObject {
    
    private int matchID;
    private Date dateOfPlay;
    private Team home;
    private int homeGoals;
    private Team away;
    private int awayGoals;
    private boolean isOver;

    public Match() {
        isOver = false;
    }

    public Match(int matchID, Date dateOfPlay, Team home, int homeGoals, Team away, int awayGoals, boolean isOver) {
        this.matchID = matchID;
        this.dateOfPlay = dateOfPlay;
        this.home = home;
        this.homeGoals = homeGoals;
        this.away = away;
        this.awayGoals = awayGoals;
        this.isOver = isOver;
    }


    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public Date getDateOfPlay() {
        return dateOfPlay;
    }

    public void setDateOfPlay(Date dateOfPlay) {
        this.dateOfPlay = dateOfPlay;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }
    
    
    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public boolean isIsOver() {
        return isOver;
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.matchID;
        hash = 53 * hash + Objects.hashCode(this.dateOfPlay);
        hash = 53 * hash + Objects.hashCode(this.home);
        hash = 53 * hash + this.homeGoals;
        hash = 53 * hash + Objects.hashCode(this.away);
        hash = 53 * hash + this.awayGoals;
        hash = 53 * hash + (this.isOver ? 1 : 0);
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
        final Match other = (Match) obj;
        if (this.matchID != other.matchID) {
            return false;
        }
        if (this.homeGoals != other.homeGoals) {
            return false;
        }
        if (this.awayGoals != other.awayGoals) {
            return false;
        }
        if (this.isOver != other.isOver) {
            return false;
        }
        if (!Objects.equals(this.dateOfPlay, other.dateOfPlay)) {
            return false;
        }
        if (!Objects.equals(this.home, other.home)) {
            return false;
        }
        return Objects.equals(this.away, other.away);
    }

    @Override
    public String toString() {
        return home.toString() +" - "+ away.toString();
    }

    

    @Override
    public String getTableName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getColumnNamesForInsert() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getSecondForeignKey() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getPrimaryKeyColumnName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public String getSelectCondition() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
