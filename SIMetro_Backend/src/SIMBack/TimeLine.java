/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author QB
 */
package SIMBack;
import java.util.ArrayList;

public class TimeLine {
    static private int timeRange;
    static ArrayList[] table;
    public TimeLine(int timeRange){
        table=new ArrayList[timeRange];
    }
    public static int getTimeRange(){
            return timeRange;
        }
}
