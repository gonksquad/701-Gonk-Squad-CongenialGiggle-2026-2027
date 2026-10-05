package org.firstinspires.ftc.teamcode.ViennaIsAwesome;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.limelightvision.LLResult;

@Autonomous
public class zoomzoomlime extends LinearOpMode {

    @Override
    public void runOpMode() {
        hardware Hardware;
        Hardware = new hardware(hardwareMap);

        Hardware.limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        Hardware.limelight.start();
        Hardware.limelight.pipelineSwitch(0);

        waitForStart();


        while(opModeIsActive()) {

            LLResult result = Hardware.limelight.getLatestResult();
            if (result != null && result.isValid()) {
                double tx = result.getTx(); // How far left or right the target is (degrees)
                double ty = result.getTy(); // How far up or down the target is (degrees)
                double ta = result.getTa(); // How big the target looks (0%-100% of the image)

                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("Target Area", ta);

                if (tx >= 0) {
                    Hardware.doDrive(0.5, -0.5, 0); //move right?
                    sleep(1000);
                    Hardware.doDrive(0, 0, 0);
                }
                else if (tx < 0) {
                    Hardware.doDrive(-0.5, -0.5, 0); //move left?
                    sleep(1000);
                    Hardware.doDrive(0,0,0);
                }
                telemetry.update();

            } else {
                telemetry.addData("Limelight", "No Targets");
            }

            telemetry.update();


        }
    }
}

