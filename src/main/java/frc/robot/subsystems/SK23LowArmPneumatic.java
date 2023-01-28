package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants.LowArmConstants;
import frc.robot.Ports.LowArmPorts;

public class SK23LowArmPneumatic extends SK23LowArm {
    final DoubleSolenoid extendArm = new DoubleSolenoid(LowArmConstants.kModuleType,
            LowArmPorts.PneumaticForwardChannel, LowArmPorts.PneumaticReverseChannel);

    public SK23LowArmPneumatic() {
    }

    public void extendArm(boolean extend) {
        extendArm.set(extend ? Value.kForward : Value.kReverse);
    }

    @Override
    public boolean isArmExtended() {
        return extendArm.get() == Value.kForward;
    }
}