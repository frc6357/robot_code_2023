// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.SK23RollerIntake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class IntakeBoxCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23RollerIntake intake;

    /**
     * This command allows the operator to extend the intake outwards or retract it
     * inwards.
     *
     * @param intake
     *            The intake subsystem the command operates on.
     */
    public IntakeBoxCommand(SK23RollerIntake intake)
    {
        this.intake = intake;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intake);
    }

    // 
    @Override
    public void initialize()
    {
        intake.setFrontRollerSpeed(Constants.IntakeConstants.CLOCKWISE_FRONTROLLER_SPEED);
        intake.setRearTopRollerSpeed(Constants.IntakeConstants.CLOCKWISE_REARTOPROLLER_SPEED);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}
