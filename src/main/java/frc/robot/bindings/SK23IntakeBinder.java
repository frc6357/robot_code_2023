package frc.robot.bindings;

import java.util.Optional;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.robot.Constants.IntakeConstants.*;

import static frc.robot.Ports.OperatorPorts.*;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.IntakeDeployerCommand;
import frc.robot.subsystems.SK23Intake;

import frc.robot.utils.filters.FilteredXboxController;

public class SK23IntakeBinder implements CommandBinder
{
    Optional<SK23Intake> subsystem;

    // Driver button command
    private final Trigger        intakeConeBtn; // (Hold) Left Trigger
    private final JoystickButton ejectConeBtn; // (Hold) Right Button

    private final JoystickButton intakeCubeBtn; // (Hold) Left Button
    private final Trigger        ejectCubeBtn; // (Hold) Right Trigger

    private final JoystickButton retractIntakeBtn; // (Press) Start Button
    private final JoystickButton extendIntakeBtn; // (Press) Back Button

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

        // uses valeus from the xbox controller to control the port values
        intakeConeBtn = controller.leftTrigger();
        ejectConeBtn = new JoystickButton(controller.getHID(), kOperatorEjectCone.value);
        intakeCubeBtn = new JoystickButton(controller.getHID(), kOperatorIntakeCube.value);
        ejectCubeBtn = controller.rightTrigger();

        retractIntakeBtn = new JoystickButton(controller.getHID(), kOperatorRetractIntake.value);
        extendIntakeBtn = new JoystickButton(controller.getHID(), kOperatorExtendIntake.value);

    }

    public void bindButtons()
    {
        // If subsystem is present then this method will bind the buttons
        if (subsystem.isPresent())
        {

            // Gets the intake subsystem and puts it into m_robotIntake
            SK23Intake m_robotIntake = subsystem.get();

            // Sets buttons with whileTrue so that they will run continuously until the button is let go
            intakeConeBtn.whileTrue(new IntakeCommand(kIntakeConeSpeed, m_robotIntake));

            ejectConeBtn.whileTrue(new IntakeCommand(kEjectConeSpeed, m_robotIntake));

            intakeCubeBtn.whileTrue(new IntakeCommand(kIntakeCubeSpeed, m_robotIntake));

            ejectCubeBtn.whileTrue(new IntakeCommand(kEjectCubeSpeed, m_robotIntake));

            // Sets the buttons with onTure so tha they will toggle extension and retraction of the intake
            retractIntakeBtn.onTrue(new IntakeDeployerCommand(Value.kForward, m_robotIntake));

            extendIntakeBtn.onTrue(new IntakeDeployerCommand(Value.kReverse, m_robotIntake));
        }

    }
}
