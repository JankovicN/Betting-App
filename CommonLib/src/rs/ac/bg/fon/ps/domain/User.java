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
public class User implements GeneralDomainObject {

    private int userID;
    private String name;
    private String surname;
    private String username;
    private String password;
    private Role role;
    private String dateOfLogin;

    public User() {
        this.dateOfLogin = "Never logged in";
    }

    public User(int userID, String name, String surname, String username, String password, Role role) {
        this.userID = userID;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getDateOfLogin() {
        return dateOfLogin;
    }

    public void setDateOfLogin(String dateOfLogin) {
        this.dateOfLogin = dateOfLogin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.userID;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.surname);
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.password);
        hash = 97 * hash + Objects.hashCode(this.role);
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
        final User other = (User) obj;
        if (this.userID != other.userID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return this.role == other.role;
    }

    @Override
    public String getTableName() {
        return "user";
    }

    @Override
    public int getPrimaryKey() {
        return userID;
    }

    @Override
    public String getColumnNamesForInsert() {
        return "userID, name, surname, username, password, role";
    }

    @Override
    public String getColumnNamesForInsertWithAlias() {
        return addAlias("userID") + ", " + addAlias("name") + ".name, " + addAlias("surname") + ", " + addAlias("username") + ", " + addAlias("password") + ", " + addAlias("role");
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
        return "u";
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
        return "userID";
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
                u.setName(rs.getString(addAlias("name")));
                u.setSurname(rs.getString(addAlias("surname")));
                u.setUsername(rs.getString(addAlias("username")));
                u.setPassword(rs.getString(addAlias("password")));
                u.setRole(Role.valueOf(rs.getString(addAlias("role")).toUpperCase()));
                u.setUserID(rs.getInt(addAlias("userID")));
                list.add(u);
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
                User u = new User();
                u.setName(rs.getString(addAlias("name")));
                u.setSurname(rs.getString(addAlias("surname")));
                u.setUsername(rs.getString(addAlias("username")));
                u.setPassword(rs.getString(addAlias("password")));
                u.setRole(Role.valueOf(rs.getString(addAlias("role")).toUpperCase()));
                u.setUserID(rs.getInt(addAlias("userID")));
                list.add(u);
            } while (rs.next());
            return list;
        } else {
            return null;
        }
    }

    @Override
    public String getSelectCondition() {
        return getAlias() + ".username='" + this.getUsername() + "' AND " + getAlias() + ".password='" + this.getPassword() + "'";
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
}
