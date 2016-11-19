/* Team Fractals Autonomous Program if on Team Red
 * Based on program by Robert Atkinson (2016)
 * Note: front is beacon pusher.
 * Negative power to the motors moves the robot
 * forward.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name="Red Team", group="Autonomous")
public class AutoRed1 extends LinearOpMode {
    Hardware robot = new Hardware(); // Initialize hardware.
    private ElapsedTime runtime = new ElapsedTime(); // Figure out how long the robot has been running.

    // Declare sensor variables.
    ColorSensor colorSensor;
    double[] rgb;

    // Constants for figuring distance using motor encoders.
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.14159265358979323846264338);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    // Constants for turning the robot theta degrees.
    static final double ROBOT_WIDTH = 14;
    static final double PIVOT_OFFSET = 3; // Units in inches.

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, true);

        // Initialize sensors.

        colorSensor = hardwareMap.colorSensor.get("sensor_color");
        colorSensor.enableLed(false); // Set to passive mode to read beacons.
        rgb = new double[3];
        telemetry.addData("Status", "Sensors online.");
        telemetry.update();

        telemetry.addData("Status", "Resetting encoders...");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart(); // Wait until ready.

        // *** Boiler Plate Code Done *** //
        // *** Main Code *** //
        encoderDrive(DRIVE_SPEED,  5.0 * 12,  5.0 * 12, 5.0);

        // *** Main Code Done *** //
        telemetry.addData("Robot", "Done...");
        telemetry.update();
    }

    public void readColorSensor() {
        rgb[0] = colorSensor.red();
        rgb[1] = colorSensor.green();
        rgb[2] = colorSensor.blue();
    }

    public void turnRobot(double THETA) {
        // Write code to turn the robot THETA degrees.
        if (THETA == 0) return;
        else if (THETA < 0) {
            // If THETA is negative, turn left.
        }
        else {
            // Otherwise, turn right.
        }
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftMotor.setTargetPosition(newLeftTarget);
            robot.rightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftMotor.setPower(Math.abs(speed));
            robot.rightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftMotor.getCurrentPosition(),
                        robot.rightMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
