package frc.robot.AutoTools;

import com.pathplanner.lib.PathPoint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 * A set of enums representing the positions on the field for scoring
 */
public enum GridPositions
{
    LeftGrid_LeftCone(4.98),
    LeftGrid_MiddleCube(4.42),
    LeftGrid_RightCone(3.86),

    MiddleGrid_LeftCone(3.31),
    MiddleGrid_MiddleCube(2.74),
    MiddleGrid_RightCone(2.18),

    RightGrid_LeftCone(1.62),
    RightGrid_MiddleCube(1.05),
    RightGrid_RightCone(0.5);


    private final double fieldWidth = Units.inchesToMeters(315.5);
    private final double lengthFromFieldBottom;
    private final double lengthFromSide = 1.9;

    private GridPositions(double length)
    {
        this.lengthFromFieldBottom = length;
    }

    /**
     * Gets the position of the field needed to score for a certain scoring location
     * depending on the alliance color
     * 
     * @param color
     *            The color of the alliance
     * 
     * @return The Pose2d of the scoring location depending on the alliance color
     */
    public Pose2d getPose(Alliance color)
    {
        if (color.equals(Alliance.Red))
        {
            return new Pose2d(lengthFromSide, fieldWidth - lengthFromFieldBottom,
                new Rotation2d(180));
        }
        else
        {
            return new Pose2d(lengthFromSide, lengthFromFieldBottom, new Rotation2d(180));
        }
    }

    /**
     * Gets the final auto state needed to score for a certain scoring location depending
     * on the alliance color
     * 
     * @param color
     *            The color of the alliance
     * 
     * @return The final path point of the scoring location depending on the alliance
     *         color
     */
    public PathPoint getPathPose(Alliance color)
    {
        Pose2d pos = getPose(color);
        return new PathPoint(pos.getTranslation(), new Rotation2d(180), pos.getRotation());
    }
}
