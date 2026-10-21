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

import com.pedropathing.paths.Path;

public class zoomzoomlime3 extends OpMode {
    hardware Hardware = new hardware(hardwareMap);

    private Follower follower;
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
                // Add mechanism commands here.
                follow(follower, park())
        );
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
    Command spinLook = infinite(() -> intake.setPower(1.0))
            .until(seePollen != null || time > 20);

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
