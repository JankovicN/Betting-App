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
    private String state;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Game() {
        this.home = new Team();
        this.homeGoals = 0;
        this.away = new Team();
        this.awayGoals = 0;
        state = "NS";
    }

    public Game(int matchID, Date dateOfPlay, Team home, int homeGoals, Team away, int awayGoals, String state) {
        this.gameID = matchID;
        this.dateOfPlay = dateOfPlay;
        this.home = home;
        this.homeGoals = homeGoals;
        this.away = away;
        this.awayGoals = awayGoals;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        hash = 53 * hash + Objects.hashCode(this.state);
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
        if (!this.state.equals(other.state)) {
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
        return "  " + home.toString() + " - " + away.toString();
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
        return " dateOfPlay, home, homeGoals, away, awayGoals, state";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return addAlias("gameID") + ", " + addAlias("dateOfPlay") + ", " + addAlias("home") + ", " + addAlias("homeGoals") + ", " + addAlias("away") + ", " + addAlias("awayGoals") + "," + addAlias("state-");
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return addAlias("dateOfPlay") + "='" + sdf.format(dateOfPlay) + "',"
                + addAlias("home") + "=" + updatedGame.getHome().getTeamID() + ","
                + addAlias("homeGoals") + "=" + updatedGame.getHomeGoals() + ","
                + addAlias("away") + "=" + updatedGame.getAway().getTeamID() + ","
                + addAlias("awayGoals") + "=" + updatedGame.getAwayGoals() + ","
                + addAlias("state") + "='" + updatedGame.getState()+"'";
    }

    @Override
    public String getInsertValues() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "('" + sdf.format(dateOfPlay) + "',"
                + this.getHome().getTeamID() + ","
                + this.getHomeGoals() + ","
                + this.getAway().getTeamID() + ","
                + this.getAwayGoals() + ","
                + "'"+this.getState() + "')";
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
                String date = rs.getString(this.addAlias("dateOfPlay"));
                Date sqlDate = sqlDateFormat.parse(date);
                SimpleDateFormat sdfTableView = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                game.setDateOfPlay(sqlDate);
                game.setHome(homeTeam);
                game.setHomeGoals(rs.getInt(this.addAlias("homeGoals")));
                game.setAway(awayTeam);
                game.setAwayGoals(rs.getInt(this.addAlias("awayGoals")));
                game.setState(rs.getString(this.addAlias("state")));

                list.add(game);
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
                game.setState(rs.getString(game.addAlias("state")));
                System.out.println("Game: " + game);

                list.add(game);

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

    public String getNotStartedCondition() {
        return addAlias("state") + " = " + " \'NS\'";
    }

    public String getNotFinishedCondition() {
        return addAlias("state") + " != " + " \'FT\'" + " AND " + addAlias("dateOfPlay") + " < '" + sqlDateFormat.format(new Date())+"'";
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

    public String getActiveCondition() {
        return addAlias("state") + " = \'NS\'";
    }

    public void setGoals() {
        homeGoals = generateRandomNumber();
        awayGoals = generateRandomNumber();
    }

    private int generateRandomNumber() {
        double range = Math.random();

        if (range < 0.5) {
            return randomWithRange(0, 2);
        } else if (range < 0.9) {
            return randomWithRange(1, 3);
        } else {
            return randomWithRange(2, 6);
        }
    }

    private int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
