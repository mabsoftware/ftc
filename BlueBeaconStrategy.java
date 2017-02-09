/*********************************************************************************************************
 * Blue Team Beacon Strategy                                                                             *
 * Summary: Drives until white line is detected. Drives two more inches, turns until white line is       *
 * detected. Turns just past white line. Detects color. Presses appropriate number of times.             *
 * Version: 2/09/17                                                                                      *
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
        drive.setToRun(0.1, 0.1);
        while (!sensors.overLine());
        // drive to line
        drive.brake();
        drive.forward(2);
        // Line up
        drive.setToRun(0.1, 0);
        while (!sensors.overLine());
        drive.brake();
        drive.setToRun(0.1, 0);
        while (sensors.overLine());
        // lined up
        drive.brake();
        drive.setToRun(0.1, 0.1);
        while (!(sensors.r() >= Constants.red_threshold || sensors.b() >= Constants.blue_threshold)); // drive forward while we don't have enough light.
        if (sensors.getBeaconColor() == 'r') {
            drive.backward(3);
            sleep(5000);
            drive.forward(3);
        }
        drive.backward(3);

        // *** Main Code Done *** //
        telemetry.addData("Robot", "Stopped.");
        telemetry.update();
    }
}