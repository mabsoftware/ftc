/* Main Driving Class
 * Drive type: Tank
 * Max operation speed: 50%.
 * Forwards: depends, starts on beacon pusher.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Main Drive", group="Manual")
public class Drive extends OpMode {

    /* Declare OpMode members. */
    Hardware robot;

    boolean forwardBeacon; // true if beacon side, false if not.
    boolean inPreciseMode; // true if precise mode, false if not.

    private static final double PRECISE = 0.25;
    private static final double MAX = 1.50 / 3; // Maximum speed is 50% of total capacity.

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        // *** Initialize the hardware variables. *** //
        robot = new Hardware(); // define the robot's hardware.
        robot.init(hardwareMap, false); // Initialize hardware, no encoders.

        forwardBeacon = true; // start w/ beacon pointing forward.
        inPreciseMode = true;

        // Send telemetry message to signify robot waiting.
        telemetry.addData("Robot", "Ready.");
    }

    // Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {
        telemetry.addData("Robot", "Please press play.");
    }

    // Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {
        telemetry.addData("Robot", "Starting...");
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP.
    @Override
    public void loop() {
        // *** Get Values from users *** //
        double myLeft = -gamepad1.left_stick_y;
        double myRight = -gamepad1.right_stick_y;
        boolean leftBumper = gamepad1.left_bumper;
        boolean rightBumper = gamepad1.right_bumper;
        boolean yButton = gamepad1.y;
        boolean aButton = gamepad1.a;
        double shooterSpeed = gamepad2.left_stick_y;
        double sweeperSpeed = gamepad2.right_stick_y;
        // *** Handle values from users *** //
        if (leftBumper) inPreciseMode = true; // If left bumper is hit, set to precise mode.
        if (rightBumper) inPreciseMode = false; // If right bumper is hit, set to speed mode.
        if (yButton) forwardBeacon = true;
        if (aButton) forwardBeacon = false;
        // *** Compute motor speeds *** //
        if (!forwardBeacon) {
            myLeft = -myLeft;
            myRight = -myRight;
            double temp = myLeft;
            myLeft = myRight;
            myRight = temp;
        } // handle direction flipping, flip left and right motors.
        if (inPreciseMode) {
            myLeft = Range.clip(myLeft, -PRECISE, PRECISE);
            myRight = Range.clip(myRight, -PRECISE, PRECISE);
            telemetry.addData("Mode: ", "Precise");
        }
        else {
            myLeft = Range.clip(myLeft, -MAX, MAX);
            myRight = Range.clip(myRight, -MAX, MAX);
            telemetry.addData("Mode: ", "Speed");
        } // handle precise and speed modes.
        // *** Set motor speeds ***//
        robot.leftMotor.setPower(myLeft);
        robot.rightMotor.setPower(myRight);
        robot.leftShooter.setPower(shooterSpeed);
        robot.rightShooter.setPower(shooterSpeed);
        // Send data via telemetry.
        telemetry.addData("Data", "**** Joystick Data ****");
        telemetry.addData("Left",  "%.2f", myLeft);
        telemetry.addData("Right", "%.2f", myRight);
    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {
        telemetry.addData("Robot", "Stopped.");
    }

}