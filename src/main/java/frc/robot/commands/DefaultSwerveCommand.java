package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.SK23Drive;
import frc.robot.utils.filters.FilteredJoystick;

public class DefaultSwerveCommand extends CommandBase
{
    private FilteredJoystick  controller;
    private Supplier<Boolean> robotCentric;
    private SK23Drive         subsystem;

    public DefaultSwerveCommand(FilteredJoystick controller, Supplier<Boolean> robotCentric,
        SK23Drive drive)
    {
        this.controller = controller;
        this.robotCentric = robotCentric;
        this.subsystem = drive;

        addRequirements(drive);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        subsystem.drive(
                // Left Y Axis
                controller.getFilteredAxis(OIConstants.kVelocityYPort),
                // Left X Axis
                controller.getFilteredAxis(OIConstants.kVelocityXPort),
                // Right X Axis
                controller.getFilteredAxis(OIConstants.kVelocityOmegaPort),
                !robotCentric.get());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted)
    {
        subsystem.drive(0, 0, 0, false);
    }
}
