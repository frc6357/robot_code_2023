package frc.robot.utils.armAngle;

/**
 * Generic class to create different types of motors to be used to find arm angles.
 */
public abstract class GenericArmMotor
{
    /**
     * Resets position of encoder to 0.0
     */
    public abstract void reset();

    /**
     * Returns the value of digital input sensor that is used for resetting the arm.
     */
    public abstract boolean getResetDIO();

    /**
     * Returns the value of digital input sensor that is used for locating the limit of
     * the arm.
     */
    public abstract boolean getLimitDIO();

    /**
     * Stops motor movement. Motor can be moved again by calling set without having to
     * re-enable the motor.
     */
    public abstract void stop();

    /**
     * Sets the angle of the arm to specified degrees
     * 
     * @param degrees
     *            Degree to which the arm should be set
     */
    public abstract void setAngle(int degrees);
}
