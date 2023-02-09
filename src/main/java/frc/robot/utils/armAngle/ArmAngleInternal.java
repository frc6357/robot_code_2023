package frc.robot.utils.armAngle;

/**
 * Generic class to set and read the angle of an arm in degrees, with the lower point
 * starting at 0 and increasing towards the upper point. It does this using a motor that
 * contains an internal encoder, and can determine when it is at it's max point and zero
 * point using upper and lower sensors.
 */
public class ArmAngleInternal
{
    GenericArmMotor motor;

    /**
     * Enumerated Value that determines the motor type that is used for the arm
     */

    public enum AngleMotorType
    {
        /**
         * CAN Spark Max motor
         */
        SparkMax
    }

    /**
     * Creates a new arm motor of the specified type
     * 
     * @param motorType
     *            Type of motor the arm is using
     * @param CanID
     *            Can ID of the motor used
     * @param rotationRatio
     *            Ratio of wheel rotations to degree (rotations/degree)
     * @param Kp
     *            Value for proportional gain constant in PID controller
     * @param Ki
     *            Value for integral gain constant in PID controller
     * @param Kd
     *            Value for derivative gain constant in PID controller
     * @param lowerSensor
     *            ID for digital input sensor that determines lower limit of arm
     * @param upperSensor
     *            ID for digital input sensor that determines upper limit of arm
     */
    public ArmAngleInternal(AngleMotorType motorType, int CanID, int rotationRatio, int Kp, int Ki,
        int Kd, int lowerSensor, int upperSensor)
    {
        switch (motorType)
        {
            case SparkMax:
                motor = new SparkMaxArm(CanID, rotationRatio, lowerSensor, upperSensor, Kp, Ki, Kd);
                break;
        }
    }

    /**
     * Creates a new arm motor of the specified type
     * 
     * @param motorType
     *            Type of motor the arm is using
     * @param CanID
     *            Can ID of the motor used
     * @param rotationRatio
     *            Ratio of wheel rotations to degree (rotations/degree)
     * @param Kp
     *            Value for proportional gain constant in PID controller
     * @param Ki
     *            Value for integral gain constant in PID controller
     * @param Kd
     *            Value for derivative gain constant in PID controller
     * @param lowerSensor
     *            ID for digital input sensor that determines lower limit of arm
     */
    public ArmAngleInternal(AngleMotorType motorType, int CanID, int rotationRatio, int Kp, int Ki,
        int Kd, int lowerSensor)
    {
        switch (motorType)
        {
            case SparkMax:
                motor = new SparkMaxArm(CanID, rotationRatio, lowerSensor, Kp, Ki, Kd);
                break;
        }
    }

    /**
     * Creates a new arm motor of the specified type
     * 
     * @param motorType
     *            Type of motor the arm is using
     * @param CanID
     *            Can ID of the motor used
     * @param rotationRatio
     *            Ratio of wheel rotations to degree (rotations/degree)
     * @param Kp
     *            Value for proportional gain constant in PID controller
     * @param Ki
     *            Value for integral gain constant in PID controller
     * @param Kd
     *            Value for derivative gain constant in PID controller
     */
    public ArmAngleInternal(AngleMotorType motorType, int CanID, int rotationRatio, int Kp, int Ki,
        int Kd)
    {
        switch (motorType)
        {
            case SparkMax:
                motor = new SparkMaxArm(CanID, rotationRatio, Kp, Ki, Kd);
                break;
        }
    }

    /**
     * Resets position of encoder to 0.0.
     */
    public void resetEncoder()
    {
        motor.resetEncoder();
    }

    /**
     * @return Returns the value of digital input sensor that is used for location the
     *         lower limit of the arm.
     */
    public boolean isLowerReached()
    {
        return motor.isLowerReached();
    }

    /**
     * @return Returns the value of digital input sensor that is used for locating the
     *         upper limit of the arm.
     */
    public boolean isUpperReached()
    {
        return motor.isUpperReached();
    }

    /**
     * Stops motor movement. Motor can be moved again by calling set without having to
     * re-enable the motor.
     */
    public void stop()
    {
        motor.stop();
    }

    /**
     * Sets the angle of the arm to specified degrees, starting at 0 at the lower point
     * and increasing towards upper point
     * 
     * @param degrees
     *            Degree to which the arm should be set
     */
    public double getSetPoint()
    {
        return motor.getSetPoint();
    }

    public double getCurrentAngle()
    {
        return motor.getCurrentAngle();
    }

    public void setAngle(int degrees)
    {
        motor.setAngle(degrees);
    }

}
