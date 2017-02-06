/*********************************************************************************************************
 * Blue Team Beacon Strategy                                                                             *
 * Summary: Drives until white line is detected. Drives two more inches, turns until white line is       *
 * detected. Turns just past white line. Detects color. Presses appropriate number of times.             *
 * Version: 2/05/17                                                                                      *
 *********************************************************************************************************/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Blue Team Beacon Strategy", group = "Autonomous")
public class BlueBeaconStrategy extends LinearOpMode {
    Hardware robot = new Hardware(); // Initialize hardware.
    private ElapsedTime runtime = new ElapsedTime(); // Figure out how long the robot has been running.
    private AutoDrive drive;
    private SensorArray sensors;
    // Declare sensor variables.

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, true);
        sensors = new SensorArray(this);
        drive = new AutoDrive(this, robot, sensors);
        telemetry.addData("Message", "All systems online.");

        waitForStart(); // Wait until ready.

        // *** Boiler Plate Code Done *** //
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftMotor.setPower(0.1);
        robot.rightMotor.setPower(0.1);
        while (!sensors.overLine());
        drive.brake();
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        drive.forward(2);
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftMotor.setPower(0.1);
        while (!sensors.overLine());
        robot.leftMotor.setPower(0);
        robot.leftMotor.setPower(0.1);
        while (sensors.overLine());
        robot.leftMotor.setPower(0);
        robot.leftMotor.setPower(0.1);
        robot.rightMotor.setPower(0.1);
        while (!(sensors.r() >= Constants.red_threshold || sensors.b() >= Constants.blue_threshold));
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (sensors.getBeaconColor() == 'r') {
            drive.backward(3);
            drive.forward(3);
        }
        drive.backward(3);

        // *** Main Code Done *** //
        telemetry.addData("Robot", "Stopped.");
        telemetry.update();
    }
}