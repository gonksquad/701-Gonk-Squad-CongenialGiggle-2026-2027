// used to be TeleopFromHardware
package org.firstinspires.ftc.teamcode.ScrimmageSturf;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class ScrimmageTele extends LinearOpMode {
    public double ctrlX, ctrlY, ctrlYaw;

    private Servo blocker;
    private DcMotor intakeFront, intakeBack;
    private DcMotor shoot;
    private DcMotor frontRight, frontLeft, backRight, backLeft;

    @Override
    public void runOpMode() throws InterruptedException {



        blocker = hardwareMap.get(Servo.class, "blocker");
        intakeFront = hardwareMap.get(DcMotor.class, "front");
        intakeBack = hardwareMap.get(DcMotor.class, "back");
        shoot = hardwareMap.get(DcMotor.class, "shoot");

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            //frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight = hardwareMap.get(DcMotor.class, "backRight");
            backRight.setDirection(DcMotorSimple.Direction.REVERSE);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
        while (opModeIsActive()) {
            ctrlX = gamepad1.left_stick_x;
            ctrlY = gamepad1.left_stick_y;
            ctrlYaw = gamepad1.right_stick_x;

            doDrive(ctrlX, ctrlY, ctrlYaw);
            outtake();

            telemetry.update();
        }
    }

    /*public void intake() {
        boolToIntFront = Boolean.compare(gamepad1.left_trigger > 0.01, true)+1;
        boolToIntBack= Boolean.compare(gamepad1.right_trigger > 0.01, true)+1;

        telemetry.addData("front intake", intakeFront);
        telemetry.addData("back intake", boolToIntBack);
    }*/

    public void outtake() {
        int boolToIntFront = Boolean.compare(gamepad1.left_trigger > 0.01, true)+1;
        int boolToIntBack= Boolean.compare(gamepad1.right_trigger > 0.01, true)+1;
        int boolToInt = Boolean.compare(gamepad1.a, true)+1; // 1 if true else 0

        if(boolToInt != 0) {
            intakeFront.setPower(boolToInt);
            intakeBack.setPower(boolToInt);
        } else {
            intakeFront.setPower(boolToIntFront);
            intakeBack.setPower(boolToIntBack);
        }

        telemetry.addData("front intake", intakeFront);
        telemetry.addData("back intake", boolToIntBack);

        blocker.setPosition(0.8 * boolToInt); // 0.8 if true else 0
        shoot.setPower(-boolToInt); // -1 if true else 0

        telemetry.addData("outtake", boolToInt);

    }

    public void doDrive(double ctrlX, double ctrlY, double ctrlYaw) {
        if (Math.abs(ctrlY) < 0.1) {
            ctrlY = 0;
        }
        if (Math.abs(ctrlX) < 0.1) {
            ctrlX = 0;
        }
        if (Math.abs(ctrlYaw) < 0.1) {
            ctrlYaw = 0;
        } else if (Math.abs(ctrlYaw) < 0.5) {
            ctrlYaw = Math.signum(ctrlYaw) * 0.35;
        }

        double flPwr = ctrlY - ctrlYaw - ctrlX;
        double frPwr = ctrlY + ctrlYaw + ctrlX;
        double blPwr = ctrlY - ctrlYaw + ctrlX;
        double brPwr = ctrlY + ctrlYaw - ctrlX;

        double denominator = Math.max(Math.max(Math.max(flPwr, frPwr), Math.max(blPwr, brPwr)), 1);

        frontLeft.setPower(flPwr / denominator * 0.5);
        frontRight.setPower(frPwr / denominator * 0.5);
        backLeft.setPower(blPwr / denominator * 0.5);
        backRight.setPower(brPwr / denominator * 0.5);
    }
}