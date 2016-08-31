/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import java.awt.*;
import javax.swing.*;
import managers.SimulationManager;

/**
 *
 * @author lukefallon
 */
public class TrafficSimUI {
    JFrame frame;
    ControlPane controlPane;
    SimulationPane simPane;
    private SimulationManager simManager;
    
    private static final int FRAME_X = 900;
    private static final int FRAME_Y = 600;
    private boolean lockFrame = true;
    
    public TrafficSimUI()
    {
        initComponents();
    }

    private void initComponents()
    {
        // Instantiate and setup the JFrame
        frame = new JFrame("Autonomous Traffic with 2-Dimensional Management");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_X,FRAME_Y);
        
        // Enable/disable the window from being resized
        if(lockFrame)
        {
            frame.setMinimumSize(new Dimension(FRAME_X, FRAME_Y));
            frame.setMaximumSize(new Dimension(FRAME_X, FRAME_Y));
        }
        
        simManager = new SimulationManager();
        
        simPane = new SimulationPane(simManager);
        controlPane = new ControlPane(simManager);
        
        simManager.setControlPane(controlPane);
        simManager.setSimPane(simPane);
        
        frame.add(simPane, BorderLayout.CENTER);
        frame.add(controlPane, BorderLayout.EAST);
        
        frame.setVisible(true);
    }
}
