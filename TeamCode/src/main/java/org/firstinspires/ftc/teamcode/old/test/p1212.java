package org.firstinspires.ftc.teamcode.old.test;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class p1212 extends Class{
    PIDFController pid = new PIDFController(0.05,0.00001,0.015,0);
    private AnalogInput analog;
    private CRServo aim1;
    private CRServo aim2;
    private Limelight3A limelight;

    @Override
    public void init() {
        aim1 = hardwareMap.get(CRServo.class, "aim1");
        aim2 = hardwareMap.get(CRServo.class,"aim2");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); limelight.start(); limelight.pipelineSwitch(0);
        analog = hardwareMap.get(AnalogInput.class,"analog");
    }

    @Override
    public void loop() {

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            double tx = result.getTx();      // 量測值
            double setpoint = 0.0;          // 目標就是 tx = 0
            double error = setpoint - tx;   // 誤差 = 目標 - 量測
            // PID output
            double power = pid.data(0,error);

//            power = Math.max(-1, Math.min(0.5, power));
            aim1.setPower(-power);
            aim2.setPower(-power);
            telemetry.addData("tx", tx);
            telemetry.addData("error", error);
            telemetry.addData("power", power);
        }

        else {
            aim1.setPower(0);
            aim2.setPower(0);
            telemetry.addData("Limelight", "No Targets");
        }
        double position = (analog.getVoltage()/ analog.getMaxVoltage())*300;
        telemetry.addData("position",position);
    }
}