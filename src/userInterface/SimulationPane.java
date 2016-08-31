/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import infrastructure.Car;
import infrastructure.Road;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import managers.SimulationManager;
import config.Enums;
import config.Enums.SimState;
import config.SimConstants;
import infrastructure.Lane;
import java.awt.BasicStroke;
import javax.swing.JLabel;
import managers.AutoTrafficManager;
/**
 *
 * @author lukefallon
 */
public class SimulationPane extends JPanel implements ActionListener
{
    private Border simBorder;
    private SimulationManager simManager;
    private static final int S_PANE_X = 550;
    private static final int S_PANE_Y = 550;
    private JLabel timeLabel;
    private double time;
    
    Timer timer = new Timer(SimConstants.REFRESH_RATE, this);
    
    SimulationPane(SimulationManager simManager)
    {
        initComponents(simManager);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        double posX, posY;
        
        super.paintComponent(g2);
        
        simManager.setCenter(this.getWidth()/2, this.getHeight()/2);
                    
        if(simManager.getState() == SimState.RUNNING)
        {
            int i;
            
            // Draw the roads
            for(Road r : simManager.getRoads())
            {
                g2.setColor(Color.DARK_GRAY);
                g2.fill(r.getRoadGUI());
            }
            
            // Draw Road Lines
            //
            
            // Draw Solid Yellow Lines
            BasicStroke solid = new BasicStroke();
            g2.setStroke(solid);
            g2.setColor(Color.yellow);

            // NORTH_SOUTH Road
            g2.drawLine(((simManager.getIntersection().getNorthEast().x + simManager.getIntersection().getNorthWest().x) / 2) - 1,
                simManager.getIntersection().getNorthEast().y, 
                ((simManager.getIntersection().getNorthEast().x + simManager.getIntersection().getNorthWest().x) / 2) - 1, 
                simManager.getIntersection().getNorthEast().y - SimConstants.ROAD_LENGTH);
            g2.drawLine(((simManager.getIntersection().getNorthEast().x + simManager.getIntersection().getNorthWest().x) / 2) + 1,
                simManager.getIntersection().getNorthEast().y, 
                ((simManager.getIntersection().getNorthEast().x + simManager.getIntersection().getNorthWest().x) / 2) + 1, 
                simManager.getIntersection().getNorthEast().y - SimConstants.ROAD_LENGTH);
            
            // EAST_WEST Road
            g2.drawLine(simManager.getIntersection().getNorthEast().x, 
                    ((simManager.getIntersection().getNorthEast().y + simManager.getIntersection().getSouthEast().y) / 2) - 1, 
                    simManager.getIntersection().getNorthEast().x + SimConstants.ROAD_LENGTH, 
                    ((simManager.getIntersection().getNorthEast().y + simManager.getIntersection().getSouthEast().y) / 2) - 1);
            g2.drawLine(simManager.getIntersection().getNorthEast().x, 
                    ((simManager.getIntersection().getNorthEast().y + simManager.getIntersection().getSouthEast().y) / 2) + 1, 
                    simManager.getIntersection().getNorthEast().x + SimConstants.ROAD_LENGTH, 
                    ((simManager.getIntersection().getNorthEast().y + simManager.getIntersection().getSouthEast().y) / 2) + 1);
            
            // SOUTH_NORTH Road
            g2.drawLine(((simManager.getIntersection().getSouthEast().x + simManager.getIntersection().getSouthWest().x) / 2) - 1,
                simManager.getIntersection().getSouthEast().y, 
                ((simManager.getIntersection().getSouthEast().x + simManager.getIntersection().getSouthWest().x) / 2) - 1, 
                simManager.getIntersection().getSouthEast().y + SimConstants.ROAD_LENGTH);
            g2.drawLine(((simManager.getIntersection().getSouthEast().x + simManager.getIntersection().getSouthWest().x) / 2) + 1,
                simManager.getIntersection().getSouthEast().y, 
                ((simManager.getIntersection().getSouthEast().x + simManager.getIntersection().getSouthWest().x) / 2) + 1, 
                simManager.getIntersection().getSouthEast().y + SimConstants.ROAD_LENGTH);
            
            // WEST_EAST Road
            g2.drawLine(simManager.getIntersection().getNorthWest().x, 
                    ((simManager.getIntersection().getNorthWest().y + simManager.getIntersection().getSouthWest().y) / 2) - 1, 
                    simManager.getIntersection().getNorthWest().x - SimConstants.ROAD_LENGTH, 
                    ((simManager.getIntersection().getNorthWest().y + simManager.getIntersection().getSouthWest().y) / 2) - 1);
            g2.drawLine(simManager.getIntersection().getNorthWest().x, 
                    ((simManager.getIntersection().getNorthWest().y + simManager.getIntersection().getSouthWest().y) / 2) + 1, 
                    simManager.getIntersection().getNorthWest().x - SimConstants.ROAD_LENGTH, 
                    ((simManager.getIntersection().getNorthWest().y + simManager.getIntersection().getSouthWest().y) / 2) + 1);
            
            // Draw dashed White Lines
            float dash1[] = {4.0f};
            BasicStroke dashed =
                new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        4.0f, dash1, 0.0f);
            
            g2.setStroke(dashed);
            g2.setColor(Color.WHITE);
            
            // NORTH_SOUTH ROAD
            for(i = 1; i < simManager.getNumLanes(); i++)
            {
                g2.drawLine(simManager.getIntersection().getNorthWest().x + i * SimConstants.LANE_WIDTH, 
                        simManager.getIntersection().getNorthWest().y,
                        simManager.getIntersection().getNorthWest().x + i * SimConstants.LANE_WIDTH,
                        simManager.getIntersection().getNorthWest().y - SimConstants.ROAD_LENGTH);
            }
            for(i = 1; i < simManager.getNumLanes(); i++)
            {
                g2.drawLine((simManager.getIntersection().getNorthWest().x + simManager.getIntersection().getNorthEast().x) / 2 + i * SimConstants.LANE_WIDTH, 
                        simManager.getIntersection().getNorthWest().y,
                        (simManager.getIntersection().getNorthWest().x + simManager.getIntersection().getNorthEast().x) / 2 + i * SimConstants.LANE_WIDTH,
                        simManager.getIntersection().getNorthWest().y - SimConstants.ROAD_LENGTH);
            }
            
            // EAST_WEST ROAD
            for(i = 1; i < simManager.getNumLanes(); i++)
            {
                g2.drawLine(simManager.getIntersection().getNorthEast().x, 
                        simManager.getIntersection().getNorthEast().y + i * SimConstants.LANE_WIDTH,
                        simManager.getIntersection().getNorthEast().x + SimConstants.ROAD_LENGTH,
                        simManager.getIntersection().getNorthEast().y + i * SimConstants.LANE_WIDTH);
            }
            for(i = 1; i < simManager.getNumLanes(); i++)
            {
                g2.drawLine(simManager.getIntersection().getNorthEast().x,
                        (simManager.getIntersection().getNorthEast().y + simManager.getIntersection().getSouthEast().y) / 2 + i * SimConstants.LANE_WIDTH,
                        simManager.getIntersection().getNorthEast().x + SimConstants.ROAD_LENGTH,
                        (simManager.getIntersection().getNorthEast().y + simManager.getIntersection().getSouthEast().y) / 2 + i * SimConstants.LANE_WIDTH);
            }
            
            // SOUTH_NORTH Road
            for(i = 1; i < simManager.getNumLanes(); i++)
            {
                g2.drawLine(simManager.getIntersection().getSouthWest().x + i * SimConstants.LANE_WIDTH, 
                        simManager.getIntersection().getSouthWest().y,
                        simManager.getIntersection().getSouthWest().x + i * SimConstants.LANE_WIDTH,
                        simManager.getIntersection().getSouthWest().y + SimConstants.ROAD_LENGTH);
            }
            for(i = 1; i < simManager.getNumLanes(); i++)
            {
                g2.drawLine((simManager.getIntersection().getSouthWest().x + simManager.getIntersection().getSouthEast().x) / 2 + i * SimConstants.LANE_WIDTH, 
                        simManager.getIntersection().getSouthWest().y,
                        (simManager.getIntersection().getSouthWest().x + simManager.getIntersection().getSouthEast().x) / 2 + i * SimConstants.LANE_WIDTH,
                        simManager.getIntersection().getSouthWest().y + SimConstants.ROAD_LENGTH);
            }
            
            // WEST_EAST Road
            for(i = 1; i < simManager.getNumLanes(); i++)
            {
                g2.drawLine(simManager.getIntersection().getNorthWest().x, 
                        simManager.getIntersection().getNorthWest().y + i * SimConstants.LANE_WIDTH,
                        simManager.getIntersection().getNorthWest().x - SimConstants.ROAD_LENGTH,
                        simManager.getIntersection().getNorthWest().y + i * SimConstants.LANE_WIDTH);
            }
            for(i = 1; i < simManager.getNumLanes(); i++)
            {
                g2.drawLine(simManager.getIntersection().getNorthWest().x,
                        (simManager.getIntersection().getNorthWest().y + simManager.getIntersection().getSouthWest().y) / 2 + i * SimConstants.LANE_WIDTH,
                        simManager.getIntersection().getNorthWest().x - SimConstants.ROAD_LENGTH,
                        (simManager.getIntersection().getNorthWest().y + simManager.getIntersection().getSouthWest().y) / 2 + i * SimConstants.LANE_WIDTH);
            }
            

            // Draw the intersection
            g2.setColor(Color.DARK_GRAY);
            Rectangle intersection = 
                    new Rectangle(
                        simManager.getIntersection().getNorthWest(),
                        new Dimension(
                                simManager.getNumLanes() * SimConstants.LANE_WIDTH * 2, 
                                simManager.getNumLanes() * SimConstants.LANE_WIDTH * 2));
            g2.fill(intersection);
        }
        
        if(simManager.getState() == Enums.SimState.RUNNING)
        {
            // Draw the cars
            for(Road road : simManager.getRoads())
                for(Lane lane : road.getLanes())
                    for(Car currCar : lane.getCars())
                    {
                        g2.setColor(currCar.gui.getColor());
                        AffineTransform at = new AffineTransform();

                        posX = currCar.gui.getX();
                        posY = currCar.gui.getY();

                        at.rotate(Math.toRadians(currCar.getHeading()), currCar.getPos_x(), 
                            currCar.getPos_y());

                        Shape shape = at.createTransformedShape(currCar.gui);
                        g2.fill(shape);
                        
//                        g2.setColor(Color.CYAN);
//                        g2.drawLine((int)currCar.getPos_x(), (int)currCar.getPos_y(),
//                                (int)currCar.getPos_x(), (int)currCar.getPos_y());

                    }
            // Update the timer
//            time += .005;
//            
//            timeLabel.setText(SimConstants.timeForm.format(time));
        }
        timer.start(); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        AutoTrafficManager temp;
        if(simManager.getState() == Enums.SimState.RUNNING)
        {
            for(Road road : simManager.getRoads())
                for(Lane lane : road.getLanes())
                    for(Car currCar : lane.getCars())
                    {
                        if(currCar.getPos_x() < -50 || currCar.getPos_x() > 600
                                || currCar.getPos_y() < -50 || currCar.getPos_y() > 600)
                        {
                            lane.getCars().remove(currCar);
                            break;
                        }
                        if(road.getAtm() == null)
                            System.out.println("ATM null");
                        road.getAtm().updateManeuvers(currCar);
                        
                        currCar.updateVelocity();
                        currCar.updatePosition();
                        currCar.updateHeading();
                    }
            repaint();
        }
        else if(simManager.getState() == Enums.SimState.RESET)
        {
            repaint();
        }
    }
    
    private void initComponents(SimulationManager simManager) 
    {
        this.setSimManager(simManager);
        simBorder = BorderFactory.createLineBorder(Color.BLUE);
        //setBorder(simBorder);
        
        time = 0;
        timeLabel = new JLabel();
        
        this.add(timeLabel);
        this.setBackground(Color.decode("#008A00"));
        this.setPreferredSize(new Dimension(S_PANE_X, S_PANE_Y));
    }

    public void setSimManager(SimulationManager simManager) {
        this.simManager = simManager;
    }
    
    public void setTime(double d)
    {
        this.time = d;
    }
}
