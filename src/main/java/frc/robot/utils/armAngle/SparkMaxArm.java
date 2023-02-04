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
    CANSparkMax           motor;
    RelativeEncoder       encoder;
    SparkMaxPIDController pidController;
    int                   rotationRatio;
    DigitalInput          limitSwitch;
    DigitalInput          resetSwitch;

    /**
     * Creates a new CAN Spark Max arm
     * 
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
    public SparkMaxArm(int CanID, int rotationRatio, int resetDioId, int limitDioId, int p, int i, int d)
    {
        this.rotationRatio = rotationRatio;
        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();

        pidController = motor.getPIDController();
        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);

        resetSwitch = new DigitalInput(resetDioId);
        limitSwitch = new DigitalInput(limitDioId);
        encoder.setPositionConversionFactor(rotationRatio); // Sets 1 native unit of encoder to 1 degree

        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void reset()
    {
        encoder.setPosition(0.0);

    }

    public boolean getResetDIO()
    {
        return resetSwitch.get();
    }

    public boolean getLimitDIO()
    {
        return limitSwitch.get();
    }

    public void stop()
    {
        motor.stopMotor();
    }

    public void setAngle(int degrees)
    {
        pidController.setReference(degrees, CANSparkMax.ControlType.kPosition);
    }
}
