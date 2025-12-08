package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ApriltagAim1208 extends OpMode {
    private CRServo aim1;
    private CRServo aim2;
    private Limelight3A limelight;
    private final double Kp = 0.08;
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
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(0);
        frontLeftDrive = hardwareMap.get(DcMotor.class, "lf");
        backLeftDrive = hardwareMap.get(DcMotor.class, "lb");
        frontRightDrive = hardwareMap.get(DcMotor.class, "rf");
        backRightDrive = hardwareMap.get(DcMotor.class, "rb");
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
        double axial   = -gamepad1.left_stick_y;
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        double frontLeftPower  = axial + lateral - yaw;
        double frontRightPower = axial - lateral + yaw;
        double backLeftPower   = axial - lateral - yaw;
        double backRightPower  = axial + lateral + yaw;
        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);

        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx();

            double power = Kp * tx;

            if (Math.abs(tx) < deadband){
                power = 0;
            }

            power = -Math.max(-0.5,Math.min(0.5,power));
            aim1.setPower(power);
            aim2.setPower(power);
            telemetry.addData("tx", tx);
            telemetry.addData("power",power);
            telemetry.addData("id",result.getDetectorResults());
        } else {
            aim1.setPower(0);
            aim2.setPower(0);
            telemetry.addData("Limelight", "No Targets");
        }


        telemetry.update();
    }
}
