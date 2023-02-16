package frc.robot.bindings;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Ports.ControllerPorts;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.SK23Arm.ArmAngleEnum;
import frc.robot.utils.filters.CubicDeadbandFilter;
import frc.robot.utils.filters.FilteredJoystick;

public class SK23ArmBinder implements CommandBinder {
    SK23Arm subsystem;
    
    // Driver button commands
    private final JoystickButton LowButton;
    private final JoystickButton MidButton;
    private final JoystickButton HighButton;
    FilteredJoystick controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *                   The contoller that the commands are being bound to
     * @param subsystem
     *                   The required drive subsystem for the commands
     */
    public SK23ArmBinder(FilteredJoystick controller, SK23Arm subsystem) {
        this.controller = controller;
        this.subsystem = subsystem;

        LowButton = new JoystickButton(controller, ControllerPorts.kLowButton);
        MidButton = new JoystickButton(controller, ControllerPorts.kMidButton);
        HighButton = new JoystickButton(controller, ControllerPorts.kHighButton);

    }

    public void bindButtons() {

        controller.setFilter(ControllerPorts.kYAxis,
        new CubicDeadbandFilter(1, 0.01,
           1, false));

    
        LowButton.onTrue(new InstantCommand(() -> subsystem.setTargetAngle(ArmAngleEnum.LowPosition)));
        MidButton.onTrue(new InstantCommand(() -> subsystem.setTargetAngle(ArmAngleEnum.MidPosition)));
        HighButton.onTrue(new InstantCommand(() -> subsystem.setTargetAngle(ArmAngleEnum.HighPosition)));
        subsystem.setDefaultCommand(
            
            // The right stick controls movement of the arm.
            // Vertical movement is controlled by the Y axis of the right stick.
            new RunCommand(() -> subsystem.setJoystickAngle(controller.getFilteredAxis(ControllerPorts.kYAxis))));

        }
}
