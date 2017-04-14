package org.firstinspires.ftc.teamcode;
/**
 * Helper class: Constants
 * Bundles all constants for encoder driving and sensor names.
 * period. Makes it easier to create multiple autonomous strategies.
 * @author Max Bowman
 * @version 4/14/17
 */

public class Constants
{
    // Constants for figuring distance using motor encoders.
    public static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    public static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    public static final double     WHEEL_DIAMETER_INCHES   = 5.3125 ;     // For figuring circumference
    public static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    // Strings for configuring sensors.
    public static final String     beacon_color_sensor     = "color_sensor";
    public static final String     beacon_optical_sensor   = "optical_sensor";
    public static final String     gyro_sensor             = "gyro_sensor";
    public static final double     optical_threshold       = 0.01;

}
