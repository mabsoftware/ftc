/* Main Driving Class
 * Drive type: Tank
 * Max operation speed: 75%.
 * Forwards: depends, starts on beacon pusher.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Drive", group="Manual")
public class Drive extends OpMode {

    /* Declare OpMode members. */
    Hardware robot;

    private static final double MAX = 0.75; // Maximum speed is 75% of total capacity.

    boolean direction; // true is beacon hitter forward, false is other side forward.
    byte dirNum;
    boolean preciseMode;

     // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here.*/
        robot = new Hardware(); // define the Pushbot's hardware.
        robot.init(hardwareMap, false);

        // Controls various speed and direction modes.
        direction = false;
        dirNum = 1;
        preciseMode = true; // put precise mode on by default.

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Robot", "Ready.");    //
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

     // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        // *** Get Values from user *** //
        double myLeft = -gamepad1.left_stick_y;
        double myRight = -gamepad1.right_stick_y;
        boolean preciseMode = !gamepad1.right_bumper;

        // *** Helper code to determine front of robot *** //
        dirNum = (direction) ? dirNum *= -1 : dirNum;

        // *** Precise Mode Code *** //
        if (preciseMode) {
            telemetry.addData("Robot", "Precise mode enabled.");
            myLeft = Range.clip(myLeft, -MAX / 4, MAX / 4);
            myRight = Range.clip(myRight, -MAX / 4, MAX / 4);
        } // Normally, only go a quarter of the speed.
        else {
            telemetry.addData("Robot", "Precise mode disabled.");
            myLeft = Range.clip(myLeft, -MAX, MAX);
            myRight = Range.clip(myRight, -MAX, MAX);
        } // But if you need to get somewhere quickly, just hit the right bumper and go.

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