package frc.robot.bindings;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Constants.GamePieceEnum;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.SK23Intake;
import frc.robot.utils.filters.FilteredJoystick;

public class SK23IntakeBinder implements CommandBinder
{
    SK23Intake subsystem;

    // Driver button command
    private final JoystickButton intakeCubeBtn;
    private final JoystickButton intakeConeBtn;

    FilteredJoystick controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23IntakeBinder(FilteredJoystick controller, SK23Intake subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        intakeCubeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorIntakeCube);
        intakeConeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorIntakeCone);

    }

    public void bindButtons()
    {

        // When the left button is pressed intake the cube when it is pressed
        intakeCubeBtn.onTrue(new IntakeCommand(Constants.IntakeConstants.CLOCKWISE_ROLLER_SPEED,
            GamePieceEnum.Cube, subsystem));
        intakeConeBtn
            .onTrue(new IntakeCommand(Constants.IntakeConstants.COUNTERCLOCKWISE_ROLLER_SPEED,
                GamePieceEnum.Cone, subsystem));

    }
}
