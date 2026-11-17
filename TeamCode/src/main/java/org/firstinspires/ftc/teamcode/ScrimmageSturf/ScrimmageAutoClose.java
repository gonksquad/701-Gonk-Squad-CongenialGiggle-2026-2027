package org.firstinspires.ftc.teamcode.ScrimmageSturf;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.*;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;


import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.PedroPathing.Constants;
@Autonomous
public class ScrimmageAutoClose extends OpMode {

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 180);
    private final Pose path1 = poseFactory.of(9, 8.01, 180);
    private final Pose point2Start = poseFactory.of(9, 8.01, 180);
    private final Pose point2 = poseFactory.of(54.4101, 124.0506, 0);
    private final Pose point2Control1 = poseFactory.of(34.5157, 40.4371, 0);
    private final Pose point2Control2 = poseFactory.of(24.5978, 105.3416, 0);


    private Servo blocker;
    private DcMotor intakeFront, intakeBack;
    private DcMotor shoot;

    public Path shoot1ToSpike() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path spikeToShoot2() {
        return Paths.curve(point2Start, point2Control1, point2Control2, point2).linear(point2Start, point2);
    }

    @Override
    public void init() {

        blocker = hardwareMap.get(Servo.class, "blocker");
        intakeFront = hardwareMap.get(DcMotor.class, "front");
        intakeBack = hardwareMap.get(DcMotor.class, "back");
        shoot = hardwareMap.get(DcMotor.class, "shoot");
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }


    private Command autoRoutine() {
        return sequential(
                //shoot first
                setIntakePower(1),
                setShootPower(0.95f),
                waitMs(1000),
                block(false),
                waitMs(3000),
                block(true),
                setShootPower(0),

                //grab from spike mark
                follow(follower, shoot1ToSpike()),
                waitMs(1000),
                setIntakePower(0),

                //move to shoot pos and shoot
                follow(follower, spikeToShoot2()),
                setShootPower(1),
                setIntakePower(1),
                waitMs(100),
                block(false),
                waitMs(3000),
                setIntakePower(0),
                setShootPower(0),
                block(true)


        );
    }

    private Command setIntakePower(float power) {
        return instant(() -> setIntakePowerVoid(power));
    }
    private void setIntakePowerVoid(float power) {
        intakeFront.setPower(power);
        intakeBack.setPower(power);
    }

    private Command block(boolean blocking) {
        return instant(() -> blockVoid(blocking));
    }
    private void blockVoid(boolean blocking) {
        blocker.setPosition(blocking ? 0 : 0.8);
    }

    private Command setShootPower(float power) {
        return instant(() -> setShootPowerVoid(power));
    }
    private void setShootPowerVoid(float power) {
        shoot.setPower(-power);
    }

}