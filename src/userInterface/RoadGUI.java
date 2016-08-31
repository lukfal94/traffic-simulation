/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import config.SimConstants;
import infrastructure.Road;
import managers.SimulationManager;
import java.awt.Rectangle;

/**
 *
 * @author lukefallon
 */
public class RoadGUI extends Rectangle{
    private int roadWidth;
    
    // Sets the origin (point used to set up lanes and spawn cars) and bounds
    // (rectangle used to draw the GUI component
    public RoadGUI(Road r, SimulationManager sm)
    {
        
        roadWidth = sm.getNumLanes() * SimConstants.LANE_WIDTH;
        if(r.isIsIncoming())
        {
            
            // The "origin" of a road is the (x, y) of the left of the left-most lane
            switch(r.getDirection()){
                case NORTH_SOUTH: // Done
                    r.setOrigin(sm.getCenterX(), sm.getCenterY() - roadWidth - SimConstants.ROAD_LENGTH);
                    this.setBounds(sm.getCenterX() - roadWidth, 
                            sm.getCenterY() - roadWidth - SimConstants.ROAD_LENGTH, 
                            roadWidth, SimConstants.ROAD_LENGTH);
                    break;
                case EAST_WEST: // Done
                    r.setOrigin(sm.getCenterX() + roadWidth + SimConstants.ROAD_LENGTH,
                            sm.getCenterY());
                    this.setBounds(sm.getCenterX() + roadWidth, 
                            sm.getCenterY() - roadWidth, 
                            SimConstants.ROAD_LENGTH, roadWidth);
                    break;
                case SOUTH_NORTH: // Done
                    r.setOrigin(sm.getCenterX(), sm.getCenterY() + roadWidth + SimConstants.ROAD_LENGTH);
                    this.setBounds(sm.getCenterX(), sm.getCenterY() + roadWidth,
                            roadWidth, SimConstants.ROAD_LENGTH);
                    break;
                case WEST_EAST:
                    r.setOrigin(sm.getCenterX() - roadWidth - SimConstants.ROAD_LENGTH, sm.getCenterY());
                    this.setBounds(sm.getCenterX() - roadWidth - SimConstants.ROAD_LENGTH, 
                            sm.getCenterY(), SimConstants.ROAD_LENGTH, roadWidth);
                    break;
            }
        }
        else
        {
           switch(r.getDirection()){
                case NORTH_SOUTH:
                    r.setOrigin(sm.getCenterX(), sm.getCenterY() + roadWidth);
                    this.setBounds(sm.getCenterX() - roadWidth, sm.getCenterY() + roadWidth, 
                        roadWidth, SimConstants.ROAD_LENGTH);
                    break;
                case EAST_WEST:
                    r.setOrigin(sm.getCenterX() - roadWidth, sm.getCenterY());
                    this.setBounds(sm.getCenterX() - roadWidth - SimConstants.ROAD_LENGTH,
                        sm.getCenterY() - roadWidth, 
                        SimConstants.ROAD_LENGTH, roadWidth);
                    break;
                case SOUTH_NORTH:
                    r.setOrigin(sm.getCenterX(), sm.getCenterY() - roadWidth);
                    this.setBounds(sm.getCenterX(), sm.getCenterY() - roadWidth - SimConstants.ROAD_LENGTH, 
                        roadWidth, SimConstants.ROAD_LENGTH);
                    break;
                case WEST_EAST:
                    r.setOrigin(sm.getCenterX() + roadWidth, sm.getCenterY());
                        this.setBounds(sm.getCenterX() + roadWidth, sm.getCenterY(), 
                            SimConstants.ROAD_LENGTH, roadWidth);
                    break;
            } 
        }
    }

}
