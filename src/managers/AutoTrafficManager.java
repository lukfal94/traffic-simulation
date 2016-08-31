/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managers;

import infrastructure.Car;
import comms.Maneuver;
import comms.Request;
import comms.Reservation;
import config.SimConstants;
import config.Velocity;
import infrastructure.Lane;
import infrastructure.Road;
import java.awt.Color;

/**
 *
 * @author lukefallon
 */
public class AutoTrafficManager {
    private Road road;              // the road this ATM controls
    private AutoIntersectionManager aim;
    
    public AutoTrafficManager(Road road)
    {
        this.road = road;
    } 
    
    public void updateManeuvers(Car car)
    {
        Reservation newReservation;
        Maneuver laneChange;
        
        if(car.isHasReservation())
        {
            if(car.isCorrectLane() && car.getReservation() != null)
            {
                car.getManeuverQueue().add(car.getReservation().getManeuver());
                
                car.setReservation(null);
                car.gui.setColor(Color.CYAN);
            }
            else if(!car.isCorrectLane())
            {

                laneChange = this.createLaneChangeManeuver(car, car.getCurr_lane(), car.getReservation().getStart());
                
                car.getManeuverQueue().add(laneChange);
                car.setCorrectLane(true);
                car.gui.setColor(Color.YELLOW);
            }   

        }
        else
        {
            newReservation = aim.processRequest(
                    new Request(car.getCurr_road(), 
                            car.getCurr_lane(), car.getDest_road(), 
                            new Velocity(car.getVel_x(), car.getVel_y())));            

            car.setReservation(newReservation); 

            car.gui.setColor(Color.BLUE);
            
            car.setCorrectLane(car.getCurr_lane() == newReservation.getStart());
            car.setHasReservation(true);
        }
    }
    
    public Maneuver createLaneChangeManeuver(Car car, Lane start, Lane end)
    {
        int numLanes, numSteps;
        double deltaX, deltaY;

        // If the change = 0, we're changing lanes perpendicular to that direction
        deltaX = end.getOriginX() - start.getOriginX();
        deltaY = end.getOriginY() - start.getOriginY();
        
        numLanes = Math.abs((deltaX == 0) ?
                ((int)(deltaY / SimConstants.LANE_WIDTH)) : 
                ((int)(deltaX / SimConstants.LANE_WIDTH)));
        
        numSteps = 20 * numLanes;
        
        // Remove the car from this lane and put it in the other lane
        // TODO in thread-safe application
//        start.getCars().remove(car);
//        end.getCars().add(car);
        
        return new Maneuver(deltaX, deltaY, numSteps, new Velocity(car.getVel_x(), car.getVel_y()));
    }

    public AutoIntersectionManager getAim() {
        return aim;
    }

    public void setAim(AutoIntersectionManager aim) {
        this.aim = aim;
    }
}
