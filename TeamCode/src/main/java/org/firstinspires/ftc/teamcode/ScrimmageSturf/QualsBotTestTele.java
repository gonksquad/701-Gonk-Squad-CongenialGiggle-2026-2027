package org.firstinspires.ftc.teamcode.ScrimmageSturf;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class QualsBotTestTele extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        QualsHardware hw = new QualsHardware(hardwareMap);
        waitForStart();

        // drive el robo
        while (opModeIsActive()) {
            float ctrlX = gamepad1.left_stick_x;
            float ctrlY = gamepad1.left_stick_y;
            float ctrlYaw = gamepad1.right_stick_x;
            hw.runDrive(ctrlX, ctrlY, ctrlYaw);

            boolean intakeBtn = gamepad1.x;
            boolean flushBtn = gamepad1.b;
            float intakePower = 1;
            hw.runIntake(intakeBtn, flushBtn, intakePower);

            telemetry.update();
        }

    }
}
