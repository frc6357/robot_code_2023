package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Ports.ArmPorts;
import frc.robot.subsystems.superclasses.Arm;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm.
 */
public class SK23HighArm extends Arm
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

    ArmAngleInternal angleMotor;

    public SK23HighArm()
    {
        angleMotor = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor,
            kRotationRatio, kArmMotorP, kArmMotorI, kArmMotorD, ArmPorts.kResetSwitch,
            ArmPorts.kLimitSwitch);
    }

    public void setArmAngle(ArmAngleEnum angle)
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

    @Override
    public double getCurrentAngle()
    {
        return angleMotor.getCurrentAngle();
    }

    @Override
    public double getSetPoint()
    {
        return angleMotor.getSetPoint();
    }

    @Override
    public void extendArm(boolean extend)
    {
        DriverStation.reportWarning("High Arm cannot extend", true);
    }

    @Override
    public boolean isArmExtended()
    {
        DriverStation.reportWarning("High Arm cannot extend", true);
        return false;
    }
}
