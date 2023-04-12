package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK23Drive;
import frc.robot.subsystems.SK23Vision;

public class CenterPoleCommand extends CommandBase
{
    private SK23Drive  drive;
    private SK23Vision vision;

    private Supplier<Double> manualSpeed;
    private PIDController    rotPID;
    private PIDController    transPID;

    // In radians per second
    private double maxRot = 4;

    public CenterPoleCommand(Supplier<Double> forwardSpeed, SK23Drive drive, SK23Vision vision)
    {
        this.drive = drive;
        this.vision = vision;

        manualSpeed = forwardSpeed;

        rotPID = new PIDController(0.1, 0, 0, 0.02);
        rotPID.enableContinuousInput(-180, 180);
        rotPID.setSetpoint(180);

        transPID = new PIDController(0.1, 0, 0, 0.02);
        transPID.setSetpoint(0);

        addRequirements(drive, vision);
    }

    @Override
    public void initialize()
    {
        vision.setPoleMode();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        double rot = rotPID.calculate(drive.getPose().getRotation().getDegrees());
        rot = Math.abs(rot) > maxRot ? Math.copySign(maxRot, rot) : rot;

        if (vision.isTargetPresent())
        {
            drive.drive(manualSpeed.get(), transPID.calculate(vision.getHorizontalOffset()), rot,
                true);
        }
        else
        {
            drive.drive(manualSpeed.get(), 0.0, rot, true);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted)
    {
        vision.setDriverCamMode();
        drive.drive(0.0, 0.0, 0.0, false);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}
