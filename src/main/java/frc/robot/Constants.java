// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.auto.PIDConstants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical
 * or boolean constants. This class should not be used for any other purpose. All
 * constants should be declared globally (i.e. public static). Do not put anything
 * functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes) wherever
 * the constants are needed, to reduce verbosity.
 */
public final class Constants
{
    public static class OperatorConstants
    {
    }

    public static class IntakeConstants
    {
        // TODO: Test to make sure that the intake speed is correct
        public static final double CLOCKWISE_FRONTROLLER_SPEED          = 0.625;
        public static final double COUNTERCLOCKWISE_FRONTROLLER_SPEED   = -0.625;
        public static final double CLOCKWISE_REARTOPROLLER_SPEED        = 0.625;
        public static final double COUNTERCLOCKWISE_REARTOPROLLER_SPEED = 0.625;
    }

    public static enum GamePieceEnum
    {
        // Represents a case where there is no game object 
        None,
        // Represents a Cone game object
        Cone,
        // Represents a Cube game object
        Cube,
    }

    public static class ArmConstants
    {
        // High Arm Constants
        public static final double kGearRatio = 0;
        public static final double kArmMotorP     = 0;
        public static final double kArmMotorI     = 0;
        public static final double kArmMotorD     = 0;

        public static final double kHighPosition       = 0;
        public static final double kMidPosition        = 0;
        public static final double kLowPosition        = 0;
        public static final double kSubstationPosition = 0;

        public static final double kJoystickRatio = 0;

        // Low Arm Constants
        // public static final double kRotationRatio = 0;
        // public static final double kArmMotorP     = 0;
        // public static final double kArmMotorI     = 0;
        // public static final double kArmMotorD     = 0;

        // public static final double kHighPosition = 0;
        // public static final double kMidPosition  = 0;
        // public static final double kLowPosition  = 0;
        //public static final double kSubstationPosition = 0;

        //public static final double kJoystickRatio = 0;
    }

    public static class ExtenderConstants
    {
        public static final double kRotationRatio = 0;
        public static final double kArmMotorP     = 0;
        public static final double kArmMotorI     = 0;
        public static final double kArmMotorD     = 0;

        public static final double kHighPosition       = 0;
        public static final double kMidPosition        = 0;
        public static final double kLowPosition        = 0;
        public static final double kSubstationPosition = 0;
    }

    public static final class DriveConstants
    {
        // Whether or not the turning motor needs to be reversed. Positive input should
        // cause CCW+ angle change
        public static final boolean kFrontLeftTurnMotorReversed  = true;
        public static final boolean kRearLeftTurnMotorReversed   = true;
        public static final boolean kFrontRightTurnMotorReversed = true;
        public static final boolean kRearRightTurnMotorReversed  = true;

        // Whether or not the drive motors need to be reversed. Positive input should
        // cause the robot to go into the positive x or y direction as outlined by the
        // FIELD coordinate system
        public static final boolean kFrontLeftDriveMotorReversed    = false;
        public static final boolean kRearLeftDriveEncoderReversed   = false;
        public static final boolean kFrontRightDriveEncoderReversed = true;
        public static final boolean kRearRightDriveMotorReversed    = true;

        // Offset for the CANCoders in Degrees
        public static final double kFrontLeftAngleOffset  = -327.8;
        public static final double kRearLeftAngleOffset   = -18.8;
        public static final double kFrontRightAngleOffset = -2.8;
        public static final double kRearRightAngleOffset  = -337.9;

        /** Distance between centers of right and left wheels on robot */
        public static final double kTrackWidth = 0.60325;
        /** Distance between front and back wheels on robot */
        public static final double kWheelBase  = 0.60325;

        /** Contains position of the swerve modules relative to the robot center */
        public static final SwerveDriveKinematics kDriveKinematics =
                new SwerveDriveKinematics(new Translation2d(kWheelBase / 2, kTrackWidth / 2),
                    new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
                    new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
                    new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

        /** Whether or not the gyro needs to be reversed to be CCW+ */
        public static final boolean kGyroReversed = false;

        /** The default state of the swerve drive to allow for maximum defense */
        public static final SwerveModuleState[] kDefenseState =
                {new SwerveModuleState(0, Rotation2d.fromDegrees(315)),
                    new SwerveModuleState(0, Rotation2d.fromDegrees(45)),
                    new SwerveModuleState(0, Rotation2d.fromDegrees(45)),
                    new SwerveModuleState(0, Rotation2d.fromDegrees(315))};

        // Characterization constants as determined by the SysID tool.
        // TODO: Characterize the robot to find these values.
        public static final double ksVolts                      = 1;
        public static final double kvVoltSecondsPerMeter        = 0.8;
        public static final double kaVoltSecondsSquaredPerMeter = 0.15;

        public static final double kMaxSpeedMetersPerSecond = 5;
    }

    public static final class ModuleConstants
    {
        // Turning Constraints
        public static final double kMaxModuleAngularSpeedDegreesPerSecond               = 360;
        public static final double kMaxModuleAngularAccelerationDegreesPerSecondSquared = 360;

        // PID Constants
        public static final double kPModuleTurningController = 0.25;
        public static final double kPModuleDriveController   = 0.01;
        public static final double kFModuleDriveController   = 0.0465;
        public static final double kPIDAngleDeadband         = 0.01;

        // Module Characteristics
        public static final int    kEncoderCPR                   = 2048;
        public static final int    kDegreesPerRevolution         = 360;
        public static final double kWheelDiameterMeters          = Units.inchesToMeters(4.0);
        public static final double kDriveGearRatio               = 6.75;
        public static final double kTurnGearRatio                = 150.0 / 7.0;
        public static final double kDriveEncoderDistancePerPulse =
                (kWheelDiameterMeters * Math.PI) / (kDriveGearRatio * (double) kEncoderCPR);
        public static final double kTurnEncoderDistancePerPulse  =
                kDegreesPerRevolution / (kTurnGearRatio * (double) kEncoderCPR);

    }

    public static final class OIConstants
    {
        // Axes for driving
        public static final int kVelocityXPort     = 0;
        public static final int kVelocityYPort     = 1;
        public static final int kVelocityOmegaPort = 4;

        // Buttons for driving
        public static final int kResetGyro        = 4;
        public static final int kRobotCentricMode = 5;

        // Controller constraints
        public static final double kDriveGain        = 0.95;
        public static final double kRotationGain     = 0.95;
        public static final double kJoystickDeadband = 0.01;
    }

    public static final class CameraConstants
    {
        public static final int kDriverCameraResolutionX = 640;
        public static final int kDriverCameraResolutionY = 360;
        public static final int kDriverCameraFPS         = 15;
    }

    public static final class AutoConstants
    {
        // Folder name for autos in deploy directors
        public static final String kSplineDirectory = "pathplanner";

        // Autonomous translation constraints
        public static final double          kMaxSpeedMetersPerSecond               = 1.5;
        public static final double          kMaxAccelerationMetersPerSecondSquared = 0.75;
        public static final PathConstraints kPathConstraints                       =
                new PathConstraints(kMaxSpeedMetersPerSecond,
                    kMaxAccelerationMetersPerSecondSquared);

        // Autonomous turning constraints
        public static final double kMaxAngularSpeedDegreesPerSecond        = 180;
        public static final double kMaxAngularSpeedDegreesPerSecondSquared = 180;

        // PID Constants
        public static final PIDConstants kTranslationPIDConstants = new PIDConstants(0.25, 0, 0);
        public static final PIDConstants kRotationPIDConstants    = new PIDConstants(0.012, 0, 0);

        // Constraint for the motion profiled robot angle controller
        public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
                new TrapezoidProfile.Constraints(kMaxAngularSpeedDegreesPerSecond,
                    kMaxAngularSpeedDegreesPerSecondSquared);
    }

    public static final String SUBSYSTEMFILE = "Subsystems.json";
}
