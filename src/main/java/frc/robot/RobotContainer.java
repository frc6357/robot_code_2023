// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Ports.OperatorPorts;
import frc.robot.AutoTools.SK23AutoGenerator;
import frc.robot.bindings.CommandBinder;
import frc.robot.bindings.SK23ArmBinder;
import frc.robot.bindings.SK23DriveBinder;
import frc.robot.bindings.SK23IntakeBinder;
//import frc.robot.commands.DoNothingCommand;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.SK23Drive;
import frc.robot.subsystems.SK23Intake;
import frc.robot.subsystems.SK23Vision;
import frc.robot.utils.SubsystemControls;
import frc.robot.utils.filters.FilteredJoystick;
import frc.robot.utils.filters.FilteredXboxController;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems
    private final SK23Drive driveSubsystem = new SK23Drive();
    // Optional subsystems are only instantiated if the Subsystem.json
    // file determines that the subsystem is present on the robot
    private Optional<SK23Intake> intakeSubsystem = Optional.empty();
    private Optional<SK23Vision> visionSubsystem = Optional.empty();
    private Optional<SK23Arm>    armSubsystem    = Optional.empty();

    // The driver's controller
    private final FilteredXboxController driveController =
            new FilteredXboxController(OperatorPorts.kDriverControllerPort);

    // Operator controller set to xbox controller
    private final FilteredXboxController operatorController =
            new FilteredXboxController(OperatorPorts.kOperatorControllerPort);

    // The list containing all the command binding classes
    private List<CommandBinder> buttonBinders = new ArrayList<CommandBinder>();

    // The class used to create all PathPlanner Autos
    private final SK23AutoGenerator autoGenerator;
    // An option box on shuffleboard to choose the auto path
    SendableChooser<Command> autoCommandSelector = new SendableChooser<Command>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer()
    {
        // Creates all subsystems that are on the robot
        configureSubsystems();

        // Configure the button bindings
        configureButtonBindings();

        // Configures the autonomous paths and smartdashboard chooser
        autoGenerator = new SK23AutoGenerator(driveSubsystem, armSubsystem, intakeSubsystem);
        configureAutos();
    }

    /**
     * Will create all the optional subsystems using the json file in the deploy directory
     */
    private void configureSubsystems()
    {
        File deployDirectory = Filesystem.getDeployDirectory();

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();

        try
        {
            // Looking for the Subsystems.json file in the deploy directory
            JsonParser parser =
                    factory.createParser(new File(deployDirectory, Constants.SUBSYSTEMFILE));
            SubsystemControls subsystems = mapper.readValue(parser, SubsystemControls.class);

            // Instantiating subsystems if they are present
            // This is decided by looking at Subsystems.json
            if (subsystems.isIntakePresent())
            {
                intakeSubsystem = Optional.of(new SK23Intake());
            }
            if (subsystems.isVisionPresent())
            {
                visionSubsystem = Optional.of(new SK23Vision());
            }
            if (subsystems.isArmPresent())
            {
                armSubsystem = Optional.of(new SK23Arm());
            }
        }
        catch (IOException e)
        {
            DriverStation.reportError("Failure to read Subsystem Control File!", e.getStackTrace());
        }
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link FilteredJoystick}), and then
     * calling passing it to a {@link JoystickButton}.
     */
    private void configureButtonBindings()
    {

        // Adding all the binding classes to the list
        buttonBinders.add(new SK23DriveBinder(driveController, driveSubsystem));

        buttonBinders.add(new SK23IntakeBinder(operatorController, intakeSubsystem));
        buttonBinders.add(new SK23ArmBinder(operatorController, armSubsystem));

        // Traversing through all the binding classes to actually bind the buttons
        for (CommandBinder subsystemGroup : buttonBinders)
        {
            subsystemGroup.bindButtons();
        }

    }

    /**
     * Displays all the auto paths that can be run to the ShuffleBoard window
     */
    private void configureAutos()
    {
        autoGenerator.displayAllPathCommands(
            (name, command) -> autoCommandSelector.addOption(name, command));
        SmartDashboard.putData("Auto Chooser", autoCommandSelector);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        return autoCommandSelector.getSelected();
    }
}
