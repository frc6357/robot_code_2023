package frc.robot.bindings;

import static frc.robot.Constants.OIConstants.*;
import static frc.robot.Ports.OperatorPorts.*;
import static frc.robot.AutoTools.GridPositions.*;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
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

public class SK23DriveBinder implements CommandBinder
{
    Optional<SK23Drive> subsystem;

    // Driver Buttons
    private final Trigger robotCentric;
    private final Trigger autoBalance;
    private final Trigger rotateDSS;
    private final Trigger rotateGrid;
    private final Trigger rotateLeft;
    private final Trigger rotateRight;

    // Gyro Reset Buttons
    private final Trigger resetGyroDSS;
    private final Trigger resetGyroGrid;
    private final Trigger resetGyroLeft;
    private final Trigger resetGyroRight;

    // Buttons for On The Fly Driving
    private final Trigger GridLeftModifier;
    private final Trigger GridRightModifier;
    private final Trigger GPLeftButton;
    private final Trigger GPMiddleButton;
    private final Trigger GPRightButton;

    private final Trigger slowmode;

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

        resetGyroDSS = new POVButton(controller.getHID(), kResetGyroDSS);
        resetGyroGrid = new POVButton(controller.getHID(), kResetGyroGrid);
        resetGyroLeft = new POVButton(controller.getHID(), kResetGyroLeft);
        resetGyroRight = new POVButton(controller.getHID(), kResetGyroRight);

        robotCentric = controller.rightTrigger();
        autoBalance = new JoystickButton(controller.getHID(), kAutoLevel.value);

        rotateDSS = new JoystickButton(controller.getHID(), kRotateDSS.value);
        rotateGrid = new JoystickButton(controller.getHID(), kRotateGrid.value);
        rotateLeft = new JoystickButton(controller.getHID(), kRotateLeft.value);
        rotateRight = new JoystickButton(controller.getHID(), kRotateRight.value);

        GridLeftModifier = new JoystickButton(controller.getHID(), kGridLeftModifier.value);
        GridRightModifier = new JoystickButton(controller.getHID(), kGridRightModifier.value);
        GPLeftButton = new JoystickButton(controller.getHID(), kGPLeftButton.value);
        GPMiddleButton = new JoystickButton(controller.getHID(), kGPMiddleButton.value);
        GPRightButton = new JoystickButton(controller.getHID(), kGPRightButton.value);

        slowmode = controller.leftTrigger();
    }

    public void bindButtons()
    {
        if (subsystem.isPresent())
        {
            SK23Drive drive = subsystem.get();

            // Sets filters for driving axes
            controller.setFilter(kVelocityXPort.value, new CubicDeadbandFilter(kDriveGain,
                kJoystickDeadband, DriveConstants.kMaxSpeedMetersPerSecond, true));

            controller.setFilter(kVelocityYPort.value, new CubicDeadbandFilter(kDriveGain,
                kJoystickDeadband, DriveConstants.kMaxSpeedMetersPerSecond, true));

            controller.setFilter(kVelocityOmegaPort.value,
                new CubicDeadbandFilter(kRotationGain, kJoystickDeadband,
                    Math.toRadians(ModuleConstants.kMaxModuleAngularSpeedDegreesPerSecond), true));

            // Resets gyro angles
            resetGyroDSS.onTrue(new InstantCommand(() -> {drive.setHeading(0);}));
            resetGyroGrid.onTrue(new InstantCommand(() -> {drive.setHeading(180);}));
            resetGyroLeft.onTrue(new InstantCommand(() -> {drive.setHeading(90);}));
            resetGyroRight.onTrue(new InstantCommand(() -> {drive.setHeading(270);}));

            // Advanced features
            autoBalance.whileTrue(new AutoBalanceCommand(
                () -> controller.getFilteredAxis(kVelocityOmegaPort.value), drive));
            rotateDSS
                .whileTrue(new DriveTurnCommand(controller, robotCentric::getAsBoolean, 0, drive));
            rotateGrid.whileTrue(
                new DriveTurnCommand(controller, robotCentric::getAsBoolean, 180, drive));
            rotateLeft.whileTrue(
                    new DriveTurnCommand(controller, robotCentric::getAsBoolean, 90, drive));
            rotateRight.whileTrue(
                new DriveTurnCommand(controller, robotCentric::getAsBoolean, 270, drive));

            // Default command for driving
            drive.setDefaultCommand(
                new DefaultSwerveCommand(controller, robotCentric::getAsBoolean, drive));

            configureOTFCommands(drive);
        }
    }

    /**
     * Creates all the on the fly command bindings
     * 
     * @param drive The drive subsystem requirement for the commands.
     */
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

    public Command setGainCommand(double gain)
    {
        return null;
    }
}
