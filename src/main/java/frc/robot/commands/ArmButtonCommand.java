// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.ArmConstants.ArmPosition;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK23Arm;

public class ArmButtonCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Arm      Arm;
    private final ArmPosition position;

    /**
     * This command allows the operator to set the angle of the arm to a specified
     * position.
     * 
     * @param position
     *            The position to set the arm to
     * @param Arm
     *            The Arm subsystem the command operates on.
     */
    public ArmButtonCommand(ArmPosition position, SK23Arm Arm)
    {
        this.position = position;
        this.Arm = Arm;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Arm);
    }

    @Override
    public void initialize()
    {
        Arm.setTargetAngle(position);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        if(DriverStation.isAutonomousEnabled())
        {
            return Arm.isAtTargetAngle();
        }
        else
        {
            return true;
        }
        
    }
}
