/* Beacon Strategy on Blue Side
 * Based on program by Robert Atkinson (2016)
 * Note: front is beacon pusher.
 * Negative power to the motors drives the robot forward, beacon pusher first.
 * So for autonomous, with ball shooting, make sure to remember this.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous(name = "Blue Team Beacon Strategy", group = "Autonomous")
public class BlueBeaconStrategy extends LinearOpMode {
    Hardware robot = new Hardware(); // Initialize hardware.
    private ElapsedTime runtime = new ElapsedTime(); // Figure out how long the robot has been running.

    // Declare sensor variables.
    ColorSensor cSensor;
    ColorSensing colorSensor;
    TouchSensor touchSensor;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, true);

        telemetry.addData("Status", "Resetting encoders...");
        telemetry.update();

        resetEncoders();
        idle();

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Status", "Done.");
        telemetry.update();

        // *** Initialize all sensors *** //
        cSensor = hardwareMap.colorSensor.get("sensor_color");
        colorSensor = new ColorSensing(cSensor);
        colorSensor.setMode('p'); // set sensor to passive mode.
        touchSensor = hardwareMap.touchSensor.get("sensor_touch");
        telemetry.addData("Status", "Color Sensor Enabled.");
        telemetry.addData("Status", "Touch Sensor Enabled.");
        telemetry.update();


        waitForStart(); // Wait until ready.

        // *** Boiler Plate Code Done *** //

        forward(52);
        sleep(250);
        turn(90);
        sleep(250);
        pressBeacon();
        if (colorSensor.getColor() == 'b') {
            // Uncomment this to test the color sensor.
            //telemetry.addData("::", "Got Blue.");
            //sleep(10000);
            forward(2);
            sleep(250);
        } else {
            backward(2);
            sleep(5000); // Wait for 5 seconds.
            forward(2);
        }
        // *** Main Code Done *** //
        telemetry.addData("Robot", "Stopped.");
        telemetry.update();
    }

    public void resetEncoders() {
        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // Precondition: Robot is already lined up.
    public void pressBeacon() {
        while (!touchSensor.isPressed()) {
            robot.leftMotor.setPower(Constants.DRIVE_SPEED);
            robot.rightMotor.setPower(Constants.DRIVE_SPEED);
        }
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
    }

    public void forward(double inches) {
        encoderDrive(Constants.DRIVE_SPEED, inches, inches);
    }

    public void backward(double inches) {
        encoderDrive(Constants.DRIVE_SPEED, -inches, -inches);
    }

    // Preconditions: theta is in the interval [-359, 359]
    public void turn(double theta) {
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // turn left if negative, right otherwise.
        if (theta < 0) {
            double distanceToMove = Constants.ROBOT_WIDTH * (-theta / 360) * Math.PI;
            encoderDrive(Constants.DRIVE_SPEED, 0, distanceToMove * 2);
        } else {
            telemetry.addData("Message", "Turning right...");
            double distanceToMove = Constants.ROBOT_WIDTH * (theta / 360) * Math.PI;
            encoderDrive(Constants.DRIVE_SPEED, distanceToMove * 2, 0);
        }
    }

    public void encoderDrive(double speed, double leftInches, double rightInches) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftMotor.getCurrentPosition() + (int)(leftInches * Constants.COUNTS_PER_INCH);
            newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(rightInches * Constants.COUNTS_PER_INCH);
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
            while (opModeIsActive() && ((robot.leftMotor.isBusy() || robot.rightMotor.isBusy()))) {

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
