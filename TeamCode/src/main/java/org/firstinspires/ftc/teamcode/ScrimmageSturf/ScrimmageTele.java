// used to be TeleopFromHardware
package org.firstinspires.ftc.teamcode.QualifierScripts;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class QualTele extends LinearOpMode {
    public double ctrlX, ctrlY, ctrlYaw;

    private Servo blocker;
    private DcMotor front;
    private DcMotor back;
    private DcMotor shoot;
    @Override
    public void runOpMode() throws InterruptedException {



        blocker = hardwareMap.get(Servo.class, "blocker");
        front = hardwareMap.get(DcMotor.class, "front");
        back = hardwareMap.get(DcMotor.class, "back");
        shoot = hardwareMap.get(DcMotor.class, "shoot");
        while (opModeIsActive()) {
            ctrlX = gamepad1.left_stick_x;
            ctrlY = gamepad1.left_stick_y;
            ctrlYaw = gamepad1.right_stick_x;

            doDrive();
            intake();
            outtake();

            telemetry.update();
        }
    }

    public void intake() {
        front.setPower(Boolean.compare(gamepad1.x, true));
        back.setPower(Boolean.compare(gamepad1.x, true));
    }

    public void outtake() {
        front.setPower(Boolean.compare(gamepad1.a, true));
        back.setPower(Boolean.compare(gamepad1.a, true));
        blocker.setPosition(0.8 * Boolean.compare(gamepad1.a, true));
        shoot.setPower(Boolean.compare(gamepad1.a, true))
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
            ctrlYaw = Math.signum(ctrlYaw) * 0.5;
        }

        double flPwr = ctrlY - ctrlYaw - ctrlX;
        double frPwr = ctrlY + ctrlYaw + ctrlX;
        double blPwr = ctrlY - ctrlYaw + ctrlX;
        double brPwr = ctrlY + ctrlYaw - ctrlX;

        double denominator = Math.max(Math.max(Math.max(flPwr, frPwr), Math.max(blPwr, brPwr)), 1);

        frontLeft.setPower(flPwr / denominator);
        frontRight.setPower(frPwr / denominator);
        backLeft.setPower(blPwr / denominator);
        backRight.setPower(brPwr / denominator);
    }
}