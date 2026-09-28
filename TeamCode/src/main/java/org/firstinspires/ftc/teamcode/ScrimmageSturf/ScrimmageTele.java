// used to be TeleopFromHardware
package org.firstinspires.ftc.teamcode.ScrimmageSturf;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        waitForStart();
        while (opModeIsActive()) {
            ctrlX = gamepad1.left_stick_x;
            ctrlY = gamepad1.left_stick_y;
            ctrlYaw = gamepad1.right_stick_x;

            doDrive(ctrlX, ctrlY, ctrlYaw);
            intake();
            outtake();

            telemetry.update();
        }
    }

    public void intake() {
        intakeFront.setPower(Boolean.compare(gamepad1.x, true)+1); // 1 if true else 0
        intakeBack.setPower(Boolean.compare(gamepad1.x, true)+1); // 1 if true else 0
    }

    public void outtake() {
        intakeFront.setPower(Boolean.compare(gamepad1.a, true)+1); // 1 if true else 0
        intakeBack.setPower(Boolean.compare(gamepad1.a, true)+1); // 1 if true else 0
        blocker.setPosition(0.8 * (1 + Boolean.compare(gamepad1.a, true))); // 0.8 if true else 0
        shoot.setPower(-Boolean.compare(gamepad1.a, false)); // -1 if true else 0
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

        frontLeft.setPower(flPwr / denominator);
        frontRight.setPower(-frPwr / denominator);
        backLeft.setPower(blPwr / denominator);
        backRight.setPower(-brPwr / denominator);
    }
}