package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.util.dairy.subsystems.Chassis.follower;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.dairy.Paths;
import org.firstinspires.ftc.teamcode.util.dairy.Robot;
import org.firstinspires.ftc.teamcode.util.dairy.features.LoopTimes;
import org.firstinspires.ftc.teamcode.util.dairy.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.util.dairy.subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.dairy.subsystems.IntakeSlides;
import org.firstinspires.ftc.teamcode.util.dairy.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.util.dairy.subsystems.OuttakeSlides;
import org.firstinspires.ftc.teamcode.util.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.util.pedroPathing.constants.LConstants;

import dev.frozenmilk.dairy.core.util.features.BulkRead;
import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.commands.groups.Parallel;
import dev.frozenmilk.mercurial.commands.groups.Sequential;
import dev.frozenmilk.mercurial.commands.util.IfElse;
import dev.frozenmilk.mercurial.commands.util.Wait;

@Mercurial.Attach
@Chassis.Attach
@Outtake.Attach
@OuttakeSlides.Attach
@Intake.Attach
@IntakeSlides.Attach
@LoopTimes.Attach
@BulkRead.Attach
@Autonomous
public class OnePlusThreeSpec extends OpMode {

    @Override
    public void init() {
        Robot.init();
        Outtake.setClaw(Outtake.clawClosePos);
        Outtake.isClawOpen = false;
        Outtake.setPosition(Outtake.armSpecPos);
        Outtake.setPivotManual(Outtake.pivotSpecPos);

        Robot.stateMachine.setState(Robot.State.INTAKE_SPEC);
    }

    @Override
    public void loop() {
    }

    @Override
    public void start() {
        new Sequential(
                OuttakeSlides.runToPosition(OuttakeSlides.submirsiblePos),

                // Preload
                new Parallel(
                        Robot.setState(Robot.State.OUTTAKE_SUBMERSIBLE),
                        Chassis.followPath(Paths.plusFourSpec.get(0))
                ),
                Robot.setState(Robot.State.OUTTAKE_SUBMERSIBLE_SCORE),
                new Wait(0.5),
                Outtake.openClaw(),

                // Pushing samples
                new Parallel(
                        Chassis.followPath(Paths.plusFourSpec.get(1)),
                        Robot.setState(Robot.State.INTAKE_SPEC)
                ),
                Chassis.followPath(Paths.plusFourSpec.get(2)),
                Chassis.followPath(Paths.plusFourSpec.get(3)),
                Chassis.followPath(Paths.plusFourSpec.get(4)),
                Chassis.followPath(Paths.plusFourSpec.get(5)),
                Chassis.followPath(Paths.plusFourSpec.get(6)),
                Chassis.followPath(Paths.plusFourSpec.get(7)),

                // plus 1 intake
                new Wait(0.5),
                Outtake.closeClaw(),

                // plus 1 outtake
                new Parallel(
                        Robot.setState(Robot.State.OUTTAKE_SUBMERSIBLE),
                        Chassis.followPath(Paths.plusFourSpec.get(8))
                ),
                Robot.setState(Robot.State.OUTTAKE_SUBMERSIBLE_SCORE),
                Chassis.followPath(Paths.plusFourSpec.get(9)),
                new Wait(0.5),
                Outtake.openClaw(),

                // plus 2 intake
                new Parallel(
                        Robot.setState(Robot.State.INTAKE_SPEC),
                        Chassis.followPath(Paths.plusFourSpec.get(10))
                ),
                new Wait(0.5),
                Outtake.closeClaw(),

                // plus 2 outtake
                new Parallel(
                        Robot.setState(Robot.State.OUTTAKE_SUBMERSIBLE),
                        Chassis.followPath(Paths.plusFourSpec.get(11))
                ),
                Robot.setState(Robot.State.OUTTAKE_SUBMERSIBLE_SCORE),
                Chassis.followPath(Paths.plusFourSpec.get(12)),
                new Wait(0.5),
                Outtake.openClaw(),

                // plus 3 intake
                new Parallel(
                        Robot.setState(Robot.State.INTAKE_SPEC),
                        Chassis.followPath(Paths.plusFourSpec.get(13))
                ),
                new Wait(0.5),
                Outtake.closeClaw(),

                // plus 3 outtake
                new Parallel(
                        Robot.setState(Robot.State.OUTTAKE_SUBMERSIBLE),
                        Chassis.followPath(Paths.plusFourSpec.get(14))
                ),
                Robot.setState(Robot.State.OUTTAKE_SUBMERSIBLE_SCORE),
                Chassis.followPath(Paths.plusFourSpec.get(15)),
                new Wait(0.5),
                Outtake.openClaw(),

                // park
                new Parallel(
                        Robot.setState(Robot.State.INTAKE_SPEC),
                        Chassis.followPath(Paths.parkWithSpecPush)
                )
        )
                .schedule();
    }
}