// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.GamePieceEnum;

//import frc.robot.Constants;

import frc.robot.subsystems.SK23Intake;

/**
 * An intake command that sets the speeds of the intake depending on the game piece state
 * that the intake has been set to.
 */
public class StateIntakeCommand extends CommandBase
{
    private final double    cubeSpeed;
    private final double    coneSpeed;
    Supplier<GamePieceEnum> gamePiece;

    private final SK23Intake intake;

    /**
     * Creates a command that sets the roller speed depending on what game piece state the
     * intake is in
     * 
     * @param gp
     *            The supplier to tell which state the game piece is in
     * @param cubeSpeed
     *            The speed for the rollers if the game piece state is a cube
     * @param coneSpeed
     *            The speed for the rollers if the game piece state is a cone
     * @param intake
     *            The subsystem required for the command
     */
    public StateIntakeCommand(Supplier<GamePieceEnum> gp, double cubeSpeed, double coneSpeed,
        SK23Intake intake)
    {

        this.cubeSpeed = cubeSpeed;
        this.coneSpeed = coneSpeed;
        this.gamePiece = gp;

        this.intake = intake;
        addRequirements(intake);
    }

    // 
    @Override
    public void initialize()
    {
        double speed;

        switch (gamePiece.get())
        {
            case Cube:
                speed = cubeSpeed;
                break;
            case Cone:
                speed = coneSpeed;
                break;
            default:
                speed = 0.0;
        }

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
