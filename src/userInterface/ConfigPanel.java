/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import config.SimConstants;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Contains GUI Components to customize 
 * @author lukefallon
 */
public class ConfigPanel extends JPanel{
    private JSlider numLanesSlider;
    private JLabel numLanesSliderLabel;
    
    private JLabel[] vphSliderLabels;
    private JSlider[] vphSliders;
    
    private GridBagLayout layout;
    private GridBagConstraints c;
    
    public ConfigPanel()
    {
        initComponents();
    }
    
    private void initComponents()
    {
        int i;
        
        // Set up GridBadLayout
        layout = new GridBagLayout();
        c = new GridBagConstraints();
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(300, 400));
        // Initialize Lane Slider & Label
        numLanesSliderLabel = new JLabel("Number of Lanes");
        
        numLanesSlider = new JSlider(1, 4);
        numLanesSlider.setMajorTickSpacing(1);
        numLanesSlider.setLabelTable(numLanesSlider.createStandardLabels(1));
        numLanesSlider.setSnapToTicks(true);
        numLanesSlider.setPaintLabels(true);
        numLanesSlider.setPaintTicks(true);
        
        this.addComponent(1, 1, 8, 2, c, this, numLanesSliderLabel);
        this.addComponent(1, 3, 8, 4, c, this, numLanesSlider);
        
        vphSliderLabels = new JLabel[SimConstants.NUM_ROADS];
        vphSliders = new JSlider[SimConstants.NUM_ROADS];
        
        for(i = 0; i < SimConstants.NUM_ROADS; i++)
        {
            // Slider/Slider Label Setup
            vphSliderLabels[i] = new JLabel("VPH (Road " + i + ")");
            

            vphSliders[i] = new JSlider(0, 1000);

            vphSliders[i].setMajorTickSpacing(100);
            vphSliders[i].setMinorTickSpacing(50);
            vphSliders[i].setLabelTable(vphSliders[i].createStandardLabels(250));
            vphSliders[i].setPaintLabels(true);
            vphSliders[i].setPaintTicks(true);
            
            this.addComponent(1, (i * 6) + 8, 8, 2, c, this, vphSliderLabels[i]);
            this.addComponent(1, (i * 6) + 10, 8, 4, c, this, vphSliders[i]);
        }
    }
    
    public int getVPH(int i)
    {
        return vphSliders[i].getValue();
    }
    public int getNumLanes()
    {
        return numLanesSlider.getValue();
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
}
