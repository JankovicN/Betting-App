/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operations;

/**
 *
 * @author nikol
 */
public interface Operations {

    public static final int LOGIN = 1;
    public static final int GET_USER_TICKETS = 2;
    public static final int GET_TICKET_WITH_BETS = 3;
    public static final int GET_TEAMS = 4;
    public static final int ADD_TEAM = 5;
    public static final int GET_BETTYPE = 6;
    public static final int CREATE_GAME = 7;
    public static final int GET_ODDS = 8;
    public static final int GET_GAMES_NOT_STARTED = 9;
    public static final int CREATE_TICKET = 10;
    public static final int GET_BETS_FOR_GAME = 11;
    public static final int UPDATE_GAME = 12;
    public static final int UPDATE_TICKET=13;   
    public static final int GET_ACTIVE_GAMES=14;
    public static final int GET_UNPROCESSED_TICKETS=15;
    public static final int CANCEL_TICKET=16;
}
