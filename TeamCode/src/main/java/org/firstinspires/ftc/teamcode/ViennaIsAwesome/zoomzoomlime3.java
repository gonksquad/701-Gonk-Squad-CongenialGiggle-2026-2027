/*
package org.firstinspires.ftc.teamcode.ViennaIsAwesome;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.PedroPathing.Constants;
import static com.pedropathing.api.Paths.*;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.infinite;
import static com.pedropathing.ivy.commands.Commands.waitUntil;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

import com.pedropathing.paths.Path;

import java.util.function.BooleanSupplier;

@Autonomous
public class zoomzoomlime3 extends OpMode {
    hardware Hardware = new hardware(hardwareMap);

    private Hardware hardware;
    private Follower follower;
    private double a = 45; //angle of servo from vertial (0)
    private double a = 30; //angle of servo from vertial (0)
    private double b; //angle of ty
    private double d = 3; //distance to center of robot
    private double d = 4.75; //distance to center of robot
    private double x; //angle of tx
    private double distance = Math.tan(90-a-b)+d;
    private double h = 14.25; //height of limelight
    private double distance;
    private LLResult result;
    private final PoseFactory p = PoseFactory.degrees();
    // our poses (x,y,heading)
    private final Pose startPose = p.of(0, 0, 0);
    private final Pose midway = p.of(0, 0, 180);

    public zoomzoomlime3() {
        b = result.getTy();
    private final Pose midway = p.of(1, 1, 180);

    private Command seePollen;
    private Command limeServoSet;
    private Command waitToSpotPollen;
    private Command spinLook;

    private double calculateDistance() {
        if (result != null) {
            b = result.getTy();
            distance = (h*Math.tan(Math.toRadians(90 - a - b))) + d;
            return distance;
            //a = angle of servo, b = angle of ty, d = distance to center of robot
        }
        return 0;
    }

    private Path spin() {
        return line(startPose, midway).linear(startPose, midway);
    }

    // Keep a flag to check if moving is actually necessary
    private boolean needsToMove = true;

    public Path buildPollenPath() {
        Pose currentPose = follower.pose();
        if (currentPose == null) {
            currentPose = startPose;
        }
        double currentDistance = calculateDistance();
        double targetTx = (result != null) ? result.getTx() : currentPose.heading();

        Pose targetPose = new Pose(
                currentPose.x(), currentPose.y() + distance, result.getTx()
                currentPose.x(), currentPose.y() + currentDistance, targetTx
        );

        double deltaX = targetPose.x() - currentPose.x();
        double deltaY = targetPose.y() - currentPose.y();
        double pathLength = Math.hypot(deltaX, deltaY);

        if (pathLength < 0.5) {
            needsToMove = false;
            Pose lilMove = new Pose(currentPose.x(), currentPose.y() + 0.1, currentPose.heading());
            return Paths.line(currentPose, lilMove).linear(currentPose, lilMove);
        }

        needsToMove = true;
        return Paths.line(currentPose, targetPose).linear(currentPose, targetPose);
    }
*/
/*for heading could also do currentPose.getHeading() if its pointing in the right
    direction wien spinLook command stops*//*


    private Command autoRoutine() {
        return sequential(
                servoPos,
        return parallel(
                limeServoSet,
                seePollen,
                spinLook,
                follow(follower, buildPollenPath())
                linearAuto()
        );
    }

    Command seePollen = infinite(() -> result = Hardware.limelight.getLatestResult());
    Command servoPos = Command.build()
            .setStart(() -> Hardware.limeservo.setPosition(a/270.0));
    Command waitToSpotPollen = waitUntil(() -> result.isValid());
    Command spinLook = race(
            waitToSpotPollen,
            follow(follower, spin())
    );
    private Command linearAuto() {
        return sequential(
               // spinLook,

                Command.build().setStart(() -> {
                            Path dynamicPath = buildPollenPath();
                            if (needsToMove) {
                                Scheduler.schedule(follow(follower, dynamicPath));
                            }

                        }
                ));
    }

*/
/*
need to spin in a circle until a pollen is spotted within a certain area on the field
   1. when looking:
        get robot position on the field (x,y, facing),
        get position of closest pollen relative to robot,
        make sure that it is within the correct side of the field,
        get position of second closest,
        repeat check etc times,
        if nothing is found,
        spin idk how much (camera is 12 inches off the ground probably, horizontal FOV is 54.5)
*//*



    @Override
    public void init() {
        hardware = new Hardware(hardwareMap);

        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();

        limeServoSet = Command.build()
                .setStart(() -> hardware.limeservo.setPosition(.4));
        seePollen = infinite(() -> result = hardware.limelight.getLatestResult());
        waitToSpotPollen = waitUntil(() -> result != null && result.isValid());
        spinLook = race(
                waitToSpotPollen,
                follow(follower, spin())
        );

        hardware.limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        hardware.limelight.start();
        hardware.limelight.pipelineSwitch(0);
    }

    @Override
    public void start() {
        schedule(autoRoutine());
        // using the same park path from the last page
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

        distance = calculateDistance();

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.addData("distance", distance);
        telemetry.update();
        telemetry.addData("a", a);
        telemetry.addData("b", b);
        telemetry.addData("d", d);
        telemetry.addData("h", h);
        telemetry.addData("limeservopos", hardware.limeservo.getPosition());


        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);

            telemetry.addData("Target Area", ta);
            telemetry.update();
        }
        else {
            telemetry.addData("Limelight", "No Targets");
        }
        telemetry.update();

    }

}
*/
