/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.repository;

import java.util.List;
import rs.ac.bg.fon.ps.domain.GeneralDomainObject;

/**
 *
 * @author nikol
 */
public interface Repository<T> {
    
    void add(T param) throws Exception;
    void edit(T param) throws Exception;
    void update(T param, String condition) throws Exception;
    void delete(T param) throws Exception;
    List<T> search(T param) throws Exception;
    List<T> search(T param, String condition) throws Exception;
    List<T> searchByForeignKey(T param, T foreign) throws Exception;
    List<T> searchByForeignKeyBasic(T param, T foreign) throws Exception;
    List<T> getAll(T param) throws Exception;
    List<T> getAllJoinConditionBasic(T ticket, T user, String condition) throws Exception;
    List<T> getAllJoinCondition(T game,T team, String condition) throws Exception;
    List<T> getAllJoin(T odds,T game,T team,T type) throws Exception;
    List<T> getAllJoinCondition(T odds,T game,T team,T type, String condition) throws Exception;
    List<T> getAllJoin(T bet,T odds,T match,T type,T team) throws Exception;
    List<T> getAllJoin(T bet,T odds,T match,T type,T team,T ticket) throws Exception;
    List<T> getAllJoin(T bet,T odds,T match,T type,T team,T ticket, T user) throws Exception;
    List<T> getAllJoin(T bet,T odds,T match,T type,T team,T ticket, T user, String condition) throws Exception;
    List<T> getJoinForTicket(T ticket,T bet, T odds,T match,T type) throws Exception;
    int addReturnKey(T param) throws Exception;// insert into table and return primary key
}
