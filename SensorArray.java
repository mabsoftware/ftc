/* Sensor Array Class
 * This class is meant to simplify getting sensor
 * inputs. It makes it easy to initialize and use
 * sensors.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class SensorArray {
    private ColorSensor beaconcSensor;
    private ColorSensing beaconColorSensor;
    private TouchSensor beaconTouchSensor;
    public SensorArray(LinearOpMode l) {
        beaconcSensor = l.hardwareMap.colorSensor.get(Constants.beacon_color_sensor);
        beaconColorSensor = new ColorSensing(beaconcSensor);
        beaconTouchSensor = l.hardwareMap.touchSensor.get(Constants.beacon_touch_sensor);

        beaconColorSensor.setMode('p');
    }
    public char getBeaconColor() {
        return beaconColorSensor.getColor();
    }

    public boolean isTouchingBeacon() {
        return beaconTouchSensor.isPressed();
    }
}
