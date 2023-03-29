// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.bindings.*;
import frc.robot.subsystems.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.AutoTools.SK23AutoGenerator;
import frc.robot.Constants.IntakeConstants;
import frc.robot.bindings.CommandBinder;
import frc.robot.utils.SubsystemControls;
import frc.robot.utils.filters.FilteredJoystick;

import static frc.robot.Constants.CameraConstants.*;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems
    // Optional subsystems are only instantiated if the Subsystem.json
    // file determines that the subsystem is present on the robot
    private Optional<SK23Drive>  driveSubsystem  = Optional.empty();
    private Optional<SK23Intake> intakeSubsystem = Optional.empty();
    private Optional<SK23Arm>    armSubsystem    = Optional.empty();
    // TODO: Uncomment this when we want to start using the vision subsystem
    //private Optional<SK23Vision> visionSubsystem = Optional.empty();
    private UsbCamera            driverCamera;

    // The list containing all the command binding classes
    private List<CommandBinder> buttonBinders = new ArrayList<CommandBinder>();

    // The class used to create all PathPlanner Autos
    private SK23AutoGenerator autoGenerator;
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
            if (subsystems.isCameraPresent())
            {
                // Start the driver camera streaming.
                driverCamera = CameraServer.startAutomaticCapture("Driver Camera", 0);
                driverCamera.setResolution(kDriverCameraResolutionX, kDriverCameraResolutionY);
                driverCamera.setFPS(kDriverCameraFPS);
                driverCamera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
            }
            if (subsystems.isIntakePresent())
            {
                intakeSubsystem = Optional.of(new SK23Intake());
            }
            if (subsystems.isArmPresent())
            {
                armSubsystem = Optional.of(new SK23Arm());
            }
            if (subsystems.isDrivePresent())
            {
                driveSubsystem = Optional.of(new SK23Drive());

                // Configures the autonomous paths and smartdashboard chooser
                autoGenerator = new SK23AutoGenerator(driveSubsystem.get(), armSubsystem, intakeSubsystem);
                autoGenerator.displayAllPathCommands(
                    (name, command) -> autoCommandSelector.addOption(name, command));
                SmartDashboard.putData("Auto Chooser", autoCommandSelector);
            }
            // TODO: Uncomment this when we start using the vision subsystem.
            // if (subsystems.isVisionPresent())
            // {
            //     visionSubsystem = Optional.of(new SK23Vision());
            // }
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
        buttonBinders.add(new SK23DriveBinder(driveSubsystem));
        buttonBinders.add(new SK23IntakeBinder(intakeSubsystem));
        buttonBinders.add(new SK23ArmBinder(armSubsystem));

        // Traversing through all the binding classes to actually bind the buttons
        for (CommandBinder subsystemGroup : buttonBinders)
        {
            subsystemGroup.bindButtons();
        }
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

    public void testPeriodic(){
        if(armSubsystem.isPresent()){
            armSubsystem.get().testPeriodic();
        }
    }
    public void testInit(){
        if(armSubsystem.isPresent()){
            armSubsystem.get().testInit();
        }
    }

    public void matchInit()
    {
        if (armSubsystem.isPresent())
        {
            SK23Arm arm = armSubsystem.get();
            arm.resetAngle();
            arm.setTargetAngle(0.0);
        }
        if(intakeSubsystem.isPresent()){
            SK23Intake intake = intakeSubsystem.get();
            intake.resetEncoder(IntakeConstants.kStartAngle);
            intake.setTargetAngle(0.0);
        }
    }
}
