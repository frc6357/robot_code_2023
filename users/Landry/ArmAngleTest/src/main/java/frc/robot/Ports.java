package frc.robot;

public class Ports
{
    public class OperatorPorts{
        public static final int kDriverControllerPort = 0;
        public static final int kOperatorControllerPort = 1;

        public static final int kLowButton = 1; //A-Button
        public static final int kMidButton = 3; //X-Button
        public static final int kHighButton = 4; // Y-Button
        public static final int kStopButton = 2; //B-Button

        public static final int kOperatorArmAxis = 5; //Y-Axis for right joystick
    }
    public class ArmPorts
    {
        public static final int kMainMotor = 30;
        public static final int kFollowerMotor = 20;
        public static final int kLowerSwitch = 0;
        public static final int kUpperSwitch = 0;
    }
}
