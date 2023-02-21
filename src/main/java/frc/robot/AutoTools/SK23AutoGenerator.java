package frc.robot.AutoTools;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.DoNothingCommand;
import frc.robot.subsystems.SK23Drive;
import frc.robot.utils.files.FileScanner;

public class SK23AutoGenerator
{

    private SK23Drive         driveSubsystem;
    private SwerveAutoBuilder autoBuilder;

    // An event map connects a marker name to a given command that will run
    HashMap<String, Command> eventMap = new HashMap<>();

    public SK23AutoGenerator(SK23Drive driveSubsystem)
    {
        this.driveSubsystem = driveSubsystem;

        createAutoBuilder(this.driveSubsystem);
        createEventMap();
    }

    private void createAutoBuilder(SK23Drive driveSubsystem)
    {
        // Create the AutoBuilder. Used to generate full auto paths using PathPlannerLib
        autoBuilder = new SwerveAutoBuilder(driveSubsystem::getPose,                // Pose2d supplier
            driveSubsystem::resetOdometry,          // Pose2d consumer, used to reset odometry at the beginning of auto
            DriveConstants.kDriveKinematics,        // SwerveDriveKinematics
            AutoConstants.kTranslationPIDConstants, // PID constants to correct for translation error (used to create the X and Y PID controllers)
            AutoConstants.kRotationPIDConstants,    // PID constants to correct for rotation error (used to create the rotation controller)
            driveSubsystem::setModuleStates,        // Module states consumer used to output to the drive subsystem
            eventMap,                               // Correlates marker names to actual commands
            true,                                   // Should the path be automatically mirrored depending on alliance color.
            driveSubsystem);                        // The drive subsystem. Used to set requirements of path following commands
    }

    private void createEventMap()
    {
        // eventMap.put("marker1", new PrintCommand("Passed marker 1"));
    }

    /**
     * Finds all the files in the pathplanner folder in the deploy directory and creates
     * auto commands from all present files. Displays the found paths as desired by the
     * function
     * 
     * @param displayMethod
     *            The function that is used to add the auto path names and commands
     */
    public void displayAllPathCommands(BiConsumer<String, Command> displayMethod)
    {
        displayMethod.accept("None", new DoNothingCommand());
        File pathplannerFolder = FileScanner.getFileFromDeploy(AutoConstants.kSplineDirectory);
        FileScanner.applyStringFunctionByType(pathplannerFolder, ".path",
            (autoName) -> createPathCommand(autoName, displayMethod), false);
    }

    /**
     * Creates a command using the autobuilder and adds it to the display options
     * 
     * @param autoName
     *            The name of the auto path
     * @param displayMethod
     *            The function that is used to add the auto path name and command
     */
    private void createPathCommand(String autoName, BiConsumer<String, Command> displayMethod)
    {
        // Create the command if possible
        try
        {
            final List<PathPlannerTrajectory> trajectory =
                    PathPlanner.loadPathGroup(autoName, AutoConstants.kPathConstraints);
            displayMethod.accept(autoName, autoBuilder.fullAuto(trajectory));
        }
        catch (Exception e)
        {
            DriverStation.reportWarning(autoName + ".path could not be generated", true);
        }
    }

}
