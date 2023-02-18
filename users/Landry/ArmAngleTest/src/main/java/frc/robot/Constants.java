package frc.robot;

public class Constants {

    public static class ArmConstants {
        // High Arm Constants
        public static final double kGearRatio = 4.0; // Gear Ratio (Typically > 0)
        public static final double kArmMotorP = 0.35;
        public static final double kArmMotorI = 0.0;
        public static final double kArmMotorD = 0.0;

        public static final double kHighPosition = 45.0; // Y-Button
        public static final double kMidPosition = 180.0; // X-Button
        public static final double kLowPosition = 0.0; // A-Button

        public static final double kJoystickRatio = 1.0;

        // Low Arm Constants
        // public static final double kRotationRatio = 0;
        // public static final double kArmMotorP = 0;
        // public static final double kArmMotorI = 0;
        // public static final double kArmMotorD = 0;

        // public static final double kHighPosition = 0;
        // public static final double kMidPosition = 0;
        // public static final double kLowPosition = 0;

        // public static final double kJoystickRatio = 1;
    }
}
