package org.firstinspires.ftc.teamcode.util;

public class Utils {
    public static boolean isWithinTolerance(double value, double target, double tolerance) {
        return Math.abs(value - target) < tolerance;
    }
}
