// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.superclasses;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.utils.MotorEncoder;
import frc.robot.utils.WrappedMotorEncoder;
import frc.robot.utils.wrappers.SK_CANCoder;

/**
 * A class that represents an entire swerve module and is used to set the states of the
 * modules needed to move the drivetrain
 */
public class SwerveModule
{
    private final WPI_TalonFX m_driveMotor;
    private final WPI_TalonFX m_turnMotor;

    private final SK_CANCoder         m_CANEncoder;
    private final MotorEncoder        m_driveEncoder;
    private final WrappedMotorEncoder m_turnEncoder;
    private double                    m_turningEncoderOffset;

    /**
     * Constructs a SwerveModule.
     *
     * @param driveMotorChannel
     *            The channel of the drive motor.
     * @param turningMotorChannel
     *            The channel of the turning motor.
     * @param turningEncoderChannel
     *            The channel of the turning encoder.
     * @param driveMotorReversed
     *            Whether the drive motor is reversed.
     * @param turnMotorReversed
     *            Whether the turning motor is reversed.
     */
    public SwerveModule(int driveMotorChannel, int turningMotorChannel, int turningEncoderChannel,
        boolean driveMotorReversed, boolean turnMotorReversed, double turningEncoderOffset)
    {

        m_turningEncoderOffset = turningEncoderOffset;

        m_driveMotor = new WPI_TalonFX(driveMotorChannel);
        m_driveMotor.configFactoryDefault();
        m_driveMotor.setNeutralMode(NeutralMode.Brake);     // Set drive motor to brake mode
        m_driveMotor.setInverted(driveMotorReversed);       // Set whether drive encoder should be reversed or not

        TalonFXConfiguration config = new TalonFXConfiguration();
        config.slot0.kP = ModuleConstants.kPModuleTurningController;
        config.neutralDeadband = ModuleConstants.kPIDAngleDeadband;
        m_turnMotor = new WPI_TalonFX(turningMotorChannel);
        m_turnMotor.configFactoryDefault();
        m_turnMotor.configAllSettings(config);
        m_turnMotor.setInverted(turnMotorReversed);         // Set whether turn encoder should be reversed or not
        m_turnMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        m_CANEncoder = new SK_CANCoder(turningEncoderChannel, m_turningEncoderOffset);
        // Encoder should never be inverted. Inversion should be left to the motor.
        m_driveEncoder = new MotorEncoder(m_driveMotor,
            ModuleConstants.kDriveEncoderDistancePerPulse, false);
        m_turnEncoder = new WrappedMotorEncoder(m_turnMotor,
            ModuleConstants.kTurnEncoderDistancePerPulse, m_CANEncoder.getAbsolutePosition());

        // Setting the update speeds of the motors and CANCoders
        m_CANEncoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 10);
        m_driveMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10);
        m_turnMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10);
    }

    /**
     * Returns the current state of the module.
     *
     * @return The current state of the module.
     */
    public SwerveModuleState getState()
    {
        return new SwerveModuleState(m_driveEncoder.getVelocityMeters(),
            m_turnEncoder.getRotation2d());
    }

    /**
     * Returns the current position of the module.
     *
     * @return The current position of the module.
     */
    public SwerveModulePosition getPosition()
    {
        return new SwerveModulePosition(m_driveEncoder.getPositionMeters(),
            m_turnEncoder.getRotation2d());
    }

    /**
     * Sets the desired state for the module.
     *
     * @param desiredState
     *            Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState desiredState)
    {
        // Optimize the reference state to avoid spinning further than 90 degrees
        SwerveModuleState state =
                SwerveModuleState.optimize(desiredState, m_CANEncoder.getRotation2d());

        setDrive(state);
        setAngle(state);
    }

    public void setDrive(SwerveModuleState desiredState)
    {
        m_driveMotor
            .set(desiredState.speedMetersPerSecond / DriveConstants.kMaxSpeedMetersPerSecond);
    }

    public void setAngle(SwerveModuleState desiredState)
    {
        double desiredPosition = desiredState.angle.getDegrees();
        double setpoint = m_turnEncoder.getPulsePositionFromDesiredAngle(desiredPosition);

        m_turnMotor.set(ControlMode.Position, setpoint);
    }

    /** Zeroes the drive encoders. */
    public void resetEncoders()
    {
        m_driveEncoder.resetEncoder();
    }

    /** Stops all movement of the module */
    public void stop()
    {
        m_driveMotor.set(0.0);
        m_turnMotor.set(0.0);
    }
}
