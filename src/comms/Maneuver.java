/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comms;

import config.Enums.ManeuverType;
import config.Velocity;
import infrastructure.Lane;
import java.awt.Point;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author lukefallon
 */
public class Maneuver {

    private Queue<ManeuverStep> steps;
    private ManeuverType type;
    private int startX;
    private int startY;
    
    // Nothing to do for a straight maneuver
    public Maneuver()
    {
        
    }
    
    //
    // Create a turning maneuver
    public Maneuver(Point center, int a, int b, 
            Lane startLane,
            double finalVx, double finalVy,
            double thetaI, double thetaF, int numSteps)
    {
        int i;
        Point start = startLane.getTerminus();
        double thetaInc = (thetaF - thetaI) / numSteps;
        double theta;
        double prevX = start.x, prevY = start.y;
        double currPosX, currPosY;
        double velX, velY;
        
        steps = new LinkedBlockingQueue<>();
        
        for(i = 1; i < numSteps; i++)
        {
            theta = thetaI + (i * thetaInc);
            
            // Calculate the current position along the path
            currPosX = center.x + a * Math.cos(theta);
            currPosY = center.y + b * Math.sin(theta);
            
            // Determine the difference between the last position and this one
            velX = currPosX - prevX;
            velY = currPosY - prevY;
            
            steps.add(new ManeuverStep( velX, velY, 0 ));

            prevX = currPosX;
            prevY = currPosY;
        }
        
        // Correct error
        steps.add(new ManeuverStep( finalVx, finalVy, 0));
    }
    
    // Create a lane change maneuver
    public Maneuver( 
            double deltaX, double deltaY,
            int numSteps, Velocity vel)
    {
        int i;

        steps = new LinkedBlockingQueue<>();
        
        for(i = 0; i < numSteps; i++)
        {
            steps.add(
                    new ManeuverStep( 
                            vel.getX() + deltaX/numSteps, 
                            vel.getY() + deltaY/numSteps, 0 ));
        }
       
        // Reset the velocity
        steps.add(new ManeuverStep( vel.getX(), vel.getY(), 0));
    }
    
    // Create an acceleration maneuver
    public Maneuver(int numSteps, double initXVelocity, double initYVelocity, 
            double finalXVelocity, double finalYVelocity)
    {
        int i;
        double delta_vX = finalXVelocity - initXVelocity;
        double delta_vY = finalYVelocity - initYVelocity;
        
        steps = new LinkedBlockingQueue<>();
        
        for(i = 0; i < numSteps; i++)
        {
            steps.add(new ManeuverStep( delta_vX/numSteps, 
                                        delta_vY/numSteps, 0));
        }
    }
    
    // Create an acceleration maneuver
    public Maneuver(int numSteps, int startX, int startY,
            double initXVelocity, double finalXVelocity,
            double initYVelocity, double finalYVelocity)
    {
        int i;
        double delta_vX = finalXVelocity - initXVelocity;
        double delta_vY = finalYVelocity - initYVelocity;
        double error_X;
        double error_Y;
        
        this.startX = startX;
        this.startY = startY;
        
        for(i = 0; i < numSteps; i++)
        {
            steps.add(new ManeuverStep(delta_vX/(double)numSteps, delta_vY/(double)numSteps, 0));
        }
        
        // Ensure the correct delta_V is achieved
        error_X = (delta_vX > (delta_vX/(double)numSteps) * numSteps) ? 
                (delta_vX - (delta_vX/(double)numSteps) * numSteps) :
                ((delta_vX/(double)numSteps) * numSteps - delta_vX);
        
        error_Y = (delta_vY > (delta_vY/(double)numSteps) * numSteps) ? 
                (delta_vY - (delta_vY/(double)numSteps) * numSteps) :
                ((delta_vY/(double)numSteps) * numSteps - delta_vY);
        
        steps.add(new ManeuverStep(error_X, error_Y, 0));
    }
    
    public Queue<ManeuverStep> getSteps() {
        return steps;
    }

    public void setSteps(Queue<ManeuverStep> steps) {
        this.steps = steps;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }    

    public ManeuverType getType() {
        return type;
    }

    public void setType(ManeuverType type) {
        this.type = type;
    }
}
