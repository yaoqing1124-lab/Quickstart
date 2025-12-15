package org.firstinspires.ftc.teamcode.old.test;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@Configurable
@TeleOp
public class test3A1211 extends OpMode {
    private CRServo aim1;
    private Limelight3A limelight;

    private double kP = 0.01;
    private double kI = 0.00001;
    private double kD = 0.0007;

    private double integral = 0.0;
    private double lastError = 0.0;
    private double lastTime = 0.0;

    @Override
    public void init() {
        aim1 = hardwareMap.get(CRServo.class, "crservo");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); limelight.start(); limelight.pipelineSwitch(0);

        lastTime = getRuntime();
        integral = 0.0;
        lastError = 0.0;
    }

    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx();      // 量測值
            double setpoint = 0.0;          // 目標就是 tx = 0
            double error = setpoint - tx;   // 誤差 = 目標 - 量測

            double now = getRuntime();
            double dt = now - lastTime;
            if (dt <= 0) dt = 0.001;        // 避免除以 0

            integral += error * dt;

            // anti-windup：限制積分避免飄太大
            double iLimit = 10.0;
            if (integral > iLimit) integral = iLimit;
            if (integral < -iLimit) integral = -iLimit;

            // 微分項
            double derivative = (error - lastError) / dt;

            // PID output
            double power = kP * error + kI * integral + kD * derivative;

            power = Math.max(-0.5, Math.min(0.5, power));
            aim1.setPower(power);

            lastError = error;
            lastTime = now;

            telemetry.addData("tx", tx);
            telemetry.addData("error", error);
            telemetry.addData("P", kP * error);
            telemetry.addData("I", kI * integral);
            telemetry.addData("D", kD * derivative);
            telemetry.addData("derivative",derivative);
            telemetry.addData("power", power);
        } else {
            aim1.setPower(0);
            integral = 0.0;
            lastError = 0.0;
            lastTime = getRuntime();
            telemetry.addData("Limelight", "No Targets");
        }
    }
}