package frc.robot.utils.armAngle;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Specific class to set the angle of an arm using a CAN Spark Max Brushless motor with an
 * internal encoder, and to determine when it is at it's max point and zero point using
 * digital input sensors.
 */
public class SparkMaxArm extends GenericArmMotor
{
    double                Kp;
    double                Ki;
    double                Kd;
    boolean               isLowerPresent;
    boolean               isUpperPresent;
    double                setPoint;
    CANSparkMax           motor;
    RelativeEncoder       encoder;
    SparkMaxPIDController pidController;
    double                gearRatio;
    DigitalInput          UpperSensor;
    DigitalInput          LowerSensor;

    /**
     * Creates a new CAN Spark Max arm
     * 
     * @param CanID
     *            Can ID of the motor used
     * @param gearRatio
     *            Number of motor shaft rotations per output shaft rotations
     * @param Kp
     *            Value for proportional gain constant in PID controller
     * @param Ki
     *            Value for integral gain constant in PID controller
     * @param Kd
     *            Value for derivative gain constant in PID controller
     * @param LowerSensorID
     *            ID for digital input sensor that determines reset point of arm
     * @param UpperSensorID
     *            ID for digital input sensor that determines max limit point of arm
     */
    public SparkMaxArm(int CanID, double gearRatio, double Kp, double Ki, double Kd,
        int LowerSensorID, int UpperSensorID)
    {
        this(CanID, gearRatio, Kp, Ki, Kd, LowerSensorID);
        this.gearRatio = gearRatio;

        this.UpperSensor = new DigitalInput(UpperSensorID);
        isUpperPresent = true;
    }

    /**
     * Creates a new CAN Spark Max arm
     * 
     * @param CanID
     *            Can ID of the motor used
     * @param gearRatio
     *            Number of motor shaft rotations per output shaft rotations
     * @param Kp
     *            Value for proportional gain constant in PID controller
     * @param Ki
     *            Value for integral gain constant in PID controller
     * @param Kd
     *            Value for derivative gain constant in PID controller
     * @param LowerSensorID
     *            ID for digital input sensor that determines reset point of arm
     */
    public SparkMaxArm(int CanID, double gearRatio, double Kp, double Ki, double Kd,
        int LowerSensorID)
    {
        this(CanID, gearRatio, Kp, Ki, Kd);

        this.LowerSensor = new DigitalInput(LowerSensorID);
        isLowerPresent = true;
    }

    /**
     * Creates a new CAN Spark Max arm
     * 
     * @param CanID
     *            Can ID of the motor used
     * @param gearRatio
     *            Number of motor shaft rotations per output shaft rotations
     * @param p
     *            Value for proportional gain constant in PID controller
     * @param i
     *            Value for integral gain constant in PID controller
     * @param d
     *            Value for derivative gain constant in PID controller
     */

    public SparkMaxArm(int CanID, double gearRatio, double p, double i, double d)
    {
        this.gearRatio = gearRatio;
        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        encoder = motor.getEncoder();

        pidController = motor.getPIDController();
        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);

        isLowerPresent = false;
        isUpperPresent = false;

        encoder.setPositionConversionFactor(1.0);
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }


    public double getAppliedOutput(){
        return motor.getAppliedOutput();
    }

    public void resetEncoder()
    {
        encoder.setPosition(0.0); //Reset Position of encoder is 0.0

    }

    public void addFollowerMotor(int CanID)
    {
        try (CANSparkMax followerMotor =
                new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless))
        {
            followerMotor.follow(motor);
        }
    }

    public boolean isLowerAvailable()
    {
        return isLowerPresent;
    }

    public boolean isLowerReached()
    {
        return LowerSensor.get();
    }

    public boolean isUpperAvailable()
    {
        return isUpperPresent;
    }

    public boolean isUpperReached()
    {
        return UpperSensor.get();
    }

    public void stop()
    {
        motor.stopMotor();
    }

    public double getCurrentAngle()
    {
        double current_value = encoder.getPosition();
        return current_value;
    }

    public double getTargetAngle()
    {
        return setPoint;
    }

    public void setTargetAngle(double degrees)
    {
        setPoint = (degrees * gearRatio) / 360.0;
        pidController.setReference(setPoint, CANSparkMax.ControlType.kPosition);
    }

    public void periodic(){
        double applied_output = motor.getAppliedOutput();
        SmartDashboard.putNumber("Applied Output", applied_output );
        double current = motor.getOutputCurrent();
        SmartDashboard.putNumber("Current", current);
    }
}
