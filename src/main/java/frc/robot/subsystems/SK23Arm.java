package frc.robot.subsystems;

import static frc.robot.Constants.ArmConstants.*;
import static frc.robot.Ports.ArmPorts.*;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.superclasses.Arm;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm. Using only one CANSparkMax motor and a
 * CANCoder.
 */
public class SK23Arm extends Arm
{
    PIDController PID;
    CANSparkMax   motor;
    int           joystickCount;
    CANCoder      CANCoder;
    double        targetAngle;
    double        currentAngle;

    public SK23Arm()
    {
        PID = new PIDController(kArmMotorP, kArmMotorI, kArmMotorD);
        PID.setSetpoint(0.0);

        motor = new CANSparkMax(kMainMotor.ID, MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        motor.setIdleMode(IdleMode.kBrake);

        targetAngle = 0.0;
        currentAngle = 0.0;

        CANCoder = new CANCoder(kEncoder.ID, kEncoder.bus);
        CANCoderConfiguration config = new CANCoderConfiguration();

        config.initializationStrategy = SensorInitializationStrategy.BootToZero; // Relative positioning
        config.unitString = "deg";
        config.sensorDirection = false; // CCW+
        config.sensorCoefficient = 360.0 / 4096 / kCANCoderGearRatio;

        CANCoder.configAllSettings(config);

        CANCoder.setPosition(0.0);
    }

    /**
     * {@inheritDoc}
     */
    public void setTargetAngle(ArmPosition angle)
    {
        setTargetAngle(angle.angle);
    }

    /**
     * {@inheritDoc}
     */

    public void setTargetAngle(double angle)
    {
        targetAngle = angle;
        PID.setSetpoint(angle);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAtTargetAngle()
    {
        return Math.abs(getCurrentAngle() - getTargetAngle()) < kAngleTolerance;
    }

    /**
     * {@inheritDoc}
     */
    public double getCurrentAngle()
    {
        return CANCoder.getPosition();
    }

    /**
     * {@inheritDoc}
     */
    public double getTargetAngle()
    {
        return targetAngle;
    }

    /**
     * {@inheritDoc}
     */
    public void resetAngle()
    {
        CANCoder.setPosition(0.0);
    }

    /**
     * Code to run at the initialization of test mode being enabled
     */
    public void testInit()
    {

    }

    /**
     * Code to run periodically when test mode is enabled
     */
    public void testPeriodic()
    {
        periodic();
    }

    @Override
    public void periodic()
    {

        double current_angle = getCurrentAngle();
        double target_angle = getTargetAngle();

        motor.set(PID.calculate(current_angle)); //Sets PID value with current angle as input every period call

        SmartDashboard.putNumber("Current Angle", current_angle);
        SmartDashboard.putNumber("Target Angle", target_angle);
        SmartDashboard.putBoolean("Arm at Setpoint", isAtTargetAngle());
    }
}
