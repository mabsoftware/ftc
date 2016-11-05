package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Team Fractals Robot", group="Pushbot")
//@Disabled
public class PushbotTeamFractal extends OpMode {

    /* Declare OpMode members. */
    Hardware robot;

    double x, y;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot = new Hardware(); // define the Pushbot's hardware.
        robot.init(hardwareMap);

        x = 0;
        y = 0;

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Ready.");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        telemetry.addData("Say", "Please press play.");
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        telemetry.addData("Say", "Starting...");
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        double throttle = gamepad1.left_stick_y;
        double steer = gamepad1.right_stick_x; // The robot drives "backwards," so don't negate the joystick values.
        robot.leftMotor.setPower(throttle / 2 + steer); // left motor is actually the right motor.
        robot.rightMotor.setPower(throttle / 2 - steer); // and vice versa.

        // Send telemetry message to signify robot running;
        telemetry.addData("Say", "**** Joystick Data ****");
        telemetry.addData("left",  "%.2f", x);
        telemetry.addData("right", "%.2f", y);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        telemetry.addData("Say", "Stopped.");
    }

}
