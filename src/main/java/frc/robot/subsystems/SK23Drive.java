// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.superclasses.SwerveModule;

import static frc.robot.Constants.DriveConstants.*;
import static frc.robot.Ports.DrivePorts.*;

/**
 * A class that represents the swerve drive on the robot. Used to drive the robot in both
 * autonomous and telop mode
 */
public class SK23Drive extends SubsystemBase
{
    // Robot swerve modules
    private final SwerveModule m_frontLeft = new SwerveModule(
        kFrontLeftDriveMotorPort,
        kFrontLeftTurningMotorPort,
        kFrontLeftTurningEncoderPort,
        kFrontLeftDriveMotorReversed,
        kFrontLeftTurnMotorReversed,
        kFrontLeftAngleOffset);

    private final SwerveModule m_rearLeft = new SwerveModule(
        kRearLeftDriveMotorPort,
        kRearLeftTurningMotorPort,
        kRearLeftTurningEncoderPort,
        kRearLeftDriveEncoderReversed,
        kRearLeftTurnMotorReversed,
        kRearLeftAngleOffset);

    private final SwerveModule m_frontRight = new SwerveModule(
        kFrontRightDriveMotorPort,
        kFrontRightTurningMotorPort,
        kFrontRightTurningEncoderPort,
        kFrontRightDriveEncoderReversed,
        kFrontRightTurnMotorReversed,
        kFrontRightAngleOffset);

    private final SwerveModule m_rearRight = new SwerveModule(
        kRearRightDriveMotorPort,
        kRearRightTurningMotorPort,
        kRearRightTurningEncoderPort,
        kRearRightDriveMotorReversed,
        kRearRightTurnMotorReversed,
        kRearRightAngleOffset);

    // The gyro sensor
    private final WPI_Pigeon2 m_gyro = new WPI_Pigeon2(25, "DriveCAN");

    // Odometry class for tracking robot pose
    SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(
        kDriveKinematics,
        m_gyro.getRotation2d(),
        new SwerveModulePosition[]{
            m_frontLeft.getPosition(),
            m_frontRight.getPosition(),
            m_rearLeft.getPosition(),
            m_rearRight.getPosition()});

    /** Creates a new DriveSubsystem. */
    public SK23Drive()
    {
        m_gyro.reset();
    }

    @Override
    public void periodic()
    {
        // Update the odometry in the periodic block
        m_odometry.update(
            m_gyro.getRotation2d(),
            new SwerveModulePosition[]{
                m_frontLeft.getPosition(),
                m_frontRight.getPosition(),
                m_rearLeft.getPosition(),
                m_rearRight.getPosition()});
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose()
    {
        return m_odometry.getPoseMeters();
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose
     *            The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose)
    {
        m_odometry.resetPosition(
            m_gyro.getRotation2d(),
            new SwerveModulePosition[]{
                m_frontLeft.getPosition(),
                m_frontRight.getPosition(),
                m_rearLeft.getPosition(),
                m_rearRight.getPosition()},
            pose);
    }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed
     *            Speed of the robot in the x direction (forward).
     * @param ySpeed
     *            Speed of the robot in the y direction (sideways).
     * @param rot
     *            Angular rate of the robot in radians per second.
     * @param fieldRelative
     *            Whether the provided x and y speeds are relative to the field.
     */
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative)
    {

        SwerveModuleState[] swerveModuleStates;

        // Set the robot to defense mode if no input is received.
        // When driving, if in field relative mode, use the gyro to rotate the speeds
        // otherwise use robot centric drive
        if ((xSpeed == 0.0) && (ySpeed == 0.0) && (rot == 0.0))
        {
            swerveModuleStates = kDefenseState;
        }
        else if (fieldRelative)
        {
            swerveModuleStates = kDriveKinematics.toSwerveModuleStates(ChassisSpeeds
                .fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, getPose().getRotation()));
        }
        else
        {
            swerveModuleStates =
                    kDriveKinematics.toSwerveModuleStates(new ChassisSpeeds(xSpeed, ySpeed, rot));
        }

        setModuleStates(swerveModuleStates);
    }

    /**
     * Sets the swerve ModuleStates.
     *
     * @param desiredStates
     *            The desired SwerveModule states.
     */
    public void setModuleStates(SwerveModuleState[] desiredStates)
    {
        // Ensure desired motor speeds do not exceed max motor speed
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, kMaxSpeedMetersPerSecond);

        m_frontLeft.setDesiredState(desiredStates[0]);
        m_frontRight.setDesiredState(desiredStates[1]);
        m_rearLeft.setDesiredState(desiredStates[2]);
        m_rearRight.setDesiredState(desiredStates[3]);
    }

    public SwerveModulePosition[] getModulePositions()
    {
        return new SwerveModulePosition[]{
            m_frontLeft.getPosition(),
            m_frontRight.getPosition(),
            m_rearLeft.getPosition(),
            m_rearRight.getPosition()};
    }

    public SwerveModuleState[] getmoduleStates()
    {
        return new SwerveModuleState[]{
            m_frontLeft.getState(),
            m_frontRight.getState(),
            m_rearLeft.getState(),
            m_rearRight.getState()};
    }

    public ChassisSpeeds getChassisState()
    {
        return kDriveKinematics.toChassisSpeeds(getmoduleStates());
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders()
    {
        m_frontLeft.resetEncoders();
        m_rearLeft.resetEncoders();
        m_frontRight.resetEncoders();
        m_rearRight.resetEncoders();
    }

    /** Zeroes the heading of the robot. */
    public void zeroHeading()
    {
        m_gyro.reset();

        Pose2d newPose = new Pose2d(getPose().getTranslation(), new Rotation2d());
        resetOdometry(newPose);
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from -180 to 180
     */
    public double getHeading()
    {
        return m_gyro.getRotation2d().getDegrees();
    }

    /**
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    public double getTurnRate()
    {
        return m_gyro.getRate() * (kGyroReversed ? -1.0 : 1.0);
    }

    /**
     * Get the pitch of the robot
     * 
     * @return The pitch of the robot in degrees (CCW+)
     */
    public double getPitch()
    {
        return m_gyro.getPitch();
    }

    /**
     * Get the roll of the robot
     * 
     * @return The roll of the robot in degrees (CCW+)
     */
    public double getRoll()
    {
        return m_gyro.getRoll();
    }
}
