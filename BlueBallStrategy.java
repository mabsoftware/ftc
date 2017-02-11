/*********************************************************************************************************
 * Blue Team Ball Strategy                                                                               *
 * Summary: Drives straight the diagonal length of the field to hit the cap ball.                        *
 * Version: 2/09/17                                                                                      *
 *********************************************************************************************************/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Blue Team Ball Strategy", group = "Autonomous")
public class BlueBallStrategy extends LinearOpMode {
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
        sleep(10000); // maintain compatability
        drive.fastBackward(5.0 * 12);
        drive.brake();
        drive.shoot();
        drive.fastBackward(5);
        drive.brake();
        // *** Main Code Done *** //
        telemetry.addData("Robot", "Stopped.");
        telemetry.update();
    }
}
