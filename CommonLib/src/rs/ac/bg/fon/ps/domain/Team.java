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
public class Team implements GeneralDomainObject {

    int teamID;
    String teamName;

    public Team() {
    }

    public Team(int teamID, String teamName) {
        this.teamID = teamID;
        this.teamName = teamName;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.teamID;
        hash = 89 * hash + Objects.hashCode(this.teamName);
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
        final Team other = (Team) obj;
        if (this.teamID != other.teamID) {
            return false;
        }
        return Objects.equals(this.teamName, other.teamName);
    }

    @Override
    public String toString() {
        return teamName;
    }

    @Override
    public String getTableName() {
        return "team";
    }

    @Override
    public int getPrimaryKey() {
        return teamID;
    }

    @Override
    public String getColumnNamesForInsert() {
        return "teamID, teamName";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return addAlias(getPrimaryKeyColumnName()) + ", " + addAlias("teamName");
    }

    @Override
    public String getDeleteCondition() {
        return addAlias(getPrimaryKeyColumnName()) + "=" + this.getTeamID();
    }

    @Override
    public String getUpdateCondition() {
        return addAlias(getPrimaryKeyColumnName()) + "=" + this.getTeamID();
    }

    @Override
    public String getUpdateValues(GeneralDomainObject gdo) {
        Team updatedTeam = (Team) gdo;
        return addAlias("teamName") + "=" + updatedTeam.getTeamName();
    }

    @Override
    public String getInsertValues() {
        return "(" + this.getTeamName() + ")";
    }

    @Override
    public String getAlias() {
        return "homeTeam";
    }

    @Override
    public String getSecondAlias() {
        return "awayTeam";
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
        return "teamID";
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
                Team t = new Team();
                t.setTeamID(rs.getInt(this.addAlias("teamID")));
                t.setTeamName(rs.getString(this.addAlias("teamName")));
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
                Team t = new Team();
                t.setTeamID(rs.getInt(this.addAlias("teamID")));
                t.setTeamName(rs.getString(this.addAlias("teamName")));
                list.add(t);
            } while (rs.next());
            return list;
        } else {
            return null;
        }
    }

    @Override
    public String getSelectCondition() {
        return getPrimaryKeyColumnNameWithAlias() + "=" + this.getTeamID();
    }

    @Override
    public String getForeignKeyWithAlias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getSecondForeignKeyWithAlias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getPrimaryKeyColumnNameWithAlias() {
        return this.addAlias(getPrimaryKeyColumnName());
    }

    @Override
    public String getSecondPrimarykeyColumnNameWithAlias() {
        return getSecondAlias() + "." + getSecondPrimarykeyColumnName();
    }

    @Override
    public String addAlias(String column) {
        return this.getAlias() + "." + column;
    }

    @Override
    public int getSecondPrimaryKey() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
