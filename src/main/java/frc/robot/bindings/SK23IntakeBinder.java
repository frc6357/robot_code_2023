package frc.robot.bindings;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Constants.GamePieceEnum;

import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.SK23Intake;

import frc.robot.utils.filters.FilteredXboxController;

public class SK23IntakeBinder implements CommandBinder
{
    Optional<SK23Intake> subsystem;

    // Driver button command
    private final JoystickButton intakeConeBtn;
    private final JoystickButton ejectConeBtn;

    private final JoystickButton intakeCubeBtn;
    private final JoystickButton ejectCubeBtn;

    FilteredXboxController controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param intakeSubsystem
     *            The required drive subsystem for the commands
     */
    public SK23IntakeBinder(FilteredXboxController controller, Optional<SK23Intake> subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        intakeConeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorIntakeCone);
        ejectConeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorEjectCone);
        intakeCubeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorIntakeCube);
        ejectCubeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorEjectCube);

    }

    public void bindButtons()
    {
        // If subsystem is present then this method will bind the buttons
        if (subsystem.isPresent())
        {

            SK23Intake m_robotArm = subsystem.get();

            intakeConeBtn.onTrue(new IntakeCommand(GamePieceEnum.Cone,
                Constants.IntakeConstants.kIntakeConeSpeed, m_robotArm));
            ejectConeBtn.onTrue(new IntakeCommand(GamePieceEnum.Cone,
                Constants.IntakeConstants.kEjectConeSpeed, m_robotArm));
            ejectCubeBtn.onTrue(new IntakeCommand(GamePieceEnum.Cube,
                Constants.IntakeConstants.kEjectCubeSpeed, m_robotArm));
            // When the left button is pressed intake the cube when it is pressed
            intakeCubeBtn.onTrue(new IntakeCommand(GamePieceEnum.Cube,
                Constants.IntakeConstants.kIntakeCubeSpeed, m_robotArm));
        }

    }
}
