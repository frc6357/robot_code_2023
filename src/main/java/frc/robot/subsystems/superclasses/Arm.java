package frc.robot.subsystems.superclasses;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Arm extends SubsystemBase
{
    public static enum ArmAngleEnum
    {
        /** Set the angle to reach the top cube node */
        HighPosition,
        /** Set the angle to reach the middle cube node */
        MidPosition,
        /** Set the angle to reach the bottom cube node */
        FloorPosition,
        /** Set the angle to reach the bottom cube node */
        ZeroPosition,
        /** Set the angle to reach the substation */
        SubstationPosition
    }

    /**
     * Set the arm angle to a specific angle using pre-set values.
     * 
     * @param angle
     *            Enum that specifies which angle you want the arm to be set at
     */
    public abstract void setTargetAngle(ArmAngleEnum angle);

    /**
     * Sets the arm to the specified angle in degrees
     * 
     * @param angle
     *            Angle to which the arm is set to (degrees)
     */
    public abstract void setTargetAngle(double angle);

    /**
     * @return Returns the angle that the arm is currently at
     */
    public abstract double getCurrentAngle();

    /**
     * @return Returns the current setpoint that the arm is attempting to reach
     */
    public abstract double getTargetAngle();

    /**
     * 
     * @return Returns true if the arm has reached it's current set point.
     */
    public abstract boolean isAtTargetAngle();
}
