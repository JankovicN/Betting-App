/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.repository;

import java.util.List;

/**
 *
 * @author nikol
 */
public interface Repository<T> {
    
    void add(T param) throws Exception;
    void edit(T param) throws Exception;
    void delete(T param) throws Exception;
    List<T> getAll(T param) throws Exception;
    int addReturnKey(T param) throws Exception;// insert into table and return primary key
}
