//
//SK23RollerIntake: This is the system for manuipulating game pieces in the 2023 season
//
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.Constants.IntakeConstants;

public class SK23Intake extends SubsystemBase
{
    // The solonoid used for extending or retracting intake
    DoubleSolenoid intakeExtender =
            new DoubleSolenoid(Ports.IntakePorts.kPneumaticsModule, PneumaticsModuleType.REVPH,
                Ports.IntakePorts.kIntakeForwardChannel, Ports.IntakePorts.kIntakeReverseChannel); 
    // The motor responsible for controlling the front roller close the ground
    private final CANSparkMax insideMotor =
            new CANSparkMax(Ports.IntakePorts.kFrontIntakeMotorPort, MotorType.kBrushless);
    // The motor responsible for controlling the back roller and the front roller far above the ground
    private final CANSparkMax outerMotor =
            new CANSparkMax(Ports.IntakePorts.kBackTopIntakeMotorPort, MotorType.kBrushless);

    /** Creates a new SK23RollerIntake. */
    public SK23Intake()
    {
        insideMotor.setInverted(true);
        outerMotor.setInverted(true);

        //TODO: Find correct current limit 
        insideMotor.setSmartCurrentLimit(IntakeConstants.kIntakeCurrentLimit);
        outerMotor.setSmartCurrentLimit(IntakeConstants.kIntakeCurrentLimit);

        intakeExtender.set(Value.kReverse);
    }

    /**
     * Sets the extension solenoid to either kforwards for extend or kReverse for
     * retraction
     */
    public void setIntakeExtension(Value setExtend)
    {
        intakeExtender.set(setExtend);
    }

    /**
     * Sets the intake to intake game pieces of the motor that controls the front roller.
     * Changes the mode of intake based on whether it is intaking a cube or cone
     */
    public void setInnerRollerSpeed(double speed)
    {
        insideMotor.set(speed);
    }

    /**
     * Sets the intake to intake game pieces of the motor that controls the top and rear
     * roller. Changes the mode of intake based on whether it is intaking a cube or cone
     */
    public void setOuterRollerSpeed(double speed)
    {
        outerMotor.set(speed);
    }

    /**
     * Get the current motor speed for the motor that controls the front roller
     */
    public double getFrontRollerSpeed()
    {
        return insideMotor.get();
    }

    /**
     * Get the current motor speed for the motor that controls the back and top rollers
     */
    public double getRearTopRollerSpeed()
    {
        return outerMotor.get();
    }

}
