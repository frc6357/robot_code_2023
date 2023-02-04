package frc.robot.utils.armAngle;

/**
 * Enumerated Value that determines the motor type that is used for the arm
 */
enum MotorType
{
    /**
     * CAN Spark Max motor
     */
    SparkMax
}

/**
 * Generic class to set the angle of an arm using a motor that contains an internal encoder, and
 * to determine when it is at it's max point and zero point using digital input sensors.
 */
public class ArmAngleInternal
{
    GenericArmMotor motor;

    /**
     * Creates a new arm motor of the specified type
     * 
     * @param motorType
     *            Type of motor the arm is using
     * @param CanID
     *            Can ID of the motor used
     * @param rotationRatio
     *            Ratio of wheel rotations to degree (rotations/degree)
     * @param resetDioId
     *            ID for digital input sensor that determines reset point of arm
     * @param limitDioId
     *            ID for digital input sensor that determines max limit point of arm
     * @param p
     *            Value for proportional gain constant in PID controller
     * @param i
     *            Value for integral gain constant in PID controller
     * @param d
     *            Value for derivative gain constant in PID controller
     */
    public ArmAngleInternal(MotorType motorType, int CanID, int rotationRatio,
        int resetDioId, int limitDioId, int p, int i, int d)
    {
        if (motorType == MotorType.SparkMax)
        {
            motor = new SparkMaxArm(CanID, rotationRatio, resetDioId, limitDioId, p, i, d);
        }
    }

    /**
     * Resets position of encoder to 0.0.
     */
    public void reset()
    {
        motor.reset();
    }

    /**
     * Returns the value of digital input sensor that is used for resetting the arm.
     */
    public boolean getResetDIO()
    {
        return motor.getResetDIO();
    }

    /**
     * Returns the value of digital input sensor that is used for locating the limit of
     * the arm.
     */
    public boolean getLimitDIO()
    {
        return motor.getLimitDIO();
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
     * Sets the angle of the arm to specified degrees
     * 
     * @param degrees
     *            Degree to which the arm should be set
     */
    public void setAngle(int degrees)
    {
        motor.setAngle(degrees);
    }

}
