/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comms;

import infrastructure.Lane;


/**
 *
 * @author lukefallon
 */
public class Reservation {
    
    // Time to reach position
    private Lane start;
    private Lane end;
    private double vel_x;
    private double vel_y;
    private Maneuver maneuver;
    
    public Reservation()
    {
        
    }
    
    public Reservation(/*Time,*/ Lane start, 
            double vX, double vY, Maneuver man)
    {
        this.start = start;
        this.vel_x = vX;
        this.vel_y = vY;
        this.maneuver = man;
    }
    
    public Maneuver getManeuver()
    {
        return this.maneuver;
    }

    public Lane getStart() {
        return start;
    }

    public void setStart(Lane start) {
        this.start = start;
    }

    public double getVel_x() {
        return vel_x;
    }

    public void setVel_x(double vel_x) {
        this.vel_x = vel_x;
    }

    public double getVel_y() {
        return vel_y;
    }

    public void setVel_y(double vel_y) {
        this.vel_y = vel_y;
    }

    public void setManeuver(Maneuver maneuver) {
        this.maneuver = maneuver;
    }

    public Lane getEnd() {
        return end;
    }

    public void setEnd(Lane end) {
        this.end = end;
    }
}
