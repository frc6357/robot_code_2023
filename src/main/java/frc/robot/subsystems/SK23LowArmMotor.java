package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.LowArmConstants;
import frc.robot.Ports.LowArmPorts;


public class SK23LowArmMotor extends SK23LowArm {

    private CANSparkMax extendArm = new CANSparkMax(LowArmPorts.kExtensionMotor, MotorType.kBrushless);
    private RelativeEncoder extendArmEncoder = extendArm.getEncoder();

    @Override
    public void extendArm(boolean extend) {
        extendArm.set(LowArmConstants.kMotorSpeed);
        if (extendArmEncoder.getPosition() >= LowArmConstants.kMaxHeight)
        {
            extendArm.stopMotor();
        }
    }

    @Override
    public boolean isArmExtended() {
        if (extendArmEncoder.getPosition() >= LowArmConstants.kMaxHeight)
        {
            return true;
        }
        return false;
    }
    
}
