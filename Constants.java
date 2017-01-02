package org.firstinspires.ftc.teamcode;

public class Constants
{
    // Constants for figuring distance using motor encoders.
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5.3125 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     DRIVE_SPEED             = 0.3;

    static final double PHYSICAL_MAX = 1.0;

    static final double DISTANCE_BETWEEN_BEACONS = 15;
    static final double ROBOT_WIDTH = 16;

    // Strings for configuring sensors.
    static final String beacon_color_sensor = "sensor_color";
    static final double optical_threshold = 10000;
    static final String beacon_optical_sensor = "sensor_optical";
}
