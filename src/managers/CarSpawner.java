/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managers;

import infrastructure.Car;
import infrastructure.Road;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

/**
 *
 * @author lukefallons
 */
public class CarSpawner extends TimerTask{
    private Road road;
    private ArrayList<Road> destinationRoads;
    
    public CarSpawner(Road road)
    {
        this.road = road;
    }
    
    // Methods
    @Override
    public void run()
    {
        double rotation = 0;
        double vel_x, vel_y;
        int lane, destIndex;
        Random rand = new Random();
        Road destRoad;
        
        // Randomize car
        vel_x = 3;
        vel_y = 3;
        lane = rand.nextInt(road.getNumLanes());
        
        destIndex = rand.nextInt(3);
        destRoad = destinationRoads.get(destIndex);
        
        //System.out.println(">> " + this.road.toString() + " --> " + destRoad.toString());
        
        switch(road.getDirection()){
            case NORTH_SOUTH:
                rotation = 90;
                vel_x = 0; 
                vel_y *= 1;
                break;
            case EAST_WEST:
                rotation = 0;
                vel_y = 0;
                vel_x *= -1;
                break;
            case SOUTH_NORTH:
                rotation = 90;
                vel_x = 0;
                vel_y *= -1;
                break;
            case WEST_EAST:
                rotation = 0;
                vel_y = 0;
                vel_x *= 1;
                break;
        }
        
        road.getLanes()[lane].addCar( 
                new Car(
                    (double)road.getLanes()[lane].getOriginX(), 
                    (double)road.getLanes()[lane].getOriginY(), 
                    vel_x, vel_y, rotation, this.road, destRoad));
    }

    public ArrayList<Road> getDestinationRoads() {
        return destinationRoads;
    }

    public void setDestinationRoads(ArrayList<Road> destinationRoads) {
        this.destinationRoads = destinationRoads;
    }
}
