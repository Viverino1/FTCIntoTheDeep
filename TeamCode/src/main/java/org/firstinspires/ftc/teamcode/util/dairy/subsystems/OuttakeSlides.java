package org.firstinspires.ftc.teamcode.util.dairy.subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

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
public class OuttakeSlides implements Subsystem {
    public static final OuttakeSlides INSTANCE = new OuttakeSlides();
    public static DcMotorEx slideR;
    public static DcMotorEx slideL;
    public static Telemetry telemetry;
    public static int tolerance = 40;
    public static int submirsiblePos = 0;
    public static int bucketPos = 0;
    public static int scoreSubmersiblePos = 0;
//    static final OpModeLazyCell<PIDFService> thingy = new OpModeLazyCell<>(() -> new PIDFService(OuttakeSlides.controller, OuttakeSlides.slideL, OuttakeSlides.slideR));
    public static double Kp = 0.0014;
    public static double Ki = 0.0000;
    public static double Kd = 0.0000;
    public static double Kf = 0.0000;
    public static double minArmMoveHeight = 500;
    public static int maxPos = 2200;
    public static int minPos = 0;
    public static double currentLimit = 4;

    public static PIDFController controller = new PIDFController(Kp, Ki, Kd, Kf);

    private OuttakeSlides() {}

    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE) @MustBeDocumented
    @Inherited
    public @interface Attach { }

    @Override
    public void preUserInitHook(@NonNull Wrapper opMode) {
        HardwareMap hMap = opMode.getOpMode().hardwareMap;
        telemetry = opMode.getOpMode().telemetry;
        slideR = hMap.get(DcMotorEx.class, "outtakeSR");
        slideL = hMap.get(DcMotorEx.class, "outtakeSL");
        slideR.setCurrentAlert(currentLimit, CurrentUnit.AMPS);
        slideL.setCurrentAlert(currentLimit, CurrentUnit.AMPS);
        slideR.setDirection(DcMotorSimple.Direction.REVERSE);
        slideL.setDirection(DcMotorSimple.Direction.REVERSE);
        slideL.setCurrentAlert(currentLimit, CurrentUnit.AMPS);
        slideR.setCurrentAlert(currentLimit, CurrentUnit.AMPS);

        reset();

        controller.setTolerance(tolerance);

        setDefaultCommand(runPID());
    }

    @Override
    public void postUserLoopHook(@NonNull Wrapper opMode) {}

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

    public static void setPower(double power){
        slideL.setPower(power);
        slideR.setPower(power);
    }

    public static Lambda setPowerCommand(double power){
        return new Lambda("set-power")
                .setExecute(() -> {
                    setPower(power);
                });
    }

    public static boolean isOverCurrent() {
        return slideR.isOverCurrent() || slideL.isOverCurrent();
    }

    public static void reset() {
        slideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        controller.reset();
        controller.setSetPoint(0);
    }

    public static Lambda setTargetPos(int pos){
        return new Lambda("set-target-pos")
                .setInit(() -> {
                    controller.setSetPoint(pos);
                })
                .setFinish(() -> true);
    }

    public static void logCurrent(){
        telemetry.addData("isOver", isOverCurrent());
        telemetry.update();
    }

    public static double getPos(){
        return slideR.getCurrentPosition();
    }

    public static void logTele(){
        telemetry.addData("Slide Pos", getPos());
        telemetry.addData("Slide Power", slideL.getPower());
        telemetry.addData("Slide Setpoint", controller.getSetPoint());
        telemetry.addData("Slide Error", controller.getPositionError());
    }

    public static Lambda runPID() {
        return new Lambda("outtake-pid")
                .addRequirements(INSTANCE)
                .setInterruptible(true)
                .setExecute(() -> {
                    double velo = controller.calculate(getPos());
                    setPower(velo);
                    logTele();
                })
                .setFinish(() -> false);
    }

    public static Lambda home() {
        return new Lambda("home-outtake")
                .setInterruptible(true)
                .setInit(() -> controller.setSetPoint(0))
                .setFinish(() -> true);
    }
}