/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comms;

import config.Enums.RoadDirection;
import config.Enums.ManeuverType;
import config.Velocity;
import infrastructure.Lane;
import infrastructure.Road;

/**
 *
 * @author lukefallon
 */
public class Request {
    
    private Road origin;
    private Lane currLane;      // Used when testing
    private Velocity velocity;
    private Road destination;
    private ManeuverType type;
    
    public Request(Road origin, Road destination)
    {
        this.origin = origin;
        this.destination = destination;
        this.type = calcManeuverType();
    }
    public Request(Road origin, Lane currLane, Road destination, Velocity currVelocity)
    {
        this.origin = origin;
        this.currLane = currLane;
        this.destination = destination;
        this.velocity = currVelocity;
        this.type = calcManeuverType();
    }
    
    private ManeuverType calcManeuverType()
    {
        ManeuverType turnType;
        turnType = null;
        RoadDirection origDir = origin.getDirection(), 
                destDir = destination.getDirection();
        
        // Set the maneuver type
        if(origDir == RoadDirection.NORTH_SOUTH)
        {
            if(destDir == RoadDirection.WEST_EAST)
            {
                turnType = ManeuverType.LEFT;
            }
            else if(destDir == RoadDirection.EAST_WEST)
            {
                turnType = ManeuverType.RIGHT;
            }
            else
            {
                turnType = ManeuverType.STRAIGHT;
            }
        }
        else if(origDir == RoadDirection.WEST_EAST)
        {
            if(destDir == RoadDirection.SOUTH_NORTH)
            {
                turnType = ManeuverType.LEFT;
            }
            else if(destDir == RoadDirection.NORTH_SOUTH)
            {
                turnType = ManeuverType.RIGHT;
            }
            else
                turnType = ManeuverType.STRAIGHT;
        }
        else if(origDir == RoadDirection.EAST_WEST)
        {
            if(destDir == RoadDirection.NORTH_SOUTH)
            {
                turnType = ManeuverType.LEFT;
            }
            else if(destDir == RoadDirection.SOUTH_NORTH)
            {
                turnType = ManeuverType.RIGHT;
            }
            else
                turnType = ManeuverType.STRAIGHT;
        }
        else if(origDir == RoadDirection.SOUTH_NORTH)
        {
            if(destDir == RoadDirection.EAST_WEST)
            {
                turnType = ManeuverType.LEFT;
            }
            else if(destDir == RoadDirection.WEST_EAST)
            {
                turnType = ManeuverType.RIGHT;
            }
            else
                turnType = ManeuverType.STRAIGHT;
        }
        
        return turnType;
    }

    public Road getOrigin() {
        return origin;
    }

    public void setOrigin(Road origin) {
        this.origin = origin;
    }

    public Road getDestination() {
        return destination;
    }

    public void setDestination(Road destination) {
        this.destination = destination;
    }

    public ManeuverType getType() {
        return type;
    }

    public void setType(ManeuverType type) {
        this.type = type;
    } 

    public Lane getCurrLane() {
        return currLane;
    }

    public void setCurrLane(Lane currLane) {
        this.currLane = currLane;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }
}
