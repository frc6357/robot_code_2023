package frc.robot;

import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.utils.CANPort;

import static edu.wpi.first.wpilibj.XboxController.Axis.*;
import static edu.wpi.first.wpilibj.XboxController.Button.*;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Ports
{
    // TODO: Whenever you add a CAN ID to the file, actually assign a number. A
    // definition of 0 is definitely wrong and the choice is essentially
    // arbitrary so why create a job that someone needs to remember to do later
    // when you could just stick in any non-clashing value when the port is 
    // initially added? If we assign groups of 10 IDs per subsystem, we can also
    // guard against clashes. For example, all ARM-related CAN IDs could start at,
    // say, 80 and increment.
    public static class ArmPorts
    {
        // CAN IDs for Arm have a tens digit of 5
        private static final String busName      = "";
        public static final CANPort kMainMotor   = new CANPort(50, busName);
        public static final int     kLowerSwitch = -1;
        public static final int     kUpperSwitch = -1;
    }

    public static class IntakePorts
    {
        // CAN IDs for Intake have a tens digit of 4
        public static final int kFrontIntakeMotorPort   = 40;
        public static final int kBackTopIntakeMotorPort = 41;

        // Intake PCM channels
        public static final int kIntakeForwardChannel = 0;
        public static final int kIntakeReverseChannel = 1;

        // Pneumatics Module Constants
        // The PCM has a single digit CAN ID to increase CAN bus priority
        public static final int                  kPneumaticsModule = 2;
        public static final PneumaticsModuleType kModuleType       = PneumaticsModuleType.REVPH;

    }

    /**
     * Defines the button, controller, and axis IDs needed to get input from an external
     * controller
     */
    public static class OperatorPorts
    {
        public static final int kDriverControllerPort = 0;
 
        // Axes for driving
        public static final Axis kVelocityXPort     = kLeftY;
        public static final Axis kVelocityYPort     = kLeftX;
        public static final Axis kVelocityOmegaPort = kRightX;

        // Buttons for driving
        public static final Axis   kRobotCentricMode = kRightTrigger;
        public static final Button kRotateDSS        = kY;
        public static final Button kRotateGrid       = kA;
        public static final Button kRotateLeft       = kX;
        public static final Button kRotateRight      = kB;
        public static final Button kAutoLevel        = kBack;

        // Buttons for driver angle reset
        public static final int kResetGyroDSS   = 0;
        public static final int kResetGyroGrid  = 180;
        public static final int kResetGyroLeft  = 270;
        public static final int kResetGyroRight = 90;

        // Buttons for On The Fly Driving
        public static final Button kGridLeftModifier  = kLeftBumper;
        public static final Button kGridRightModifier = kLeftBumper;
        public static final Button kGPLeftButton      = kX;
        public static final Button kGPMiddleButton    = kA;
        public static final Button kGPRightButton     = kB;

        public static final Axis kSlowMode = kLeftTrigger;

        public static final int kOperatorControllerPort = 1;

        // Modifiers
        public static final Button kOperatorCone = kLeftBumper;
        public static final Button kOperatorCube = kRightBumper;

        // Buttons for intake
        public static final Button kOperatorIntake        = kBack;
        public static final Button kOperatorEject         = kStart;
        public static final Axis   kOperatorExtendIntake  = kLeftTrigger;
        public static final Axis   kOperatorRetractIntake = kRightTrigger;

        // Buttons for arm
        public static final Button kOperatorHighArm       = kY;
        public static final Button kOperatorMidArm        = kX;
        public static final Button kOperatorLowArm        = kA;
        public static final Button kOperatorSubstationArm = kB;
        public static final int    kOperatorZeroPosition  = 180;
        public static final Axis   kOperatorArmAxis       = kRightY;
        public static final Button kOperatorResetArmPos   = kLeftStick;
        public static final Button kOperatorArmOverride   = kRightStick;

    }

    /**
     * Defines all the ports needed to create sensors and actuators for the drivetrain.
     */
    public static class DrivePorts
    {
        private static final String busName = "DriveCAN";

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

        // CAN ID for IMU
        public static final CANPort kPigeonPort = new CANPort(25, busName);
    }
}
