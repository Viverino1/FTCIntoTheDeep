package org.firstinspires.ftc.teamcode.util.dairy;

import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;

import java.util.ArrayList;
import java.util.Collections;

public class Paths {

    public static ArrayList<Path> plusFourSpec = new ArrayList<>();
    public static ArrayList<Path> plusThreeRedSpec = new ArrayList<>();

    public static void init() {
        Collections.addAll(plusFourSpec,
                createPath(
                        new BezierLine(
                                new Point(9.000, 65.000, Point.CARTESIAN),
                                new Point(39.000, 65.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierCurve(
                                new Point(39.000, 65.000, Point.CARTESIAN),
                                new Point(31.500, 66.000, Point.CARTESIAN),
                                new Point(13.000, 26.000, Point.CARTESIAN),
                                new Point(63.000, 45.000, Point.CARTESIAN),
                                new Point(58.500, 23.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierLine(
                                new Point(58.500, 23.000, Point.CARTESIAN),
                                new Point(25.000, 22.500, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierCurve(
                                new Point(25.000, 22.500, Point.CARTESIAN),
                                new Point(62.500, 32.000, Point.CARTESIAN),
                                new Point(58.500, 12.500, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierLine(
                                new Point(58.500, 12.500, Point.CARTESIAN),
                                new Point(25.000, 12.500, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierCurve(
                                new Point(25.000, 12.500, Point.CARTESIAN),
                                new Point(62.000, 18.500, Point.CARTESIAN),
                                new Point(58.500, 7.500, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierLine(
                                new Point(58.500, 7.500, Point.CARTESIAN),
                                new Point(25.000, 7.500, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierCurve(
                                new Point(25.000, 7.500, Point.CARTESIAN),
                                new Point(27.000, 23.000, Point.CARTESIAN),
                                new Point(10.500, 32.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierLine(
                                new Point(10.500, 32.000, Point.CARTESIAN),
                                new Point(39.000, 67.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierCurve(
                                new Point(39.000, 67.000, Point.CARTESIAN),
                                new Point(24.655, 54.670, Point.CARTESIAN),
                                new Point(30.194, 26.084, Point.CARTESIAN),
                                new Point(10.500, 32.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierLine(
                                new Point(10.500, 32.000, Point.CARTESIAN),
                                new Point(39.000, 69.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierCurve(
                                new Point(39.000, 69.000, Point.CARTESIAN),
                                new Point(24.655, 54.849, Point.CARTESIAN),
                                new Point(30.372, 25.548, Point.CARTESIAN),
                                new Point(10.500, 32.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierLine(
                                new Point(10.500, 32.000, Point.CARTESIAN),
                                new Point(39.000, 69.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierCurve(
                                new Point(39.000, 69.000, Point.CARTESIAN),
                                new Point(24.655, 54.849, Point.CARTESIAN),
                                new Point(30.372, 25.548, Point.CARTESIAN),
                                new Point(10.500, 32.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierLine(
                                new Point(10.500, 32.000, Point.CARTESIAN),
                                new Point(39.000, 71.000, Point.CARTESIAN)
                        )
                ),
                createPath(
                        new BezierLine(
                                new Point(39.000, 71.000, Point.CARTESIAN),
                                new Point(13.000, 20.000, Point.CARTESIAN)
                        ), Math.toRadians(0), Math.toRadians(-45)
                )
        );
    }


    private static Path createPath(BezierLine line, double heading) {
        Path path = new Path(line);
        path.setConstantHeadingInterpolation(heading); // Constant heading
        return path;
    }

    private static Path createPath(BezierLine line, double startHeading, double endHeading) {
        Path path = new Path(line);
        path.setLinearHeadingInterpolation(startHeading, endHeading); // Linear heading
        return path;
    }

    private static Path createPath(BezierLine line, boolean tangent) {
        Path path = new Path(line);
        if (tangent) path.setTangentHeadingInterpolation();
        return path;
    }

    private static Path createPath(BezierLine line) {
        Path path = new Path(line);
        path.setConstantHeadingInterpolation(0);
        return path;
    }

    private static Path createPath(BezierCurve curve) {
        Path path = new Path(curve);
        path.setConstantHeadingInterpolation(0);
        return path;
    }

    // Constant Heading
    private static Path createPath(BezierCurve curve, double heading) {
        Path path = new Path(curve);
        path.setConstantHeadingInterpolation(heading); // Constant heading
        return path;
    }

    // Linear Heading
    private static Path createPath(BezierCurve curve, double startHeading, double endHeading) {
        Path path = new Path(curve);
        path.setLinearHeadingInterpolation(startHeading, endHeading); // Linear heading
        return path;
    }

    // Tangent or Default Constant Heading
    private static Path createPath(BezierCurve curve, boolean tangent) {
        Path path = new Path(curve);
        if (tangent) path.setTangentHeadingInterpolation(); // Tangent heading
        else path.setConstantHeadingInterpolation(0);      // Default constant heading
        return path;
    }
}
