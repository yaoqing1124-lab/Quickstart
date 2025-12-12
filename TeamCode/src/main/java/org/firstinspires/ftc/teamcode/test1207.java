package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class test1207 extends test1130 {

    private Servo Lservo;
    private Servo Rservo;
    private DcMotor lf;
    private DcMotor lb;
    private DcMotor rf;
    private DcMotor rb;

    @Override
    public void init() {
        Lservo = hardwareMap.get(Servo.class,"L");
        Rservo = hardwareMap.get(Servo.class,"R");
        lf = hardwareMap.get(DcMotor.class,"lf");
        lb = hardwareMap.get(DcMotor.class,"lb");
        rf = hardwareMap.get(DcMotor.class,"rf");
        rb = hardwareMap.get(DcMotor.class,"rb");
    }

    @Override
    public void loop() {

//        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setPower(-gamepad1.right_stick_y);
        lb.setPower(gamepad1.right_stick_y);
        rf.setPower(-gamepad1.right_stick_y);
        rb.setPower(gamepad1.right_stick_y);

        if(gamepad1.a){
            Lservo.setPosition(0.3);
            Rservo.setPosition(0.8);
        }
        if(gamepad1.b){
            Lservo.setPosition(0.5);
            Rservo.setPosition(0.51);
        }
        telemetry.addData("L",Lservo.getPosition());
        telemetry.addData("R",Rservo.getPosition());
    }
}
