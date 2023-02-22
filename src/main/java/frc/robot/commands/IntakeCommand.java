// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//import frc.robot.Constants;
import frc.robot.Constants.GamePieceEnum;
import frc.robot.Constants.IntakeStateEnum;
import frc.robot.subsystems.SK23Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Intake      intake;
    private final IntakeStateEnum intakeState;
    //private final IntakeState   state;
    private final double speed;

    /**
     * This command allows the operator to extend the intake outwards or retract it
     * inwards.
     *
     * @param intake
     *            The intake subsystem the command operates on.
     */
    public IntakeCommand(double speed, IntakeStateEnum intakeState, SK23Intake intake)
    {
        this.speed = speed;
        this.intakeState = intakeState;
        //this.state = state;
        this.intake = intake;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(intake);
    }

    // 
    @Override
    public void initialize()
    {

        switch (intakeState)
        {
            // Intake intaking cone
            case IntakeCone:
                intake.setFrontRollerSpeed(speed);
                intake.setRearTopRollerSpeed(speed);
                break;

            case IntakeCube:
                intake.setFrontRollerSpeed(speed);
                intake.setRearTopRollerSpeed(speed);
                break;

            case None:
                // Do nothing in this case.
                break;
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
