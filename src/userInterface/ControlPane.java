/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import managers.SimulationManager;
import config.Enums;
import config.SimConstants;
import infrastructure.Road;
/**
 *
 * @author lukefallon
 */
public class ControlPane extends JPanel{
    private JTabbedPane tabs;
    
    private JButton startSimulation;
    private JButton pauseSimulation;
    private JButton resetSimulation;
    
    private ConfigPanel configPane;
    private RoadPanel roadPane;
    private JComponent aboutPage;
    private SimulationManager simManager;
    
    
    Border cPaneBorder;
    
    
    private static final int C_PANE_X = 350;
    private static final int C_PANE_Y = 550;
    
    public ControlPane(SimulationManager simManager)
    {   
        initComponents(simManager);
    }
    
    private void initComponents(SimulationManager simManager)
    {
//        cPaneBorder = BorderFactory.createLineBorder(Color.BLACK);
//        setBorder(cPaneBorder);
//        
        this.setSimManager(simManager);
        
        startSimulation = new JButton("Start");
        startSimulation.addActionListener(new SimulationAction());
        pauseSimulation = new JButton("Pause");
        pauseSimulation.addActionListener(new SimulationAction());
        resetSimulation = new JButton("Reset");
        resetSimulation.addActionListener(new SimulationAction());
        
        tabs = new JTabbedPane();
        int i;
        
        configPane = new ConfigPanel();
        tabs.add("Settings", configPane);
        
//        for(i = 0 ; i < simManager.getNumRoads(); i+=2)
//        {
//            roadPane = new RoadPanel(simManager.getRoads()[i]);
//
//            tabs.addTab("Road " + i, roadPane);
//        }
        
        this.setPreferredSize(new Dimension(C_PANE_X, C_PANE_Y));
        this.add(tabs);
        this.add(startSimulation);
        this.add(pauseSimulation);
        this.add(resetSimulation);
    }

    private class SimulationAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equals("Start") && 
                    simManager.getState() != Enums.SimState.RUNNING &&
                    simManager.getState() != Enums.SimState.PAUSED)
            {
                simManager.setNumLanes(configPane.getNumLanes()); 
                simManager.startSimulation();
            }
            else if(e.getActionCommand().equals("Pause") && 
                    simManager.getState() == Enums.SimState.RUNNING)
            {
                simManager.setState(Enums.SimState.PAUSED);
                simManager.pauseSimulation();
            }
            else if(e.getActionCommand().equals("Start") && 
                    simManager.getState() == Enums.SimState.PAUSED)
            {
                simManager.setState(Enums.SimState.RUNNING);
                simManager.restartSimulation();
            }
            else if(e.getActionCommand().equals("Reset"))
            {
                simManager.setState(Enums.SimState.RESET);
                simManager.resetSimulation();
            }
        }
        
    }
    
//    public void startSimulation()
//    {
//        int i;
//        for(i = 1; i < SimConstants.NUM_ROADS / 2; i++)
//        {
//            RoadPanel temp = (RoadPanel)tabs.getComponentAt(i);
//            temp.setLabels();
//        }
//    }
    public int[] getVPH()
    {
        int[] vph = new int[SimConstants.NUM_ROADS];
        int i;
        for(i = 0; i < vph.length; i++)
        {
            vph[i] = configPane.getVPH(i);
        }
        return vph;
    }
    
    public int getNumLanes()
    {
        ConfigPanel temp = (ConfigPanel)tabs.getComponentAt(0);
        return temp.getNumLanes();
    }

    public void setSimManager(SimulationManager simManager) {
        this.simManager = simManager;
    }
    
}
