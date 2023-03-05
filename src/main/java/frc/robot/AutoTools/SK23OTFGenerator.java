package frc.robot.AutoTools;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.SK23Drive;

/**
 * A simple tool to create trajectory of the robot for On The Fly (OTF) path generation
 */
public class SK23OTFGenerator
{

    private SK23Drive drive;

    public SK23OTFGenerator(SK23Drive driveSubsystem)
    {
        drive = driveSubsystem;
    }

    /**
     * Creates the trajectory of the OTF path using the current robot state and the
     * desired final robot state
     * 
     * @param finalPos
     *            The desired final position of the robot
     * @return A PathPlanner trajectory bringing the robot from its current position to
     *         the desired final position
     */
    public PathPlannerTrajectory generatePath(GridPositions finalPos)
    {
        Pose2d curPos = drive.getPose();
        ChassisSpeeds curState = drive.getChassisState();

        return PathPlanner.generatePath(AutoConstants.kPathConstraints,
            PathPoint.fromCurrentHolonomicState(curPos, curState),
            finalPos.getPathPose(DriverStation.getAlliance()));
    }

}
