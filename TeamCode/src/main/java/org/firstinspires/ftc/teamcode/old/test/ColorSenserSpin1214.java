package org.firstinspires.ftc.teamcode.old.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ColorSenserSpin1214 extends OpMode {
    AnalogInput analog;
    ColorSensor c1;
    ColorSensor c2;
    CRServo spin;
    Servo light;
    boolean isgreen = false;
    @Override
    public void init() {

        c1 = hardwareMap.get(ColorSensor.class, "c1");
        c2 = hardwareMap.get(ColorSensor.class, "c2");
        spin = hardwareMap.get(CRServo.class, "spin");
        analog = hardwareMap.get(AnalogInput.class, "analog");
        light = hardwareMap.get(Servo.class, "light");
        telemetry.addData("Status", "Initialized");
        if(Math.abs((analog.getVoltage() / analog.getMaxVoltage()) * 300 - 50) < 15) {
            spin.setPower(-0.15);
        }
        telemetry.update();
    }
        @Override
        public void loop(){
            telemetry.addData("R", c1.red()+c2.red());
            telemetry.addData("G", c1.green()+c2.green());
            telemetry.addData("B", c1.blue()+c2.blue());
            telemetry.addData("Alpha", c1.alpha()+c2.alpha());
            if(c1.alpha()+c2.alpha()>2000){
                telemetry.addLine("gold");
            }
            else if(c1.alpha()+ c2.alpha()<300){
                telemetry.addLine("no");
            }
            else{
                if (c1.green()+c2.green()>c1.blue()+c2.blue()){
                    telemetry.addLine("green ball");
                    isgreen = true;
                    while(true){
                        spin.setPower(0);
                    }
                }
                else{
                    telemetry.addLine("purple ball");
                    isgreen = false;
                }
            }
            double position = (analog.getVoltage()/ analog.getMaxVoltage())*300;
            telemetry.addData("position",position);
            telemetry.update();
    }
}

    //1  142~182 2 241~281 3 43~84