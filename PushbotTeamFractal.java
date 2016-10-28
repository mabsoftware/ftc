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
public class PushbotTeamFractal extends OpMode{

    /* Declare OpMode members. */
    Hardware robot;
    double[] controls;


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

        /* 0 : Left joystick up / down value.
         * 1 : Right joystick up / down value.
         */
        controls = new double[2]; // Holds all robot control data to be processed.

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

        controls[0] = gamepad1.left_stick_y;
        controls[1] = gamepad1.right_stick_y; // The robot drives "backwards," so don't negate the joystick values.
        robot.leftMotor.setPower(controls[0]);
        robot.rightMotor.setPower(controls[1]);

        // Send telemetry message to signify robot running;
        telemetry.addData("Say", "**** Joystick Data ****");
        telemetry.addData("left",  "%.2f", controls[0]);
        telemetry.addData("right", "%.2f", controls[1]);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        telemetry.addData("Say", "Stopped.");
    }

}
