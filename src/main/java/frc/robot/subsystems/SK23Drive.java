// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Ports.DrivePorts;
import frc.robot.subsystems.superclasses.SwerveModule;
import frc.robot.utils.wrappers.SK_ADIS16470_IMU;

/**
 * A class that represents the swerve drive on the robot. Used to drive the robot in both
 * autonomous and telop mode
 */
public class SK23Drive extends SubsystemBase
{
    // Robot swerve modules
    private final SwerveModule m_frontLeft = new SwerveModule(
            DrivePorts.kFrontLeftDriveMotorPort,
            DrivePorts.kFrontLeftTurningMotorPort,
            DrivePorts.kFrontLeftTurningEncoderPort,
            DriveConstants.kFrontLeftDriveMotorReversed,
            DriveConstants.kFrontLeftTurnMotorReversed,
            DriveConstants.kFrontLeftAngleOffset);

    private final SwerveModule m_rearLeft = new SwerveModule(
            DrivePorts.kRearLeftDriveMotorPort,
            DrivePorts.kRearLeftTurningMotorPort,
            DrivePorts.kRearLeftTurningEncoderPort,
            DriveConstants.kRearLeftDriveEncoderReversed,
            DriveConstants.kRearLeftTurnMotorReversed,
            DriveConstants.kRearLeftAngleOffset);

    private final SwerveModule m_frontRight = new SwerveModule(
            DrivePorts.kFrontRightDriveMotorPort,
            DrivePorts.kFrontRightTurningMotorPort,
            DrivePorts.kFrontRightTurningEncoderPort,
            DriveConstants.kFrontRightDriveEncoderReversed,
            DriveConstants.kFrontRightTurnMotorReversed,
            DriveConstants.kFrontRightAngleOffset);

    private final SwerveModule m_rearRight = new SwerveModule(
            DrivePorts.kRearRightDriveMotorPort,
            DrivePorts.kRearRightTurningMotorPort,
            DrivePorts.kRearRightTurningEncoderPort,
            DriveConstants.kRearRightDriveMotorReversed,
            DriveConstants.kRearRightTurnMotorReversed,
            DriveConstants.kRearRightAngleOffset);

    // The gyro sensor
    private final SK_ADIS16470_IMU m_gyro = new SK_ADIS16470_IMU();

    // Odometry class for tracking robot pose
    SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(DriveConstants.kDriveKinematics,
        m_gyro.getRotation2d(), new SwerveModulePosition[]{m_frontLeft.getPosition(),
            m_frontRight.getPosition(), m_rearLeft.getPosition(), m_rearRight.getPosition()});

    /** Creates a new DriveSubsystem. */
    public SK23Drive() {}

    @Override
    public void periodic()
    {
        // SmartDashboard.putNumber("CANCoder Angle",
        // m_frontLeft.getPosition().angle.getDegrees());
        // Update the odometry in the periodic block
        m_odometry.update(m_gyro.getRotation2d(),
            new SwerveModulePosition[]{m_frontLeft.getPosition(), m_frontRight.getPosition(),
                m_rearLeft.getPosition(), m_rearRight.getPosition()});

        double p = SmartDashboard.getNumber("P Value", 0.0);
        m_frontLeft.setP(p);
        m_frontRight.setP(p);
        m_rearLeft.setP(p);
        m_rearRight.setP(p);
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
            m_gyro.getRotation2d(), new SwerveModulePosition[]{m_frontLeft.getPosition(),
                m_frontRight.getPosition(), m_rearLeft.getPosition(), m_rearRight.getPosition()},
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
     *            Angular rate of the robot.
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
            swerveModuleStates = DriveConstants.kDefenseState;
        }
        else if (fieldRelative)
        {
            swerveModuleStates =
                DriveConstants.kDriveKinematics.toSwerveModuleStates(
                    ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, m_gyro.getRotation2d()));
        }
        else
        {
            swerveModuleStates =
                DriveConstants.kDriveKinematics.toSwerveModuleStates(
                        new ChassisSpeeds(xSpeed, ySpeed, rot));
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
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates,
            DriveConstants.kMaxSpeedMetersPerSecond);

        m_frontLeft.setDesiredState(desiredStates[0]);
        m_frontRight.setDesiredState(desiredStates[1]);
        m_rearLeft.setDesiredState(desiredStates[2]);
        m_rearRight.setDesiredState(desiredStates[3]);
    }

    public SwerveModulePosition[] getModulePositions()
    {
        return new SwerveModulePosition[]{m_frontLeft.getPosition(), m_frontRight.getPosition(),
            m_rearLeft.getPosition(), m_rearRight.getPosition()};
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
        return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
    }
}
