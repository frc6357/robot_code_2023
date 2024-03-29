package frc.robot.utils.armAngle;

/**
 * Generic class to set and read the angle of an arm in degrees, with the lower
 * point
 * starting at 0 and increasing towards the upper point. It does this using a
 * motor that
 * contains an internal encoder, and can determine when it is at it's max point
 * and zero
 * point using upper and lower sensors.
 */
public class ArmAngleInternal {
    GenericArmMotor motor;

    /**
     * Enumerated Value that determines the motor type that is used for the arm
     */

    public static enum AngleMotorType {
        /**
         * CAN Spark Max motor
         */
        SparkMax
    }

    /**
     * Creates a new arm motor of the specified type
     * 
     * @param motorType
     *                      Type of motor the arm is using
     * @param CanID
     *                      Can ID of the motor used
     * @param gearRatio
     *                      Number of motor shaft rotations per output shaft
     *                      rotations
     * @param Kp
     *                      Value for proportional gain constant in PID controller
     * @param Ki
     *                      Value for integral gain constant in PID controller
     * @param Kd
     *                      Value for derivative gain constant in PID controller
     * @param Kiz
     *                      Value for integral zone gain constant in PID controller
     * 
     * @param rampRate
     *                      The maximum rate at which the motor controller's output
     *                      is allowed to change.
     * @param lowerSensorID
     *                      ID for digital input sensor that determines lower limit
     *                      of arm
     * @param upperSensorID
     *                      ID for digital input sensor that determines upper limit
     *                      of arm
     */
    public ArmAngleInternal(AngleMotorType motorType, int CanID, double gearRatio, double Kp,
            double Ki, double Kd, double Kiz, double rampRate, int lowerSensorID, int upperSensorID) {
        switch (motorType) {
            case SparkMax:
                motor = new SparkMaxArm(CanID, gearRatio, Kp, Ki, Kd, Kiz, rampRate, lowerSensorID,
                        upperSensorID);
                break;
        }
    }

    /**
     * Creates a new arm motor of the specified type
     * 
     * @param motorType
     *                      Type of motor the arm is using
     * @param CanID
     *                      Can ID of the motor used
     * @param gearRatio
     *                      Number of motor shaft rotations per output shaft
     *                      rotations
     * @param Kp
     *                      Value for proportional gain constant in PID controller
     * @param Ki
     *                      Value for integral gain constant in PID controller
     * @param Kd
     *                      Value for derivative gain constant in PID controller
     * @param Kiz
     *                      Value for integral zone gain constant in PID controller
     * @param rampRate
     *                      The maximum rate at which the motor controller's output
     *                      is allowed to change.
     * @param lowerSensorID
     *                      ID for digital input sensor that determines lower limit
     *                      of arm
     */
    public ArmAngleInternal(AngleMotorType motorType, int CanID, double gearRatio, double Kp,
            double Ki, double Kd, double Kiz, double rampRate, int lowerSensorID) {
        switch (motorType) {
            case SparkMax:
                motor = new SparkMaxArm(CanID, gearRatio, Kp, Ki, Kd, Kiz, rampRate, lowerSensorID);
                break;
        }
    }

    /**
     * Creates a new arm motor of the specified type
     * 
     * @param motorType
     *                  Type of motor the arm is using
     * @param CanID
     *                  Can ID of the motor used
     * @param gearRatio
     *                  Number of motor shaft rotations per output shaft rotations
     * @param Kp
     *                  Value for proportional gain constant in PID controller
     * @param Ki
     *                  Value for integral gain constant in PID controller
     * @param Kd
     *                  Value for derivative gain constant in PID controller
     * @param Kiz
     *                  Value for integral zone gain constant in PID controller
     * @param rampRate
     *                  The maximum rate at which the motor controller's output is
     *                  allowed to change.
     */
    public ArmAngleInternal(AngleMotorType motorType, int CanID, double gearRatio, double Kp,
            double Ki, double Kd, double Kiz, double rampRate) {
        switch (motorType) {
            case SparkMax:
                motor = new SparkMaxArm(CanID, gearRatio, Kp, Ki, Kd, Kiz, rampRate);
                break;
        }
    }

    /**
     * Resets position of encoder to 0.0.
     */
    public void resetEncoder() {
        motor.resetEncoder();
    }

    /**
     * Adds a new motor that follows the actions of the lead motor
     * 
     * @param CanID
     *              CanID for the follower motor
     */

    public void addFollowerMotor(int CanID, boolean isReversed) {
        motor.addFollowerMotor(CanID, isReversed);
    }

    /**
     * @return Returns the value of digital input sensor that is used for location
     *         the
     *         lower limit of the arm.
     */
    public boolean isLowerReached() {
        return motor.isLowerReached();
    }

    /**
     * @return Returns true if the lower sensor is present
     */
    public boolean isLowerAvailable() {
        return motor.isLowerAvailable();
    }

    /**
     * @return Returns the value of digital input sensor that is used for locating
     *         the
     *         upper limit of the arm.
     */
    public boolean isUpperReached() {
        return motor.isUpperReached();
    }

    /**
     * @return Returns true if the upper sensor is present
     */
    public boolean isUpperAvailable() {
        return motor.isUpperAvailable();
    }

    /**
     * Stops motor movement. Motor can be moved again by calling set without having
     * to
     * re-enable the motor.
     */
    public void stop() {
        motor.stop();
    }

    /**
     * @return Returns the current setpoint that the arm is attempting to reach
     */
    public double getTargetAngle() {
        return motor.getTargetAngle();
    }

    /**
     * @return Returns the angle that the arm is currently at
     */
    public double getCurrentAngle() {
        return motor.getCurrentAngle();
    }

    /**
     * Sets the angle of the arm to specified degrees, starting at 0 at the lower
     * point
     * and increasing towards upper point
     * 
     * @param degrees
     *                Degree to which the arm should be set
     */
    public void setTargetAngle(double degrees) {
        motor.setTargetAngle(degrees);
    }

    /**
     * 
     * Checks both the upper and lower sensor, if they are present, to determine if
     * they
     * have been reached. If so it will stop the motor, and reset the encoder if it
     * has
     * reached the bottom sensor.
     */
    public void checkLimitSensors() {
        if (isLowerAvailable() && isLowerReached()) {
            resetEncoder();
            stop();
        }

        if (isUpperAvailable() && isUpperReached()) {
            stop();
        }
    }

    public void periodic() {
        motor.periodic();
    }

    public void testPeriodic() {
        motor.testPeriodic();
    }
}
