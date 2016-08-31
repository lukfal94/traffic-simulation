/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managers;

import config.Enums;
import infrastructure.Car;
import infrastructure.Road;
import java.util.ArrayList;
import config.Enums.RoadDirection;
import config.Enums.SimState;
import config.SimConstants;
import infrastructure.Intersection;
import userInterface.ControlPane;
import userInterface.RoadGUI;
import userInterface.SimulationPane;

/**
 * A class to manage statistics about the traffic simulation and instantiate
 * objects for the simulation.
 * i.e. an interface between the simulation and GUI.
 * @author lukefallon
 */
public class SimulationManager {
    private SimState state;
    
    private AutoIntersectionManager aim;
    private AutoTrafficManager[] atm;
    
    private SimulationPane simPane;
    private ControlPane controlPane;
    
    private int numLanes;
    private int numRoads = 2 * SimConstants.NUM_ROADS;              
    private Road[] roads;               // collection of roads
    private Intersection intersection;
    
    private int centerX, centerY;
        
    private ArrayList<Car> vehicles;    // all the cars currently being simulated
    
    public ArrayList<Car> getVehicles() {
        return vehicles;
    }
    
    public SimulationManager()
    { 
        this.state = Enums.SimState.RESTING;
        this.intersection = new Intersection(0, 0, 0);
        
        this.aim = new AutoIntersectionManager();
        aim.setIntersection(intersection);
        
        initRoads();
        initATMs();
    }
    
    private void initRoads()
    {
        int i, j;
        boolean bool;
        
        roads = new Road[numRoads];
        for(i = 0; i < numRoads; i++)
        {
            bool = i % 2 == 0;
            RoadDirection dir = null;
            switch(i){
                case 0: case 3:
                    dir = RoadDirection.WEST_EAST;
                    break;
                case 1: case 6:
                    dir = RoadDirection.NORTH_SOUTH;
                    break;
                case 2: case 5:
                    dir = RoadDirection.SOUTH_NORTH;
                    break;
                case 4: case 7:
                    dir = RoadDirection.EAST_WEST;
                    break;
            }
            
            roads[i] = new Road(i, bool, dir, 0, 0);  
        }
        
        for(i = 0; i < numRoads; i+=2)
        {
            roads[i].setDestinationRoads(new ArrayList<>());
            // Add all the outgoing lanes
            for(j = 1; j < numRoads; j+=2)
            {
                roads[i].getDestinationRoads().add(roads[j]);
            }
            // Remove the road parallel to this one
            roads[i].getDestinationRoads().remove(roads[(i + 7) % 8]);
        }
        
    }
    
    private void initATMs()
    {
        int i;
        atm = new AutoTrafficManager[numRoads];
        
        for(i = 0; i < numRoads; i++)
        {
            atm[i] = new AutoTrafficManager(roads[i]);
            atm[i].setAim(this.aim);
            roads[i].setAtm(atm[i]);
        }
    }
    

    public void resetSimulation()
    {
        initRoads();
        initATMs();
    }
    
    public void pauseSimulation()
    {
        for(Road r : roads)
        {
            if(r.isIsIncoming())
                r.stopTimer();
        }
    }
    
    public void restartSimulation()
    {
        for(Road r : roads)
        {
            if(r.isIsIncoming())
                r.startTimer();
        }
    }
    public void startSimulation()
    {
        this.setState(Enums.SimState.RUNNING);
        this.simPane.setTime(0);
        
        this.intersection = new Intersection(centerX, centerY, 2 * numLanes * SimConstants.LANE_WIDTH);
        this.aim.setIntersection(intersection);
        
        int[] vph = controlPane.getVPH();
        int i = 0;
        
        for(Road r : roads)
        {
            r.setRoadGUI(new RoadGUI(r, this));
            if(r.isIsIncoming())
            {
                r.startSim(numLanes, vph[i]);
                i++;
            }
            else
            {
                r.startSim(numLanes, 0);
            }
        }  
        
        //this.controlPane.startSimulation();
    }
    
    public void setCenter(int x, int y)
    {
        this.centerX = x;
        this.centerY = y;
    }
    public AutoIntersectionManager getAim() {
        return aim;
    }

    public void setAim(AutoIntersectionManager aim) {
        this.aim = aim;
    }

    public AutoTrafficManager[] getAtm() {
        return atm;
    }

    public void setAtm(AutoTrafficManager[] atm) {
        this.atm = atm;
    }

    public int getNumRoads() {
        return numRoads;
    }

    public void setNumRoads(int numRoads) {
        this.numRoads = numRoads;
    }

    public Road[] getRoads() {
        return roads;
    }

    public void setRoads(Road[] roads) {
        this.roads = roads;
    }

    public SimState getState() {
        return state;
    }

    public void setState(SimState state) {
        this.state = state;
    }

    public SimulationPane getSimPane() {
        return simPane;
    }

    public void setSimPane(SimulationPane simPane) {
        this.simPane = simPane;
    }

    public ControlPane getControlPane() {
        return controlPane;
    }

    public void setControlPane(ControlPane controlPane) {
        this.controlPane = controlPane;
    }

    public int getNumLanes() {
        return numLanes;
    }

    public void setNumLanes(int numLanes) {
        this.numLanes = numLanes;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }
}
