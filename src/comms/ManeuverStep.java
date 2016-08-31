/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comms;

/**
 *
 * @author lukefallon
 */
public class ManeuverStep {
    private double vel_x;
    private double vel_y;
    private double rot_v;
    
    public ManeuverStep(double vX, double vY, double rV)
    {
        this.vel_x = vX;
        this.vel_y = vY;
        this.rot_v = rV;   
    }

    public double getRot_v() {
        return rot_v;
    }

    public void setRot_v(double rot_v) {
        this.rot_v = rot_v;
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
}
