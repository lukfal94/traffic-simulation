/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infrastructure;

import managers.CarSpawner;
import config.Enums.RoadDirection;
import config.SimConstants;
import java.util.ArrayList;
import java.util.Timer;
import managers.AutoTrafficManager;
import userInterface.RoadGUI;

/**
 *
 * @author lukefallon
 */
public class Road{
    private int ID;
    
    private int numLanes;
    private Lane[] lanes;
    private int laneWidth = SimConstants.LANE_WIDTH;
    private int vPerHr;             // Vehicles per hour on this road
    private CarSpawner carSpawner;
    private Timer timer;
    
    private ArrayList<Road> destinationRoads;
    private RoadDirection direction;
    private AutoTrafficManager atm;
    
    private int x;  // The road's left-most/rear-most point
    private int y;
    
    private RoadGUI roadGUI;
    
    private boolean isIncoming;     // Does this road lead into intersection?
     
    public Road(int id, boolean bool, RoadDirection dir, int x, int y)
    {
        setID(id);
        setIsIncoming(bool);
        
        this.direction = dir;
        
        this.x = x;
        this.y = y;
    }

    public void initLanes(int numLanes)
    {
        int i;
        lanes = new Lane[numLanes];
        System.out.println(this.toString() + ": (" + this.x + ", " + this.y + ")");
        
        for(i = 0; i < numLanes; i++)
        {
            lanes[i] = new Lane(this.ID + i);

            switch(this.direction){
                case NORTH_SOUTH:
                    lanes[i].setOrigin(this.x - ((i + 1) * laneWidth) + (laneWidth/2), this.y);
                    lanes[i].setTerminus(this.x - ((i + 1) * laneWidth) + (laneWidth/2), this.y + SimConstants.ROAD_LENGTH);
                    break;
                case EAST_WEST:
                    lanes[i].setOrigin(this.x, this.y - ((i + 1) * laneWidth) + (laneWidth/2));
                    lanes[i].setTerminus(this.x - SimConstants.ROAD_LENGTH, this.y - ((i + 1) * laneWidth) + (laneWidth/2));
                    break;
                case SOUTH_NORTH:
                    lanes[i].setOrigin(this.x + ((i + 1) * laneWidth) - (laneWidth/2), this.y);
                    lanes[i].setTerminus(this.x + ((i + 1) * laneWidth) - (laneWidth/2), this.y - SimConstants.ROAD_LENGTH);
                    break;
                case WEST_EAST:
                    lanes[i].setOrigin(this.x, this.y + ((i + 1) * laneWidth) - (laneWidth/2));
                    lanes[i].setTerminus(this.x + SimConstants.ROAD_LENGTH, this.y + ((i + 1) * laneWidth) - (laneWidth/2));
                    break;
                    
                }
//            System.out.println(lanes[i].toString() + " -> Origin (" + lanes[i].getOriginX() +", " + lanes[i].getOriginY() + 
//                    "), Terminus (" + lanes[i].getTerminusX() + ", " + lanes[i].getTerminusY() + ")");
            
        }   
    }
    
    public void startTimer()
    {
        if(this.isIncoming)
        {
            timer = new Timer();
            carSpawner = new CarSpawner(this);
            carSpawner.setDestinationRoads(this.destinationRoads);
            if(this.vPerHr > 0)
            {
                timer.scheduleAtFixedRate(carSpawner, 0, (int)(this.vPerHr*10));
            }
        }
    }
    
    public void stopTimer()
    {
        timer.cancel();
    }
    
    public void startSim(int numLanes, int vph)
    {
        this.numLanes = numLanes;
        this.vPerHr = vph;
        this.initLanes(numLanes);
        if(this.isIncoming)
        {
            timer = new Timer();
            carSpawner = new CarSpawner(this);
            carSpawner.setDestinationRoads(this.destinationRoads);
            if(vph > 0)
            {
                timer.scheduleAtFixedRate(carSpawner, 0, (int)(vph*10));
            }
        }
    }
    
    public RoadDirection getDirection() {
        return direction;
    }

    public void setDirection(RoadDirection direction) {
        this.direction = direction;
    }
    
    public int getID() {
        return ID;
    }

    private void setID(int ID) {
        this.ID = ID;
    }

    public Lane[] getLanes() {
        return lanes;
    }

    private void setLanes(Lane[] lanes) {
        this.lanes = lanes;
    }

    public int getvPerHr() {
        return vPerHr;
    }

    private void setvPerHr(int vPerHr) {
        this.vPerHr = vPerHr;
    }

    public CarSpawner getCarSpawner() {
        return carSpawner;
    }

    private void setCarSpawner(CarSpawner carSpawner) {
        this.carSpawner = carSpawner;
    }

    public boolean isIsIncoming() {
        return isIncoming;
    }

    private void setIsIncoming(boolean isIncoming) {
        this.isIncoming = isIncoming;
    }

    public int getNumLanes() {
        return numLanes;
    }

    private void setNumLanes(int numLanes) {
        this.numLanes = numLanes;
    }

    public void setOrigin(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public RoadGUI getRoadGUI() {
        return roadGUI;
    }

    public void setRoadGUI(RoadGUI roadGUI) {
        this.roadGUI = roadGUI;
    }

    public AutoTrafficManager getAtm() {
        return atm;
    }

    public void setAtm(AutoTrafficManager atm) {
        this.atm = atm;
    }

    public ArrayList<Road> getDestinationRoads() {
        return destinationRoads;
    }

    public void setDestinationRoads(ArrayList<Road> destinationRoads) {
        this.destinationRoads = destinationRoads;
    }

    public int getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(int laneWidth) {
        this.laneWidth = laneWidth;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
