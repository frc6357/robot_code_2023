package frc.robot.bindings;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ArmConstants;
import frc.robot.Ports.OperatorPorts;
import frc.robot.commands.ArmJoystickCommand;
import frc.robot.commands.ArmButtonCommand;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.superclasses.Arm.ArmAngleEnum;
import frc.robot.utils.filters.FilteredJoystick;

public class SK23ArmBinder implements CommandBinder
{
    Optional<SK23Arm> subsystem;

    // Arm button commands
    private final JoystickButton LowButton;
    private final JoystickButton MidButton;
    private final JoystickButton HighButton;
    private final JoystickButton SubstationButton;
    FilteredJoystick             controller;

    /**
     * The class that is used to bind all the commands for the arm subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23ArmBinder(FilteredJoystick controller, Optional<SK23Arm> subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;
        LowButton = new JoystickButton(controller, OperatorPorts.kOperatorLowArm);
        MidButton = new JoystickButton(controller, OperatorPorts.kOperatorMidArm);
        HighButton = new JoystickButton(controller, OperatorPorts.kOperatorLowArm);
        SubstationButton = new JoystickButton(controller, OperatorPorts.kOperatorSubstationArm);
        
    }

    public void bindButtons()
    {
        // If subsystem is present then this method will bind the buttons
        if(subsystem.isPresent()){
            
            SK23Arm m_robotArm = subsystem.get();

            LowButton
                .onTrue(new ArmButtonCommand(ArmAngleEnum.FloorPosition, m_robotArm));
            MidButton
                .onTrue(new ArmButtonCommand(ArmAngleEnum.MidPosition, m_robotArm));
            HighButton
                .onTrue(new ArmButtonCommand(ArmAngleEnum.HighPosition, m_robotArm));
            SubstationButton
                .onTrue(new ArmButtonCommand(ArmAngleEnum.SubstationPosition, m_robotArm));

            controller.setYChannel(OperatorPorts.kOperatorArmAxis);
            m_robotArm.setDefaultCommand(
                // Vertical movement of the arm is controlled by the Y axis of the right stick.
                // Up on joystick moving arm up and down on stick moving arm down.
                new ArmJoystickCommand(controller, OperatorPorts.kOperatorArmAxis, ArmConstants.isJoystickReversed, m_robotArm));

        }
    }
    
}
