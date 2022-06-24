/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author nikol
 */
public class Game implements GeneralDomainObject {

    private int gameID;
    private Date dateOfPlay;
    private Team home;
    private int homeGoals;
    private Team away;
    private int awayGoals;
    private boolean isOver;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Game() {
        this.home = new Team();
        this.homeGoals = 0;
        this.away = new Team();
        this.awayGoals = 0;
        isOver = false;
    }

    public Game(int matchID, Date dateOfPlay, Team home, int homeGoals, Team away, int awayGoals, boolean isOver) {
        this.gameID = matchID;
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

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
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
        hash = 53 * hash + this.gameID;
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
        final Game other = (Game) obj;
        if (this.gameID != other.gameID) {
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
        return home.toString() + " - " + away.toString();
    }

    @Override
    public String getTableName() {
        return "game";
    }

    @Override
    public int getPrimaryKey() {
        return gameID;
    }

    @Override
    public String getColumnNamesForInsert() {
        return " dateOfPlay, home, homeGoals, away, awayGoals, isOver";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return addAlias("gameID") + ", " + addAlias("dateOfPlay") + ", " + addAlias("home") + ", " + addAlias("homeGoals") + ", " + addAlias("away") + ", " + addAlias("awayGoals") + "," + addAlias("isOver");
    }

    @Override
    public String getDeleteCondition() {
        return getPrimaryKeyColumnNameWithAlias() + "=" + this.getGameID();
    }

    @Override
    public String getUpdateCondition() {
        return getPrimaryKeyColumnNameWithAlias() + "=" + this.getGameID();
    }

    @Override
    public String getUpdateValues(GeneralDomainObject gdo) {
        Game updatedGame = (Game) gdo;
        return addAlias("dateOfPlay") + "=" + sdf.format(updatedGame.getDateOfPlay()) + ","
                + addAlias("home") + "=" + updatedGame.getHome().getTeamID() + ","
                + addAlias("homeGoals") + "=" + updatedGame.getHomeGoals() + ","
                + addAlias("away") + "=" + updatedGame.getAway().getTeamID() + ","
                + addAlias("awayGoals") + "=" + updatedGame.getAwayGoals() + ","
                + addAlias("isOver") + "=" + updatedGame.isIsOver();
    }

    @Override
    public String getInsertValues() {
        java.sql.Date date = new java.sql.Date(dateOfPlay.getTime());
        return "('" + date + "',"
                + this.getHome().getTeamID() + ","
                + this.getHomeGoals() + ","
                + this.getAway().getTeamID() + ","
                + this.getAwayGoals() + ","
                + this.isIsOver() + ")";
    }

    @Override
    public String getAlias() {
        return "g";
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
        return "home";
    }

    @Override
    public String getSecondForeignKey() {
        return "away";
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "gameID";
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
                Team homeTeam = new Team();
                homeTeam.setTeamID(rs.getInt(homeTeam.addAlias("teamID")));
                homeTeam.setTeamName(rs.getString(homeTeam.addAlias("teamName")));

                Team awayTeam = new Team();
                awayTeam.setTeamID(rs.getInt(awayTeam.getSecondAlias() + ".teamID"));
                awayTeam.setTeamName(rs.getString(awayTeam.getSecondAlias() + ".teamName"));

                Game game = new Game();
                game.setGameID(rs.getInt(this.addAlias("gameID")));
                game.setDateOfPlay(rs.getDate(this.addAlias("dateOfPlay")));
                game.setHome(homeTeam);
                game.setHomeGoals(rs.getInt(this.addAlias("homeGoals")));
                game.setAway(awayTeam);
                game.setAwayGoals(rs.getInt(this.addAlias("awayGoals")));
                game.setIsOver(rs.getBoolean(this.addAlias("isOver")));

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

            } while (rs.next());
            return list;
        } else {
            return null;
        }
    }

    @Override
    public String getSelectCondition() {
        return this.getPrimaryKeyColumnNameWithAlias() + "=" + this.getGameID();
    }

    @Override
    public String getForeignKeyWithAlias() {
        return this.addAlias(getForeignKey());
    }

    @Override
    public String getSecondForeignKeyWithAlias() {
        return this.addAlias(getSecondForeignKey());
    }

    @Override
    public String getPrimaryKeyColumnNameWithAlias() {
        return this.addAlias(getPrimaryKeyColumnName());
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
}
