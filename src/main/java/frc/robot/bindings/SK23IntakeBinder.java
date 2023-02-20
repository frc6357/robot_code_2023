package frc.robot.bindings;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Ports;
import frc.robot.commands.EjectBoxCommand;
import frc.robot.commands.EjectConeCommand;
import frc.robot.commands.IntakeBoxCommand;
import frc.robot.commands.IntakeConeCommand;
import frc.robot.subsystems.SK23RollerIntake;
import frc.robot.utils.filters.FilteredJoystick;

public class SK23IntakeBinder implements CommandBinder
{
    SK23RollerIntake subsystem;

    // Driver button command
    private final JoystickButton intakeCubeBtn;
    private final JoystickButton ejectConeBtn;
    private final JoystickButton intakeConeBtn;
    private final JoystickButton ejectCubeBtn;
    FilteredJoystick             controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23IntakeBinder(FilteredJoystick controller, SK23RollerIntake subsystem)
    {
        this.controller = controller;
        this.subsystem = subsystem;

        intakeCubeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorIntakeCube);
        ejectConeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorEjectCone);
        intakeConeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorIntakeCone);
        ejectCubeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorEjectCube);

    }

    public void bindButtons()
    {

        // When the left button is pressed intake the cube while it is held down
        intakeCubeBtn.whileTrue(new IntakeBoxCommand(subsystem));
        // When the right button is pressed eject the cone while it is held down
        ejectConeBtn.whileTrue(new EjectConeCommand(subsystem));
        // When the left trigger is pressed intake the cone while it is held down
        intakeConeBtn.whileTrue(new IntakeConeCommand(subsystem));
        // When the right trigger is pressed eject the cube while it is held down
        ejectCubeBtn.whileTrue(new EjectBoxCommand(subsystem));
    }
}