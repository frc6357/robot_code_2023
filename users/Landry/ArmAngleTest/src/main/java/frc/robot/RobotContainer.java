// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Ports.ControllerPorts;
import frc.robot.bindings.CommandBinder;
import frc.robot.bindings.SK23ArmBinder;
import frc.robot.subsystems.SK23Arm;


/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{

    // The robot's subsystems
    private final SK23Arm m_Arm = new SK23Arm();


    // The driver's controller
    private final Joystick driveController = new Joystick(ControllerPorts.kDriverControllerPort);
    private final Joystick operatorController = new Joystick(ControllerPorts.kOperatorControllerPort);

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
        buttonBinders.add(new SK23ArmBinder(driveController, m_Arm));

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

    }
}
