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
    double smartDegrees;
    double kMaxOutput;
    double kMinOutput;
    double Kiz;
    double KFF;
    double Kp;
    double Ki;
    double Kd;
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
     * @param LowerSensorID
     *                      ID for digital input sensor that determines reset point
     *                      of arm
     * @param UpperSensorID
     *                      ID for digital input sensor that determines max limit
     *                      point of arm
     */
    public SparkMaxArm(int CanID, double gearRatio, double Kp, double Ki, double Kd, double rampRate,
            int LowerSensorID, int UpperSensorID) {
        this(CanID, gearRatio, Kp, Ki, Kd, rampRate, LowerSensorID);
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
     * @param LowerSensorID
     *                      ID for digital input sensor that determines reset point
     *                      of arm
     */
    public SparkMaxArm(int CanID, double gearRatio, double Kp, double Ki, double Kd, double rampRate,
            int LowerSensorID) {
        this(CanID, gearRatio, Kp, Ki, Kd, rampRate);

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
     * @param p
     *                  Value for proportional gain constant in PID controller
     * @param i
     *                  Value for integral gain constant in PID controller
     * @param d
     *                  Value for derivative gain constant in PID controller
     */

    public SparkMaxArm(int CanID, double gearRatio, double p, double i, double d, double rampRate) {
        this.gearRatio = gearRatio;
        this.rampRate = rampRate;
        
        Kp = 0.1; 
        Ki = 1e-4;
        Kd = 1; 
        Kiz = 0; 
        KFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;

        motor = new CANSparkMax(CanID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        encoder = motor.getEncoder();

        motor.setClosedLoopRampRate(rampRate);
        motor.setOpenLoopRampRate(rampRate);

        pidController = motor.getPIDController();
        pidController.setP(p);
        pidController.setI(i);
        pidController.setD(d);

        pidController.setIZone(Kiz);
        pidController.setFF(KFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);

        isLowerPresent = false;
        isUpperPresent = false;

        
        SmartDashboard.putNumber("I Gain", Ki);
        SmartDashboard.putNumber("kP Gain", Kp);
        SmartDashboard.putNumber("D Gain", Kd);
        // SmartDashboard.putNumber("I Zone", Kiz);
        SmartDashboard.putNumber("Feed Forward", KFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);
        SmartDashboard.putNumber("kSet Point", smartDegrees);

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
        double followAppliedOutput = followerMotor.getAppliedOutput();
        SmartDashboard.putNumber("Follower Applied Output", followAppliedOutput);
        double folowCurrent = followerMotor.getOutputCurrent();
        SmartDashboard.putNumber("Follower Current", folowCurrent);
        double followSpeed = followerMotor.get();
        SmartDashboard.putNumber("Follower Speed", followSpeed);
        double mainSpeed = motor.get();
        SmartDashboard.putNumber("Main Speed", mainSpeed);
        double mainAppliedOutput = motor.getAppliedOutput();
        SmartDashboard.putNumber("Main Applied Output", mainAppliedOutput);
        double mainCurrent = motor.getOutputCurrent();
        SmartDashboard.putNumber("Main Current", mainCurrent);

        double p = SmartDashboard.getNumber("kP Gain", Kp);
        double i = SmartDashboard.getNumber("I Gain", Ki);
        double d = SmartDashboard.getNumber("D Gain", Kd);
        double ff = SmartDashboard.getNumber("Feed Forward", KFF);
        double max = SmartDashboard.getNumber("Max Output", kMaxOutput);
        double min = SmartDashboard.getNumber("Min Output", kMinOutput);

        if((p != Kp)) { pidController.setP(p); Kp = p; }
        if((i != Ki)) { pidController.setI(i); Ki = i; }
        if((d != Kd)) { pidController.setD(d); Kd = d; }
        if((ff != KFF)) { pidController.setFF(ff); KFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
        pidController.setOutputRange(min, max); 
        kMinOutput = min; kMaxOutput = max; 
        }
    }

    public void testPeriodic(){
            
    }
}
