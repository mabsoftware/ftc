package org.firstinspires.ftc.teamcode;
/**
 * Wrapper class: Hardware
 * Provides an easy-to-use wrapper that makes
 * referencing hardware objects easy.
 * Based heavily on Hardware.java written
 * by Robert Atkinson, 2016
 * @author Max Bowman
 * @version 4/12/17
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware
{
    /**
     *  Public OpMode members.
     */
    public DcMotor  leftMotor    = null;
    public DcMotor  rightMotor   = null;

    public boolean usingEncoders = false;

    /**
     * Local opmode members
     */
    private HardwareMap hwMap    =  null;
    private ElapsedTime period   =  new ElapsedTime();

    /**
     * Constructor for Hardware
     * (Empty)
     */
    public Hardware() {

    }

    /**
     * Initialize standard Hardware interfaces
     */
    public void init(HardwareMap ahwMap, boolean usingEncoders) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        this.usingEncoders = usingEncoders;

        // Define and Initialize Motors
        leftMotor  = hwMap.dcMotor.get(Constants.left_motor);
        rightMotor = hwMap.dcMotor.get(Constants.right_motor);

        // Textrix Motors
        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if we transfer to AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if we transfer to AndyMark motors

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if (usingEncoders) {
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

