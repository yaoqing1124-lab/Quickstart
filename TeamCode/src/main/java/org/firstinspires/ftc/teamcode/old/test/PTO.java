package org.firstinspires.ftc.teamcode.old.test;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
@TeleOp
public class PTO extends OpMode {
    private Servo R;
    private Servo L;
    private DcMotor lf,lb,rf,rb;
    @Override
    public void init() {
        R = hardwareMap.get(Servo.class,"R");
        L = hardwareMap.get(Servo.class,"L");
        lf = hardwareMap.get(DcMotor.class,"lf");
        lb = hardwareMap.get(DcMotor.class,"lb");
        rf = hardwareMap.get(DcMotor.class,"rf");
        rb = hardwareMap.get(DcMotor.class,"rb");

    }

    @Override
    public void loop() {
        if(gamepad1.circle){
            R.setPosition(0.5);
            L.setPosition(0.44);
        }
        if(gamepad1.cross){
            R.setPosition(0.35);
            L.setPosition(0.6);
        }
        double y = gamepad1.right_stick_y;
        lf.setPower(y);
        lb.setPower(-y);
        rf.setPower(-y);
        rb.setPower(y);
    }
}
