package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.ftclib.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.util.ftclib.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.ftclib.subsystems.IntakeSlides;
import org.firstinspires.ftc.teamcode.util.ftclib.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.util.ftclib.subsystems.OuttakeSlides;

import dev.frozenmilk.dairy.core.util.supplier.logical.EnhancedBooleanSupplier;
import dev.frozenmilk.dairy.core.util.supplier.numeric.EnhancedDoubleSupplier;
import dev.frozenmilk.dairy.pasteurized.Pasteurized;
import dev.frozenmilk.dairy.pasteurized.PasteurizedGamepad;
import dev.frozenmilk.mercurial.Mercurial;

@Mercurial.Attach
@Chassis.Attach
@IntakeSlides.Attach
@OuttakeSlides.Attach
@Intake.Attach
@Outtake.Attach
public class DairyTeleOp extends OpMode {
    private Chassis chassis;
    private IntakeSlides intakeSlides;
    private OuttakeSlides outtakeSlides;
    private Intake intake;
    private Outtake outtake;
    private PasteurizedGamepad<EnhancedDoubleSupplier, EnhancedBooleanSupplier> tejas;
    private PasteurizedGamepad<EnhancedDoubleSupplier, EnhancedBooleanSupplier> arvind;

    @Override
    public void init() {
        tejas = Pasteurized.gamepad1();
        arvind = Pasteurized.gamepad2();
    }


    @Override
    public void loop() {
        // keybinds go here, see this example for reference
        // https://github.com/Iris-TheRainbow/27971-IntoTheDeep-Teamcode/tree/main
        // put the controls here instead of the init method like the example
    }
}
