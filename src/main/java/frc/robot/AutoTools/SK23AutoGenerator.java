package frc.robot.AutoTools;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.SK23Drive;

public class SK23AutoGenerator {

    private SK23Drive driveSubsystem;
    private SwerveAutoBuilder autoBuilder;

    // An event map connects a marker name to a given command that will run
    HashMap<String, Command> eventMap = new HashMap<>();

    public SK23AutoGenerator(SK23Drive driveSubsystem) {

        this.driveSubsystem = driveSubsystem;

        createAutoBuilder(this.driveSubsystem);
        createEventMap();
    }

    private void createAutoBuilder(SK23Drive driveSubsystem) {
        // Create the AutoBuilder. Used to generate full auto paths using PathPlannerLib
        autoBuilder = new SwerveAutoBuilder(
                driveSubsystem::getPose, // Pose2d supplier
                driveSubsystem::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
                DriveConstants.kDriveKinematics, // SwerveDriveKinematics
                AutoConstants.kTranslationPIDConstants, // PID constants to correct for translation error (used to
                                                        // create the X and Y PID controllers)
                AutoConstants.kRotationPIDConstants, // PID constants to correct for rotation error (used to create the
                                                     // rotation controller)
                driveSubsystem::setModuleStates, // Module states consumer used to output to the drive subsystem
                eventMap,
                true, // Should the path be automatically mirrored depending on alliance color.
                driveSubsystem); // The drive subsystem. Used to set requirements of path following commands
    }

    private void createEventMap() {
        // eventMap.put("marker1", new PrintCommand("Passed marker 1"));
    }

    /**
     * Finds all the files in the pathplanner folder in the deploy directory and
     * creates auto commands from all present files
     * 
     * @return A list containing the auto name as shown in the pathplanner tool and
     *         the auto command connected to that auto name
     */
    private Map<String, Command> getAllPathCommands() {
        Map<String, Command> commandList = new HashMap<String, Command>();

        File deployDirectory = Filesystem.getDeployDirectory();
        File pathDirectory = new File(deployDirectory, AutoConstants.kSplineDirectory);
        String autoName;

        // Gets all the files from the directory holding all the auto paths
        File[] pathNames = pathDirectory.listFiles();

        // Creates an auto command from every path file in the directory
        for (File pathname : pathNames) {
            // Only consider files which appear to be .path.
            if (pathname.getName().contains(".path")) {

                // Get the name of the path
                autoName = pathname.getName().replace(".path", "");

                // Create the command if possible
                try {
                    final List<PathPlannerTrajectory> trajectory = PathPlanner.loadPathGroup(autoName,
                            AutoConstants.kPathConstraints);
                    commandList.put(autoName, autoBuilder.fullAuto(trajectory));
                } catch (Exception e) {
                    DriverStation.reportWarning(autoName + "Could not be generated", true);
                }
            }
        }
        return commandList;
    }

    /**
     * Adds all the options to the shuffleboard using the auto name and command.
     * 
     * @param displayMethod
     *                      The Shuffleboard chooser required to add the options for
     *                      the paths
     */
    public void displayPossibleAutos(SendableChooser<Command> displayMethod) {
        Map<String, Command> commandList = getAllPathCommands();
        for (String autoName : commandList.keySet()) {
            displayMethod.addOption(autoName, commandList.get(autoName));
        }
    }

}