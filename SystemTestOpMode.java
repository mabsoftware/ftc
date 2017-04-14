package org.firstinspires.ftc.teamcode;
/**
 * Test class: Gyro and Elapsed time Testing
 * A sandbox OpMode to test new systems
 * Makes it easier to create multiple autonomous strategies.
 * @author Max Bowman
 * @version 4/13/17
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "System Test OpMode", group = "Autonomous")
public class SystemTestOpMode extends LinearOpMode {
    Hardware robot = new Hardware(); // Initialize hardware.
    private ElapsedTime runtime;
    private AutoDrive drive;
    private SensorArray sensors;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();
        robot.init(hardwareMap, true);
        sensors = new SensorArray(this);
        drive   = new AutoDrive(this, robot, sensors);
        drive.resetEncoders();
        telemetry.addData("Status", "All systems online.");
        telemetry.update();

        waitForStart(); // Wait until ready.
        runtime = new ElapsedTime(); // Start the timer

        while (opModeIsActive()) {
            telemetry.addData("Gyro", sensors.getHeading());
            telemetry.addData("Time", runtime.seconds());
            telemetry.update();
        }

        /*drive.move(10);
        drive.turn(90);
        drive.turn(-90);
        drive.turn(0);
        drive.turn(359);
        drive.turn(1);*/
    }
}