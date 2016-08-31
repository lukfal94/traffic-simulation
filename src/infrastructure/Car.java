/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infrastructure;

import comms.ManeuverStep;
import comms.Maneuver;
import comms.Reservation;
import config.Enums.ManeuverType;
import config.SimConstants;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import userInterface.CarGUI;

/**
 *
 * @author lukefallon
 */
public class Car {
    
    private double vel_x;   // current x velocity of vehicle
    private double vel_y;   //  ''     y    ''    ''   ''
    
    private double accel_x;
    private double accel_y;
    
    private double pos_x;   // The center of the car
    private double pos_y;   
    
    private double heading = 0;
    private double rot_v = 0;
    
    private Lane curr_lane; // current lane
    private Road curr_road; // current road
    private Road dest_road; // destination road
    
    private int length = SimConstants.CAR_LENGTH;     // Length of the car
    private int width = SimConstants.CAR_WIDTH;      // width of the car
    
    private Maneuver currManeuver;
    private Queue<Maneuver> maneuvers;
    private Reservation reservation;
    
    public CarGUI gui;     // GUI information for the car
    
    private boolean hasReservation = false;
    private boolean correctLane = false;    
    
    public Car(double x, double y, double initX, double initY, double initHead, 
            Road currRoad, Road destRoad)
    {
        this.pos_x = x;
        this.pos_y = y;
        this.vel_x = initX;
        this.vel_y = initY;
        this.heading = initHead;

        this.curr_road = currRoad;
        this.dest_road = destRoad;
        
        this.maneuvers = new LinkedBlockingQueue<>();
        
        this.gui = new CarGUI(x, y, this.length, this.width);
    }
    
    public void changeVelocity(int newX, int newY)
    {
        this.setVel_x(newX);
        this.setVel_y(newY);
    }
    
    public void updatePosition() {
        pos_x += vel_x;
        pos_y += vel_y;
        
        gui.setLocation((int)pos_x - SimConstants.CAR_LENGTH/2, (int)pos_y - SimConstants.CAR_WIDTH/2);
    }

    public void updateHeading() {
        this.heading += rot_v;
    }
    
    public void updateVelocity() {
        if(currManeuver != null)
        {
            executeManeuver(currManeuver);
        }
        else if(!maneuvers.isEmpty())
        {
            // Get the next maneuver
            currManeuver = maneuvers.remove();
        }
//        // If there are no maneuvers queued, and no current maneuver, just add the acceleration
//        if(this.maneuvers.isEmpty() && this.currManeuver == null)
//        {
//            this.vel_x += this.accel_x;
//            this.vel_y += this.accel_y;
//        }
//        // There are maneuvers queued, but no current maneuver.
//        else if(this.maneuvers.isEmpty())
//        {
//            executeManeuver(currManeuver);
//        }
//        else if(null == this.currManeuver)
//        {
//            // Remove and load the first maneuver.
//            currManeuver = maneuvers.remove();
//            executeManeuver(currManeuver);
//        }
        
        
        
    }
    
    private void executeManeuver(Maneuver man)
    {
        ManeuverStep currStep;
        
        if(man == null || man.getSteps() == null)
            return;
        
        switch(this.getCurr_road().getDirection())
        {
            case WEST_EAST:
            {
                if((int)this.pos_x >= man.getStartX())
                {
                    try{
                        currStep = man.getSteps().remove();
                        this.vel_x = currStep.getVel_x();
                        this.vel_y = currStep.getVel_y();
                        this.heading = currStep.getRot_v();
                    }catch(NoSuchElementException ex)
                    {
                        man = null;
                    }
                }
                break;
            }
            case SOUTH_NORTH:
            {
                if((int)this.pos_y <= man.getStartY())
                {
                    try{
                        currStep = man.getSteps().remove();
                        this.vel_x = currStep.getVel_x();
                        this.vel_y = currStep.getVel_y();
                        this.heading = currStep.getRot_v();
                    }catch(NoSuchElementException ex)
                    {
                        man = null;
                    }
                }
                break;
            }
            case EAST_WEST:
            {
                if((int)this.pos_x <= man.getStartX())
                {
                    try{
                        currStep = man.getSteps().remove();
                        if(man.getType() == ManeuverType.LEFT)
                        {
                            this.vel_x = currStep.getVel_x();
                            this.vel_y = currStep.getVel_y();
                            this.heading = currStep.getRot_v();
                        }
                    }catch(NoSuchElementException ex)
                    {
                        man = null;
                    }
                }
                break;
            }
            case NORTH_SOUTH:
            {
                if((int)this.pos_y >= man.getStartY())
                {
                    try{
                        currStep = man.getSteps().remove();
                        this.vel_x = currStep.getVel_x();
                        this.vel_y = currStep.getVel_y();
                        this.heading = currStep.getRot_v();
                    }catch(NoSuchElementException ex)
                    {
                        //System.out.println("Ended maneuver");
                        man = null;
                    }
                }
                break;
            }
        }
    }
    
    // Getters and setters are used for encapsulation
    public double getVel_x() {
        return vel_x;
    }

    private void setVel_x(int vel_x) {
        this.vel_x = vel_x;
    }

    public double getVel_y() {
        return vel_y;
    }

    private void setVel_y(int vel_y) {
        this.vel_y = vel_y;
    }

    public Lane getCurr_lane() {
        return curr_lane;
    }

    public void setCurr_lane(Lane curr_lane) {
        this.curr_lane = curr_lane;
    }

    public Road getCurr_road() {
        return curr_road;
    }

    private void setCurr_road(Road curr_road) {
        this.curr_road = curr_road;
    }

    public Road getDest_road() {
        return dest_road;
    }

    private void setDest_road(Road dest_road) {
        this.dest_road = dest_road;
    }

    public int getLength() {
        return length;
    }

    private void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public boolean isHasReservation() {
        return hasReservation;
    }

    public void setHasReservation(boolean hasReservation) {
        this.hasReservation = hasReservation;
    }

    public double getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public double getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    
    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getRot_v() {
        return rot_v;
    }

    public void setRot_v(double rot_v) {
        this.rot_v = rot_v;
    }

    public void setVel_x(double vel_x) {
        this.vel_x = vel_x;
    }

    public void setVel_y(double vel_y) {
        this.vel_y = vel_y;
    }
    
    public double getAccel_x() {
        return accel_x;
    }

    public void setAccel_x(double accel_x) {
        this.accel_x = accel_x;
    }

    public double getAccel_y() {
        return accel_y;
    }

    public void setAccel_y(double accel_y) {
        this.accel_y = accel_y;
    }
    
    public Queue<Maneuver> getManeuverQueue()
    {
        return this.maneuvers;
    }

    public boolean isCorrectLane() {
        return correctLane;
    }

    public void setCorrectLane(boolean correctLane) {
        this.correctLane = correctLane;
    }

    public Maneuver getCurrManeuver() {
        return currManeuver;
    }

    public void setCurrManeuver(Maneuver currManeuver) {
        this.currManeuver = currManeuver;
    }

    public Queue<Maneuver> getManeuvers() {
        return maneuvers;
    }

    public void setManeuvers(Queue<Maneuver> maneuvers) {
        this.maneuvers = maneuvers;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
