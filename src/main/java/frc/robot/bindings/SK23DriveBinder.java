package frc.robot.bindings;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;

import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.DefaultSwerveCommand;
import frc.robot.commands.DriveTurnCommand;
import frc.robot.subsystems.SK23Drive;
import frc.robot.utils.filters.CubicDeadbandFilter;
import frc.robot.utils.filters.FilteredXboxController;

import static frc.robot.Constants.OIConstants.*;
import static frc.robot.Ports.OperatorPorts.*;

public class SK23DriveBinder implements CommandBinder
{
    SK23Drive subsystem;

    // Driver button commands
    private final JoystickButton resetGyro;
    private final JoystickButton robotCentric;
    private final JoystickButton autoBalance;
    private final JoystickButton rotateDSS;
    private final JoystickButton rotateGrid;

    FilteredXboxController controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23DriveBinder(FilteredXboxController controller, SK23Drive subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        resetGyro = new JoystickButton(controller, kResetGyro.value);
        robotCentric = new JoystickButton(controller, kRobotCentricMode.value);
        autoBalance = new JoystickButton(controller, kAutoLevel.value);
        rotateDSS = new JoystickButton(controller, kRotateDSS.value);
        rotateGrid = new JoystickButton(controller, kRotateGrid.value);

    }

    public void bindButtons()
    {
        controller.setFilter(kVelocityXPort.value, new CubicDeadbandFilter(kDriveGain,
            kJoystickDeadband, DriveConstants.kMaxSpeedMetersPerSecond, true));

        controller.setFilter(kVelocityYPort.value, new CubicDeadbandFilter(kDriveGain,
            kJoystickDeadband, DriveConstants.kMaxSpeedMetersPerSecond, true));

        controller.setFilter(kVelocityOmegaPort.value,
            new CubicDeadbandFilter(kRotationGain, kJoystickDeadband,
                Math.toRadians(ModuleConstants.kMaxModuleAngularSpeedDegreesPerSecond), true));

        resetGyro.onTrue(new InstantCommand(subsystem::zeroHeading));

        autoBalance.whileTrue(new AutoBalanceCommand(
            () -> controller.getFilteredAxis(kVelocityOmegaPort.value), subsystem));
        rotateDSS
            .whileTrue(new DriveTurnCommand(controller, robotCentric::getAsBoolean, 0, subsystem));
        rotateGrid.whileTrue(
            new DriveTurnCommand(controller, robotCentric::getAsBoolean, 180, subsystem));

        subsystem.setDefaultCommand(
            new DefaultSwerveCommand(controller, robotCentric::getAsBoolean, subsystem));
    }
}
