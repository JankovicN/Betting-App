/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ps.domain;

import java.io.Serializable;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author nikol
 */
public interface GeneralDomainObject extends Serializable{
    
    String getTableName(); // returns String representing the name of the table
    String getColumnNamesForInsert(); // returns String representing column names that are used for INSERT query
    String getColumnNamesForInsertWithAlias(); // returns String representing column names with alias that are used for INSERT query
    String getDeleteCondition(); // returns String representing the condition for DELETE query
    String getUpdateCondition(); // returns String representing the condition for UPDATE query
    String getUpdateValues(GeneralDomainObject gdo); // returns String representing values that are changing in UPDATE query
    String getInsertValues(); // returns String representing values that are inserted in table, in INSERT queey
    String getSelectCondition();// returns String represeiting valuses for WHERE in SELECT query
    String getAlias(); //returns alias for SQL query
    String getSecondAlias();
    String getValuesForDoubleJoin(); // returns String representing values when joining three tables
    String getForeignKey(); // returns foreign key as String
    String getSecondForeignKey(); // returns second foreign key as String, if the table has two foreign keys
    String getPrimaryKeyColumnName(); // returns the name of the column of primary key
    String getSecondPrimarykeyColumnName(); // returns the name of the column of second primary key, if the table has two primary keys
    List<GeneralDomainObject> readResultSet(ResultSet rs) throws Exception; // reads ResultSet and returns list of objects
    
}
