package frc.robot.bindings;

import static frc.robot.Constants.ArmConstants.*;
import static frc.robot.Ports.OperatorPorts.*;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ArmButtonCommand;
import frc.robot.commands.ArmJoystickCommand;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.superclasses.Arm.ArmAngleEnum;
import frc.robot.utils.filters.DeadbandFilter;
import frc.robot.utils.filters.FilteredXboxController;

public class SK23ArmBinder implements CommandBinder
{
    Optional<SK23Arm> subsystem;

    // Arm button commands
    private final Trigger LowButton;
    private final Trigger MidButton;
    private final Trigger HighButton;
    private final Trigger SubstationButton;
    private final Trigger zeroPositionButton;
    private final Trigger resetPos;

    FilteredXboxController       controller;

    /**
     * The class that is used to bind all the commands for the arm subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23ArmBinder(FilteredXboxController controller, Optional<SK23Arm> subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        zeroPositionButton = new JoystickButton(controller.getHID(), kOperatorZeroPosition.value);
        LowButton = new JoystickButton(controller.getHID(), kOperatorLowArm.value);
        MidButton = new JoystickButton(controller.getHID(), kOperatorMidArm.value);
        HighButton = new JoystickButton(controller.getHID(), kOperatorHighArm.value);
        SubstationButton = new JoystickButton(controller.getHID(), kOperatorSubstationArm.value);

        resetPos = new JoystickButton(controller.getHID(), kOperatorResetArmPos.value);
    }

    public void bindButtons()
    {
        // If subsystem is present then this method will bind the buttons
        if (subsystem.isPresent())
        {

            SK23Arm m_robotArm = subsystem.get();
            
            
            double joystickGain = kJoystickReversed ? -kJoystickChange : kJoystickChange;
            controller.setFilter(kOperatorArmAxis.value, new DeadbandFilter(kJoystickDeadband, joystickGain));

            zeroPositionButton.onTrue(new ArmButtonCommand(ArmAngleEnum.ZeroPosition, m_robotArm));
            LowButton.onTrue(new ArmButtonCommand(ArmAngleEnum.FloorPosition, m_robotArm));
            MidButton.onTrue(new ArmButtonCommand(ArmAngleEnum.MidPosition, m_robotArm));
            HighButton.onTrue(new ArmButtonCommand(ArmAngleEnum.HighPosition, m_robotArm));
            SubstationButton
                .onTrue(new ArmButtonCommand(ArmAngleEnum.SubstationPosition, m_robotArm));

            
            resetPos.onTrue(new InstantCommand(m_robotArm::resetAngle, m_robotArm));

            m_robotArm.setDefaultCommand(
                // Vertical movement of the arm is controlled by the Y axis of the right stick.
                // Up on joystick moving arm up and down on stick moving arm down.
                new ArmJoystickCommand(
                    () -> {return controller.getFilteredAxis(kOperatorArmAxis.value);},
                    controller.button(kOperatorArmOverride.value)::getAsBoolean,
                    m_robotArm));
        }
    }

}
