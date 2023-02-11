package frc.robot.subsystems;

import frc.robot.Ports.ArmPorts;
import frc.robot.subsystems.superclasses.Arm;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm.
 */
public abstract class SK23LowArm extends Arm
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

    ArmAngleInternal mainMotor;

    public SK23LowArm()
    {
        mainMotor = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor,
            kRotationRatio, ArmPorts.kLowerSwitch, ArmPorts.kUpperSwitch, kArmMotorP, kArmMotorI,
            kArmMotorD);
    }

    public void setArmAngle(ArmAngleEnum angle)
    {
        switch (angle)
        {
            case HighCube:
                mainMotor.setTargetAngle(kHighCube);
                break;
            case MidCube:
                mainMotor.setTargetAngle(kMidCube);
                break;
            case LowCube:
                mainMotor.setTargetAngle(kLowCube);
                break;
            case HighCone:
                mainMotor.setTargetAngle(kHighCone);
                break;
            case LowCone:
                mainMotor.setTargetAngle(kLowCone);
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
    public abstract void extendArm(boolean extend);

    @Override
    public abstract boolean isArmExtended();

    @Override
    public void periodic()
    {
        mainMotor.checkLimitSensors();
    }
}
