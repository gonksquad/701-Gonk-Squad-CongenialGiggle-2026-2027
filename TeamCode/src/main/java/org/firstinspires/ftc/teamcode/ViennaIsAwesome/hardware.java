package org.firstinspires.ftc. teamcode.ViennaIsAwesome;

import android.annotation.SuppressLint;

import com.qualcomm.hardware.limelightvision. Limelight3A;
import com. qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore. hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com. qualcomm.robotcore.hardware.Servo;
import com. qualcomm.robotcore.util.ElapsedTime;


public class hardware {
    private HardwareMap hwMap;
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public Limelight3A limelight;

    public hardware(HardwareMap hardwareMap) {
        this.hwMap = hardwareMap;
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void doDrive(double speedX, double speedY, double speedYaw) {
        double pwrY = speedY;
        double pwrX = speedX;
        double pwrYaw = speedYaw;

        if (Math.abs(pwrY) < 0.1) {
            pwrY = 0;
        }
        if (Math.abs(pwrX) < 0.1) {
            pwrX = 0;
        }
        if (Math.abs(pwrYaw) < 0.1) {
            pwrYaw = 0;
        } else if (Math.abs(pwrYaw) < 0.5) {
            pwrYaw = Math.signum(pwrYaw) * 0.5;
        }

        double flPwr = pwrY - pwrYaw - pwrX;
        double frPwr = pwrY + pwrYaw + pwrX;
        double blPwr = pwrY - pwrYaw + pwrX;
        double brPwr = pwrY + pwrYaw - pwrX;

        double denominator = Math.max(Math.max(Math.max(flPwr, frPwr), Math.max(blPwr, brPwr)), 1);

        frontLeft.setPower(flPwr / denominator);
        frontRight.setPower(frPwr / denominator);
        backLeft.setPower(blPwr / denominator);
        backRight.setPower(brPwr / denominator);
    }
}