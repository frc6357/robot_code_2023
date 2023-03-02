package frc.robot.AutoTools;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.DoNothingCommand;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.SK23Drive;
import frc.robot.subsystems.SK23Intake;
import frc.robot.utils.files.FileScanner;

import static frc.robot.Constants.AutoConstants.*;
import static frc.robot.Constants.DriveConstants.kDriveKinematics;

/**
 * The class the creates all the autonomous commands for the 2023 Charged Up FRC game
 */
public class SK23AutoGenerator
{

    private SK23Drive            driveSubsystem;
    private Optional<SK23Arm>    armSubsystem;
    private Optional<SK23Intake> intakeSubsystem;
    private SwerveAutoBuilder    autoBuilder;

    // An event map connects a marker name to a given command that will run
    HashMap<String, Command> eventMap = new HashMap<>();

    public SK23AutoGenerator(SK23Drive driveSubsystem, Optional<SK23Arm> armSubsystem,
        Optional<SK23Intake> intakeSubsystem)
    {
        this.driveSubsystem = driveSubsystem;
        this.armSubsystem = armSubsystem;
        this.intakeSubsystem = intakeSubsystem;

        createAutoBuilder(this.driveSubsystem);
        createEventMap();
    }

    private void createAutoBuilder(SK23Drive driveSubsystem)
    {
        // Create the AutoBuilder. Used to generate full auto paths using PathPlannerLib
        autoBuilder = new SwerveAutoBuilder(driveSubsystem::getPose,                // Pose2d supplier
            driveSubsystem::resetOdometry,      // Pose2d consumer, used to reset odometry at the beginning of auto
            kDriveKinematics,                   // SwerveDriveKinematics
            kTranslationPIDConstants,           // PID constants to correct for translation error (used to create the X and Y PID controllers)
            kRotationPIDConstants,              // PID constants to correct for rotation error (used to create the rotation controller)
            driveSubsystem::setModuleStates,    // Module states consumer used to output to the drive subsystem
            eventMap,                           // Correlates marker names to actual commands
            true,                               // Should the path be automatically mirrored depending on alliance color.
            driveSubsystem);                    // The drive subsystem. Used to set requirements of path following commands
    }

    /**
     * Defines the relationship between a marker on the pathplanner tool and an actual
     * command that the robot should run.
     */
    private void createEventMap()
    {
        // Create the drivetrain commands
        eventMap.put("Level", new AutoBalanceCommand(() -> {
            return 0.0;
        }, driveSubsystem));

        // TODO: Define commands for these markers
        // Creates the arm commands if the arm subsystem is present (not null)
        if (armSubsystem.isPresent())
        {
            eventMap.put("High Arm", null);
            eventMap.put("Mid Arm", null);
            eventMap.put("Low Arm", null);
        }
        else
        {
            DriverStation.reportWarning("Did not create arm commands for auto", false);
        }

        // Creates the intake commands if the intake subsystem is present (not null)
        if (intakeSubsystem.isPresent())
        {
            eventMap.put("Intake Cone", null);
            eventMap.put("Eject Cone", null);
            eventMap.put("Intake Cube", null);
            eventMap.put("Eject Cube", null);

            eventMap.put("Extend Intake", null);
            eventMap.put("Retract Intake", null);
        }
        else
        {
            DriverStation.reportWarning("Did not create intake commands for auto", false);
        }
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
        File pathplannerFolder = FileScanner.getFileFromDeploy(kSplineDirectory);
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
                    PathPlanner.loadPathGroup(autoName, kPathConstraints);
            displayMethod.accept(autoName, autoBuilder.fullAuto(trajectory));
        }
        catch (Exception e)
        {
            DriverStation.reportWarning(autoName + ".path could not be generated", true);
        }
    }

}
