package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ExtenderConstants;
import frc.robot.Ports.ExtenderPorts;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the extension arm of the robot. Capable of moving the arm to a
 * specified position and reading the current position of the arm.
 */
public class SK23Extender extends SubsystemBase
{

    ArmAngleInternal mainMotor;

    public static enum ExtenderEnum
    {
        /** Set the position to reach the top cube node */
        HighPosition,
        /** Set the position to reach the middle cube node */
        MidPosition,
        /** Set the position to reach the bottom cube node */
        LowPosition,
        /** Set the position to reach the substation */
        SubstationPosition
    }

    public SK23Extender()
    {
        mainMotor = new ArmAngleInternal(AngleMotorType.SparkMax, ExtenderPorts.kMainMotor.ID,
            ExtenderConstants.kRotationRatio, ExtenderConstants.kArmMotorP,
            ExtenderConstants.kArmMotorI, ExtenderConstants.kArmMotorD, ExtenderPorts.kLowerSwitch,
            ExtenderPorts.kUpperSwitch);
    }

    /**
     * Set the arm position to a specific angle using pre-set values.
     * 
     * @param angle
     *            Enum that specifies which position you want the arm to be set at
     */
    public void setTargetPosition(ExtenderEnum angle)
    {
        switch (angle)
        {
            case HighPosition:
                mainMotor.setTargetAngle(ExtenderConstants.kHighPosition);
                break;
            case MidPosition:
                mainMotor.setTargetAngle(ExtenderConstants.kMidPosition);
                break;
            case LowPosition:
                mainMotor.setTargetAngle(ExtenderConstants.kLowPosition);
                break;
            case SubstationPosition:
                mainMotor.setTargetAngle(ExtenderConstants.kSubstationPosition);

        }
    }

    /**
     * Set the arm angle to a specific angle using pre-set values.
     * 
     * @param angle
     *            Enum that specifies which angle you want the arm to be set at
     */
    public void setTargetPosition(double angle)
    {
        mainMotor.setTargetAngle(angle);
    }

    /**
     * @return Returns the position that the arm is currently at
     */
    public double getCurrentPosition()
    {
        return mainMotor.getCurrentAngle();
    }

    /**
     * 
     * @return Returns true if the arm has reached it's current set point.
     */
    public boolean isAtSetPoint()
    {
        return mainMotor.getCurrentAngle() == mainMotor.getTargetAngle();
    }

    /**
     * @return Returns the current setpoint that the arm is attempting to reach
     */
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
