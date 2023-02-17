package frc.robot;

import frc.robot.utils.CANPort;

public class Ports
{
    // TODO: Whenever you add a CAN ID to the file, actually assign a number. A
    // definition of 0 is definitely wrong and the choice is essentially
    // arbitrary so why create a job that someone needs to remember to do later
    // when you could just stick in any non-clashing value when the port is 
    // initially added?
    public static class ArmPorts
    {
        private static final String busName      = "";
        public static final CANPort kMainMotor   = new CANPort(1, busName);
        public static final int     kLowerSwitch = 0;
        public static final int     kUpperSwitch = 0;
    }

    public static class ExtenderPorts
    {
        private static final String busName      = "";
        public static final CANPort kMainMotor   = new CANPort(1, busName);
        public static final int     kLowerSwitch = 0;
        public static final int     kUpperSwitch = 0;
    }

    public static class OperatorPorts
    {
        // TODO: What do these "ports" represent? I presume they are Joystick
        // IDs?
        public static final int kDriverControllerPort   = 0;
        public static final int kOperatorControllerPort = 1;
    }

    public static class DrivePorts
    {
        private static final String busName = "";

        // CAN IDs for the drive motors on the swerve module
        public static final CANPort kFrontLeftDriveMotorPort  = new CANPort(10, busName);
        public static final CANPort kRearLeftDriveMotorPort   = new CANPort(11, busName);
        public static final CANPort kFrontRightDriveMotorPort = new CANPort(12, busName);
        public static final CANPort kRearRightDriveMotorPort  = new CANPort(13, busName);

        // CAN IDs for the turning motors on the swerve module
        public static final CANPort kFrontLeftTurningMotorPort  = new CANPort(20, busName);
        public static final CANPort kRearLeftTurningMotorPort   = new CANPort(21, busName);
        public static final CANPort kFrontRightTurningMotorPort = new CANPort(22, busName);
        public static final CANPort kRearRightTurningMotorPort  = new CANPort(23, busName);

        // CAN IDs for the CANCoders
        public static final CANPort kFrontLeftTurningEncoderPort  = new CANPort(30, busName);
        public static final CANPort kRearLeftTurningEncoderPort   = new CANPort(31, busName);
        public static final CANPort kFrontRightTurningEncoderPort = new CANPort(32, busName);
        public static final CANPort kRearRightTurningEncoderPort  = new CANPort(33, busName);

        public static final CANPort kPigeonPort = new CANPort(25, "DriveCAN");
    }
}
