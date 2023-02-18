package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.Ports.ArmPorts;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm.
 */
public class SK23Arm extends SubsystemBase
{
    ArmAngleInternal Arm;

    public static enum ArmAngleEnum
    {
        /** Set the angle to reach the top cube node */
        HighPosition,
        /** Set the angle to reach the middle cube node */
        MidPosition,
        /** Set the angle to reach the bottom cube node */
        LowPosition
    }

    public SK23Arm()
    {
        Arm = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor,
            ArmConstants.kRotationRatio, ArmConstants.kArmMotorP, ArmConstants.kArmMotorI,
            ArmConstants.kArmMotorD);
        Arm.resetEncoder();
    }

    public void setTargetAngle(ArmAngleEnum angle)
    {
        switch (angle)
        {
            case HighPosition:
                Arm.setTargetAngle(ArmConstants.kHighPosition);
                break;
            case MidPosition:
                Arm.setTargetAngle(ArmConstants.kMidPosition);
                break;
            case LowPosition:
                Arm.setTargetAngle(ArmConstants.kLowPosition);
                break;

        }
    }

    public void setTargetAngle(double angle)
    {
        Arm.setTargetAngle(angle);
    }

    public void setJoystickAngle(double joystickInput){
        double angleChange = joystickInput * ArmConstants.kJoystickRatio; // Converting joystick input into degrees moved on the arm
        double currentAngle = getTargetAngle() + angleChange;
        setTargetAngle(currentAngle);
        
    }
    public boolean isAtSetPoint()
    {
        return Arm.getCurrentAngle() == Arm.getTargetAngle();
    }

    public double getCurrentAngle()
    {
        return Arm.getCurrentAngle();
    }

    public double getTargetAngle()
    {
        return Arm.getTargetAngle();
    }

    public void periodic()
    {
        Arm.periodic();
        Arm.checkLimitSensors();
        double current_angle = Arm.getCurrentAngle();
        double target_angle = Arm.getTargetAngle();
        SmartDashboard.putNumber("Current Angle", current_angle);
        SmartDashboard.putNumber("Target Angle", target_angle);
    }
}
