
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic teleoperation", group="Linear Opmode")
public class bruh extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lf = null;
    private DcMotor rf = null;
    private DcMotor lb = null;
    private DcMotor rb = null;
    DcMotor arm;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        lf  = hardwareMap.get(DcMotor.class, "left_drive_front");
        rf = hardwareMap.get(DcMotor.class, "right_drive_front");
        lb  = hardwareMap.get(DcMotor.class, "left_drive_back");
        rb = hardwareMap.get(DcMotor.class, "right_drive_back");
        arm  = hardwareMap.get(DcMotor.class, "arm");
        //reset motor encoders to zero
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        //leftDrive.setDirection(DcMotor.Direction.FORWARD);
        //rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
//            double drive = -gamepad1.left_stick_y;
//            double turn  =  gamepad1.right_stick_x;
//            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
//            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            leftPower  = gamepad1.left_stick_y ;
            rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            lf.setPower(leftPower);
            lb.setPower(leftPower);
            rb.setPower(rightPower);
            rf.setPower(rightPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();

            if (gamepad1.y) {
                moveArm(500, 0.3);
            }else if(gamepad1.x){
                moveArm(0,0.5);
            }
            else {
                arm.setPower(0);
            }

        }
    }

    public void moveArm(int target,double power) {

        //set encoder target to 1000 ticks
        arm.setTargetPosition(target);
        //setup the run mode so that the motor goes to target
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //set power to the motor
        //this sets the maximum power the motor will run at but is not constant
        arm.setPower(power);
        while (arm.isBusy()) {
            //wait until encoder reaches target ticks
        }
        arm.setPower(0);
    }
}