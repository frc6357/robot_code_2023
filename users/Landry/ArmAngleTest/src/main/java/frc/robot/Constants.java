package frc.robot;

public class Constants {

    public static class ArmConstants {
        // High Arm Constants
        public static final double kGearRatio = 4.0; // Gear Ratio (Typically > 0)
        public static final double kArmMotorP = 0.17;
        public static final double kArmMotorI = 0.0005;
        public static final double kArmMotorD = 0.0;
        public static final double kArmMotorIZone = 0.0;
        public static final double kJoystickTime = 1.0; // Time between checking joystick in seconds
        public static final double kJoystickRate = 5.0; // Degrees moved per 1 kJoystickTime
        public static final double kJoystickDeadband = 0.3; //Joystick position at which it begins to move
        public static final double kRampRate = 1.0;

        public static final double kHighPosition = 22.5; // Y-Button
        public static final double kMidPosition = 45.0; // X-Button
        public static final double kLowPosition = 0.0; // A-Button
    }
}
