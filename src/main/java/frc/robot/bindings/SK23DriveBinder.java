package frc.robot.bindings;

import static frc.robot.Constants.OIConstants.kDriveGain;
import static frc.robot.Constants.OIConstants.kJoystickDeadband;
import static frc.robot.Constants.OIConstants.kRotationGain;
import static frc.robot.Ports.OperatorPorts.kAutoLevel;
import static frc.robot.Ports.OperatorPorts.kResetGyro;
import static frc.robot.Ports.OperatorPorts.kRotateDSS;
import static frc.robot.Ports.OperatorPorts.kRotateGrid;
import static frc.robot.Ports.OperatorPorts.kVelocityOmegaPort;
import static frc.robot.Ports.OperatorPorts.kVelocityXPort;
import static frc.robot.Ports.OperatorPorts.kVelocityYPort;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.DefaultSwerveCommand;
import frc.robot.commands.DriveTurnCommand;
import frc.robot.subsystems.SK23Drive;
import frc.robot.utils.filters.CubicDeadbandFilter;
import frc.robot.utils.filters.FilteredXboxController;

public class SK23DriveBinder implements CommandBinder
{
    Optional<SK23Drive> subsystem;

    // Driver button commands
    private final JoystickButton resetGyro;
    private final Trigger robotCentric;
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
    public SK23DriveBinder(FilteredXboxController controller, Optional<SK23Drive> subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        resetGyro = new JoystickButton(controller.getHID(), kResetGyro.value);
        robotCentric = controller.rightTrigger();
        autoBalance = new JoystickButton(controller.getHID(), kAutoLevel.value);
        rotateDSS = new JoystickButton(controller.getHID(), kRotateDSS.value);
        rotateGrid = new JoystickButton(controller.getHID(), kRotateGrid.value);

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
        }
    }
}
