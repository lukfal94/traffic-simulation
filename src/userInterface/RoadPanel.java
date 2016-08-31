/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import infrastructure.Road;
import managers.SimulationManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author lukefallon
 */
public class RoadPanel extends JPanel {
    
    // Components for road statistics
    private Road road;
    private SimulationManager simManager;
    
    private JLabel roadIDLabel;
    private JLabel directionLabel;
    private JLabel vPerHourLabel;
    
    private GridBagLayout layout;
    private GridBagConstraints c;
    
    public RoadPanel(Road road)
    {
        this.road = road;
        initComponents();
    }
    
    private void initComponents()
    {
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(300, 400));
        
        // Road Information
        roadIDLabel = new JLabel("Road ID:\t" + road.getID());
        directionLabel = new JLabel("Direction:\t" + road.getDirection().toString());
        vPerHourLabel = new JLabel("Vehicles/hr:\t" + road.getvPerHr());
        
        this.addComponent(1, 1, 8, 1, c, this, roadIDLabel);
        this.addComponent(1, 2, 8, 1, c, this, directionLabel);
        this.addComponent(1, 3, 8, 1, c, this, vPerHourLabel);
        

//        this.add(trafficPerHour);
//        this.add(numLanes);
    }
    
    private void addComponent(int x, int y, int w, int h, GridBagConstraints c, 
        Container aContainer, Component aComponent )  
    {  
        c.gridx = x;  
        c.gridy = y;  
        c.gridwidth = w;  
        c.gridheight = h;  
        layout.setConstraints( aComponent, c );  
        aContainer.add( aComponent );  
    } 
    
    public void setLabels()
    {
        this.vPerHourLabel.setText("Vehicles per Hour (hundreds):\t" + road.getvPerHr());
        this.directionLabel.setText("Direction:\t" + road.getDirection().toString());
    }

    public int getNumLanes()
    {
        return 3;
    }
    
    public Road getRoad()
    {
        return this.road;
    }
    public void setRoad(Road road) {
        this.road = road;
    }
}
