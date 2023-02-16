package frc.robot.subsystems;

import frc.robot.Constants.ArmConstants;
import frc.robot.Ports.ArmPorts;
import frc.robot.subsystems.superclasses.Arm;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm.
 */
public class SK23Arm extends Arm
{
    ArmAngleInternal Arm;

    public SK23Arm()
    {
        Arm = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor.ID,
            ArmConstants.kRotationRatio, ArmConstants.kArmMotorP, ArmConstants.kArmMotorI,
            ArmConstants.kArmMotorD, ArmPorts.kLowerSwitch, ArmPorts.kUpperSwitch);
    }

    public void setTargetAngle(ArmAngleEnum angle)
    {
        switch (angle)
        {
            case HighPosition:
                Arm.setTargetAngle(ArmConstants.kHighPosition);
                break;
            case MidPosition:
                Arm.setTargetAngle(ArmConstants.kMidPosition);
                break;
            case LowPosition:
                Arm.setTargetAngle(ArmConstants.kLowPosition);
                break;
            case SubstationPosition:
                Arm.setTargetAngle(ArmConstants.kSubstationPosition);

        }
    }

    public void setTargetAngle(double angle)
    {
        Arm.setTargetAngle(angle);
    }

    @Override
    public boolean isAtSetPoint()
    {
        return Arm.getCurrentAngle() == Arm.getTargetAngle();
    }

    @Override
    public void setJoystickAngle(double joystickInput){
        double angleChange = joystickInput * ArmConstants.kJoystickRatio; // Converting joystick input into degrees moved on the arm
        setTargetAngle(getCurrentAngle() + angleChange);
        
    }
    @Override
    public double getCurrentAngle()
    {
        return Arm.getCurrentAngle();
    }

    @Override
    public double getSetPoint()
    {
        return Arm.getTargetAngle();
    }

    @Override
    public void periodic()
    {
        Arm.checkLimitSensors();
    }
}
