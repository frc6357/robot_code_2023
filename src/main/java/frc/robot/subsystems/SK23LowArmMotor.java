package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.LowArmConstants;
import frc.robot.Ports.LowArmPorts;

/**
 * A version of the arm subsystem in which the pivot point of the arm is located at a
 * relatively low point. This causes there to be a rotational motion controlled by N
 * motors and an extension motion controlled by either motors. This arm will rotate to set
 * positions to score the game piece.
 */

public class SK23LowArmMotor extends SK23LowArm
{

    private CANSparkMax     extendArm        =
            new CANSparkMax(LowArmPorts.kExtensionMotor.ID, MotorType.kBrushless);
    private RelativeEncoder extendArmEncoder = extendArm.getEncoder();

    public SK23LowArmMotor()
    {
        super();
        extendArmEncoder.setPositionConversionFactor(LowArmConstants.kExtensionDistancePerPulse);
        extendArmEncoder.setVelocityConversionFactor(LowArmConstants.kExtensionDistancePerPulse);
    }

    @Override
    public void extendArm(boolean extend)
    {
        extendArm.set(LowArmConstants.kMotorSpeed);
        if (extendArmEncoder.getPosition() >= LowArmConstants.kMaxHeight)
        {
            extendArm.stopMotor();
        }
    }

    @Override
    public boolean isArmExtended()
    {
        return (extendArmEncoder.getPosition() >= LowArmConstants.kMaxHeight);
    }

}
