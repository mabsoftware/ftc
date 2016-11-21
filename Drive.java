/* Main Driving Class
 * Drive type: Tank
 * Max operation speed: 75%.
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

    private static final double MAX = 0.75; // Maximum speed is 75% of total capacity.
    private static final double PHYSICAL_MAX = 1.00; // Physical maximum speed.

    boolean direction; // true is beacon hitter forward, false is other side forward.
    byte dirNum;
    boolean preciseMode;
    byte mode;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        // *** Initialize the hardware variables. *** //
        robot = new Hardware(); // define the Pushbot's hardware.
        robot.init(hardwareMap, false); // Initialize hardware, no encoders.

        // Controls various speed and direction modes.
        direction = false;
        dirNum = 1;
        preciseMode = true; // put precise mode on by default.
        mode = 1; // 1 is precise, 2 is fast, 3 is turbo.

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
        // *** Get Values from user *** //
        double myLeft = -gamepad1.left_stick_y;
        double myRight = -gamepad1.right_stick_y;
        boolean myRightBumper = gamepad1.right_bumper;

        // *** Helper code to determine front of robot *** //
        dirNum = (direction) ? dirNum *= -1 : dirNum;

        // *** Precise Mode Code *** //
        if (myRightBumper) {
            telemetry.addData("Robot", "Mode changed.");
            mode ++;
            mode %= 3;
        } // Normally, only go a quarter of the speed.

        switch (mode) {
            // Precise Mode
            case 1:
                myLeft = Range.clip(myLeft, -MAX / 4, MAX / 4);
                myRight = Range.clip(myRight, -MAX / 4, MAX / 4);
                break;
            // Fast Mode
            case 2:
                myLeft = Range.clip(myLeft, -MAX, MAX);
                myRight = Range.clip(myRight, -MAX, MAX);
                break;
            // Turbo Mode
            case 3:
                myLeft = Range.clip(myLeft, -PHYSICAL_MAX, PHYSICAL_MAX);
                myRight = Range.clip(myRight, -PHYSICAL_MAX, PHYSICAL_MAX);
                break;
            // Catch any potential errors.
            default:
                telemetry.addData("Emergency", "Mode error!");
                telemetry.update();
        }

        // *** Set Motor Powers *** //
        robot.leftMotor.setPower(myLeft);
        robot.rightMotor.setPower(myRight);

        // *** Get User Input for Direction *** //
        if (gamepad1.right_stick_button) {
            direction = !direction;
        } // Toggle direction based on right stick button.

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