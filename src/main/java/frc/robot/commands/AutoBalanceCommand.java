package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK23Drive;

/**
 * A command that is used to translate the robot in the correct direction to balance on
 * the charge station. Uses the pitch and roll of the robot, which needs to be calibrated
 * before hand to match the definition and coordinate system of pitch and roll.
 */
public class AutoBalanceCommand extends CommandBase
{
    private SK23Drive        subsystem;
    private Supplier<Double> rotation;
    private PIDController    xPID;
    private PIDController    yPID;
    private double           maxSpeed      = 2.5;
    private double           angleDeadband = 2.5;

    /**
     * Creates a command used to balance the robot on the charge station using the pitch
     * and roll
     * 
     * @param controller
     * @param drive
     */
    public AutoBalanceCommand(Supplier<Double> rotation, SK23Drive drive)
    {
        this.subsystem = drive;
        this.rotation = rotation;

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
        if (DriverStation.isTeleop())
        {
            subsystem.drive(xSpeed, ySpeed, rotation.get(), false);
        }
        else
        {
            subsystem.drive(xSpeed, ySpeed, 0, false);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted)
    {
        subsystem.drive(0, 0, 0, false);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return DriverStation.isAutonomousEnabled() && Math.hypot(subsystem.getPitch(), subsystem.getRoll()) < angleDeadband;
    }
}
