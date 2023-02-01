// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.auto.PIDConstants;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    
  }

  public static class HighArmConstants {
      public static final double kArmPositionOffsetDegrees = 0.0;

      public static final double kDegreesPerPulse = 0.0;
  }
  public static class LowArmConstants{
      public static final double kArmPositionOffsetDegrees = 0.0;  

      public static final double kDegreesPerPulse = 0.0;

      public static final PneumaticsModuleType kModuleType = PneumaticsModuleType.CTREPCM;

      public static final double kMotorSpeed = 0.0;

      public static final double kMaxHeight = 0.0;
  }

  public static final class DriveConstants {
    public static final int kFrontLeftDriveMotorPort = 10;
    public static final int kRearLeftDriveMotorPort = 11;
    public static final int kFrontRightDriveMotorPort = 12;
    public static final int kRearRightDriveMotorPort = 13;

    public static final int kFrontLeftTurningMotorPort = 20;
    public static final int kRearLeftTurningMotorPort = 21;
    public static final int kFrontRightTurningMotorPort = 22;
    public static final int kRearRightTurningMotorPort = 23;

    public static final int kFrontLeftTurningEncoderPorts = 30;
    public static final int kRearLeftTurningEncoderPorts = 31;
    public static final int kFrontRightTurningEncoderPorts = 32;
    public static final int kRearRightTurningEncoderPorts = 33;

    public static final boolean kFrontLeftTurnMotorReversed = true;
    public static final boolean kRearLeftTurnMotorReversed = true;
    public static final boolean kFrontRightTurnMotorReversed = true;
    public static final boolean kRearRightTurnMotorReversed = true;

    public static final boolean kFrontLeftDriveMotorReversed = true;
    public static final boolean kRearLeftDriveEncoderReversed = true;
    public static final boolean kFrontRightDriveEncoderReversed = true;
    public static final boolean kRearRightDriveMotorReversed = true;

    /** Offset for CANcoders in degrees */
    public static final double kFrontLeftAngleOffset = -147.8;
    /** Offset for CANcoders in degrees */
    public static final double kRearLeftAngleOffset = -198.8;
    /** Offset for CANcoders in degrees */
    public static final double kFrontRightAngleOffset = -2.8;
    /** Offset for CANcoders in degrees */
    public static final double kRearRightAngleOffset = -337.9;

    /** Distance between centers of right and left wheels on robot */
    public static final double kTrackWidth = 0.60325;
    /** Distance between front and back wheels on robot */
    public static final double kWheelBase = 0.60325;

    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    public static final boolean kGyroReversed = false;

    // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
    // These characterization values MUST be determined either experimentally or
    // theoretically
    // for *your* robot's drive.
    // The SysId tool provides a convenient method for obtaining these values for
    // your robot.
    public static final double ksVolts = 1;
    public static final double kvVoltSecondsPerMeter = 0.8;
    public static final double kaVoltSecondsSquaredPerMeter = 0.15;

    public static final double kMaxSpeedMetersPerSecond = 3;
}

public static final class ModuleConstants {
    public static final double kMaxModuleAngularSpeedDegreesPerSecond = 360;
    public static final double kMaxModuleAngularAccelerationDegreesPerSecondSquared = 360;

    public static final int kEncoderCPR = 2048;
    public static final double kWheelDiameterMeters = 0.1016002032;
    public static final double kDriveGearRatio = 6.75;
    public static final double kDriveEncoderDistancePerPulse = (kWheelDiameterMeters * Math.PI)
            / (kDriveGearRatio * (double) kEncoderCPR);

    public static final double kPModuleTurningController = 0.012;
    public static final double kIModuleTurningController = 0.00;
    // public static final double kIModuleTurningController = 0.001;
    public static final double kDModuleTurningController = 0.00;
    // public static final double kDModuleTurningController = 0.00005;
    public static final double kInegratorResetValue = 10;

    public static final double kPModuleDriveController = 0.3;

    public static final double kPIDAngleDeadband = 0.01;
}

public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;

    public static final int kVelocityXPort = 0;
    public static final int kVelocityYPort = 1;
    public static final int kVelocityOmegaPort = 4;

    public static final int kResetGyro = 4;
    public static final int kRobotCentricMode = 5;

    public static final double kDriveGain = 0.95;
    public static final double kRotationGain = 0.95;
    public static final double kJoystickDeadband = 0.05;
}

public static final class AutoConstants {
    public static final String kSplineDirectory = "pathplanner";

    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final PathConstraints kPathConstraints = new PathConstraints(kMaxSpeedMetersPerSecond,
            kMaxAccelerationMetersPerSecondSquared);

    public static final double kMaxAngularSpeedDegreesPerSecond = 180;
    public static final double kMaxAngularSpeedDegreesPerSecondSquared = 180;

    public static final PIDConstants kTranslationPIDConstants = new PIDConstants(0.25, 0, 0);
    public static final PIDConstants kRotationPIDConstants = new PIDConstants(0.012, 0, 0);

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
            kMaxAngularSpeedDegreesPerSecond, kMaxAngularSpeedDegreesPerSecondSquared);
}
}
