/* Map Class
 * Helps the robot keep track of its location
 * during the autonomous period.
 */

package org.firstinspires.ftc.teamcode;

public class Map {
    double x, y;
    public Map(double startX, double startY) {
        this.x = startX;
        this.y = startY;
    }
    public void config(double myX, double myY) {
        this.x = myX;
        this.y = myY;
    }
    public void update(double secs, double avgL, double avgR) {
        // Calculate new location based on number of seconds elapsed + average left and right motor speeds.
    }
}
