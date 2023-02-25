// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//import frc.robot.Constants;
import frc.robot.Constants.GamePieceEnum;
import frc.robot.subsystems.SK23Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Intake    intake;
    private final GamePieceEnum gamePiece;
    private final double        speed;
    //private final IntakeState   state;

    /**
     * This command allows the operator to extend the intake outwards or retract it
     * inwards.
     *
     * @param intake
     *            The intake subsystem the command operates on.
     */
    public IntakeCommand(GamePieceEnum gamePiece, double speed, SK23Intake intake)
    {

        this.gamePiece = gamePiece;

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
        if (gamePiece == GamePieceEnum.Cube && speed < 0)
        {
            intake.setFrontRollerSpeed(speed);
            intake.setRearTopRollerSpeed(speed);
        }

        else if (gamePiece == GamePieceEnum.Cube && speed > 0)
        {
            intake.setFrontRollerSpeed(speed);
            intake.setRearTopRollerSpeed(speed);
        }

    }

    public void end(boolean interrupted)
    {
        intake.setFrontRollerSpeed(0.0);
        intake.setRearTopRollerSpeed(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}
