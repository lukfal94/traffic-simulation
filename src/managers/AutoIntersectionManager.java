/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managers;

import comms.Maneuver;
import comms.Request;
import comms.Reservation;
import config.Enums.ManeuverType;
import config.Enums.RoadDirection;
import config.SimConstants;
import infrastructure.Intersection;
import infrastructure.Lane;
import infrastructure.Road;
import java.awt.Point;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author lukefallon
 */
public class AutoIntersectionManager {
    private Intersection intersection;
    
    
    private Queue<Request> requestQueue;    // Not implemented
    
    private Point turnCenter;
    
    private int numSteps;
    private int xAxisLen;
    private int yAxisLen;
    private double xVelFinal;
    private double yVelFinal;
    private double thetaInit;
    private double thetaFinal;
    private double arcLength;
    private double manTime;
    private int majorAxis;
    private int minorAxis;
    
    public AutoIntersectionManager()
    {
        requestQueue = new ConcurrentLinkedQueue<>();
    }
    

    public Reservation processRequest(Request rq) {
        Reservation newRes = new Reservation();
        
        if(rq.getType() == ManeuverType.STRAIGHT)
        {
            // Go "straight on through"
            newRes.setManeuver(new Maneuver());
            newRes.setStart(rq.getCurrLane());
        }
        else
        {
            int origLane = new Random().nextInt(rq.getOrigin().getLanes().length);
            int destLane = new Random().nextInt(rq.getDestination().getLanes().length);
            
            Lane startTemp = rq.getOrigin().getLanes()[origLane];
            Lane endTemp = rq.getDestination().getLanes()[destLane];

            // Find the turn center of the two roads
            findEllipseCenter(rq.getOrigin(), rq.getDestination()); 

            // Find the distance between the the two lanes and the turn center
            findAxes(rq.getType(), rq.getOrigin().getDirection(), 
                    startTemp, endTemp);
            
            calculateFinalVelocities(rq, rq.getVelocity().getX(), 
                    rq.getVelocity().getY(), this.xAxisLen, this.yAxisLen);
           
            this.majorAxis = (this.xAxisLen >= this.yAxisLen) ? (this.xAxisLen) : (this.yAxisLen);
            this.minorAxis = (this.xAxisLen <= this.yAxisLen) ? (this.xAxisLen) : (this.yAxisLen);
            
            this.arcLength = this.findArcLength((double)this.majorAxis, (double)this.minorAxis);
            
            this.numSteps = calcTime(arcLength, rq.getVelocity().getMax() );
            

            newRes = createTurnManeuver(rq, newRes);

            newRes.setStart(startTemp);
            newRes.setEnd(endTemp);
            
            if(newRes.getEnd() == null)
                System.out.println("End is null");
            else if(newRes.getStart() == null)
                System.out.println("start is null)");
            
            newRes.getManeuver().setStartX(newRes.getStart().getTerminusX()); 
            newRes.getManeuver().setStartY(newRes.getStart().getTerminusY());
        }
                
        return newRes;
    }
    
    private Reservation createTurnManeuver(Request rq, Reservation newRes)
    {
        // Create turn maneuver
            switch(rq.getOrigin().getDirection()){
                case WEST_EAST:
                {
                    if(rq.getType() == ManeuverType.LEFT)
                    {
                        newRes.setManeuver( 
                                new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                        rq.getCurrLane(), rq.getVelocity().getY(), -rq.getVelocity().getX(),
                                        this.thetaInit, this.thetaFinal, 
                                        numSteps));  
                    }
                    else
                    {
                        newRes.setManeuver( 
                                new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                        rq.getCurrLane(), rq.getVelocity().getY(), rq.getVelocity().getX(),
                                        this.thetaInit, this.thetaFinal, 
                                        numSteps));                    
                    }
                    break;
                }
                case SOUTH_NORTH:
                {
                    if(rq.getType() == ManeuverType.LEFT)
                    {
                        newRes.setManeuver( 
                                new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                        rq.getCurrLane(), rq.getVelocity().getY(), rq.getVelocity().getX(),
                                        this.thetaInit, this.thetaFinal, 
                                        numSteps));
                    }
                    else
                    {
                        newRes.setManeuver( 
                                new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                        rq.getCurrLane(), -rq.getVelocity().getY(), rq.getVelocity().getX(),
                                        this.thetaInit, this.thetaFinal, 
                                        numSteps));                    
                    }
                    break;
                }
                case EAST_WEST:
                {
                    if(rq.getType() == ManeuverType.LEFT)
                    {
                        newRes.setManeuver( 
                                new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                        rq.getCurrLane(), rq.getVelocity().getY(), -rq.getVelocity().getX(),
                                        this.thetaInit, this.thetaFinal, 
                                        numSteps));
                    }
                    else
                    {
                        newRes.setManeuver( 
                                new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                        rq.getCurrLane(), rq.getVelocity().getY(), rq.getVelocity().getX(),
                                        this.thetaInit, this.thetaFinal, 
                                        numSteps));                    
                    }
                    break;
                }
                case NORTH_SOUTH:
                {
                    if(rq.getType() == ManeuverType.LEFT)
                    {
                        newRes.setManeuver( 
                                new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                        rq.getCurrLane(), rq.getVelocity().getY(), rq.getVelocity().getX(),
                                        this.thetaInit, this.thetaFinal, 
                                        numSteps));
                    }
                    else
                    {
                        newRes.setManeuver( 
                                new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                        rq.getCurrLane(), -rq.getVelocity().getY(), rq.getVelocity().getX(),
                                        this.thetaInit, this.thetaFinal, 
                                        numSteps));                    
                    }
                    break;
                }
                default:
                {
                    newRes.setManeuver( 
                            new Maneuver( this.turnCenter, this.xAxisLen, this.yAxisLen, 
                                    rq.getCurrLane(), rq.getVelocity().getY(), -rq.getVelocity().getX(),
                                    this.thetaInit, this.thetaFinal, 
                                    numSteps)); 
                    break;
                }
                
            }
            return newRes;
    }
    private boolean hasNoConflict(Maneuver m)
    {
        return true;
    }
    private int calcTime(double arcLength, double velocity)
    {
        return (int)Math.ceil(arcLength/velocity);
    }
    
    private double findArcLength(double a, double b)
    {
        double c = Math.PI * ( (3 * (a+b)) - ( Math.sqrt( (3*a+b) * (a + (3*b) ))));
        return c/4;
    }
    
    private void calculateFinalVelocities(Request rq, double velX, double velY, int x, int y)
    {
        switch(rq.getOrigin().getDirection())
        {
            case WEST_EAST:
            {
                if(rq.getType() == ManeuverType.LEFT)
                {
                    this.xVelFinal = 0;
                    this.yVelFinal = -(y * (velX / x));
                }
                else
                {
                    this.xVelFinal = 0;
                    this.yVelFinal = y * (velX / x);
                }
                break;
            }
            case EAST_WEST: 
            {
                if(rq.getType() == ManeuverType.LEFT)
                {
                    this.xVelFinal = 0;
                    this.yVelFinal = y * (velX / x);
                }
                else
                {
                    this.xVelFinal = 0;
                    this.yVelFinal = -(y * (velX / x));
                }
                break;
            }
            case SOUTH_NORTH:
            {
                if(rq.getType() == ManeuverType.LEFT)
                {
                    this.xVelFinal = x * (velY / y);
                    this.yVelFinal = y * (velX / x);
                }
                else
                {
                    this.xVelFinal = x * (velY / y);
                    this.yVelFinal = y * (velX / x);
                }
                break;
            }
            case NORTH_SOUTH:
            {
                if(rq.getType() == ManeuverType.LEFT)
                {
                    this.xVelFinal = x * (velY / y);
                    this.yVelFinal = y * (velX / x);
                }
                else
                {
                    this.xVelFinal = x * (velY / y);
                    this.yVelFinal = y * (velX / x);
                }
                break;
            }    
        }
        

    }
    
    private void findAxes(ManeuverType type, RoadDirection dir, Lane start, Lane end)
    {
        switch(dir)
        {
            case WEST_EAST:
            {
                if(type == ManeuverType.LEFT)
                {
                   this.xAxisLen = end.getOriginX() - this.turnCenter.x;
                   this.yAxisLen = start.getOriginY() - this.turnCenter.y; 
                   this.thetaInit = Math.PI / 2;
                   this.thetaFinal = 0;
                }
                else
                {
                    this.xAxisLen = end.getOriginX() - this.turnCenter.x;
                    this.yAxisLen = this.turnCenter.y - start.getOriginY();
                    this.thetaInit = 3 * Math.PI / 2;
                    this.thetaFinal = 2*Math.PI;
                }
                break;
            }
            case EAST_WEST: 
            {
                if(type == ManeuverType.LEFT)
                {
                   this.xAxisLen = this.turnCenter.x - end.getOriginX();
                   this.yAxisLen = this.turnCenter.y - start.getTerminusY();
                   this.thetaInit =  3 * Math.PI / 2;
                   this.thetaFinal = Math.PI;
                }
                else
                {
                    this.xAxisLen = end.getOriginX() - this.turnCenter.x;
                    this.yAxisLen = start.getTerminusY() - this.turnCenter.y;
                    this.thetaInit =  Math.PI/2;
                    this.thetaFinal = Math.PI;
                }
                break;
            }
            case SOUTH_NORTH:
            {
                if(type == ManeuverType.LEFT)
                {
                    this.xAxisLen = start.getTerminusX() - this.turnCenter.x;
                    this.yAxisLen = this.turnCenter.y - end.getOriginY();
                    this.thetaInit = 2*Math.PI;
                    this.thetaFinal = 3 * Math.PI / 2;
                }
                else
                {
                    this.xAxisLen = this.turnCenter.x - start.getTerminusX();
                    this.yAxisLen = this.turnCenter.y - end.getOriginY();
                    this.thetaInit = Math.PI;
                    this.thetaFinal = 3 * Math.PI / 2;
                }
                break;
            }
            case NORTH_SOUTH:
            {
                if(type == ManeuverType.LEFT)
                {
                    this.xAxisLen = this.turnCenter.x - start.getTerminusX();
                    this.yAxisLen = end.getOriginY() - this.turnCenter.y;
                    this.thetaInit = Math.PI;
                    this.thetaFinal = Math.PI / 2;
                }
                else
                {
                    this.xAxisLen = start.getTerminusX() - this.turnCenter.x;
                    this.yAxisLen = end.getOriginY() - this.turnCenter.y;
                    this.thetaInit = 0;
                    this.thetaFinal = Math.PI / 2;
                }
                break;
            }    
        }
        
        if(this.xAxisLen < 0)
            this.xAxisLen *= -1;
        if(this.yAxisLen < 0)
            this.yAxisLen *= -1;
    }
    
    private void findEllipseCenter(Road start, Road end)
    {
        RoadDirection sDir = start.getDirection();
        RoadDirection eDir = end.getDirection();
        
        switch(sDir)
        {
            case NORTH_SOUTH:
            {
                switch(eDir){
                    case EAST_WEST:
                        turnCenter = intersection.getNorthWest();
                        break;
                    case WEST_EAST:
                        turnCenter = intersection.getNorthEast();
                        break;
                }
                break;
            }
            case WEST_EAST:
            {
                switch(eDir){
                    case SOUTH_NORTH:
                        turnCenter = intersection.getNorthWest();
                        break;
                    case NORTH_SOUTH:
                        turnCenter = intersection.getSouthWest();
                        break;
                }
                break;
            }
            case SOUTH_NORTH:
            {
                switch(eDir){
                    case EAST_WEST:
                        turnCenter = intersection.getSouthWest();
                        break;
                    case WEST_EAST:
                        turnCenter = intersection.getSouthEast();
                        break;
                }
                break;
            }
            case EAST_WEST:
            {
                switch(eDir){
                    case SOUTH_NORTH:
                        turnCenter = intersection.getNorthEast();
                        break;
                    case NORTH_SOUTH:
                        turnCenter = intersection.getSouthEast();
                        break;
                }
                break;
            } 
        }
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public Queue<Request> getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(Queue<Request> requestQueue) {
        this.requestQueue = requestQueue;
    }

    public Point getTurnCenter() {
        return turnCenter;
    }

    public void setTurnCenter(Point turnCenter) {
        this.turnCenter = turnCenter;
    }

    public int getxAxisLen() {
        return xAxisLen;
    }

    public void setxAxisLen(int xAxisLen) {
        this.xAxisLen = xAxisLen;
    }

    public int getyAxisLen() {
        return yAxisLen;
    }

    public void setyAxisLen(int yAxisLen) {
        this.yAxisLen = yAxisLen;
    }
}
