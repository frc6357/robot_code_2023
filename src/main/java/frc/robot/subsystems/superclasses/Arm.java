package frc.robot.subsystems.superclasses;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Arm extends SubsystemBase
{
    public static enum ArmAngleEnum
    {
        /** Set the angle to reach the top cube node */
        HighCube,
        /** Set the angle to reach the middle cube node */
        MidCube,
        /** Set the angle to reach the bottom cube node */
        LowCube,
        /** Set the angle to reach the top cone node */
        HighCone,
        /** Set the angle to reach the bottom cone node */
        LowCone
    }

    /**
     * Set the arm angle to a specific angle using pre-set values.
     * 
     * @param angle
     *            Enum that specifies which angle you want the arm to be set at
     */
    public abstract void setArmAngle(ArmAngleEnum angle);

    /**
     * @return Returns the angle that the arm is currently at
     */
    public abstract double getCurrentAngle();

    /**
     * @return Returns the current setpoint that the arm is attempting to reach
     */
    public abstract double getSetPoint();

    public enum armState
    {
        EXTEND, 
        RETRACT
    }

    /**
     * Extends or retracts the arm to a set position
     * 
     * @param extend
     *            Whether or not the arm should be extended
     */
    public abstract void extendArm(boolean extend);

    /**
     * 
     * @return Whether or not the arm is in an extended position
     */
    public abstract boolean isArmExtended();
}
