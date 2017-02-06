/* Team Fractals Autonomous Program if on Team Red, beacon strategy.
 * Based on program by Robert Atkinson (2016)
 * Note: front is beacon pusher.
 * Negative power to the motors moves the robot
 * forward.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robocol.PeerApp;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red Team Beacon Strategy", group="Autonomous")
public class RedBeaconStrategy extends LinearOpMode {
    Hardware robot = new Hardware(); // Initialize hardware.
    private ElapsedTime runtime = new ElapsedTime(); // Figure out how long the robot has been running.
    private AutoDrive drive;
    private SensorArray sensors;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, true); // Initialize hardware with encoders.
        sensors = new SensorArray(this);
        drive = new AutoDrive(this, robot, sensors);
        telemetry.addData("Message", "All systems online.");

        waitForStart(); // Wait until ready.

        // *** Main Code *** //
        drive.forward(52);
        drive.turn(-93);
        drive.pressBeacon();
        if (sensors.getBeaconColor() == 'b') {
            drive.tap();
            sleep(5000);
            drive.backward(3);
            drive.tap();
        }
        else {
            drive.tap();
        }
        drive.backward(3 + 25);
        /*
        drive.turn(92);
        drive.forward(Constants.DISTANCE_BETWEEN_BEACONS);
        drive.turn(-94);
        drive.forward(10);
        if (sensors.getBeaconColor() == 'b') {
            drive.tap();
            sleep(5000);
            drive.backward(3);
            drive.tap();
        }
        else {
            drive.tap();
        }
        drive.backward(4);
        //sleep(3000);
        */
        // *** Main Code Done *** //
        telemetry.addData("Robot", "Done...");
        telemetry.update();
    }
}
