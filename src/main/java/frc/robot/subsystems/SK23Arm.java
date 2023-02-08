package frc.robot.subsystems;

import frc.robot.Ports.ArmPorts;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm.
 */
public class SK23Arm
{
    public static final int kRotationRatio = 0;
    public static final int kArmMotorP     = 0;
    public static final int kArmMotorI     = 0;
    public static final int kArmMotorD     = 0;

    public static final int kHighCube = 0;
    public static final int kMidCube  = 0;
    public static final int kLowCube  = 0;
    public static final int kHighCone = 0;
    public static final int kLowCone  = 0;

    public static final AngleMotorType kMotorType = AngleMotorType.SparkMax;

    public enum armAngleEnum
    {
        HighCube,
        MidCube,
        LowCube,
        HighCone,
        LowCone
    }

    ArmAngleInternal angleMotor;

    public SK23Arm()
    {
        angleMotor = new ArmAngleInternal(kMotorType, ArmPorts.kMainMotor, kRotationRatio,
            ArmPorts.kResetSwitch, ArmPorts.kLimitSwitch, kArmMotorP, kArmMotorI, kArmMotorD);
    }

    public void setArmAngle(armAngleEnum angle)
    {
        switch (angle)
        {
            case HighCube:
                angleMotor.setAngle(kHighCube);
                break;
            case MidCube:
                angleMotor.setAngle(kMidCube);
                break;
            case LowCube:
                angleMotor.setAngle(kLowCube);
                break;
            case HighCone:
                angleMotor.setAngle(kHighCone);
                break;
            case LowCone:
                angleMotor.setAngle(kLowCone);
                break;

        }
    }

    public double getCurrentAngle()
    {
        return angleMotor.getCurrentAngle();
    }

    public double getSetPoint()
    {
        return angleMotor.getSetPoint();
    }
}
