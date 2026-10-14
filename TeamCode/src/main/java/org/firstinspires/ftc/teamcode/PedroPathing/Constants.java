package org.firstinspires.ftc.teamcode.PedroPathing;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeft");
        c.frontRightName.set("frontRight");
        c.backLeftName.set("backLeft");
        c.backRightName.set("backRight");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-1.6253735699991543);
        c.yPodOffset.set(-0.13002457581167146);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.32261367044563904);
                Controller secondaryTranslationalForward = Controller.proportional(0.11919713083362521);
                Controller primaryTranslationalLateral = Controller.proportional(0.39817124603531945);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1471136360784091);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.012211016410644678));
                c.brake.set(Controller.proportionalFeedforward(0.010379363949047976));

                c.headingFeedback.set(Controller.proportional(3.779202111902697));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.045510972523644985, 0.00452111173661166));

                c.linearBrakeCoefficients.set(Matrix.diag(0.08909164737302822, 0.050554471332605766));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001404030978618182, 0.0019916671469081253));

                c.maxAchievableForwardVelocity.set(78.72808684991875);
                c.maxAchievableStrafeVelocity.set(63.304437775993506);
                c.naturalForwardDeceleration.set(32.54390951453557);
                c.naturalStrafeDeceleration.set(66.36365926303783);
            }


    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}