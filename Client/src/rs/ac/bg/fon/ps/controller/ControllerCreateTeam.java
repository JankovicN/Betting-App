/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import rs.ac.bg.fon.ps.domain.Team;
import rs.ac.bg.fon.ps.view.form.DialogCreateTeam;

/**
 *
 * @author nikol
 */
public class ControllerCreateTeam {

    private final DialogCreateTeam dialogCreateTeam;

    public ControllerCreateTeam(DialogCreateTeam dialogCreateTeam) {
        this.dialogCreateTeam = dialogCreateTeam;
    }

    public void openForm() {
        this.dialogCreateTeam.setVisible(true);
    }
    
}
