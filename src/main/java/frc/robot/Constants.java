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
    /** Defines the game pieces for the 2023 season */
    public static enum GamePieceEnum
    {
        /** Represents a case where there is no game object */
        None,
        /** Represents a Cone game object */
        Cone,
        /** Represents a Cube game object */
        Cube
    }
    
    /** Constants that define characteristics for the intake */
    public static class IntakeConstants
    {
        // TODO: Do we really need/use this?
        public static enum IntakeStateEnum
        {
            /** State of intaking a cone */
            IntakeCone,

            /** State of ejecting a cone */
            EjectCone,

            /** State of intaking a cube */
            IntakeCube,

            /** State of ejecting a cube */
            EjectCube
        }

        public static final double kIntakeConeSpeed = 1;
        public static final double kEjectConeSpeed  = -1;
        public static final double kIntakeCubeSpeed = -1;
        public static final double kEjectCubeSpeed  = 0.4;

        // Current limit for the neo 550 motors for the intake
        public static final int kIntakeCurrentLimit = 35;
        public static final double kIntakeMotorP = 0.0475;
        public static final double kIntakeMotorI = 0.00005;
        public static final double kIntakeMotorD = 0;
        public static final double kIntakeMotorIZone = 1.5;
        public static final double MinOutput = -0.3;
        public static final double MaxOutput = 0.9; 

        public static final double kJoystickChange   = 10.0; // Manual setpoint value for degrees moved per second
        public static final double kJoystickDeadband = 0.3;  // Manual intake movement axis deadband

        public static final boolean kJoystickReversed = true;

        // Angle limits for the intake positions
        public static final double kMaxAngle = 0; // Degrees, Starting position of intake
        public static final double kMinAngle = -120;   // Degrees, negative moves downwards TODO - Find minimum angle of intake

        public static final double kGearRatio = 48.0;

        //Positive angle moves upward and Negative angle moves downward
        public static final double kStartAngle      = -42.5;
        public static final double kExtendAngle     = -90.0;
        public static final double kRetractAngle    = -10.0; 
        public static final double kSubstationAngle = -28.0;

    }

    /** Constants that define characteristics for the arm */
    public static class ArmConstants
    {
        /** Angles for the different arm positions */
        public static enum ArmPosition
        {
            /** Set the angle to reach the top cube node */
            HighPosition(77.0),
            /** Set the angle to reach the middle cube node */
            MidPosition(60.0),
            /** Set the angle to reach the bottom cube node */
            FloorPosition(12.5),
            /** Set the angle to reach the bottom cube node */
            ZeroPosition(0.0),
            /** Set the angle to a dipped position */
            DipPosition(10.5),
            /** Set the angle to reach the substation */
            SSS(9.0);

            public final double angle;

            ArmPosition(double angle)
            {
                this.angle = angle;
            }
        }

        // Arm Constants
        // Motor rotations to arm rotations, 4/3 from expiremental calculations
        public static final double kGearRatio = ((75.0 * (36.0/24.0)) * 4.0) * (3.0 / 4.0);
        // Encoder rotations to arm rotations
        public static final double kCANCoderGearRatio = 160.0 / 48.0; //Convert encoder degree units to arm degrees

        // TODO: Tune PID values
        public static final double kArmMotorP = 0.0375;
        public static final double kArmMotorI = 0.00075;
        public static final double kArmMotorD = 0.001;

        public static final double kMinInteg = 0.0;
        public static final double kMaxInteg = 0.15;

        public static final double kArmMotorMaxOutput = 0.8;
        public static final double kArmMotorMinOutput = -0.5;

        public static final double kPositiveAccelLimit = 1.0; // in %/sec
        public static final double kNegativeAccelLimit = -5.0; // in %/sec

        public static final int kArmCurrentLimit = 30;

        // Angle limits for the arm positions
        public static final double kMaxAngle = 130; // Degrees
        public static final double kMinAngle = 0;   // Degrees

        public static final double kJoystickChange   = 30.0; // Manual setpoint value for degrees moved per second
        public static final double kJoystickDeadband = 0.3;  // Manual arm movement axis deadband

        public static final boolean kJoystickReversed = true;  // Determines if the joystick movement is reversed
        public static final double  kArmMotorIZone     = 0.0;  // Test Value Number for I Zone of PID controller

        public static final double kAngleTolerance     = 5.0; // The tolerance for the arm position in both directions
    }

    /** Constants that define the drivetrain as a whole */
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
        public static final double kFrontLeftAngleOffset  = -326.162;
        public static final double kRearLeftAngleOffset   = -247.412;
        public static final double kFrontRightAngleOffset = -1.934;
        public static final double kRearRightAngleOffset  = -108.896;

        /** Distance between centers of right and left wheels on robot */
        public static final double kTrackWidth = 0.5588;
        /** Distance between front and back wheels on robot */
        public static final double kWheelBase  = 0.5588;

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

        /** The max speed the drive wheels should be allowed to go */
        public static final double kMaxSpeedMetersPerSecond = 5;
        public static final double kMaxRotationDegreesPerSecond = 360.0;
    }

    /** Constants that define each swerve module as an individual */
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

    /** Constants that are used when defining filters for controllers */
    public static final class OIConstants
    {
        // Controller constraints
        public static final double kDriveCoeff       = 0.95;
        public static final double kRotationCoeff    = 0.95;
        public static final double kJoystickDeadband = 0.15;
        public static final double kSlowModePercent  = 0.2;

        public static final double kAccelLimit = 2;
    }

    /** Constants that define the settings of the driver camera */
    public static final class CameraConstants
    {
        public static final int kDriverCameraResolutionX = 640;
        public static final int kDriverCameraResolutionY = 360;
        public static final int kDriverCameraFPS         = 15;
    }

    /** Defines constraints and information for autonomous development */
    public static final class AutoConstants
    {
        // Folder name for autos in deploy directors
        public static final String kSplineDirectory = "pathplanner";

        // Autonomous translation constraints
        public static final double          kMaxSpeedMetersPerSecond               = 3;
        public static final double          kMaxAccelerationMetersPerSecondSquared = 2;
        public static final PathConstraints kPathConstraints                       =
                new PathConstraints(kMaxSpeedMetersPerSecond,
                    kMaxAccelerationMetersPerSecondSquared);

        // PID Constants
        public static final PIDConstants kTranslationPIDConstants = new PIDConstants(6, 0, 0);
        public static final PIDConstants kRotationPIDConstants    = new PIDConstants(1.75, 0, 0);
    }

    /** The file that is used for system instantiation at runtime */
    public static final String SUBSYSTEMFILE = "Subsystems.json";
}
