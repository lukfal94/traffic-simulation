/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infrastructure;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author lukefallon
 */
public class Lane {
    private int ID;

    private int numCars;            // number of cars waiting in this lane
    private Lane[] adjLanes;        // adjacent lanes (2)
    private double speedLimit;   
    private ArrayList<Car> cars;             // record of all cars in this lane
    
    private int originX;
    private int originY;
    
    private int terminusX;
    private int terminusY;


    public Lane(int id)
    {
        setID(id);
        cars = new ArrayList<>();
    }
    
    public void addCar(Car car)
    {
        car.setCurr_lane(this);
        
        this.cars.add(car);
        this.numCars++;
    }
    
    public int getID() {
        return ID;
    }

    private void setID(int ID) {
        this.ID = ID;
    }
    
    public void setTerminus(int x, int y)
    {
        this.terminusX = x;
        this.terminusY = y;
    }
    public void setOrigin(int x, int y)
    {
        this.originX = x;
        this.originY = y;
    }
    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public Point getTerminus()
    {
        return new Point(terminusX, terminusY);
    }
    public int getTerminusX() {
        return terminusX;
    }

    public void setTerminusX(int terminusX) {
        this.terminusX = terminusX;
    }

    public int getTerminusY() {
        return terminusY;
    }

    public void setTerminusY(int terminusY) {
        this.terminusY = terminusY;
    }
}   

