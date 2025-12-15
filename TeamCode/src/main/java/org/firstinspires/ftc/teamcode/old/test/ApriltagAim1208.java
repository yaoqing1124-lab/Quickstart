package org.firstinspires.ftc.teamcode.old.test;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ApriltagAim1208 extends OpMode {
    private CRServo aim1;
    private CRServo aim2;
    private Limelight3A limelight;

    // PID 參數（先從 P 開始調，I 和 D 先設小或 0）
    private double kP = 0.015;
    private double kI = 0.001;
    private double kD = 0.005;

    // PID 狀態
    private double integral = 0.0;
    private double lastError = 0.0;
    private double lastTime = 0.0;

    private final double deadband = 1.0;

    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    @Override
    public void init() {
        aim1 = hardwareMap.get(CRServo.class, "aim1");
        aim2 = hardwareMap.get(CRServo.class, "aim2");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); limelight.start(); limelight.pipelineSwitch(0);
        frontLeftDrive = hardwareMap.get(DcMotor.class, "lf");
        backLeftDrive = hardwareMap.get(DcMotor.class, "lb");
        frontRightDrive = hardwareMap.get(DcMotor.class, "rf");
        backRightDrive = hardwareMap.get(DcMotor.class, "rb");
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        lastTime = getRuntime();
        integral = 0.0;
        lastError = 0.0;
    }

    @Override
    public void loop() {
        double axial = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x;
        double frontLeftPower = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower = axial - lateral + yaw;
        double backRightPower = axial + lateral - yaw;
        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx();      // 量測值
            double setpoint = 0.0;          // 目標就是 tx = 0
            double error = setpoint - tx;   // 誤差 = 目標 - 量測

            double now = getRuntime();
            double dt = now - lastTime;
            if (dt <= 0) dt = 0.001;        // 避免除以 0

            // deadband：誤差很小就當作到位，順便清掉積分避免抖動
            if (Math.abs(error) < deadband) {
                error = 0.0;
                integral = 0.0;
            }

            // 積分項
            integral += error * dt;

            // anti-windup：限制積分避免飄太大
            double iLimit = 10.0;
            if (integral > iLimit) integral = iLimit;
            if (integral < -iLimit) integral = -iLimit;

            // 微分項
            double derivative = (error - lastError) / dt;

            // PID output
            double power = kP * error + kI * integral + kD * derivative;

            // 限制輸出
            power = Math.max(-0.5, Math.min(0.5, power));
            // 如果你原本有反向（之前多一個負號），就依照方向需要再加：
            // power = -power;

            aim1.setPower(power);
            aim2.setPower(power);

            lastError = error;
            lastTime = now;

            telemetry.addData("tx", tx);
            telemetry.addData("error", error);
            telemetry.addData("P", kP * error);
            telemetry.addData("I", kI * integral);
            telemetry.addData("D", kD * derivative);
            telemetry.addData("power", power);
        } else {
            aim1.setPower(0);
            aim2.setPower(0);
            // 沒看到目標時可以順便 reset 積分避免下次衝太大
            integral = 0.0;
            lastError = 0.0;
            lastTime = getRuntime();

            telemetry.addData("Limelight", "No Targets");
        }

    }
}