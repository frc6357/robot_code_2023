package frc.robot.utils.armAngle;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Specific class to set the angle of an arm using a CAN Spark Max Brushless motor with an
 * internal encoder, and to determine when it is at it's max point and zero point using
 * digital input sensors.
 */
public class SparkMaxArm extends GenericArmMotor
{
    boolean               lowerPresent;
    boolean               upperPresent;
    double                setPoint;
    CANSparkMax           motor;
    RelativeEncoder       encoder;
    SparkMaxPIDController pidController;
    int                   rotationRatio;
    DigitalInput          UpperSensor;
    DigitalInput          LowerSensor;

    /**
     * Creates a new CAN Spark Max arm
     * 
     * @param CanID
     *            Can ID of the motor used
     * @param rotationRatio
     *            Ratio of wheel rotations to degree (rotations/degree)
     * @param p
     *            Value for proportional gain constant in PID controller
     * @param i
     *            Value for integral gain constant in PID controller
     * @param d
     *            Value for derivative gain constant in PID controller
     * @param LowerSensor
     *            ID for digital input sensor that determines reset point of arm
     * @param UpperSensor
     *            ID for digital input sensor that determines max limit point of arm
     */
    public SparkMaxArm(int CanID, int rotationRatio, int p, int i, int d, int LowerSensor,
        int UpperSensor)
    {
        this.rotationRatio = rotationRatio;
        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();

        pidController = motor.getPIDController();
        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);

        this.LowerSensor = new DigitalInput(LowerSensor);
        lowerPresent = true;
        this.UpperSensor = new DigitalInput(UpperSensor);
        upperPresent = true;
        encoder.setPositionConversionFactor(rotationRatio); // Sets 1 native unit of encoder to 1 degree

        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    /**
     * Creates a new CAN Spark Max arm
     * 
     * @param CanID
     *            Can ID of the motor used
     * @param rotationRatio
     *            Ratio of wheel rotations to degree (rotations/degree)
     * @param p
     *            Value for proportional gain constant in PID controller
     * @param i
     *            Value for integral gain constant in PID controller
     * @param d
     *            Value for derivative gain constant in PID controller
     * @param LowerSensor
     *            ID for digital input sensor that determines reset point of arm
     */
    public SparkMaxArm(int CanID, int rotationRatio, int p, int i, int d, int LowerSensor)
    {
        this.rotationRatio = rotationRatio;
        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();

        pidController = motor.getPIDController();
        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);

        this.LowerSensor = new DigitalInput(LowerSensor);
        lowerPresent = true;
        upperPresent = false;
        encoder.setPositionConversionFactor(rotationRatio); // Sets 1 native unit of encoder to 1 degree

        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    /**
     * Creates a new CAN Spark Max arm
     * 
     * @param CanID
     *            Can ID of the motor used
     * @param rotationRatio
     *            Ratio of wheel rotations to degree (rotations/degree)
     * @param p
     *            Value for proportional gain constant in PID controller
     * @param i
     *            Value for integral gain constant in PID controller
     * @param d
     *            Value for derivative gain constant in PID controller
     */
    public SparkMaxArm(int CanID, int rotationRatio, int p, int i, int d)
    {
        this.rotationRatio = rotationRatio;
        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();

        pidController = motor.getPIDController();
        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);

        lowerPresent = false;
        upperPresent = false;
        encoder.setPositionConversionFactor(rotationRatio); // Sets 1 native unit of encoder to 1 degree

        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void resetEncoder()
    {
        encoder.setPosition(0.0); //Reset Position of encoder is 0.0

    }

    public boolean isLowerAvailable()
    {
        return lowerPresent;
    }

    public boolean isLowerReached()
    {
        return LowerSensor.get();
    }

    public boolean isUpperAvailable()
    {
        return upperPresent;
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
        return encoder.getPosition();
    }

    public double getSetPoint()
    {
        return setPoint;
    }

    public void setAngle(double degrees)
    {
        setPoint = degrees;
        pidController.setReference(degrees, CANSparkMax.ControlType.kPosition);
    }
}
