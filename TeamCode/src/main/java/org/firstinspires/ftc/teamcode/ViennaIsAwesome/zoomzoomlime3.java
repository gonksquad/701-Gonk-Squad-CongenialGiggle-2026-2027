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

import com.pedropathing.paths.Path;

import java.util.function.BooleanSupplier;

@Autonomous
public class zoomzoomlime3 extends OpMode {
    hardware Hardware = new hardware(hardwareMap);

    private Follower follower;
    private double a = 45; //angle of servo from vertial (0)
    private double b; //angle of ty
    private double d = 3; //distance to center of robot
    private double x; //angle of tx
    private double distance = Math.tan(90-a-b)+d;
    private LLResult result;
    private final PoseFactory p = PoseFactory.degrees();
    // our poses (x,y,heading)
    private final Pose startPose = p.of(0, 0, 0);
    private final Pose midway = p.of(0, 0, 180);

    public zoomzoomlime3() {
        b = result.getTy();
    }

    private Path spin() {
        return line(startPose, midway).linear(startPose, midway);
    }

    public Path buildPollenPath() {
        Pose currentPose = follower.pose();

        Pose targetPose = new Pose(
                currentPose.x(), currentPose.y() + distance, result.getTx()
        );

        return Paths.line(currentPose, targetPose).linear(currentPose, targetPose);
    }
    /*for heading could also do currentPose.getHeading() if its pointing in the right
    direction wien spinLook command stops*/
    private Command autoRoutine() {
        return sequential(
                servoPos,
                seePollen,
                spinLook,
                follow(follower, buildPollenPath())
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

   /*need to spin in a circle until a pollen is spotted within a certain area on the field
   1. when looking:
        get robot position on the field (x,y, facing),
        get position of closest pollen relative to robot,
        make sure that it is within the correct side of the field,
        get position of second closest,
        repeat check etc times,
        if nothing is found,
        spin idk how much (camera is 12 inches off the ground probably, horizontal FOV is 54.5)
    */

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
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

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.addData("distance", distance);
        telemetry.update();

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
    }
}
