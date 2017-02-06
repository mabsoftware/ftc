/* Sensor Array Class
 * This class is meant to simplify getting sensor
 * inputs. It makes it easy to initialize and use
 * sensors.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

public class SensorArray {
    private ColorSensor beaconcSensor;
    private ColorSensing beaconColorSensor;
    private OpticalDistanceSensor odsSensor;
    private LinearOpMode l;
    public SensorArray(LinearOpMode l) {
        beaconcSensor = l.hardwareMap.colorSensor.get(Constants.beacon_color_sensor);
        beaconColorSensor = new ColorSensing(beaconcSensor);
        odsSensor = l.hardwareMap.opticalDistanceSensor.get(Constants.beacon_optical_sensor);

        this.l = l;
        odsSensor.enableLed(true);
        beaconColorSensor.setMode('p');
    }
    public char getBeaconColor() {
        return beaconColorSensor.getColor();
    }

    public double r()
    { return beaconColorSensor.r(); }

    public double g()
    { return beaconColorSensor.g(); }

    public double b()
    { return beaconColorSensor.b(); }

    public boolean overLine() {
        if (odsSensor.getLightDetected() >= Constants.optical_threshold) {
            return true;
        }
        return false;
    }
}
