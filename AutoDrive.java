package org.firstinspires.ftc.teamcode;
/**
 * Helper class: AutoDrive
 * Bundles all encoder-based driving functionality required for autonomous
 * period. Makes it easier to create multiple autonomous strategies.
 * @author Max Bowman
 * @version 4/12/17
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutoDrive {
    LinearOpMode l;
    Hardware robot;
    SensorArray s;
    int speed;

    /**
     * AutoDrive Constructor
     * @param l for access to information on OpMode
     * @param robot for access to hardware
     * @param s for access to sensor array
     */
    public AutoDrive(LinearOpMode l, Hardware robot, SensorArray s) {
        this.l = l;
        this.robot = robot;
        this.s = s;
        speed = 0;
        resetEncoders();
    }

    /**
     * Sets the speed of the motors in drive
     * @param speed desired running speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Resets Encoders
     */
    public void resetEncoders() {
        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * Sets speed of motors - no encoders
     * @param left speed of left motor
     * @param right speed of right motor
     */
    public void setMotorSpeeds(double left, double right) {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);
    }

    /**
     * Sets speed of both motors to parameter
     * @param a desired speed of both motors
     */
    public void setMotorSpeeds(double a) {
        setMotorSpeeds(a, a);
    }

    /**
     * Moves robot using encoders forward and backward. Supply positive
     * value for inches to move forward, and a negative value to move backward
     * @param inches inches to drive forward / backwards
     */
    public void move(double inches) {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        encoderDrive(inches, inches);
    }

    /**
     * Stops all motion and brakes drive motors
     */
    public void brake() {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
    }

    // Preconditions: theta is in the interval [-359, 359]
    // To do: find out how to optimize this... too many lines of code
    // for a simple job.
    // NOTE: REWRITE THIS ONCE USING GYRO
    /*
    public void turn(double theta) {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // turn left if negative, right otherwise.
        if (theta < 0) {
            double distanceToMove = Constants.ROBOT_WIDTH * (-theta / 360) * Math.PI;
            encoderDrive(0, distanceToMove * 2);
        } else {
            double distanceToMove = Constants.ROBOT_WIDTH * (theta / 360) * Math.PI;
            encoderDrive(distanceToMove * 2, 0);
        }
    }*/

    /**
     * Drives the motors forward / backward using motor encoders
     * Based heavily on encoderDrive(double speed, leftInches, rightInches)
     * by Robert Atkinson, (c) 2016
     * @param leftInches
     * @param rightInches
     */
    private void encoderDrive(double leftInches, double rightInches) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (l.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftMotor.getCurrentPosition() + (int) (leftInches * Constants.COUNTS_PER_INCH);
            newRightTarget = robot.rightMotor.getCurrentPosition() + (int) (rightInches * Constants.COUNTS_PER_INCH);
            robot.leftMotor.setTargetPosition(newLeftTarget);
            robot.rightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            robot.leftMotor.setPower(Math.abs(speed));
            robot.rightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (l.opModeIsActive() && ((robot.leftMotor.isBusy() || robot.rightMotor.isBusy())));

            // Stop all motion;
            brake();

            // Turn off RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
