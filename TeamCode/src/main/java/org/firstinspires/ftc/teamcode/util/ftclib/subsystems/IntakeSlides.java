package org.firstinspires.ftc.teamcode.util.ftclib.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.ServoEx;
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
import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import kotlin.annotation.MustBeDocumented;

@Config
public class IntakeSlides implements Subsystem {
    public static final IntakeSlides INSTANCE = new IntakeSlides();

    public static ServoEx slideR;
    public static ServoEx slideL;
    public static Telemetry telemetry;

    public static class Gains{
        public double Kp = 0.01;
        public double Ki = 0;
        public double Kd = 0;
    }
    public static int maxPos = 1000;
    public static int minPos = 0;

    public static Gains GAINS = new Gains();

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

        slideR = hMap.get(ServoEx.class, "slideR");
        slideL = hMap.get(ServoEx.class, "slideL");

        slideL.setInverted(true);

        // setDefaultCommand(setPosition(Mercurial.gamepad2().leftTrigger().state()));
    }

    @Override
    public void postUserLoopHook(@NonNull Wrapper opMode) {}

    public static Lambda setPosition(double pos) {
        return new Lambda("set position")
                .addRequirements(INSTANCE)
                .setInit(() -> {
                    slideR.setPosition(pos);
                    slideL.setPosition(pos);
                });
    }
}
