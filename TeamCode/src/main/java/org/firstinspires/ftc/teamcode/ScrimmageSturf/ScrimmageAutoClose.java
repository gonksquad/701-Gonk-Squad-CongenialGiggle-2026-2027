package org.firstinspires.ftc.teamcode.ScrimmageSturf;

import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.interpolator.Interpolator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.PedroPathing.Constants;
@Autonomous
public class ScrimmageAutoClose extends OpMode {

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(58.4101, 23.3551, 90);
    private final Pose path1 = poseFactory.of(56, 94, 35.407);
    private final Pose path1Control1 = poseFactory.of(95, 48, 0);
    private final Pose path1Control2 = poseFactory.of(24, 72, 0);
    private final Pose point2 = poseFactory.of(58, 23.5, 33.5356);
    private final Pose point2Control1 = poseFactory.of(24.2067, 72.373, 0);
    private final Pose point2Control2 = poseFactory.of(95.7011, 47.7652, 0);


    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(start);
    }

    @Override
    public void start() {
        schedule(follow(follower, path1()));
        schedule(follow(follower, path2()));

    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }

    public Path path1() {
        return Paths.curve(start, path1Control1, path1Control2, path1).tangent();
    }

    public Path path2() {
        return Paths.curve(path1, point2Control1, point2Control2, point2).reverseTangent();
    }
}