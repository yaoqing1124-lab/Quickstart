package org.firstinspires.ftc.teamcode.old.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class Class extends OpMode {
    public class PIDFController {

        private double kP, kI, kD, kF;
        private double integralSum = 0;
        private double lastError = 0;
        private long lastTime = 0;  // 用於計算 Δt

        public PIDFController(double kP, double kI, double kD, double kF) {
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
            this.kF = kF;
            this.lastTime = System.nanoTime();
        }

        public double data(double target, double current) {

            double error = target - current;

            long now = System.nanoTime();
            double dt = (now - lastTime) / 1e9;
            lastTime = now;

            // 積分
            integralSum += error * dt;

            // 微分
            double derivative = (dt > 0) ? (error - lastError) / dt : 0;
            lastError = error;

            // PIDF 輸出
            double output = (kP * error) +
                    (kI * integralSum) +
                    (kD * derivative) +
                    (kF * target);     // Feedforward 通常乘以目標速度

            return output;
        }

        // 可選：用來清積分
        public void reset() {
            integralSum = 0;
            lastError = 0;
            lastTime = System.nanoTime();
        }
    }
}