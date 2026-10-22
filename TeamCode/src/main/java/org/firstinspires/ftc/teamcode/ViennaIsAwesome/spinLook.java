//package org.firstinspires.ftc.teamcode.ViennaIsAwesome;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
//
//import com.pedropathing.controllers.PIDController;
//import com.pedropathing.ivy.Command;
//import com.pedropathing.ivy.behaviors.*;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import java.util.Set;
//
//public class spinLook implements Command {
//    hardware Hardware = new hardware(hardwareMap);
//
//    private double a = 45; //angle of servo from vertial (0)
//    private double b; //angle of ty
//    private double d = 8; //distance to center of robot
//    private double x; //angle of tx
//    private final PIDController pid;
//    private final double target;
//
//    public spinLook(DcMotor armMotor, PIDController pid, double target) {
//        this.armMotor = armMotor;
//        this.pid = pid;
//        this.target = target;
//    }
//
//    @Override
//    public void start() {
//        double distance = Math.tan(90-a-b)+d;
//        Hardware.limeservo.setPosition(a/270);
//        telemetry.addData("distance", distance);
//    }
//
//    @Override
//    public void execute() {
//
//        telemetry.update();
//    }
//
//    @Override
//    public boolean done() {
//        return Math.abs(target - armMotor.getCurrentPosition()) < 10;
//    }
//
//    @Override
//    public void end(EndCondition endCondition) {
//        armMotor.setPower(0);
//    }
//
//    @Override
//    public Set<Object> requirements() {
//        return Set.of(armMotor);
//    }
//
//    @Override
//    public int priority() {
//        return 0;
//    }
//
//    @Override
//    public InterruptedBehavior interruptedBehavior() {
//        return InterruptedBehavior.END;
//    }
//
//    @Override
//    public BlockedBehavior blockedBehavior() {
//        return BlockedBehavior.CANCEL;
//    }
//
//    @Override
//    public ConflictBehavior conflictBehavior() {
//        return ConflictBehavior.OVERRIDE;
//    }
//}