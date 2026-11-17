package org.firstinspires.ftc.teamcode.ScrimmageSturf;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class QualsHardware{

    public DcMotor frontRight, frontLeft, backRight, backLeft;
    public DcMotor intakeFront, intakeBack;

    public QualsHardware(HardwareMap hardwareMap) {

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

        intakeFront = hardwareMap.get(DcMotor.class, "intakeFront");
            intakeFront.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeBack = hardwareMap.get(DcMotor.class, "intakeBack");
            intakeBack.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public void runDrive(double ctrlX, double ctrlY, double ctrlYaw) {
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

        frontLeft.setPower(flPwr / denominator);// * 0.5);
        frontRight.setPower(frPwr / denominator);// * 0.5);
        backLeft.setPower(blPwr / denominator);// * 0.5);
        backRight.setPower(brPwr / denominator);// * 0.5);
    }

    public void runIntake(boolean intakeBtn, boolean flushBtn, double power) {
        if(intakeBtn) {
            power = Math.max(-1, Math.min(power, 1)); // clamp between -1 and 1

            intakeFront.setPower(power);
            intakeBack.setPower(power);
        } else if (flushBtn){
            intakeFront.setPower(-power);
            intakeBack.setPower(-power);
        } else {
            intakeFront.setPower(0);
            intakeBack.setPower(0);
        }
    }
}
