package org.firstinspires.ftc.teamcode.old.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class light extends OpMode {
    Servo light;
    @Override
    public void init() {
        light = hardwareMap.get(Servo.class,"light");
    }

    @Override
    public void loop() {
        double a = 0.277;
        while(a < 0.722) {
            light.setPosition(a);
            a += 0.0005;
        }
        while(a>0.277){
            light.setPosition(a);
            a-=0.0005;
        }
    }
}
