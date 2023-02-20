package frc.robot.bindings;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ArmConstants;
import frc.robot.Ports.OperatorPorts;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.superclasses.Arm.ArmAngleEnum;
import frc.robot.utils.filters.FilteredJoystick;

public class SK23ArmBinder implements CommandBinder
{
    SK23Arm subsystem;

    // Driver button commands
    private final JoystickButton LowButton;
    private final JoystickButton MidButton;
    private final JoystickButton HighButton;
    private final JoystickButton SubstationButton;
    FilteredJoystick             controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23ArmBinder(FilteredJoystick controller, SK23Arm subsystem)
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

        LowButton
            .onTrue(new InstantCommand(() -> subsystem.setTargetAngle(ArmAngleEnum.LowPosition)));
        MidButton
            .onTrue(new InstantCommand(() -> subsystem.setTargetAngle(ArmAngleEnum.MidPosition)));
        HighButton
            .onTrue(new InstantCommand(() -> subsystem.setTargetAngle(ArmAngleEnum.HighPosition)));
        SubstationButton.onTrue(
            new InstantCommand(() -> subsystem.setTargetAngle(ArmAngleEnum.SubstationPosition)));

        controller.setYChannel(OperatorPorts.kOperatorArmAxis);
        subsystem.setDefaultCommand(
            // Vertical movement of the arm is controlled by the Y axis of the right stick.
            //Up on joystick moving arm up and down on stick moving arm down.
            new RunCommand(
                () -> subsystem
                    .setJoystickAngle(controller.getY(), ArmConstants.kJoystickTime),
                subsystem));

    }
}
