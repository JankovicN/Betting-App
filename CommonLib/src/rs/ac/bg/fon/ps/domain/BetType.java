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
public class BetType implements GeneralDomainObject {

    private int typeID;
    private String typeName;

    public BetType() {
    }

    public BetType(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.typeID;
        hash = 23 * hash + Objects.hashCode(this.typeName);
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
        final BetType other = (BetType) obj;
        if (this.typeID != other.typeID) {
            return false;
        }
        return Objects.equals(this.typeName, other.typeName);
    }

    @Override
    public String toString() {
        return typeName;
    }

    @Override
    public String getTableName() {
        return "bettype";
    }

    @Override
    public int getPrimaryKey() {
        return typeID;
    }

    @Override
    public String getColumnNamesForInsert() {
        return "typeID, typeName";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return getPrimaryKeyColumnNameWithAlias() + ", " + addAlias("typeName");
    }

    @Override
    public String getDeleteCondition() {
        return getPrimaryKeyColumnNameWithAlias() + "=" + this.getTypeID();
    }

    @Override
    public String getUpdateCondition() {
        return getPrimaryKeyColumnNameWithAlias() + "=" + this.getTypeID();
    }

    @Override
    public String getUpdateValues(GeneralDomainObject gdo) {
        BetType updatedBetType = (BetType) gdo;
        return addAlias("teamName") + "=" + updatedBetType.getTypeName();
    }

    @Override
    public String getInsertValues() {
        return "(" + this.getTypeName() + ")";
    }

    @Override
    public String getAlias() {
        return "t";
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
        return "typeID";
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
                BetType bt = new BetType();
                bt.setTypeID(rs.getInt(this.addAlias("typeID")));
                bt.setTypeName(rs.getString(this.addAlias("typeName")));
                list.add(bt);
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
                BetType bt = new BetType();
                bt.setTypeID(rs.getInt(addAlias("typeID")));
                bt.setTypeName(rs.getString(addAlias("typeName")));
                list.add(bt);
            } while (rs.next());
            return list;
        } else {
            return null;
        }
    }

    @Override
    public String getSelectCondition() {
        return this.getPrimaryKeyColumnNameWithAlias() + "=" + this.getTypeID();

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
