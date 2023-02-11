package frc.robot.bindings;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.SK23Arm.ArmAngleEnum;

public class SK23ArmBinder implements CommandBinder {
    SK23Arm subsystem;

    // Driver button commands
    private final JoystickButton LowCube;
    private final JoystickButton MidCube;
    private final JoystickButton HighCube;
    private final JoystickButton LowCone;
    private final JoystickButton HighCone;
    Joystick controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *                   The contoller that the commands are being bound to
     * @param subsystem
     *                   The required drive subsystem for the commands
     */
    public SK23ArmBinder(Joystick controller, SK23Arm subsystem) {
        this.controller = controller;
        this.subsystem = subsystem;

        LowCube = new JoystickButton(controller, Constants.kLowCubeButton);
        MidCube = new JoystickButton(controller, Constants.kMidCubeButton);
        HighCube = new JoystickButton(controller, Constants.kHighCubeButton);
        LowCone = new JoystickButton(controller, Constants.kLowConeButton);
        HighCone = new JoystickButton(controller, Constants.kHighConeButton);

    }

    public void bindButtons() {
        LowCube.onTrue(new InstantCommand(() -> subsystem.setArmAngle(ArmAngleEnum.LowCube)));
        MidCube.onTrue(new InstantCommand(() -> subsystem.setArmAngle(ArmAngleEnum.MidCube)));
        HighCube.onTrue(new InstantCommand(() -> subsystem.setArmAngle(ArmAngleEnum.HighCube)));
        LowCone.onTrue(new InstantCommand(() -> subsystem.setArmAngle(ArmAngleEnum.LowCone)));
        HighCone.onTrue(new InstantCommand(() -> subsystem.setArmAngle(ArmAngleEnum.HighCone)));
    }
}
