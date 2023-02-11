package frc.robot;

public class Ports
{
    public class ArmPorts
    {
        public static final int kMainMotor   = 0;
        public static final int kLowerSwitch = 0;
        public static final int kUpperSwitch = 0;
    }

    public class OperatorPorts
    {
        public static final int kDriverControllerPort   = 0;
        public static final int kOperatorControllerPort = 1;
    }

    public class HighArmPorts
    {
        public static final int kMainMotor     = 0;
        public static final int kFollowerMotor = 0;
    }

    public class LowArmPorts
    {
        public static final int kMainMotor     = 0;
        public static final int kFollowerMotor = 0;

        public static final int kPneumaticForwardChannel = 0;
        public static final int kPneumaticReverseChannel = 0;

        public static final int kExtensionMotor = 0;

    }

    public class DrivePorts
    {
        // CAN IDs for the drive motors on the swerve module
        public static final int kFrontLeftDriveMotorPort  = 10;
        public static final int kRearLeftDriveMotorPort   = 11;
        public static final int kFrontRightDriveMotorPort = 12;
        public static final int kRearRightDriveMotorPort  = 13;

        // CAN IDs for the turning motors on the swerve module
        public static final int kFrontLeftTurningMotorPort  = 20;
        public static final int kRearLeftTurningMotorPort   = 21;
        public static final int kFrontRightTurningMotorPort = 22;
        public static final int kRearRightTurningMotorPort  = 23;

        // CAN IDs for the CANCoders
        public static final int kFrontLeftTurningEncoderPort  = 30;
        public static final int kRearLeftTurningEncoderPort   = 31;
        public static final int kFrontRightTurningEncoderPort = 32;
        public static final int kRearRightTurningEncoderPort  = 33;
    }
}
