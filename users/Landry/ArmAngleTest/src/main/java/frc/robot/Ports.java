package frc.robot;

public class Ports
{
    public class ControllerPorts{
        public static final int kDriverControllerPort = 0;
        public static final int kOperatorControllerPort = 0;

        public static final int kLowButton = 1; //A-Button
        public static final int kMidButton = 3; //X-Button
        public static final int kHighButton = 4; // Y-Button

        public static final int kYAxis = 0; //Y-Axis for controller
    }
    public class ArmPorts
    {
        public static final int kMainMotor = 1;
        public static final int kLowerSwitch = 0;
        public static final int kUpperSwitch = 0;
    }
}
