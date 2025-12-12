package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous
public class path extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Follower follower = Constants.createFollower(hardwareMap);


        Pose startPose = new Pose(105.6, 33, Math.toRadians(0));
        follower.setPose(startPose);

        PathChain path1 = follower.pathBuilder()
                .addPath(new BezierLine(startPose, new Pose(105.6, 83.5, Math.toRadians(90))))
                .setLinearHeadingInterpolation(Math.toRadians(90),Math.toRadians(0),0.8)
                .setBrakingStart(0.3)
                .build();
        PathChain path2 = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(105.6, 82.5, Math.toRadians(0)), new Pose(125.6, 82.5, Math.toRadians(0))))
                .build();

        PathChain path3 = follower.pathBuilder()
                .addPath(new BezierLine(new Pose(125.6, 82.5, Math.toRadians(0)), new Pose(72, 72, Math.toRadians(0))))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        waitForStart();

        follower.followPath(path1);
        while (opModeIsActive() && follower.isBusy())   follower.update();

//        follower.followPath(path2);
//        while (opModeIsActive() && follower.isBusy()) follower.update();
//
//        follower.followPath(path3);
//        while (opModeIsActive() && follower.isBusy()) follower.update();
    }
}