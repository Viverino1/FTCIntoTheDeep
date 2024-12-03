package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Utils;
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
import dev.frozenmilk.mercurial.bindings.BoundGamepad;
import dev.frozenmilk.mercurial.commands.groups.Advancing;
import dev.frozenmilk.mercurial.commands.groups.Parallel;
import dev.frozenmilk.mercurial.commands.groups.Sequential;

@Mercurial.Attach
@Chassis.Attach
@IntakeSlides.Attach
@OuttakeSlides.Attach
@Intake.Attach
@Outtake.Attach
@TeleOp
public class DairyTeleOp extends OpMode {
    private BoundGamepad tejas;
    private BoundGamepad arvind;

    @Override
    public void init() {
        tejas = Mercurial.gamepad1();
        arvind = Mercurial.gamepad2();


//        tejas.triangle().onTrue(new Sequential(
//                Outtake.closeClaw(),
//                new Parallel(
//                        OuttakeSlides.setTargetPos(OuttakeSlides.bucketPos),
//                        Outtake.setArm(Outtake.armBucketPos),
//                        Outtake.setPivot(Outtake.pivotBucketPos)
//                )
//        ));
//
//
//        tejas.circle().onTrue(new Sequential(
//                Outtake.closeClaw(),
//                new Parallel(
//                        OuttakeSlides.setTargetPos(OuttakeSlides.submirsiblePos),
//                        Outtake.setArm(Outtake.armSubmersiblePos),
//                        Outtake.setPivot(Outtake.pivotSubmersiblePos)
//                )
//        ));
//
//
//        if (tejas.square().state() && Utils.isWithinTolerance(OuttakeSlides.getPos(), OuttakeSlides.submirsiblePos, 10)) {
//            new Sequential(
//                    OuttakeSlides.setTargetPos(OuttakeSlides.scoreSubmersiblePos),
//                    Outtake.openClaw()
//            );
//        }
//
//        tejas.cross().onTrue(
//                new Sequential(
//                        new Parallel(
//                                OuttakeSlides.returnLeft(),
//                                OuttakeSlides.returnRight(),
//                                Outtake.setArm(Outtake.armHomePos),
//                                Outtake.setPivot(Outtake.pivotHomePos)
//                        ),
//                        Outtake.openClaw()
//                )
//        );
//
//        tejas.rightBumper().onTrue(Outtake.openClaw());
//
//        tejas.dpadUp().whileTrue( OuttakeSlides.setPower(1) );
//        tejas.dpadDown().whileTrue( OuttakeSlides.setPower(-1) );
//
//        tejas.rightBumper().onTrue( Chassis.toggleSlow() );
//
//        arvind.rightBumper().whileTrue( Intake.spintake(1) );
//        arvind.leftBumper().whileTrue( Intake.spintake(-1) );
//
//        arvind.cross().onTrue(
//                new Sequential(
//                        Intake.raiseIntake(),
//                        IntakeSlides.setPosition(IntakeSlides.minPos)
//                )
//        );
//        arvind.triangle().onTrue( Intake.dropIntake() );
    }


    @Override
    public void loop() {
        // https://github.com/Iris-TheRainbow/27971-IntoTheDeep-Teamcode/tree/main
    }
}
