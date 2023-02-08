package frc.robot.utils.armAngle;

/**
 * Generic class to create different types of motors to be used to find arm angles.
 */
public abstract class GenericArmMotor
{
    /**
     * Resets position of encoder to 0.0
     */
    public abstract void resetEncoder();

    /**
     * @return Returns the value of the sensor that is used for locating the lower limit
     *         of the arm.
     */
    public abstract boolean isLowerReached();

    /**
     * @return Returns true if the lower sensor is present
     */
    public abstract boolean isLowerAvailable();

    /**
     * @return Returns the value of the sensor that is used for locating the upper limit
     *         of the arm.
     */
    public abstract boolean isUpperReached();

    /**
     * @return Returns true if the lower sensor is present
     */
    public abstract boolean isUpperAvailable();

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
    public abstract double getSetPoint();

    public abstract double getCurrentAngle();

    public abstract void setAngle(double degrees);
}
