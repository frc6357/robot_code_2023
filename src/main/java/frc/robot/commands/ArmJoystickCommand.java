// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.ArmConstants.*;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK23Arm;

public class ArmJoystickCommand extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final SK23Arm           arm;
    private final Supplier<Double>  controller;
    private final Supplier<Boolean> override;
    

    /**
     * Sets the angle of the arm based upon input from a joystick, adding or subtracting
     * to the current set point. Default movement will receive joystick input with
     * downward movement on joystick turning motor clockwise and upward movement on
     * joystick turning motor counter clockwise.
     * 
     * @param setpointChange
     *            The method to get the setpoint change in degrees per second
     * @param clampOverride
     *            The method to determine if the angle limits should be overridden
     * @param arm
     *            Subsystem used for this command
     */
    public ArmJoystickCommand(Supplier<Double> setpointChange, Supplier<Boolean> clampOverride, SK23Arm arm)
    {
        this.controller = setpointChange;
        this.override = clampOverride;
        this.arm = arm;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(arm);
    }

    @Override
    public void initialize()
    {}

    @Override
    public void execute()
    {
        double angleChange = controller.get() / 50; // Degrees per 20ms

         // Sets the new angle to the current angle plusor minus the constant change
        double setpoint = arm.getTargetAngle() + angleChange;

        if(!override.get())
        {
            setpoint = MathUtil.clamp(setpoint, kMinAngle, kMaxAngle);
        }

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
