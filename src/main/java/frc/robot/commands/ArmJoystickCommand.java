// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.SK23Arm;

public class ArmJoystickCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Arm Arm;
    private final double  joystickInput;
    private int           joystickCount;

    /**
     * Sets the angle of the arm based upon input from a joystick, adding or subtracting
     * to the current set point. Assumes it will receive joystick input with downward
     * movement being negative and upwards movement being positive.
     * 
     * @param joystickInput
     *            Input from the joystick (Negative input signifies downwards movement on
     *            the joystick, Positive input signifies upwards movement on the joystick).
     */
    public ArmJoystickCommand(double joystickInput, SK23Arm Arm)
    {
        this.joystickInput = joystickInput;
        this.Arm = Arm;
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

        if (joystickCount == joystickTimePeriodic)
        {
            double angleChange = ArmConstants.kJoystickChange;

            if (Math.abs(joystickInput) > ArmConstants.kJoystickDeadband)
            {
                double currentAngle =
                        Arm.getTargetAngle() + (Math.signum(joystickInput) * angleChange); //Sets the new angle to the current angle plus or minus the constant change
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
