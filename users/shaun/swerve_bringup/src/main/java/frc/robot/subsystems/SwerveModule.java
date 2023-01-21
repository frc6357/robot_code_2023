// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoderStatusFrame;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ModuleConstants;
import frc.robot.utils.MotorEncoder;
import frc.robot.utils.SK_CANCoder;

public class SwerveModule {
    private final WPI_TalonFX m_driveMotor;
    private final WPI_TalonFX m_turnMotor;

    private final MotorEncoder m_driveEncoder;
    private final SK_CANCoder m_turnEncoder;
    private double m_turningEncoderOffset;

    private final PIDController m_drivePIDController = new PIDController(ModuleConstants.kPModuleDriveController, 0, 0);

    // Using a TrapezoidProfile PIDController to allow for smooth turning
    private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(
            ModuleConstants.kPModuleTurningController,
            ModuleConstants.kIModuleTurningController,
            ModuleConstants.kDModuleTurningController,
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
     * @param turnMotorReversed Whether the turning encoder is reversed.
     */
    public SwerveModule(
            int driveMotorChannel,
            int turningMotorChannel,
            int turningEncoderChannel,
            boolean driveEncoderReversed,
            boolean turnMotorReversed,
            double turningEncoderOffset) {
                
        m_turningEncoderOffset = turningEncoderOffset;
        m_driveMotor = new WPI_TalonFX(driveMotorChannel);
        m_turnMotor = new WPI_TalonFX(turningMotorChannel);
        m_turnMotor.setInverted(turnMotorReversed);

        m_driveEncoder = new MotorEncoder(m_driveMotor, ModuleConstants.kDriveEncoderDistancePerPulse,
                driveEncoderReversed);
        m_turnEncoder = new SK_CANCoder(turningEncoderChannel, m_turningEncoderOffset);

        m_turnEncoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 10);
        m_driveMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 100);
        m_turnMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10);

        m_turningPIDController.setIntegratorRange(-100, 100);

        System.out.println("CANCoder SensorData");
        System.out.println(m_turnEncoder.getStatusFramePeriod(CANCoderStatusFrame.SensorData));

        System.out.println("Drive Motor General");
        System.out.println(m_driveMotor.getStatusFramePeriod(StatusFrameEnhanced.Status_1_General));
        
        System.out.println("Turn Motor General");
        System.out.println(m_turnMotor.getStatusFramePeriod(StatusFrameEnhanced.Status_1_General));

        // Set the distance per pulse for the drive encoder. We can simply use the
        // distance traveled for one rotation of the wheel divided by the encoder
        // resolution.

        // Set whether drive encoder should be reversed or not
        m_driveMotor.setInverted(driveEncoderReversed);

        // Limit the PID Controller's input range between 0 and 360 and set the input
        // to be continuous.
        m_turningPIDController.enableContinuousInput(0, 360);
    }

    /**
     * Returns the current state of the module.
     *
     * @return The current state of the module.
     */
    public SwerveModuleState getState() {
        return new SwerveModuleState(
            m_driveEncoder.getVelocityMeters(),
            m_turnEncoder.getRotation2d());
    }

    /**
     * Returns the current position of the module.
     *
     * @return The current position of the module.
     */
    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            m_driveEncoder.getPositionMeters(),
            m_turnEncoder.getRotation2d());
    }

    /**
     * Sets the desired state for the module.
     *
     * @param desiredState Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState desiredState) {
        // Optimize the reference state to avoid spinning further than 90 degrees
        // SwerveModuleState state = SwerveModuleState.optimize(desiredState,
        //     m_turningEncoder.getRotation2d());
                
        //setDrive(state);
        setAngle(desiredState);
    }

    public void setDrive(SwerveModuleState desiredState)
    {
        // Calculate the drive output from the drive PID controller.
        final double driveOutput = m_drivePIDController.calculate(m_driveEncoder.getVelocityMeters(),
        desiredState.speedMetersPerSecond);

            m_driveMotor.set(driveOutput);
    }

    public void setAngle(SwerveModuleState desiredState)
    {   
        double absolutePosition = m_turnEncoder.getAbsolutePosition();
        double desiredPosition = desiredState.angle.getDegrees();
        double setpoint = MathUtil.inputModulus(desiredPosition, 0, 360);
        setpoint = setpoint == 360.0 ? 0.0 : setpoint;

      
        SmartDashboard.putNumber("Angle before Wrap", desiredPosition);
        SmartDashboard.putNumber("Setpoint", setpoint);

        // Calculate the turning motor output from the turning PID controller.
        double turnOutput = m_turningPIDController.calculate(
            absolutePosition, setpoint);

        turnOutput = (Math.abs(turnOutput) < ModuleConstants.kPIDAngleDeadband) ? 0.0 : turnOutput;

        m_turnMotor.set(turnOutput);
        SmartDashboard.putNumber("PID for Turn", turnOutput);
    }

    /** Zeroes the drive encoders. */
    public void resetEncoders() {
        m_driveEncoder.resetEncoder();
    }

    public void stop()
    {
        m_driveMotor.set(0.0);
        m_turnMotor.set(0.0);
    }
}
