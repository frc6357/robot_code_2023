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
import frc.robot.bindings.SK23DriveBinder;
import frc.robot.commands.DoNothingCommand;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.SK23Drive;
import frc.robot.subsystems.SK23Extender;
import frc.robot.subsystems.SK23RollerIntake;
import frc.robot.subsystems.SK23Vision;
import frc.robot.utils.SubsystemControls;
import frc.robot.utils.filters.FilteredJoystick;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{

    // The robot's subsystems
    private final SK23Drive m_robotDrive = new SK23Drive();

    // The class used to create all PathPlanner Autos
    private final SK23AutoGenerator autoGenerator = new SK23AutoGenerator(m_robotDrive);

    // The driver's controller
    private final FilteredJoystick driveController    =
            new FilteredJoystick(OperatorPorts.kDriverControllerPort);
    private final FilteredJoystick operatorController =
            new FilteredJoystick(OperatorPorts.kOperatorControllerPort);

    // Initialization for optional
    // These are currently empty and only created in the constructor
    // based on the Subsystem.json file
    private Optional<SK23RollerIntake> intakeSubsystem   = Optional.empty();
    private Optional<SK23Vision>       visionSubsystem   = Optional.empty();
    private Optional<SK23Arm>          armSubsystem      = Optional.empty();
    private Optional<SK23Extender>     extenderSubsystem = Optional.empty();
    // The list containing all the command binding classes
    private List<CommandBinder> buttonBinders = new ArrayList<CommandBinder>();

    // An option box on shuffleboard to choose the auto path
    SendableChooser<Command> autoCommandSelector = new SendableChooser<Command>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer()
    {
        // Configure the button bindings
        configureButtonBindings();
        configureAutos();

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
                intakeSubsystem = Optional.of(new SK23RollerIntake());
                System.out.println("intake is here");
            }
            if (subsystems.isVisionPresent())
            {
                visionSubsystem = Optional.of(new SK23Vision());
            }
            if (subsystems.isArmPresent())
            {
                armSubsystem = Optional.of(new SK23Arm());
            }
            if (subsystems.isExtenderPresent())
            {
                extenderSubsystem = Optional.of(new SK23Extender());
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
        buttonBinders.add(new SK23DriveBinder(driveController, m_robotDrive));

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
        autoCommandSelector.setDefaultOption("None", new DoNothingCommand());
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
