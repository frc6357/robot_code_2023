// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.Constants.ModuleConstants;
import frc.robot.utils.MotorEncoder;
import frc.robot.utils.SK_CANCoder;

public class SwerveModule {
    private final WPI_TalonFX m_driveMotor;
    private final WPI_TalonFX m_turningMotor;

    private final MotorEncoder m_driveEncoder;
    private final SK_CANCoder m_turningEncoder;
    private double m_turningEncoderOffset;

    private final PIDController m_drivePIDController = new PIDController(ModuleConstants.kPModuleDriveController, 0, 0);

    // Using a TrapezoidProfile PIDController to allow for smooth turning
    private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(
            ModuleConstants.kPModuleTurningController,
            0,
            0,
            new TrapezoidProfile.Constraints(
                    ModuleConstants.kMaxModuleAngularSpeedDegreesPerSecond,
                    ModuleConstants.kMaxModuleAngularAccelerationDegreesPerSecondSquared));

    /**
     * Constructs a SwerveModule.
     *
     * @param driveMotorChannel      The channel of the drive motor.
     * @param turningMotorChannel    The channel of the turning motor.
     * @param turningEncoderChannels The channels of the turning encoder.
     * @param driveEncoderReversed   Whether the drive encoder is reversed.
     * @param turningEncoderReversed Whether the turning encoder is reversed.
     */
    public SwerveModule(
            int driveMotorChannel,
            int turningMotorChannel,
            int turningEncoderChannel,
            boolean driveEncoderReversed,
            boolean turningEncoderReversed,
            double turningEncoderOffset) {
                
        m_turningEncoderOffset = turningEncoderOffset;
        m_driveMotor = new WPI_TalonFX(driveMotorChannel);
        m_turningMotor = new WPI_TalonFX(turningMotorChannel);

        m_driveEncoder = new MotorEncoder(m_driveMotor, ModuleConstants.kDriveEncoderDistancePerPulse,
                driveEncoderReversed);
        m_turningEncoder = new SK_CANCoder(turningEncoderChannel, m_turningEncoderOffset);

        // Set the distance per pulse for the drive encoder. We can simply use the
        // distance traveled for one rotation of the wheel divided by the encoder
        // resolution.

        // Set whether drive encoder should be reversed or not
        m_driveMotor.setInverted(driveEncoderReversed);

        // Limit the PID Controller's input range between -pi and pi and set the input
        // to be continuous.
        m_turningPIDController.enableContinuousInput(-180, 180);
    }

    /**
     * Returns the current state of the module.
     *
     * @return The current state of the module.
     */
    public SwerveModuleState getState() {
        return new SwerveModuleState(
                m_driveEncoder.getVelocityMeters(),
                m_turningEncoder.getRotation2d());
    }

    /**
     * Returns the current position of the module.
     *
     * @return The current position of the module.
     */
    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
                m_driveEncoder.getPositionMeters(),
                m_turningEncoder.getRotation2d());
    }

    /**
     * Sets the desired state for the module.
     *
     * @param desiredState Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState desiredState) {
        // Optimize the reference state to avoid spinning further than 90 degrees
        SwerveModuleState state = SwerveModuleState.optimize(desiredState,
                m_turningEncoder.getRotation2d());

        // Calculate the drive output from the drive PID controller.
        final double driveOutput = m_drivePIDController.calculate(m_driveEncoder.getVelocityMeters(),
                state.speedMetersPerSecond);

        // Calculate the turning motor output from the turning PID controller.
        final double turnOutput = m_turningPIDController.calculate(
                m_turningEncoder.getAbsolutePosition(), state.angle.getDegrees());

        // Calculate the turning motor output from the turning PID controller.
        m_driveMotor.set(driveOutput);
        m_turningMotor.set(turnOutput);
    }

    /** Zeroes all the SwerveModule encoders. */
    public void resetEncoders() {
        m_driveEncoder.resetEncoder();
        m_turningEncoder.setPosition(0);
    }
}
