package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@Autonomous(name = "Optical Test OpMode", group = "Autonomous")
public class OpticalDistanceOpMode extends LinearOpMode {
    Hardware robot = new Hardware(); // Initialize hardware.
    private ElapsedTime runtime = new ElapsedTime(); // Figure out how long the robot has been running.
    private AutoDrive drive;
    private SensorArray sensors;
    // Declare sensor variables.

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, true);
        drive = new AutoDrive(this, robot, sensors);
        sensors = new SensorArray(this);
        telemetry.addData("Message", "All systems online.");

        waitForStart(); // Wait until ready.

        // *** Boiler Plate Code Done *** //
        // *** Main Code Done *** //
        while (true) {
            telemetry.addData("Red", sensors.r());
            telemetry.addData("Green", sensors.g());
            telemetry.addData("Blue", sensors.b());
            telemetry.update();
        }
    }
}