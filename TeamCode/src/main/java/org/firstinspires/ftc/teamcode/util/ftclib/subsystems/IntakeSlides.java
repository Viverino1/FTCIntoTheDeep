package org.firstinspires.ftc.teamcode.util.ftclib.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import kotlin.annotation.MustBeDocumented;

@Config
public class IntakeSlides implements Subsystem {
    public static final IntakeSlides INSTANCE = new IntakeSlides();

    public static DcMotorEx slide;
    public static Telemetry telemetry;

    public static class Gains{
        public double Kp = 0.01;
        public double Ki = 0;
        public double Kd = 0;
    }
    public static int maxPos = 1000;
    public static int minPos = 0;

    public static double zeroCurrent = 4;

    public static Gains GAINS = new Gains();
    static PIDController pidController = new PIDController(GAINS.Kp, GAINS.Ki, GAINS.Kd, 0);

    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE) @MustBeDocumented
    @Inherited
    public @interface Attach { }

    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Attach.class));

    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) {
        this.dependency = dependency;
    }

    @Override
    public void postUserInitHook(@NonNull Wrapper opMode) {
        HardwareMap hMap = opMode.getOpMode().hardwareMap;
        telemetry = opMode.getOpMode().telemetry;

        slide = hMap.get(DcMotorEx.class, "intakeSlide");
        slide.setCurrentAlert(zeroCurrent, CurrentUnit.AMPS);

        setDefaultCommand(runPID());
    }

    @Override
    public void postUserLoopHook(@NonNull Wrapper opMode) {}

    public static void setPower(double power){
        slide.setPower(power);
    }

    public static boolean isOverCurrent(){
        return slide.isOverCurrent();
    }

    public static void updatePID(){
        double power = pidController.getPower(slide.getCurrentPosition());
        slide.setPower(power);
    }
    public static void resetEncoder(){
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public static void resetPID(){
        pidController.reset();
    }

    public static void setTargetPos(int pos){
        pidController.setReference(pos);
    }

    public static void logCurrent(){
        telemetry.addData("isOver", isOverCurrent());
        telemetry.update();
    }

    public static int getPos(){
        return slide.getCurrentPosition();
    }

    public static void logPos(){
        telemetry.addData("Slide Pos", slide.getCurrentPosition());
        telemetry.update();
    }

    public static Lambda runPID() {
        return new Lambda("intake-pid")
                .addRequirements(INSTANCE)
                .setExecute(() -> {
                    double power = pidController.getPower(getPos());
                    slide.setPower(power);
                })
                .setFinish(() -> false);
    }
}
