package org.firstinspires.ftc.teamcode;
/**
 * Helper class: SensorArray
 * Bundles all sensor functionality into one convenient helper class
 * period. Makes it easier to create multiple autonomous strategies.
 * Current sensors supported: color, optical distance, and gyro.
 * @author Max Bowman
 * @version 4/12/17
 */

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

public class SensorArray {
    private ColorSensor colorSensor;
    private OpticalDistanceSensor odsSensor;
    private ModernRoboticsI2cGyro gyroSensor;

    private LinearOpMode l;

    /**
     * Constructor for SensorArray class
     * @param l to access sensor hardware
     */
    public SensorArray(LinearOpMode l) {
        colorSensor = l.hardwareMap.colorSensor.get(Constants.beacon_color_sensor);
        odsSensor = l.hardwareMap.opticalDistanceSensor.get(Constants.beacon_optical_sensor);
        gyroSensor = (ModernRoboticsI2cGyro)l.hardwareMap.gyroSensor.get(Constants.gyro_sensor);

        this.l = l;

        odsSensor.enableLed(true);
        colorSensor.enableLed(true);

        l.telemetry.addData("Status", "Calibrating sensors...");
        l.telemetry.addData("Warning", "Do not move!");
        l.telemetry.update();

        gyroSensor.calibrate();
        while (!l.isStopRequested() && gyroSensor.isCalibrating()) {
            l.sleep(50);
            l.idle();
        }
        gyroSensor.resetZAxisIntegrator();
    }

    /**
     * Sets the color sensor to operate in active or passive mode
     * @param c 'a' for active, 'p' for passive
     * @return true if the mode was correctly set, false if not
     */
    public boolean setColorMode(char c) {
        if (c == 'a') {
            colorSensor.enableLed(true);
            return true;
        }
        else if (c == 'p') {
            colorSensor.enableLed(false);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the RGB values of the color sensor
     * @return an integer array of RGB values
     */
    public int[] getColor() {
        int[] vals = { colorSensor.red(), colorSensor.blue(), colorSensor.green() };
        return vals;
    }

    /**
     * Uses the amount of light it receives in active mode to determine
     * whether it is over a white line. Useful for navigation
     * @return true if the robot is over a white line, false if not
     */
    public boolean overLine() {
        if (odsSensor.getLightDetected() >= Constants.optical_threshold) {
            return true;
        }
        return false;
    }

    /**
     * Returns the heading of the robot (z-axis) from the gyro
     * @return heading
     */
    public int getHeading() { return gyroSensor.getHeading(); }

    /**
     * Returns the acceleration in each axis.
     * @return array of raw X, Y, and Z rotation rates, respectively
     */
    public int[] getGyroValues() {
        int[] values = { gyroSensor.rawX(), gyroSensor.rawY(), gyroSensor.rawZ() };
        return values;
    }
}
