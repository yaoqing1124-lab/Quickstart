package org.firstinspires.ftc.teamcode.old.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class testOP1130 extends com.qualcomm.robotcore.eventloop.opmode.OpMode {
    private Servo turretLeft = null;
    private Servo turretRight = null;
    private Servo arm = null;
    private DcMotor shooterup = null;
    private DcMotor shootdown = null;
    private CRServo carousel = null;
    private DcMotor motor = null;

    @Override
    public void init(){
        telemetry.addData("Status","Initializing");
        turretLeft = hardwareMap.get(Servo.class,"left");
        turretRight = hardwareMap.get(Servo.class,"right");
        shooterup = hardwareMap.get(DcMotor.class,"shooter");
        shootdown = hardwareMap.get(DcMotor.class,"shooter2");
        carousel = hardwareMap.get(CRServo.class,"spin");
        motor = hardwareMap.get(DcMotor.class,"motor");
        arm = hardwareMap.get(Servo.class,"arm");
        telemetry.addData("Status","Initialized");
    }

    @Override
    public void loop() {
        // Turret and Shooter combined control on right stick X
        double stickX = gamepad1.right_stick_x;
        double turretPosLeft = clamp((stickX + 1) / 2, 0.0, 1.0);
        double turretPosRight = 1.0 - turretPosLeft;
        turretLeft.setPosition(turretPosLeft);
        turretRight.setPosition(turretPosRight);

        // Shooter power linked to same stick (absolute value)
        shooterup.setPower(gamepad1.right_trigger*0.9);
        shootdown.setPower(-gamepad1.right_trigger);
        motor.setPower(1);

        // Carousel control
        if (gamepad1.dpad_up) {
            carousel.setPower(-0.2);
        } else {
            carousel.setPower(0.0);
        }

        if (gamepad1.a) {
            arm.setPosition(0.07);
        }
        else{
            arm.setPosition(0.5);
        }
    }
    private double clamp(double v, double min, double max){
        if (v < min) return min;
        if (v > max) return max;
        return v;
    }
}
