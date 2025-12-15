package org.firstinspires.ftc.teamcode.FTC2025;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import org.firstinspires.ftc.teamcode.FTC2025.Class.PIDFController;

public class RobotBase {
    RobotHardware robot;
    PIDFController ShooterPIDF = new PIDFController(0, 0, 0, 0);
    PIDFController aimPIDF = new PIDFController(0, 0, 0, 0);

    public void drive(double lateral, double axial, double yaw) {
        double lfp = axial + lateral + yaw;
        double rfp = axial - lateral - yaw;
        double lbp = axial - lateral + yaw;
        double rbp = axial + lateral - yaw;

        robot.lf.setPower(lfp);
        robot.lb.setPower(lbp);
        robot.rf.setPower(rfp);
        robot.rb.setPower(rbp);
    }

    public void shooter(double Velocity) {
        robot.shooter1.setPower(ShooterPIDF.data(Velocity, robot.shooter1.getPower()));
        robot.shooter2.setPower(ShooterPIDF.data(-Velocity, robot.shooter2.getPower()));
    }

    public void aim(double angle) {
        double position = (robot.analog.getVoltage()/ robot.analog.getMaxVoltage())*300;
    }

    public void spin(double Velocoty) {
        robot.spin.setPower(Velocoty);
    }

    public double limelight(String type) {
        LLResult result = robot.limelight3A.getLatestResult();
        if (result != null && result.isValid()) {
            switch (type) {
                case "tx":
                    return result.getTx();
                case "ty":
                    return result.getTy();
                case "ta":
                    return result.getTa();
            }
        } else {
            telemetry.addData("Limelight", "No Targets");
        }
        return 0;
    }
    public void aimAuto(){
        robot.aim1.setPower(aimPIDF.data(limelight("tx"),robot.aim1.getPower()));
        robot.aim2.setPower(aimPIDF.data(limelight("tx"),robot.aim2.getPower()));
    }
}