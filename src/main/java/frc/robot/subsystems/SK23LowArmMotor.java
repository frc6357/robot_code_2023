package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.LowArmConstants;
import frc.robot.Ports.LowArmPorts;

//
// TODO: What is this class for? Add a comment describing what it is so that people
// picking up the code can understand what it's for.
//

public class SK23LowArmMotor extends SK23LowArm
{

    private CANSparkMax     extendArm        =
            new CANSparkMax(LowArmPorts.kExtensionMotor, MotorType.kBrushless);
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
