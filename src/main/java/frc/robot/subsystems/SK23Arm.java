package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmConstants;
import frc.robot.Ports.ArmPorts;
import frc.robot.subsystems.superclasses.Arm;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a specified
 * angle and reading the current angle of the arm. Using only one CANSparkMax motor.
 */
public class SK23Arm extends Arm
{
    ArmAngleInternal Arm;
    int              joystickCount;

    public SK23Arm()
    {
        Arm = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor.ID,
            ArmConstants.kGearRatio, ArmConstants.kArmMotorP, ArmConstants.kArmMotorI,
            ArmConstants.kArmMotorD, ArmConstants.kArmMotorIZone);
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
            case FloorPosition:
                Arm.setTargetAngle(ArmConstants.kLowPosition);
                break;
            case SubstationPosition:
                Arm.setTargetAngle(ArmConstants.kSubstationPosition);
                break;
        }
    }

    public void setTargetAngle(double angle)
    {
        Arm.setTargetAngle(angle);
    }

    public boolean isAtTargetAngle()
    {
        return Arm.getCurrentAngle() == Arm.getTargetAngle();
    }

    public double getCurrentAngle()
    {
        return Arm.getCurrentAngle();
    }

    /**
     * @return Returns the current target angle of the arm
     */
    public double getTargetAngle()
    {
        return Arm.getTargetAngle();
    }

    public void testInit(){
        Arm.testInit();
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
