package frc.robot.utils.armAngle;

import java.util.ArrayList;

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
    private ArrayList<SparkMaxArm> armArray = new ArrayList<SparkMaxArm>();
    double                         Kp;
    double                         Ki;
    double                         Kd;
    boolean                        isLowerPresent;
    boolean                        isUpperPresent;
    double                         setPoint;
    CANSparkMax                    motor;
    RelativeEncoder                encoder;
    SparkMaxPIDController          pidController;
    double                         rotationRatio;
    DigitalInput                   UpperSensor;
    DigitalInput                   LowerSensor;

    /**
     * Creates a new CAN Spark Max arm
     * 
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
     * @param LowerSensorID
     *            ID for digital input sensor that determines reset point of arm
     * @param UpperSensorID
     *            ID for digital input sensor that determines max limit point of arm
     */
    public SparkMaxArm(int CanID, double rotationRatio, double Kp, double Ki, double Kd,
        int LowerSensorID, int UpperSensorID)
    {
        this.rotationRatio = rotationRatio;
        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();

        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;

        pidController = motor.getPIDController();
        pidController.setP(Kp);
        pidController.setI(Ki);
        pidController.setD(Kd);

        this.LowerSensor = new DigitalInput(LowerSensorID);
        isLowerPresent = true;
        this.UpperSensor = new DigitalInput(UpperSensorID);
        isUpperPresent = true;
        encoder.setPositionConversionFactor(rotationRatio); // Sets the units of the encoder to degrees

        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        armArray.add(this);
    }

    /**
     * Creates a new CAN Spark Max arm
     * 
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
     * @param LowerSensorID
     *            ID for digital input sensor that determines reset point of arm
     */
    public SparkMaxArm(int CanID, double rotationRatio, double Kp, double Ki, double Kd,
        int LowerSensorID)
    {
        this.rotationRatio = rotationRatio;
        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();

        pidController = motor.getPIDController();
        pidController.setP(Kp);
        pidController.setI(Ki);
        pidController.setD(Kd);

        this.LowerSensor = new DigitalInput(LowerSensorID);
        isLowerPresent = true;
        isUpperPresent = false;
        encoder.setPositionConversionFactor(rotationRatio); // Sets the units of the encoder to degrees

        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        armArray.add(this);
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

    public SparkMaxArm(int CanID, double rotationRatio, double p, double i, double d)
    {
        this.rotationRatio = rotationRatio;
        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();

        pidController = motor.getPIDController();
        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);

        isLowerPresent = false;
        isUpperPresent = false;
        encoder.setPositionConversionFactor(rotationRatio); // Sets the encoder units to degrees

        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        armArray.add(this);
    }

    public void resetEncoder()
    {
        for (SparkMaxArm arm : armArray)
        {
            arm.encoder.setPosition(0.0); //Reset Position of encoder is 0.0
        }
    }

    public void addFollowerMotor(int CanID)
    {
        SparkMaxArm followerMotor = new SparkMaxArm(CanID, rotationRatio, Kp, Ki, Kd);
        armArray.add(followerMotor);
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
        for (SparkMaxArm arm : armArray)
        {
            arm.motor.stopMotor();
        }
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
        for (SparkMaxArm arm : armArray)
        {
            arm.pidController.setReference(degrees, CANSparkMax.ControlType.kPosition);
        }

    }
}
