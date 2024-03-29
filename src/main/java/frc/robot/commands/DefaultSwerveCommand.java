package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.SK23Drive;

public class DefaultSwerveCommand extends CommandBase
{
    private Supplier<Double> xSpeed;
    private Supplier<Double> ySpeed;
    private Supplier<Double> omegaSpeed;

    private Supplier<Boolean>      robotCentric;
    private SK23Drive              subsystem;

    /**
     * Creates a command that turns the robot to a specified angle using the field
     * coordinate system
     * 
     * @param xSpeed
     *            The supplier for the robot x axis speed
     * @param ySpeed
     *            The supplier for the robot y axis speed
     * @param omegaSpeed
     *            The supplier for the robot rotational speed
     * @param robotCentric
     *            Whether or not the drive mode is in robot or field centric mode
     * @param drive
     *            The subsystem required to control the drivetrain
     */
    public DefaultSwerveCommand(Supplier<Double> xSpeed, Supplier<Double> ySpeed, Supplier<Double> omegaSpeed,
        Supplier<Boolean> robotCentric, SK23Drive drive)
    {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.omegaSpeed = omegaSpeed;

        this.robotCentric = robotCentric;
        this.subsystem = drive;

        addRequirements(drive);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        subsystem.drive(xSpeed.get(), ySpeed.get(), omegaSpeed.get(), !robotCentric.get());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted)
    {
        subsystem.drive(0, 0, 0, false);
    }
}
