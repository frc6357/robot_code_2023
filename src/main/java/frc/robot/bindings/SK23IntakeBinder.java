package frc.robot.bindings;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.Constants.IntakeStateEnum;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.SK23Intake;
import frc.robot.utils.filters.FilteredJoystick;

public class SK23IntakeBinder implements CommandBinder
{
    Optional<SK23Intake> subsystem;

    // Driver button command
    private final JoystickButton intakeConeBtn;
    //private final JoystickButton ejectConeBtn;

    private final JoystickButton intakeCubeBtn;
    //private final JoystickButton ejectCubeBtn;

    FilteredJoystick controller;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param controller
     *            The contoller that the commands are being bound to
     * @param intakeSubsystem
     *            The required drive subsystem for the commands
     */
    public SK23IntakeBinder(FilteredJoystick controller, Optional<SK23Intake> intakeSubsystem)
    {
        this.controller = controller;
        this.subsystem = intakeSubsystem;

        intakeConeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorIntakeCone);
        //ejectConeBtn = new JoystickButton(controller, Ports.OperatorPorts.kO
        intakeCubeBtn = new JoystickButton(controller, Ports.OperatorPorts.kOperatorIntakeCube);

    }

    public void bindButtons()
    {
        if(subsystem.isPresent()){
            
            SK23Intake m_robotArm = subsystem.get();

            intakeConeBtn.onTrue(new IntakeCommand(Constants.IntakeConstants.kIntakeConeSpeed,
                IntakeStateEnum.IntakeCone, m_robotArm));
            // When the left button is pressed intake the cube when it is pressed
            intakeCubeBtn.onTrue(new IntakeCommand(Constants.IntakeConstants.kIntakeCubeSpeed,
                IntakeStateEnum.IntakeCube, m_robotArm));
        }

    }
}
