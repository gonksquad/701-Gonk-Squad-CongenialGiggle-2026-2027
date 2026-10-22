//package org.firstinspires.ftc.teamcode.ViennaIsAwesome;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.math.Pose;
//import com.pedropathing.paths.curves.bezier.BezierCurve;
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.hardware.limelightvision.LLResultTypes;
//
//import org.firstinspires.ftc.robotcontroller.external.samples.RobotTeleopMecanumFieldRelativeDrive;
//import org.firstinspires.ftc.teamcode.PedroPathing.Constants;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Disabled
//public class zoomzoomlime2 extends LinearOpMode {
//    public Follower follower; // Pedro Pathing follower instance
//    private int pathState; // Current autonomous path state (state machine)
//    private Paths paths; // Paths defined in the Paths class
//    private ElapsedTime pathTimer;
//    private ElapsedTime launchTimer;
//    private byte sorterPos;
//    private LLResult result;
//    private ArrayList<String> log;
//    boolean loop = true;
//    boolean pollen = false;
//    private double currentX;
//    private double currentY;
//    private double currentHeading;
//    private double targetX;
//    private double targetY;
//    private double targetHeading;
//
//
////    pathState currentPathState;
////    public enum pathState {
////        0,
////        1,
////        2,
////        3
////    }
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        hardware Hardware = new hardware(hardwareMap);
//
//        Hardware.limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
//        Hardware.limelight.start();
//        Hardware.limelight.pipelineSwitch(0);
//
//        log = new ArrayList<String>();
//
//        log.add("Log:");
//
//        pathTimer = new ElapsedTime();
//        launchTimer = new ElapsedTime();
//
//        follower = Constants.createFollower(hardwareMap);
//        follower.setStartingPose(new Pose(0, 0, Math.toRadians(0)));
//
//        paths = new Paths(follower); // Build paths
//
//
//        waitForStart();
//        while(loop) {
//            LLResult result = Hardware.limelight.getLatestResult();
//            if (result != null && result.isValid()) {
//                pollen = true;
//                double tx = result.getTx(); // How far left or right the target is (degrees)
//                double ty = result.getTy(); // How far up or down the target is (degrees)
//                double ta = result.getTa(); // How big the target looks (0%-100% of the image)
//
//                telemetry.addData("Target X", tx);
//                telemetry.addData("Target Y", ty);
//                telemetry.addData("Target Area", ta);
//                telemetry.update();
//            }
//            else {
//                pollen = false;
//                telemetry.addData("Limelight", "No Targets");
//            }
//        }
//        while (opModeIsActive()) {
//            follower.update(); // Update Pedro Pathing
//            autonomousPathUpdate(); // Update autonomous state machine
//
//
//        }
//    }
//
//
//    public static class Paths {
//        public PathChain moveforward;
//
//        public Paths(Follower follower) {
//            moveforward = follower.pathBuilder().addPath(
//                            new BezierLine(
//                                    new Pose(0, 0),
//
//                                    new Pose(12, 12)
//                            )
//                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
//
//                    .build();
//        }
//    }
//
//    public void autonomousPathUpdate() {
//        if (follower.isBusy()) return;
//        switch (pathState) {
//            case 0:
//                Hardware.limeServoPos();
//                if (pollen = true) {
//
//                    currentX = follower.pose().x();
//                    currentY = follower.pose().y();
//                    currentHeading = follower.pose().heading();
//                    targetX = ;
//                    targetY = ;
//                    targetHeading = ;
//                    setPathState(1);
//                }
//                else if (pollen = false) {
//                    setPathState(2);
//                }
//                break;
//            case 2:
//                if (pollen = true) {
//
//                }
//                else if (pollen = false) {
//
//                }
//                break;
//            case 3:
//                if (pathTimer.milliseconds() < 1000) break;
//                follower.followPath(paths.moveforward, true);
//                break;
//            default:
//                requestOpModeStop();
//                break;
//        }
//    }
//
//    public void setPathState(int pState) {
//        pathState = pState;
//        pathTimer.reset();
//    }
//}
//
