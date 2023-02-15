package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports.ArmPorts;
import frc.robot.subsystems.superclasses.Arm;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm.
 */
public class SK23Extender extends SubsystemBase
{
    public static final int kRotationRatio = 0;
    public static final int kArmMotorP     = 0;
    public static final int kArmMotorI     = 0;
    public static final int kArmMotorD     = 0;

    public static final int kHighPosition = 0;
    public static final int kMidPosition  = 0;
    public static final int kLowPosition  = 0;

    ArmAngleInternal mainMotor;

    public static enum ExtenderEnum
    {
        /** Set the angle to reach the top cube node */
        HighPosition,
        /** Set the angle to reach the middle cube node */
        MidPosition,
        /** Set the angle to reach the bottom cube node */
        LowPosition
    }

    public SK23Extender()
    {
        mainMotor = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor,
            kRotationRatio, kArmMotorP, kArmMotorI, kArmMotorD, ArmPorts.kLowerSwitch,
            ArmPorts.kUpperSwitch);
    }

    public void setTargetAngle(ExtenderEnum angle)
    {
        switch (angle)
        {
            case HighPosition:
                mainMotor.setTargetAngle(kHighPosition);
                break;
            case MidPosition:
                mainMotor.setTargetAngle(kMidPosition);
                break;
            case LowPosition:
                mainMotor.setTargetAngle(kLowPosition);
                break;

        }
    }

    public double getCurrentAngle()
    {
        return mainMotor.getCurrentAngle();
    }

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
