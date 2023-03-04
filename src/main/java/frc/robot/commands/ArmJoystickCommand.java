// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.ArmConstants.kJoystickChange;
import static frc.robot.Constants.ArmConstants.kJoystickDeadband;
import static frc.robot.Ports.OperatorPorts.kOperatorArmAxis;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK23Arm;
import frc.robot.utils.filters.FilteredXboxController;

public class ArmJoystickCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Arm                Arm;
    private final FilteredXboxController controller;
    private int                          joystickCount;
    private boolean                      isReversed;

    /**
     * Sets the angle of the arm based upon input from a joystick, adding or subtracting
     * to the current set point. Default movement will receive joystick input with
     * downward movement on joystick turning motor clockwise and upward movement on
     * joystick turning motor counter clockwise.
     * 
     * @param controller
     *            Filtered controller object from which you get y axis
     * @param y_channel
     *            Y channel of the joystick axis
     * @param isReversed
     *            Reverses typical input from joystick
     * @param Arm
     *            Subsystem used for this command
     */
    public ArmJoystickCommand(FilteredXboxController controller, boolean isReversed, SK23Arm Arm)
    {
        this.controller = controller;
        this.Arm = Arm;
        this.isReversed = isReversed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Arm);
    }

    @Override
    public void initialize()
    {
        joystickCount = 0;
    }

    @Override
    public void execute()
    {

        //TODO: Add code to input maximum input
        double joystickInput = isReversed ? -1 * controller.getRawAxis(kOperatorArmAxis.value)
            : controller.getRawAxis(kOperatorArmAxis.value); //Reverses input if isReversed is true
        double angleChange = kJoystickChange / 50; //Degrees per 20ms

        if (Math.abs(joystickInput) > kJoystickDeadband) // If joystick input is past deadband constant
        {
            double currentAngle =
                    Arm.getTargetAngle() + (Math.signum(joystickInput) * angleChange); // Sets the new angle to the current angle plus or minus the constant change
            Arm.setTargetAngle(currentAngle);
        }
    }

    @Override
    public void end(boolean interrupted)
    {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}
