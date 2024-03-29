package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.SK23Drive;

/**
 * A command used to turn the robot to a certain angle. This allows the driver to set the
 * robot angle to a set position while maintaining translation driving abilities.
 */
public class DriveTurnCommand extends CommandBase
{
    private Supplier<Double> xSpeed;
    private Supplier<Double> ySpeed;

    private Supplier<Boolean>      robotCentric;
    private SK23Drive              subsystem;
    private PIDController          PID;

    // In radians per second
    private double maxRot = 4;

    /**
     * Creates a command that turns the robot to a specified angle using the field
     * coordinate system
     * 
     * @param xSpeed
     *            The supplier for the robot x axis speed
     * @param ySpeed
     *            The supplier for the robot y axis speed
     * @param robotCentric
     *            Whether or not the drive mode is in robot or field centric mode
     * @param setpoint
     *            The desired angle [0º, 360º] using the field coordinate system
     * @param drive
     *            The subsystem required to control the drivetrain
     */
    public DriveTurnCommand(Supplier<Double> xSpeed, Supplier<Double> ySpeed,
        Supplier<Boolean> robotCentric, double setpoint, SK23Drive drive)
    {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        this.robotCentric = robotCentric;
        this.subsystem = drive;

        PID = new PIDController(0.1, 0, 0, 0.02);
        PID.enableContinuousInput(-180, 180);
        PID.setSetpoint(MathUtil.inputModulus(setpoint, -180, 180));

        addRequirements(subsystem);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        double rot = PID.calculate(subsystem.getPose().getRotation().getDegrees());
        rot = Math.abs(rot) > maxRot ? Math.copySign(maxRot, rot) : rot;

        subsystem.drive(xSpeed.get(), ySpeed.get(), rot, !robotCentric.get());
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
        return false;
    }
}
