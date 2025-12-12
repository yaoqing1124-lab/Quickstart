package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
@TeleOp
public class ColorSenserSpin extends LinearOpMode {
    ColorSensor c1;
    ColorSensor c2;

    @Override
    public void runOpMode() {

            c1 = hardwareMap.get(ColorSensor.class, "c1");
            c2 = hardwareMap.get(ColorSensor.class, "c2");
        telemetry.addData("Status", "Initialized");

        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("R", c1.red()+c2.red());
            telemetry.addData("G", c1.green()+c2.green());
            telemetry.addData("B", c1.blue()+c2.blue());
            telemetry.addData("Alpha", c1.alpha()+c2.alpha());

            if(c1.alpha()+ c2.alpha()<300){
                telemetry.addLine("no");
            }
            else{
                if (c1.green()+c2.green()>c1.blue()+c2.blue()){
                    telemetry.addLine("green ball");
                }
                else{
                    telemetry.addLine("purple ball");
                }
            }

            telemetry.update();

            }
        }
    }