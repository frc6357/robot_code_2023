package frc.robot.bindings;

import static frc.robot.Constants.OIConstants.*;
import static frc.robot.Ports.OperatorPorts.*;
import static frc.robot.AutoTools.GridPositions.*;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.DefaultSwerveCommand;
import frc.robot.commands.DriveTurnCommand;
import frc.robot.commands.OnTheFlyCommand;
import frc.robot.subsystems.SK23Drive;
import frc.robot.utils.filters.CubicDeadbandFilter;
import frc.robot.utils.filters.FilteredXboxController;
import frc.robot.utils.filters.SlewRateFilter;

public class SK23DriveBinder implements CommandBinder
{
    Optional<SK23Drive> subsystem;

    // Driver button commands
    private final JoystickButton resetGyro;
    private final Trigger robotCentric;
    private final JoystickButton autoBalance;
    private final JoystickButton rotateDSS;
    private final JoystickButton rotateGrid;

    // Buttons for On The Fly Driving
    private final JoystickButton GridLeftModifier;
    private final JoystickButton GridRightModifier;
    private final JoystickButton GPLeftButton;
    private final JoystickButton GPMiddleButton;
    private final JoystickButton GPRightButton;

    FilteredXboxController controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23DriveBinder(FilteredXboxController controller, Optional<SK23Drive> subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        resetGyro = new JoystickButton(controller.getHID(), kResetGyro.value);
        robotCentric = controller.rightTrigger();
        autoBalance = new JoystickButton(controller.getHID(), kAutoLevel.value);
        rotateDSS = new JoystickButton(controller.getHID(), kRotateDSS.value);
        rotateGrid = new JoystickButton(controller.getHID(), kRotateGrid.value);

        GridLeftModifier = new JoystickButton(controller.getHID(), kGridLeftModifier.value);
        GridRightModifier = new JoystickButton(controller.getHID(), kGridRightModifier.value);
        GPLeftButton = new JoystickButton(controller.getHID(), kGPLeftButton.value);
        GPMiddleButton = new JoystickButton(controller.getHID(), kGPMiddleButton.value);
        GPRightButton = new JoystickButton(controller.getHID(), kGPRightButton.value);

    }

    public void bindButtons()
    {
        if (subsystem.isPresent())
        {
            SK23Drive drive = subsystem.get();

            controller.setFilter(kVelocityXPort.value, new CubicDeadbandFilter(kDriveGain,
                kJoystickDeadband, DriveConstants.kMaxSpeedMetersPerSecond, true));

            controller.setFilter(kVelocityYPort.value, new CubicDeadbandFilter(kDriveGain,
                kJoystickDeadband, DriveConstants.kMaxSpeedMetersPerSecond, true));

            controller.setFilter(kVelocityOmegaPort.value,
                new CubicDeadbandFilter(kRotationGain, kJoystickDeadband,
                    Math.toRadians(ModuleConstants.kMaxModuleAngularSpeedDegreesPerSecond), true));

            resetGyro.onTrue(new InstantCommand(drive::zeroHeading));

            autoBalance.whileTrue(new AutoBalanceCommand(
                () -> controller.getFilteredAxis(kVelocityOmegaPort.value), drive));
            rotateDSS
                .whileTrue(new DriveTurnCommand(controller, robotCentric::getAsBoolean, 0, drive));
            rotateGrid.whileTrue(
                new DriveTurnCommand(controller, robotCentric::getAsBoolean, 180, drive));

            drive.setDefaultCommand(
                new DefaultSwerveCommand(controller, robotCentric::getAsBoolean, drive));

            configureOTFCommands(drive);
        }
    }

    private void configureOTFCommands(SK23Drive drive)
    {
        GridLeftModifier.and(GPLeftButton).whileTrue(new OnTheFlyCommand(LeftGrid_LeftCone, drive));
        GridLeftModifier.and(GPMiddleButton).whileTrue(new OnTheFlyCommand(LeftGrid_MiddleCube, drive));
        GridLeftModifier.and(GPRightButton).whileTrue(new OnTheFlyCommand(LeftGrid_RightCone, drive));

        GridLeftModifier.and(GridRightModifier).and(GPLeftButton).whileTrue(new OnTheFlyCommand(MiddleGrid_LeftCone, drive));
        GridLeftModifier.and(GridRightModifier).and(GPMiddleButton).whileTrue(new OnTheFlyCommand(MiddleGrid_MiddleCube, drive));
        GridLeftModifier.and(GridRightModifier).and(GPRightButton).whileTrue(new OnTheFlyCommand(MiddleGrid_RightCone, drive));

        GridRightModifier.and(GPLeftButton).whileTrue(new OnTheFlyCommand(RightGrid_LeftCone, drive));
        GridRightModifier.and(GPMiddleButton).whileTrue(new OnTheFlyCommand(RightGrid_MiddleCube, drive));
        GridRightModifier.and(GPRightButton).whileTrue(new OnTheFlyCommand(RightGrid_RightCone, drive));
    }
}
