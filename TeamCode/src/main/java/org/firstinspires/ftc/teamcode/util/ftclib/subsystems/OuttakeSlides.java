package org.firstinspires.ftc.teamcode.util.ftclib.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.util.PIDController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import kotlin.annotation.MustBeDocumented;

@Config
public class OuttakeSlides implements Subsystem {
    public static final OuttakeSlides INSTANCE = new OuttakeSlides();
    public static DcMotorEx slideR;
    public static DcMotorEx slideL;
    public static Telemetry telemetry;
    public static int tolerance = 100;
    public static int submirsiblePos = 0;
    public static int bucketPos = 0;
    public static int scoreSubmersiblePos = 0;
    public static class Gains{
        public double Kp = 0.005;
        public double Ki = 0;
        public double Kd = 0;
    }
    public static double minArmMoveHeight = 500;
    public static int maxPos = 2200;
    public static int minPos = 0;
    public static double zeroCurrent = 4;
    public static Gains GAINS = new Gains();
    public static volatile PIDController pidController = new PIDController(GAINS.Kp, GAINS.Ki, GAINS.Kd, 0);

    private OuttakeSlides() {}

    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE) @MustBeDocumented
    @Inherited
    public @interface Attach { }

    @Override
    public void postUserInitHook(@NonNull Wrapper opMode) {
        HardwareMap hMap = opMode.getOpMode().hardwareMap;
        slideR = hMap.get(DcMotorEx.class, "outtakeSR");
        slideL = hMap.get(DcMotorEx.class, "outtakeSL");
        slideR.setCurrentAlert(zeroCurrent, CurrentUnit.AMPS);
        slideL.setCurrentAlert(zeroCurrent, CurrentUnit.AMPS);
        slideR.setDirection(DcMotorSimple.Direction.FORWARD);
        slideL.setDirection(DcMotorSimple.Direction.REVERSE);
        setDefaultCommand(runPID());
    }

    @Override
    public void postUserLoopHook(@NonNull Wrapper opMode) {}

    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Mercurial.Attach.class));

    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) {
        this.dependency = dependency;
    }

    public static Lambda setPower(double power){
        return new Lambda("set-power")
                .addRequirements(INSTANCE)
                .setExecute(() -> {
                    slideR.setPower(Chassis.isSlowed? power * Chassis.slowSpeed : power);
                    slideL.setPower(Chassis.isSlowed? power * Chassis.slowSpeed : power);
                });
    }

    public static boolean isBothOverCurrent() {
        return slideR.isOverCurrent() || slideL.isOverCurrent();
    }

    public static boolean isLeftOverCurrent() {
        return slideL.isOverCurrent();
    }

    public static boolean isRightOverCurrent() {
        return slideR.isOverCurrent();
    }

    public void resetEncoder(){
        slideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void resetPID(){
        pidController.reset();
    }

    public static Lambda setTargetPos(int pos){
        return new Lambda("set-target-pos")
                .addRequirements(INSTANCE)
                .setInit(() -> pidController.setReference(pos))
                .setFinish(() -> true);
    }

    public static void logCurrent(){
        telemetry.addData("isOver", isBothOverCurrent());
        telemetry.update();
    }

    public static int getPos(){
        return slideR.getCurrentPosition();
    }

    public void logPos(){
        telemetry.addData("Slide Pos", slideR.getCurrentPosition());
        telemetry.update();
    }

    public static Lambda runPID() {
        return new Lambda("outtake-pid")
                .addRequirements(INSTANCE)
                .setExecute(() -> {
                    double power = pidController.getPower(getPos());
                    setPower(power);
                })
                .setFinish(() -> false);
    }

    public static Lambda returnLeft() {
        return new Lambda("return-left")
                .addRequirements(INSTANCE)
                .setExecute(() -> setPower(-.5))
                .setFinish(() -> isLeftOverCurrent() && getPos() < 50);
    }
    public static Lambda returnRight() {
        return new Lambda("return-right")
                .addRequirements(INSTANCE)
                .setExecute(() -> setPower(-.5))
                .setFinish(() -> isRightOverCurrent() && getPos() < 50);
    }
}
