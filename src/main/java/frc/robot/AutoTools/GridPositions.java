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
    LeftGrid_LeftCone(8, 0),
    LeftGrid_MiddleCube(7, 1),
    LeftGrid_RightCone(6, 2),

    MiddleGrid_LeftCone(5, 3),
    MiddleGrid_MiddleCube(4, 4),
    MiddleGrid_RightCone(3, 5),

    RightGrid_LeftCone(2, 6),
    RightGrid_MiddleCube(1, 7),
    RightGrid_RightCone(0, 8);

    private final double fieldWidth = Units.inchesToMeters(315.5);
    private final double lengthFromSide = 1.9;

    /**
     * The position of the scoring position from the bottom of the field when using the
     * blue alliance controller mapping
     */
    private final int      positionBlue;
    /**
     * The position of the scoring position from the bottom of the field when using the
     * blue alliance controller mapping
     */
    private final int      positionRed;
    /**
     * An array that takes in the position from the bottom and hands back the distance
     * from the field bottom
     */
    private final double[] positionFromBottom =
            {0.5, 1.05, 1.62, 2.18, 2.74, 3.31, 3.86, 4.42, 4.98};

    /**
     * Initializes the enum to contain the position of the grid for both alliance colors
     * 
     * @param positionFromBottomBlue
     *            The position of the scoring location from the bottom of the field when
     *            standing at the blue alliance driver station
     * @param positionFromBottomRed
     *            The position of the scoring location from the bottom of the field when
     *            standing at the red alliance driver station
     */
    private GridPositions(int positionFromBottomBlue, int positionFromBottomRed)
    {
        this.positionBlue = positionFromBottomBlue;
        this.positionRed = positionFromBottomRed;
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
        double yPos;

        if (color.equals(Alliance.Red))
        {
            yPos = fieldWidth - positionFromBottom[positionRed];
        }
        else
        {
            yPos = positionFromBottom[positionBlue];
        }

        return new Pose2d(lengthFromSide, yPos, new Rotation2d(180));
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
