// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK23Arm;
import frc.robot.subsystems.superclasses.Arm.ArmAngleEnum;

public class ArmSimpleCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Arm      Arm;
    private final ArmAngleEnum position;

    /**
     * This command allows the operator to set the angle of the arm to a specified position.
     * 
     * @param position
     *            The position to set the arm to
     * @param Arm
     *            The Arm subsystem the command operates on.
     */
    public ArmSimpleCommand(ArmAngleEnum position, SK23Arm Arm)
    {
        this.position = position;
        this.Arm = Arm;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Arm);
    }

    
    @Override
    public void initialize()
    {
        switch (position)
        {
            // Set arm to floor position
            case FloorPosition:
                Arm.setTargetAngle(ArmAngleEnum.FloorPosition);
                break;
            // Set arm to mid position
            case MidPosition:
                Arm.setTargetAngle(ArmAngleEnum.MidPosition);
                break;
            // Set arm to high position
            case HighPosition:
                Arm.setTargetAngle(ArmAngleEnum.HighPosition);
                break;
            // Set arm to substation position
            case SubstationPosition:
                Arm.setTargetAngle(ArmAngleEnum.SubstationPosition);

        }

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return true;
    }
}
