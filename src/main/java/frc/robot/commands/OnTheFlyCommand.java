package frc.robot.commands;

import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.AutoTools.GridPositions;
import frc.robot.AutoTools.SK23OTFGenerator;
import frc.robot.subsystems.SK23Drive;

/**
 * Moves the robot from the current position to a desired final position for scoring. This
 * essentially runs an autonomous command during telop, which means that the command logic
 * is left to the PathPlanner library.
 */
public class OnTheFlyCommand extends CommandBase
{

    private SK23Drive        subsystem;
    private SK23OTFGenerator pathGenerator;
    private GridPositions    finalPos;
    private Command          trajCommand;

    public OnTheFlyCommand(GridPositions position, SK23Drive drive)
    {
        this.subsystem = drive;
        finalPos = position;

        pathGenerator = new SK23OTFGenerator(drive);

        addRequirements(drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        trajCommand = new PPSwerveControllerCommand(
            pathGenerator.generatePath(finalPos),
            subsystem::getPose,
            DriveConstants.kDriveKinematics,
            new PIDController(0, 0, 0),
            new PIDController(0, 0, 0),
            new PIDController(0, 0, 0),
            subsystem::setModuleStates,
            false,
            subsystem);

        trajCommand.initialize();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        trajCommand.execute();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted)
    {
        trajCommand.end(interrupted);
        subsystem.drive(0, 0, 0, false);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return trajCommand.isFinished();
    }
}
