package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmConstants;
import frc.robot.Ports.ArmPorts;
import frc.robot.subsystems.superclasses.Arm;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm.
 */
public class SK23Arm extends Arm
{
    ArmAngleInternal Arm;
    int joystickCount;

    public SK23Arm()
    {
        Arm = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor.ID,
            ArmConstants.kGearRatio, ArmConstants.kArmMotorP, ArmConstants.kArmMotorI,
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
            case SubstationPosition:
                Arm.setTargetAngle(ArmConstants.kSubstationPosition);

        }
    }

    public void setTargetAngle(double angle)
    {
        Arm.setTargetAngle(angle);
    }

        /**
     * 
     * @param joystickInput
     *                      Input coming from the joystick
     * @param joystickTime
     *                      Time in seconds between each check to change the arm
     *                      position.
     */
    public void setJoystickAngle(double joystickInput, double joystickTime) {
        
        double joystickTimePeriodic = joystickTime * 50; // Converts seconds into number of periodic calls

        if (joystickCount == joystickTimePeriodic) {
            double angleChange = ArmConstants.kJoystickRate;

            if (Math.abs(joystickInput) > ArmConstants.kJoystickDeadband) {
                double currentAngle = getTargetAngle() + Math.signum(joystickInput) * angleChange;
                setTargetAngle(currentAngle);
            }

            joystickCount = 0;
        }

        joystickCount++;

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
