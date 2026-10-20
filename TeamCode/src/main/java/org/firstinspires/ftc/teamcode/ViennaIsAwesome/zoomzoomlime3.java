package org.firstinspires.ftc.teamcode.ViennaIsAwesome;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.PedroPathing.Constants;
import static com.pedropathing.api.Paths.*;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.infinite;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.pedropathing.paths.Path;

import java.util.function.BooleanSupplier;

public class zoomzoomlime3 extends OpMode {
    hardware Hardware = new hardware(hardwareMap);

    private Follower follower;
    private double a = 45; //angle of servo from vertial (0)
    private double b; //angle of ty
    private double d = 8; //distance to center of robot
    private double x; //angle of tx
    private double distance;
    private BooleanSupplier pollen;
    private final PoseFactory p = PoseFactory.degrees();
    // our poses (x,y,heading)
    private final Pose startPose = p.of(24, 24, 0);
    private final Pose controlPose = p.of(36, 60, 45);
    private final Pose park = p.of(48, 48, 90);
    private Path park() {

        return line(startPose, park).linear(startPose, park);
    }

    private Command autoRoutine() {
        return sequential(
                servoPos(),
                follow(follower, park())

        );
    }

    Command servoPos = Hardware.limeservo.setPosition(a/270);
    public void spinLook() {
        LLResult result = Hardware.limelight.getLatestResult();
        if (result != null && result.isValid()) {
            pollen = true;
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);
            telemetry.update();
        }
        else {
            pollen = false;
            telemetry.addData("Limelight", "No Targets");
        }

        distance = Math.tan(90-a-b)+d;
    }
    public double time = 1;
    public LLResult seePollen;
    Command getBallColor = infinite(() -> seePollen = Hardware.limelight.getLatestResult());
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
    Command spinLook = infinite(() -> Hardware.limeservo.setPosition(1.0))
            .until(seePollen.get != null || time > 20);

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

        if (seePollen != null && seePollen.isValid()) {
            double tx = seePollen.getTx(); // How far left or right the target is (degrees)
            double ty = seePollen.getTy(); // How far up or down the target is (degrees)
            double ta = seePollen.getTa(); // How big the target looks (0%-100% of the image)

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
