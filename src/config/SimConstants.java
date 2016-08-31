/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.text.DecimalFormat;

/**
 *
 * @author lukefallon
 */
public class SimConstants {
    public static final int MAG_INDEX = 1;
    public static final int LANE_WIDTH = 15 * MAG_INDEX;
    public static final int CAR_WIDTH = 5 * MAG_INDEX;
    public static final int CAR_LENGTH = 5 * MAG_INDEX;
    public static final int ROAD_LENGTH = 250 * MAG_INDEX;
    public static final int REFRESH_RATE = 50;
    public static final int NUM_ROADS = 4;
    
    public static DecimalFormat timeForm = new DecimalFormat("#.##");
}
