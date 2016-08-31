/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author lukefallon
 */
public class CarGUI extends Rectangle{
    private Color color;
    
    public CarGUI(double x, double y, int length, int width)
    {
        this.color = Color.RED;
        this.x = (int)x - length/2;
        this.y = (int)y - width/2;
        this.setSize(length, width);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
