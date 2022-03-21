/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

/**
 *
 * @author nikol
 */
public class Controller {
    
    private static Controller instance;

    public Controller() {
    }
    
    public static Controller getInstance(){
        if(instance==null){
            instance= new Controller();
        }
        return instance;
    }
    
}
