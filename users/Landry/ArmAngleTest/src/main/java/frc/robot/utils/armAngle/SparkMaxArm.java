package frc.robot.utils.armAngle;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Ports.ArmPorts;

/**
 * Specific class to set the angle of an arm using a CAN Spark Max Brushless
 * motor with an
 * internal encoder, and to determine when it is at it's max point and zero
 * point using
 * digital input sensors.
 */
public class SparkMaxArm extends GenericArmMotor {
    double Kiz;
    double Kp;
    double Ki;
    double Kd;
    boolean isFollowerPresent;
    boolean isLowerPresent;
    boolean isUpperPresent;
    double positionSetPoint;
    double degreeSetPoint;
    CANSparkMax motor;
    RelativeEncoder encoder;
    SparkMaxPIDController pidController;
    CANSparkMax followerMotor;
    double gearRatio;
    DigitalInput UpperSensor;
    DigitalInput LowerSensor;
    double rampRate;

    /**
     * Creates a new CAN Spark Max arm
     * 
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
     * @param LowerSensorID
     *                      ID for digital input sensor that determines reset point
     *                      of arm
     * @param UpperSensorID
     *                      ID for digital input sensor that determines max limit
     *                      point of arm
     */
    public SparkMaxArm(int CanID, double gearRatio, double Kp, double Ki, double Kd, double Kiz, double rampRate,
            int LowerSensorID, int UpperSensorID) {
        this(CanID, gearRatio, Kp, Ki, Kd, Kiz, rampRate, LowerSensorID);
        this.gearRatio = gearRatio;

        this.UpperSensor = new DigitalInput(UpperSensorID);
        isUpperPresent = true;
    }

    /**
     * Creates a new CAN Spark Max arm
     * 
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
     * @param LowerSensorID
     *                      ID for digital input sensor that determines reset point
     *                      of arm
     */
    public SparkMaxArm(int CanID, double gearRatio, double Kp, double Ki, double Kd, double Kiz, double rampRate,
            int LowerSensorID) {
        this(CanID, gearRatio, Kp, Ki, Kd, Kiz, rampRate);

        this.LowerSensor = new DigitalInput(LowerSensorID);
        isLowerPresent = true;
    }

    /**
     * Creates a new CAN Spark Max arm
     * 
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

    public SparkMaxArm(int CanID, double gearRatio, double Kp, double Ki, double Kd, double Kiz, double rampRate) {
        this.gearRatio = gearRatio;
        this.rampRate = rampRate;

        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Kiz = Kiz;

        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        encoder = motor.getEncoder();

        motor.setClosedLoopRampRate(rampRate);
        motor.setOpenLoopRampRate(rampRate);

        pidController = motor.getPIDController();
        pidController.setP(Kp);
        pidController.setI(Ki);
        pidController.setD(Kd);

        pidController.setIZone(Kiz);

        isLowerPresent = false;
        isUpperPresent = false;

        isFollowerPresent = false;

        SmartDashboard.putNumber("I Gain", Ki);
        SmartDashboard.putNumber("P Gain", Kp);
        SmartDashboard.putNumber("D Gain", Kd);
        SmartDashboard.putNumber("I Zone", Kiz);

        encoder.setPositionConversionFactor(1.0);
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public double getAppliedOutput() {
        return motor.getAppliedOutput();
    }

    public void resetEncoder() {
        encoder.setPosition(0.0); // Reset Position of encoder is 0.0

    }

    public void addFollowerMotor(int CanID, boolean isReversed) {
        isFollowerPresent = true;
        followerMotor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);

        followerMotor.getEncoder().setPosition(0.0);
        followerMotor.restoreFactoryDefaults();
        followerMotor.getEncoder().setPositionConversionFactor(1);
        followerMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        followerMotor.setOpenLoopRampRate(rampRate);
        followerMotor.setClosedLoopRampRate(rampRate);

        motor.follow(ExternalFollower.kFollowerDisabled, ArmPorts.kMainMotor);
        followerMotor.follow(motor, isReversed);

    }

    public boolean isLowerAvailable() {
        return isLowerPresent;
    }

    public boolean isLowerReached() {
        return LowerSensor.get();
    }

    public boolean isUpperAvailable() {
        return isUpperPresent;
    }

    public boolean isUpperReached() {
        return UpperSensor.get();
    }

    public void stop() {
        motor.stopMotor();
    }

    public double getCurrentAngle() {
        double current_value = (encoder.getPosition() * 360) / 4; // Convert native encoder unit of rotations to degrees
        return current_value;
    }

    public double getTargetAngle() {
        return degreeSetPoint;
    }

    public void setTargetAngle(double degrees) {
        degreeSetPoint = degrees;
        positionSetPoint = (degrees * gearRatio) / 360.0;
        pidController.setReference(positionSetPoint, CANSparkMax.ControlType.kPosition);
    }

    public void periodic() {
        if(isFollowerPresent){
            double followAppliedOutput = followerMotor.getAppliedOutput();
            SmartDashboard.putNumber("Follower Applied Output", followAppliedOutput);
            double followEncoder = followerMotor.getEncoder().getPosition();
            SmartDashboard.putNumber("Follower Position", followEncoder);
            double folowCurrent = followerMotor.getOutputCurrent();
            SmartDashboard.putNumber("Follower Current", folowCurrent);
            double followSpeed = followerMotor.get();
            SmartDashboard.putNumber("Follower Speed", followSpeed);
        }
        double mainSpeed = motor.get();
        SmartDashboard.putNumber("Main Speed", mainSpeed);
        double mainAppliedOutput = motor.getAppliedOutput();
        SmartDashboard.putNumber("Main Applied Output", mainAppliedOutput);
        double mainCurrent = motor.getOutputCurrent();
        SmartDashboard.putNumber("Main Current", mainCurrent);
        double mainEncoder = encoder.getPosition();
        SmartDashboard.putNumber("Main Position", mainEncoder);

        double p = SmartDashboard.getNumber("P Gain", Kp);
        double i = SmartDashboard.getNumber("I Gain", Ki);
        double d = SmartDashboard.getNumber("D Gain", Kd);
        double kiz = SmartDashboard.getNumber("I Zone", Kiz);

        if ((p != Kp)) {pidController.setP(p); Kp = p;}
        if ((i != Ki)) {pidController.setI(i); Ki = i;}
        if ((d != Kd)) {pidController.setD(d); Kd = d;}
        if ((kiz != Kiz)) {pidController.setIZone(kiz); Kiz = kiz;}
    }

    public void testPeriodic() {

    }
}
