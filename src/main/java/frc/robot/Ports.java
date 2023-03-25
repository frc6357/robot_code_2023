package frc.robot;

import static edu.wpi.first.wpilibj.XboxController.Axis.*;
import static edu.wpi.first.wpilibj.XboxController.Button.*;
import static frc.robot.utils.SKTrigger.INPUT_TYPE.*;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.utils.CANPort;
import frc.robot.utils.SKTrigger;
import frc.robot.utils.filters.FilteredAxis;
import frc.robot.utils.filters.FilteredXboxController;

public class Ports
{
    public static class ArmPorts
    {
        // CAN IDs for Arm have a tens digit of 5
        private static final String busName      = "";
        public static final CANPort kMainMotor   = new CANPort(50, busName);
        public static final CANPort kEncoder = new CANPort(51, busName); 
    }

    public static class IntakePorts
    {
        // CAN IDs for Intake have a tens digit of 4
        public static final int kFrontIntakeMotorPort   = 40;
        public static final int kBackTopIntakeMotorPort = 41;

        public static final int kIntakeExtendMotor = 42;

    }

    /**
     * Defines the button, controller, and axis IDs needed to get input from an external
     * controller
     */
    public static class OperatorPorts
    {
        // The driver's controller
        public static final GenericHID kDriver = new FilteredXboxController(0).getHID();
 
        // Axes for driving
        public static final FilteredAxis kVelocityXPort     = new FilteredAxis(() -> kDriver.getRawAxis(kLeftY.value));
        public static final FilteredAxis kVelocityYPort     = new FilteredAxis(() -> kDriver.getRawAxis(kLeftX.value));
        public static final FilteredAxis kVelocityOmegaPort = new FilteredAxis(() -> kDriver.getRawAxis(kRightX.value));

        // Buttons for driving
        public static final SKTrigger kRobotCentricMode = new SKTrigger(kDriver, kRightBumper.value, BUTTON);
        public static final SKTrigger kRotateDSS        = new SKTrigger(kDriver, kY.value, BUTTON);
        public static final SKTrigger kRotateGrid       = new SKTrigger(kDriver, kA.value, BUTTON);
        public static final SKTrigger kRotateLeft       = new SKTrigger(kDriver, kX.value, BUTTON);
        public static final SKTrigger kRotateRight      = new SKTrigger(kDriver, kB.value, BUTTON);
        public static final SKTrigger kAutoLevel        = new SKTrigger(kDriver, kBack.value, BUTTON);

        // Buttons for driver angle reset
        public static final SKTrigger kResetGyroDSS   = new SKTrigger(kDriver, 0, POV);
        public static final SKTrigger kResetGyroGrid  = new SKTrigger(kDriver, 180, POV);
        public static final SKTrigger kResetGyroLeft  = new SKTrigger(kDriver, 270, POV);
        public static final SKTrigger kResetGyroRight = new SKTrigger(kDriver, 90, POV);

        // Buttons for On The Fly Driving
        // public static final SKTrigger kGridLeftModifier  = new SKTrigger(kDriver, kLeftBumper.value, BUTTON);
        // public static final SKTrigger kGridRightModifier = new SKTrigger(kDriver, kRightBumper.value, BUTTON);
        // public static final SKTrigger kGPLeftButton      = new SKTrigger(kDriver, kX.value, BUTTON);
        // public static final SKTrigger kGPMiddleButton    = new SKTrigger(kDriver, kA.value, BUTTON);
        // public static final SKTrigger kGPRightButton     = new SKTrigger(kDriver, kB.value, BUTTON);

        public static final SKTrigger kSlowMode = new SKTrigger(kDriver, kLeftBumper.value, BUTTON);

        // Buttons for Intake
        public static final SKTrigger kDriverIntake = new SKTrigger(kDriver, kLeftTrigger.value, AXIS);
        public static final SKTrigger kDriverEject = new SKTrigger(kDriver, kRightTrigger.value, AXIS);


        // Operator controller set to xbox controller
        public static final GenericHID kOperator = new FilteredXboxController(1).getHID();

        // Modifiers
        public static final SKTrigger kConeState = new SKTrigger(kOperator, kRightBumper.value, BUTTON);
        public static final SKTrigger kCubeState = new SKTrigger(kOperator, kLeftBumper.value, BUTTON);

        // Buttons for intake
        public static final SKTrigger kIntake = new SKTrigger(kOperator, kLeftTrigger.value, AXIS);
        public static final SKTrigger kEject  = new SKTrigger(kOperator, kRightTrigger.value, AXIS);

        public static final SKTrigger kExtendIntake  = new SKTrigger(kOperator, 180, POV); // Down POV Button
        public static final SKTrigger kRetractIntake = new SKTrigger(kOperator, 0, POV);   // Up POV Button
        public static final SKTrigger kMidLowIntake  = new SKTrigger(kOperator, 270, POV); // Left POV Button
        public static final SKTrigger kMidHighIntake = new SKTrigger(kOperator, 90, POV);   // Right POV Button

        // Buttons for arm positions
        public static final SKTrigger kHighArm       = new SKTrigger(kOperator, kY.value, BUTTON);
        public static final SKTrigger kMidArm        = new SKTrigger(kOperator, kX.value, BUTTON);
        public static final SKTrigger kLowArm        = new SKTrigger(kOperator, kA.value, BUTTON);
        public static final SKTrigger kSubstationArm = new SKTrigger(kOperator, kB.value, BUTTON);
        public static final SKTrigger kZeroPosition  = new SKTrigger(kOperator, kStart.value, BUTTON);

        // Arm overrides
        public static final FilteredAxis kArmAxis       = new FilteredAxis(() -> kOperator.getRawAxis(kRightY.value));
        public static final SKTrigger    kResetArmPos   = new SKTrigger(kOperator, kBack.value, BUTTON);
        public static final SKTrigger    kArmOverride   = new SKTrigger(kOperator, kRightStick.value, BUTTON);
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
