// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.ArmConstants.*;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SK23Arm;

public class ArmJoystickCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Arm           arm;
    private final Supplier<Boolean>  upButton;
    private final Supplier<Boolean> downButton;
    private final double joystickGain;
    

    /**
     * Sets the angle of the arm based upon input from a joystick, adding or subtracting
     * to the current set point. Default movement will receive joystick input with
     * downward movement on joystick turning motor clockwise and upward movement on
     * joystick turning motor counter clockwise.
     * 
     * @param upButton
     *            The method to see if the up button is pressed
     * @param downButton
     *            The method to see if the down button is pressed
     * @param joystickGain
     *            The degrees by which the arm moves every 20 s
     * @param arm
     *            Subsystem used for this command
     */
    public ArmJoystickCommand(Supplier<Boolean> upButton, Supplier<Boolean> downButton, double joystickGain, SK23Arm arm)
    {
        this.upButton = upButton;
        this.downButton = downButton;
        this.arm = arm;
        this.joystickGain = joystickGain;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(arm);
    }

    @Override
    public void initialize()
    {}

    @Override
    public void execute()
    {
        double angleSign = 0;

        if(upButton.get()){
            angleSign = 1;
        }else if(downButton.get()){
            angleSign = -1;
        }

        double angleChange = (joystickGain / 50) * angleSign; // Degrees per 20ms

         // Sets the new angle to the current angle plusor minus the constant change
        double setpoint = arm.getTargetAngle() + angleChange;

        setpoint = MathUtil.clamp(setpoint, kMinAngle, kMaxAngle);
        

        arm.setTargetAngle(setpoint);
    }

    @Override
    public void end(boolean interrupted)
    {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}
