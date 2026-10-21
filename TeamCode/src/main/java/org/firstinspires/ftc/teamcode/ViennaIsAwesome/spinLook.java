package org.firstinspires.ftc.teamcode.ViennaIsAwesome;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.controllers.PIDController;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Set;

public class spinLook implements Command {
    hardware Hardware = new hardware(hardwareMap);

    private final DcMotor armMotor;
    private final PIDController pid;
    private final double target;

    public spinLook(DcMotor armMotor, PIDController pid, double target) {
        this.armMotor = armMotor;
        this.pid = pid;
        this.target = target;
    }

    @Override
    public void start() {
        pid.setTarget(target);
    }

    @Override
    public void execute() {
        armMotor.setPower(pid.calculate(armMotor.getCurrentPosition()));
    }

    @Override
    public boolean done() {
        return Math.abs(target - armMotor.getCurrentPosition()) < 10;
    }

    @Override
    public void end(EndCondition endCondition) {
        armMotor.setPower(0);
    }

    @Override
    public Set<Object> requirements() {
        return Set.of(armMotor);
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public InterruptedBehavior interruptedBehavior() {
        return InterruptedBehavior.END;
    }

    @Override
    public BlockedBehavior blockedBehavior() {
        return BlockedBehavior.CANCEL;
    }

    @Override
    public ConflictBehavior conflictBehavior() {
        return ConflictBehavior.OVERRIDE;
    }
}