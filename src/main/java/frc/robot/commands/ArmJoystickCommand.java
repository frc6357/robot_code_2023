// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.SK23Arm;
import frc.robot.utils.filters.FilteredJoystick;

public class ArmJoystickCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Arm          Arm;
    private final FilteredJoystick controller;
    private int                    joystickCount;
    private int                    y_channel;
    private boolean                isReversed;

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
    public ArmJoystickCommand(FilteredJoystick controller, int y_channel, boolean isReversed,
        SK23Arm Arm)
    {
        this.controller = controller;
        this.Arm = Arm;
        this.y_channel = y_channel;
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
        double joystickTimePeriodic = ArmConstants.kJoystickTime * ArmConstants.periodicPerSecond; // Converts seconds into number of periodic calls

        if (joystickCount == joystickTimePeriodic) // Only runs every joystickTimePeriod times it goes through
        {
            double joystickInput = isReversed ? -1 * controller.getY() : controller.getY(); //Reverses input if isReversed is true
            double angleChange = ArmConstants.kJoystickChange;
            controller.setYChannel(y_channel);
            if (Math.abs(joystickInput) > ArmConstants.kJoystickDeadband) // If joystick input is past deadband constant
            {
                double currentAngle =
                        Arm.getTargetAngle() + (Math.signum(joystickInput) * angleChange); // Sets the new angle to the current angle plus or minus the constant change
                Arm.setTargetAngle(currentAngle);
            }

            joystickCount = 0;
        }

        joystickCount++;
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
