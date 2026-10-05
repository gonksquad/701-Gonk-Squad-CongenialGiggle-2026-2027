package org.firstinspires.ftc.teamcode.ViennaIsAwesome;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class zoomzoomlime2 extends LinearOpMode {

    @Override
    public void runOpMode() {
        hardware Hardware;
        Hardware = new hardware(hardwareMap);

        Hardware.limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        Hardware.limelight.start();
        Hardware.limelight.pipelineSwitch(0);

        waitForStart();


        while(opModeIsActive()) {
            
        }
}
