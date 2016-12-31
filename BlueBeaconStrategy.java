/* Beacon Strategy on Blue Side
 * Note: front is beacon pusher.
 * Negative power to the motors drives the robot forward, beacon pusher first.
 * So for autonomous, with ball shooting, make sure to remember this.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

        drive.forward(52);
        sleep(250);
        drive.turn(90); // turn right 90 degrees
        sleep(250);
        drive.pressBeacon();
        drive.backward(8);
        drive.turn(-90); // turn left 90 degrees
        sleep(250);
        drive.forward(Constants.DISTANCE_BETWEEN_BEACONS);
        drive.turn(90);
        drive.pressBeacon();
        drive.backward(8);
        drive.turn(-90);
        // *** Main Code Done *** //
        telemetry.addData("Robot", "Stopped.");
        telemetry.update();
    }
}