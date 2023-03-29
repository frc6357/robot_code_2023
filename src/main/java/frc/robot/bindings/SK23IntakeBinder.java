package frc.robot.bindings;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.robot.Ports.OperatorPorts.*;
import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Constants.OIConstants.*;

import frc.robot.Constants.GamePieceEnum;
import frc.robot.Constants.IntakeConstants;
import frc.robot.commands.IntakeJoystickCommand;
import frc.robot.commands.StateIntakeCommand;
import frc.robot.subsystems.SK23Intake;
import frc.robot.utils.filters.CubicDeadbandFilter;
import frc.robot.utils.filters.DeadbandFilter;

public class SK23IntakeBinder implements CommandBinder
{
    Optional<SK23Intake> subsystem;

    // Driver button command
    private final Trigger coneModifier;
    private final Trigger cubeModifier;

    private final Trigger intake;
    private final Trigger eject;

    private final Trigger extendIntake;
    private final Trigger retractIntake;
    private final Trigger SubstationLeftIntake;
    private final Trigger SubstationRightIntake;
    
    private final Trigger zeroPositionButtonOperator;
    private final Trigger zeroPositionButtonDriver;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param intakeSubsystem
     *            The required drive subsystem for the commands
     */
    public SK23IntakeBinder(Optional<SK23Intake> subsystem)
    {
        this.subsystem = subsystem;

        // uses values from the xbox controller to control the port values
        coneModifier = kConeState.button;
        cubeModifier = kCubeState.button;

        intake = kIntake.button;
        eject  = kEject.button;

        extendIntake  = kExtendIntake.button;
        retractIntake = kRetractIntake.button;
        SubstationLeftIntake = kSubstationLeftIntake.button;
        SubstationRightIntake = kSubstationRightIntake.button;
        

        zeroPositionButtonOperator = kZeroPositionOperator.button;
        zeroPositionButtonDriver = kZeroPositionDriver.button;
    }

    public void bindButtons()
    {
        // If subsystem is present then this method will bind the buttons
        if (subsystem.isPresent())
        {

            // Gets the intake subsystem and puts it into m_robotIntake
            SK23Intake m_robotIntake = subsystem.get();

            kArmAxis.setFilter(
                new CubicDeadbandFilter(
                    kDriveCoeff,
                    IntakeConstants.kJoystickDeadband,
                    kJoystickChange,
                    true));
            
            // Sets buttons with whileTrue so that they will run continuously until the button is let go
            coneModifier.onTrue(new InstantCommand(() -> {m_robotIntake.setGamePieceState(GamePieceEnum.Cone);}, m_robotIntake));
            cubeModifier.onTrue(new InstantCommand(() -> {m_robotIntake.setGamePieceState(GamePieceEnum.Cube);}, m_robotIntake));
            
            intake.whileTrue(new StateIntakeCommand(m_robotIntake::getGamePieceState, kIntakeCubeSpeed, kIntakeConeSpeed, m_robotIntake));
            eject.whileTrue(new StateIntakeCommand(m_robotIntake::getGamePieceState, kEjectCubeSpeed, kEjectConeSpeed, m_robotIntake));
            
            // Sets the buttons with onTrue so that they will toggle extension and retraction of the intake
            extendIntake.onTrue(new InstantCommand(m_robotIntake::extendIntake, m_robotIntake));
            retractIntake.onTrue(new InstantCommand(m_robotIntake::retractIntake, m_robotIntake));
            SubstationLeftIntake.onTrue(new InstantCommand(m_robotIntake::substationIntake, m_robotIntake));
            SubstationRightIntake.onTrue(new InstantCommand(m_robotIntake::substationIntake, m_robotIntake));

            // Sets the intake position when bringing the arm down to the zero position
            zeroPositionButtonOperator.or(zeroPositionButtonDriver)
                .onTrue(new InstantCommand(m_robotIntake::retractIntake, m_robotIntake));

            m_robotIntake.setDefaultCommand(
                // Vertical movement of the intake is controlled by the Y axis of the right stick.
                // Up on joystick moving intake up and down on stick moving intake down.
                new IntakeJoystickCommand(
                    () -> {return kIntakeAxis.getFilteredAxis();},
                    kIntakeOverride.button::getAsBoolean,
                    m_robotIntake));
        }

    }
}
