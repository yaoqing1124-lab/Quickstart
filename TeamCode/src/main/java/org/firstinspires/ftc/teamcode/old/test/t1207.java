package org.firstinspires.ftc.teamcode.old.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class t1207 extends testOP1130 {
    private Servo Servo;
    private boolean logic = true;

    @Override
    public void init() {
        Servo = hardwareMap.get(Servo.class,"servo");
    }

    @Override
    public void loop() {

        if(gamepad1.a){
            if(logic){
                Servo.setPosition(0);;
            }
            else{
                Servo.setPosition(1);
            }
            logic = !logic;
        }



//        if(gamepad1.a){
//            if(Servo.getPosition()>0.5){
//                Servo.setPosition(0);
//            }
//            else{
//                Servo.setPosition(1);
//            }
//        }
    }
}

