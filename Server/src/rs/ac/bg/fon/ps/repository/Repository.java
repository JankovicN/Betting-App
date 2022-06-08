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
    List<T> search(T param) throws Exception;
    List<T> searchByForeignKey(T param, T foreign) throws Exception;
    List<T> searchByForeignKeyBasic(T param, T foreign) throws Exception;
    List<T> getAll(T param) throws Exception;
    List<T> getAllJoin(T bet,T odds,T match,T type,T team) throws Exception;
    List<T> getAllJoin(T bet,T odds,T match,T type,T team,T ticket) throws Exception;
    
    int addReturnKey(T param) throws Exception;// insert into table and return primary key
}
