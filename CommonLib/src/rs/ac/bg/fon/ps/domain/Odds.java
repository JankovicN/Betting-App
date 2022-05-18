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
public class Odds implements GeneralDomainObject {

    private Game game;
    private BetType type;
    private double odds;

    public Odds() {
    }

    public Odds(Game game, BetType type, double odds) {
        this.game = game;
        this.type = type;
        this.odds = odds;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public BetType getType() {
        return type;
    }

    public void setType(BetType type) {
        this.type = type;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.game);
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.odds) ^ (Double.doubleToLongBits(this.odds) >>> 32));
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
        final Odds other = (Odds) obj;
        if (Double.doubleToLongBits(this.odds) != Double.doubleToLongBits(other.odds)) {
            return false;
        }
        if (!Objects.equals(this.game, other.game)) {
            return false;
        }
        return Objects.equals(this.type, other.type);
    }

    @Override
    public String toString() {
        return "Match: " + game.toString() + "/nType: " + type.toString() + "/nOdds: " + odds;
    }

    @Override
    public String getTableName() {
        return "odds";
    }

    @Override
    public int getPrimaryKey() {
        return this.getGame().getPrimaryKey();
    }

    @Override
    public int getSecondPrimaryKey() {
        return this.getType().getPrimaryKey();
    }

    @Override
    public String getColumnNamesForInsert() {
        return "gameID, typeID, odds";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return addAlias(this.getGame().getPrimaryKeyColumnName()) + "," + addAlias(this.getType().getPrimaryKeyColumnName()) + "," + addAlias("odds");
    }

    @Override
    public String getDeleteCondition() {
        return addAlias(this.getGame().getPrimaryKeyColumnName()) + "=" + this.getGame().getPrimaryKey() + " AND "
                + addAlias(this.getType().getPrimaryKeyColumnName()) + "=" + this.getType().getPrimaryKey();
    }

    @Override
    public String getUpdateCondition() {
        return addAlias(this.getGame().getPrimaryKeyColumnName()) + "=" + this.getGame().getPrimaryKey() + " AND "
                + addAlias(this.getType().getPrimaryKeyColumnName()) + "=" + this.getType().getPrimaryKey();
    }

    @Override
    public String getUpdateValues(GeneralDomainObject gdo) {
        Odds updatedOdds = (Odds) gdo;
        return addAlias("odds") + "=" + updatedOdds.getOdds();
    }

    @Override
    public String getInsertValues() {
        return "(" + this.getGame().getGameID() + ","
                + this.getType().getTypeID() + ","
                + this.getOdds() + ")";
    }

    @Override
    public String getAlias() {
        return "o";
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
        return "gameID";
    }

    @Override
    public String getSecondPrimarykeyColumnName() {
        return "typeID";
    }

    @Override
    public List<GeneralDomainObject> readResultSet(ResultSet rs) throws Exception {
        List<GeneralDomainObject> list = new ArrayList<>();

        if (rs.next()) {
            do {
                Game g = new Game();
                g = (Game) g.readResultSet(rs).get(0);
                
                BetType bt = new BetType();
                bt = (BetType) bt.readResultSet(rs).get(0);
                
                Odds o = new Odds();
                o.setGame(g);
                o.setType(bt);
                o.setOdds(rs.getDouble(this.addAlias("odds")));
                list.add(o);
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
                Game game = new Game();
                game = (Game) game.readResultSet(rs).get(0);
                
                BetType betType = new BetType();
                betType = (BetType) betType.readResultSet(rs).get(0);
                
                Odds odds = new Odds();
                odds.setGame(game);
                odds.setType(betType);
                odds.setOdds(rs.getDouble(this.addAlias("odds")));
                list.add(odds);
            } while (rs.next());
            return list;
        } else {
            return null;
        }
    }

    @Override
    public String getSelectCondition() {
        return addAlias(this.getGame().getPrimaryKeyColumnName()) + "=" + this.getGame().getPrimaryKey() + " AND "
                + addAlias(this.getType().getPrimaryKeyColumnName()) + "=" + this.getType().getPrimaryKey();
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
        return this.addAlias(getSecondPrimarykeyColumnName());
    }

    @Override
    public String addAlias(String column) {
        return this.getAlias() + "." + column;
    }
}
