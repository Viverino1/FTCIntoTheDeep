package org.firstinspires.ftc.teamcode.util.ftclib.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.roadrunner.MecanumDrive;

public class Chassis extends SubsystemBase {
    public MecanumDrive rr;
    public static boolean isSlowed = false;
    public static double slowSpeed = 0.5;
    public Chassis(HardwareMap hMap) {
        rr = new MecanumDrive(hMap, new Pose2d(0,0,0));
    }

    public void drive(double x, double y, double z) {
        rr.setDrivePowers(new PoseVelocity2d(
                new Vector2d(x * (isSlowed? slowSpeed : 1), y * (isSlowed? slowSpeed : 1)), z * (isSlowed? slowSpeed : 1)
        ));
    }
}
