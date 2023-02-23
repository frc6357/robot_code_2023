package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK23Drive;

public class AutoBalanceCommand extends CommandBase
{
    private SK23Drive     subsystem;
    private PIDController xPID;
    private PIDController yPID;
    private double maxSpeed = 0.5;

    public AutoBalanceCommand(SK23Drive drive)
    {
        this.subsystem = drive;

        xPID = new PIDController(0.05, 0, 0, 0.02);
        yPID = new PIDController(0.05, 0, 0, 0.02);

        addRequirements(subsystem);
    }

    @Override
    public void initialize()
    {
        xPID.setSetpoint(0);
        yPID.setSetpoint(0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        double xSpeed = xPID.calculate(subsystem.getPitch());
        double ySpeed = -yPID.calculate(subsystem.getRoll());

        double totalSpeed = Math.hypot(xSpeed, ySpeed);
        double scaleFactor = Math.abs(totalSpeed) > maxSpeed ? maxSpeed / totalSpeed : 1;
        xSpeed *= scaleFactor;
        ySpeed *= scaleFactor;

        // Use the PID controllers to control the robot relative to itself,
        // NOT in field relative mode, as it is using robot angles.
        subsystem.drive(xSpeed, ySpeed, 0, false);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted)
    {
        subsystem.drive(0, 0, 0, false);
    }
}
