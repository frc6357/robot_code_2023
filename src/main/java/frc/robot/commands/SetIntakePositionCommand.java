// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SK23Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class SetIntakePositionCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Intake intake;
    private final boolean    extend;

    /**
     * This command allows the operator to extend the intake outwards or retract it
     * inwards.
     *
     * @param intake
     *            The intake subsystem the command operates on.
     * @param extended
     *            The boolean that indicates whether the intake is extended or retracted
     */
    public SetIntakePositionCommand(SK23Intake intake, boolean extended)
    {
        this.intake = intake;
        extend = extended;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intake);
    }

    // 
    @Override
    public void initialize()
    {
        if (extend)
        {
            // extends the arm
        }
        else
        {
            // retracts the arm
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}
