package org.firstinspires.ftc.teamcode.old.test;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class p1214 extends Class{
    PIDFController aimPID = new PIDFController(0.05,0.00001,0.015,0);
    PIDFController shooterPID = new PIDFController(0,0,0,0);
    private AnalogInput analog;
    private CRServo aim1,aim2;
    private DcMotor shooter1,shooter2;
    private Limelight3A limelight;
    private DcMotor lf,lb,rb,rf;

    @Override
    public void init() {
        aim1 = hardwareMap.get(CRServo.class, "aim1");
        aim2 = hardwareMap.get(CRServo.class,"aim2");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); limelight.start(); limelight.pipelineSwitch(0);
        analog = hardwareMap.get(AnalogInput.class,"analog");
        shooter1 = hardwareMap.get(DcMotor.class,"shooter1");
        shooter1.setDirection(DcMotorSimple.Direction.REVERSE);
        shooter2 = hardwareMap.get(DcMotor.class,"shooter2");
        lf = hardwareMap.get(DcMotor.class,"lf");
        lb = hardwareMap.get(DcMotor.class,"lb");
        rf = hardwareMap.get(DcMotor.class,"rf");
        rb = hardwareMap.get(DcMotor.class,"rb");
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        double axial   = -gamepad1.left_stick_y;
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        double lfp  = axial + lateral + yaw;
        double rfp = axial - lateral - yaw;
        double lbp   = axial - lateral + yaw;
        double rbp  = axial + lateral - yaw;
        lf.setPower(lfp);
        lb.setPower(lbp);
        rf.setPower(rfp);
        rb.setPower(rbp);

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            double tx = result.getTx();      // 量測值
            double setpoint = 0.0;          // 目標就是 tx = 0
            double error = setpoint - tx;   // 誤差 = 目標 - 量測
            // PID output
            double AimPower = aimPID.data(0,error);

//            power = Math.max(-1, Math.min(0.5, power));
            aim1.setPower(-AimPower);
            aim2.setPower(-AimPower);
            telemetry.addData("tx", tx);
            telemetry.addData("error", error);
            telemetry.addData("power", AimPower);
        }

        else {
            aim1.setPower(0);
            aim2.setPower(0);
            telemetry.addData("Limelight", "No Targets");
        }
        double position = (analog.getVoltage()/ analog.getMaxVoltage())*300;
        telemetry.addData("position",position);

        shooter1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        double ShooterPower = shooterPID.data(gamepad1.right_trigger,shooter1.getPower());
        shooter1.setPower(ShooterPower);
        shooter2.setPower(-ShooterPower);

        if(gamepad1.dpad_left){
            aim1.setPower(0.5);
            aim2.setPower(0.5);
        } else if (gamepad1.dpad_right) {
            aim1.setPower(-0.5);
            aim2.setPower(-0.5);
        }else{
            aim1.setPower(0);
            aim2.setPower(0);
        }
    }
}