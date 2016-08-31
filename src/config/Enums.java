/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author lukefallon
 */
public class Enums{
    public enum SimState{RESTING, RESET, RUNNING, PAUSED};
    public enum CarState{STOPPED, HOLDING, MANEUVER, FINISHED};
    public enum ManeuverType{RIGHT, LEFT, STRAIGHT, LANE_CHANGE_LEFT, LANE_CHANGE_RIGHT};
    public enum RoadDirection{NORTH_SOUTH, SOUTH_NORTH, WEST_EAST, EAST_WEST};
}
