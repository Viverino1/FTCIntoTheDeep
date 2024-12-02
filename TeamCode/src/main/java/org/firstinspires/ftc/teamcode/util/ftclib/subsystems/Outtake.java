package org.firstinspires.ftc.teamcode.util.ftclib.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import kotlin.annotation.MustBeDocumented;

@Config
public class Outtake implements Subsystem {
    public static final Outtake INSTANCE = new Outtake();

    public static Servo armR;
    public static Servo armL;

    public static Servo clawR;
    public static Servo clawL;

    public static Servo pivot;

    public static double clawOpenPos = 0;
    public static double clawClosePos = 0.35;

    public static double armSubmersiblePos = 0.15;
    public static double armSampleIntakePos = 0;
    public static double armHomePos = 0.33;
    public static double armBucketPos = 0.9;

    //pivot
    public static double pivotSubmersiblePos = 0.2;
    public static double pivotSampleIntakePos = 0.225;
    public static double pivotHomePos = 0.225;
    public static double pivotBucketPos = 0.3;

    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE) @MustBeDocumented
    @Inherited
    public @interface Attach { }

    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Mercurial.Attach.class));

    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) { this.dependency = dependency; }

    @Override
    public void postUserInitHook(@NonNull Wrapper opMode) {
        HardwareMap hMap = opMode.getOpMode().hardwareMap;

        armR = hMap.get(Servo.class, "arm1");
        armL = hMap.get(Servo.class, "arm2");

        clawR = hMap.get(Servo.class, "gripper1");
        clawL = hMap.get(Servo.class, "gripper2");
        clawL.setDirection(Servo.Direction.REVERSE);

        pivot = hMap.get(Servo.class, "pivot");

        setPivot(Outtake.pivotHomePos);
        setArm(Outtake.armHomePos);
        openClaw();
    }

    @Override
    public void postUserLoopHook(@NonNull Wrapper opMode) {}

    public void openClaw(){
        setClaw(clawOpenPos);
    }

    public void closeClaw(){
        setClaw(clawClosePos);
    }

    private void setClaw(double pos){
        clawR.setPosition(pos);
        clawL.setPosition(pos);
    }

    public void  setArm(double pos){
        armR.setPosition(pos);
        armL.setPosition(pos);
    }

    public void setPivot(double pos){
        pivot.setPosition(pos);
    }


}