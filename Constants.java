package org.firstinspires.ftc.teamcode;
/**
 * Helper class: Constants
 * Bundles all constants for encoder driving and sensor names.
 * period. Makes it easier to create multiple autonomous strategies.
 * @author Max Bowman
 * @version 4/12/17
 */

public class Constants
{
    // Constants for figuring distance using motor encoders.
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5.3125 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     TICKS_PER_DEGREE        = 1440 / 360;

    // Strings for configuring sensors.
    static final String beacon_color_sensor   = "sensor_color";
    static final double optical_threshold     = 0.01;
    static final String beacon_optical_sensor = "ods";
    static final double blue_threshold        = 7;
    static final double red_threshold         = 6;
}
