// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//import frc.robot.Constants;

import frc.robot.subsystems.SK23Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An intake command that uses an intake subsystem. */
public class IntakeCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Intake intake;
    private final double     speed;
    //private final IntakeState   state;

    /**
     * This command allows the operator to extend the intake outwards or retract it
     * inwards.
     *
     * @param intake
     *            The intake subsystem the command operates on.
     */
    public IntakeCommand(double speed, SK23Intake intake)
    {

        this.speed = speed;
        //this.state = state;
        this.intake = intake;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intake);
    }

    // 
    @Override
    public void initialize()
    {
        intake.setInnerRollerSpeed(speed);
        intake.setOuterRollerSpeed(speed);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}
