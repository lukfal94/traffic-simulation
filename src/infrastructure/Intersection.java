/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infrastructure;

import java.awt.Point;

/**
 *
 * @author lukefallon
 */
public class Intersection {
    private CollisionMap collMap;
    
    private Point center;
    private Point northWest;
    private Point northEast;
    private Point southWest;
    private Point southEast;
    
    public Intersection(int x, int y, int d)
    {
        this.center = new Point(x, y);
        
        this.northWest = new Point(x - d/2, y - d/2);
        this.northEast = new Point(x + d/2, y - d/2);
        this.southWest = new Point(x - d/2, y + d/2);
        this.southEast = new Point(x + d/2, y + d/2);
    }

    public CollisionMap getCollMap() {
        return collMap;
    }

    public void setCollMap(CollisionMap collMap) {
        this.collMap = collMap;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getNorthWest() {
        return northWest;
    }

    public void setNorthWest(Point northWest) {
        this.northWest = northWest;
    }

    public Point getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Point northEast) {
        this.northEast = northEast;
    }

    public Point getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Point southWest) {
        this.southWest = southWest;
    }

    public Point getSouthEast() {
        return southEast;
    }

    public void setSouthEast(Point southEast) {
        this.southEast = southEast;
    }
}
