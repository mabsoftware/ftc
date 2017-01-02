/* Helper class: AutoDrive
 * Bundles all encoder-based driving
 * functionality required for autonomous
 * period. Makes it easier to create
 * multiple autonomous strategies.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutoDrive {
    LinearOpMode l;
    Hardware robot;
    SensorArray s;

    public AutoDrive(LinearOpMode l, Hardware robot, SensorArray s) {
        this.l = l;
        this.robot = robot;
        this.s = s;
        resetEncoders();
    }

    // Precondition: Robot is already lined up.
    /*
    public void pressBeacon() {
        while (!s.donePressing()) {
            robot.leftMotor.setPower(Constants.DRIVE_SPEED);
            robot.rightMotor.setPower(Constants.DRIVE_SPEED);
        }
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        if (s.getBeaconColor() == 'b') {
            // Uncomment this to test the color sensor.
            //telemetry.addData("::", "Got Blue.");
            //sleep(10000);
            forward(2);
            l.sleep(250);
        } else {
            backward(2);
            l.sleep(5000); // Wait for 5 seconds.
            forward(2);
        }
    }
    */
    public void pressBeacon() {
        this.forward(25);
    }

    public void tap() {
        this.forward(3);
    }

    public void resetEncoders() {
        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void forward(double inches) {
        encoderDrive(Constants.DRIVE_SPEED, inches, inches);
    }

    public void backward(double inches) {
        encoderDrive(Constants.DRIVE_SPEED, -inches, -inches);
    }

    // Preconditions: theta is in the interval [-359, 359]
    // To do: find out how to optimize this... too many lines of code
    // for a simple job.
    public void turn(double theta) {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // turn left if negative, right otherwise.
        if (theta < 0) {
            double distanceToMove = Constants.ROBOT_WIDTH * (-theta / 360) * Math.PI;
            encoderDrive(Constants.DRIVE_SPEED, 0, distanceToMove * 2);
        } else {
            double distanceToMove = Constants.ROBOT_WIDTH * (theta / 360) * Math.PI;
            encoderDrive(Constants.DRIVE_SPEED, distanceToMove * 2, 0);
        }
    }

    public void encoderDrive(double speed, double leftInches, double rightInches) {
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
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
