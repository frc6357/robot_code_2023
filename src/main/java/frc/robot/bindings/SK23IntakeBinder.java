package frc.robot.bindings;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.robot.Constants.IntakeConstants.*;

import static frc.robot.Ports.OperatorPorts.*;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.SK23Intake;

import frc.robot.utils.filters.FilteredXboxController;

public class SK23IntakeBinder implements CommandBinder
{
    Optional<SK23Intake> subsystem;

    // Driver button command
    private final Trigger coneModifier;
    private final Trigger cubeModifier;

    private final Trigger intake;
    private final Trigger eject;

    private final Trigger extendIntake;
    private final Trigger retractIntake;

    FilteredXboxController controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param intakeSubsystem
     *            The required drive subsystem for the commands
     */
    public SK23IntakeBinder(FilteredXboxController controller, Optional<SK23Intake> subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        // uses values from the xbox controller to control the port values
        coneModifier = new JoystickButton(controller.getHID(), kOperatorCone.value);
        cubeModifier = new JoystickButton(controller.getHID(), kOperatorCube.value);
        intake = new JoystickButton(controller.getHID(), kOperatorIntake.value);
        eject = new JoystickButton(controller.getHID(), kOperatorEject.value);
        extendIntake = controller.leftTrigger();
        retractIntake = controller.rightTrigger();

    }

    public void bindButtons()
    {
        // If subsystem is present then this method will bind the buttons
        if (subsystem.isPresent())
        {

            // Gets the intake subsystem and puts it into m_robotIntake
            SK23Intake m_robotIntake = subsystem.get();

            // Sets buttons with whileTrue so that they will run continuously until the button is let go
            coneModifier.and(intake).whileTrue(new IntakeCommand(kIntakeConeSpeed, m_robotIntake));
            cubeModifier.and(intake).whileTrue(new IntakeCommand(kIntakeCubeSpeed, m_robotIntake));

            coneModifier.and(eject).whileTrue(new IntakeCommand(kEjectConeSpeed, m_robotIntake));
            cubeModifier.and(eject).whileTrue(new IntakeCommand(kEjectCubeSpeed, m_robotIntake));

            // Sets the buttons with onTrue so tha they will toggle extension and retraction of the intake
            extendIntake.onTrue(new InstantCommand(m_robotIntake::extendIntake, m_robotIntake));
            retractIntake.onTrue(new InstantCommand(m_robotIntake::retractIntake, m_robotIntake));
        }

    }
}
