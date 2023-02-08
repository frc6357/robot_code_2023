package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants.LowArmConstants;
import frc.robot.Ports.LowArmPorts;

//
// TODO: What is this class for? Add a comment describing what it is so that people
// picking up the code can understand what it's for.
//

public class SK23LowArmPneumatic extends SK23LowArm
{
    final DoubleSolenoid extendArm = new DoubleSolenoid(LowArmConstants.kModuleType,
        LowArmPorts.kPneumaticForwardChannel, LowArmPorts.kPneumaticReverseChannel);

    public void extendArm(boolean extend)
    {
        extendArm.set(extend ? Value.kForward : Value.kReverse);
    }

    @Override
    public boolean isArmExtended()
    {
        return extendArm.get() == Value.kForward;
    }
}