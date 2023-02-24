package frc.robot.bindings;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Ports.OperatorPorts;
import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.DefaultSwerveCommand;
import frc.robot.commands.DriveTurnCommand;
import frc.robot.subsystems.SK23Drive;
import frc.robot.utils.filters.CubicDeadbandFilter;
import frc.robot.utils.filters.FilteredJoystick;

public class SK23DriveBinder implements CommandBinder
{
    SK23Drive subsystem;

    // Driver button commands
    private final JoystickButton resetGyro;
    private final JoystickButton robotCentric;
    private final JoystickButton autoBalance;
    private final JoystickButton rotateDSS;
    private final JoystickButton rotateGrid;

    FilteredJoystick             controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23DriveBinder(FilteredJoystick controller, SK23Drive subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        resetGyro = new JoystickButton(controller, OperatorPorts.kResetGyro);
        robotCentric = new JoystickButton(controller, OperatorPorts.kRobotCentricMode);
        autoBalance = new JoystickButton(controller, 10);
        rotateDSS = new JoystickButton(controller, OperatorPorts.kRotateDSS);
        rotateGrid = new JoystickButton(controller, OperatorPorts.kRotateGrid);

    }

    public void bindButtons()
    {
        controller.setFilter(OperatorPorts.kVelocityXPort,
            new CubicDeadbandFilter(OIConstants.kDriveGain, OIConstants.kJoystickDeadband,
                DriveConstants.kMaxSpeedMetersPerSecond, true));

        controller.setFilter(OperatorPorts.kVelocityYPort,
            new CubicDeadbandFilter(OIConstants.kDriveGain, OIConstants.kJoystickDeadband,
                DriveConstants.kMaxSpeedMetersPerSecond, true));

        controller.setFilter(OperatorPorts.kVelocityOmegaPort,
            new CubicDeadbandFilter(OIConstants.kRotationGain, OIConstants.kJoystickDeadband,
                Math.toRadians(ModuleConstants.kMaxModuleAngularSpeedDegreesPerSecond), true));

        resetGyro.onTrue(new InstantCommand(subsystem::zeroHeading));

        autoBalance.whileTrue(new AutoBalanceCommand(() -> controller.getFilteredAxis(OperatorPorts.kVelocityOmegaPort), subsystem));
        rotateDSS.whileTrue(new DriveTurnCommand(controller, robotCentric::getAsBoolean, 0, subsystem));
        rotateGrid.whileTrue(new DriveTurnCommand(controller, robotCentric::getAsBoolean, 180, subsystem));

        subsystem.setDefaultCommand(new DefaultSwerveCommand(controller, robotCentric::getAsBoolean, subsystem));
    }
}
