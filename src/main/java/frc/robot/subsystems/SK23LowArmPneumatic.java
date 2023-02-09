package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants.LowArmConstants;
import frc.robot.Ports.LowArmPorts;

/**
 * A version of the arm subsystem in which the pivot point of the arm is located at a
 * relatively low point. This causes there to be a rotational motion controlled by N
 * motors and an extension motion controlled by pneumatics. This arm will rotate to set
 * positions to score the game piece.
 */

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