/* Color Sensor Helper Class *
 * Written on 11/25/16       *
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorSensing {
    ColorSensor sensor;
    public ColorSensing(ColorSensor s) {
        sensor = s;
    }
    public char getColor() {
        if (sensor.red() > sensor.blue()) return 'r';
        return 'b';
    }
    public void setMode(char a) {
        if (a == 'a') {
            sensor.enableLed(true);
        }
        else {
            sensor.enableLed(false);
        }
    }
}
