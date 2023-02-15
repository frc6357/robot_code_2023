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
    ArmAngleInternal mainMotor;

    public SK23Arm()
    {
        mainMotor = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor,
            ArmConstants.kRotationRatio, ArmConstants.kArmMotorP, ArmConstants.kArmMotorI,
            ArmConstants.kArmMotorD, ArmPorts.kLowerSwitch, ArmPorts.kUpperSwitch);
    }

    public void setTargetAngle(ArmAngleEnum angle)
    {
        switch (angle)
        {
            case HighPosition:
                mainMotor.setTargetAngle(ArmConstants.kHighPosition);
                break;
            case MidPosition:
                mainMotor.setTargetAngle(ArmConstants.kMidPosition);
                break;
            case LowPosition:
                mainMotor.setTargetAngle(ArmConstants.kLowPosition);
                break;

        }
    }

    @Override
    public double getCurrentAngle()
    {
        return mainMotor.getCurrentAngle();
    }

    @Override
    public double getSetPoint()
    {
        return mainMotor.getTargetAngle();
    }

    @Override
    public void periodic()
    {
        mainMotor.checkLimitSensors();
    }
}
