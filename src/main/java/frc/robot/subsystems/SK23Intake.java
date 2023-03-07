//
//SK23RollerIntake: This is the system for manuipulating game pieces in the 2023 season
//
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Ports.IntakePorts.*;

public class SK23Intake extends SubsystemBase
{
    // The solonoid used for extending or retracting intake
    DoubleSolenoid intakeExtender = new DoubleSolenoid(kPneumaticsModule, kModuleType,
        kIntakeForwardChannel, kIntakeReverseChannel);
    // The motor responsible for controlling the front roller close the ground
    private final CANSparkMax insideMotor =
            new CANSparkMax(kFrontIntakeMotorPort, MotorType.kBrushless);
    // The motor responsible for controlling the back roller and the front roller far above the ground
    private final CANSparkMax outerMotor =
            new CANSparkMax(kBackTopIntakeMotorPort, MotorType.kBrushless);

    /** Creates a new SK23RollerIntake. */
    public SK23Intake()
    {
        insideMotor.setInverted(true);
        outerMotor.setInverted(true);

        //TODO: Find correct current limit 
        insideMotor.setSmartCurrentLimit(kIntakeCurrentLimit);
        outerMotor.setSmartCurrentLimit(kIntakeCurrentLimit);

        intakeExtender.set(Value.kReverse);
    }

    /**
     * Extends the intake to allow for gamepiece pick up
     */
    public void extendIntake()
    {
        intakeExtender.set(Value.kForward);
    }

    /**
     * Retracts the intake for safer driving
     */
    public void retractIntake()
    {
        intakeExtender.set(Value.kReverse);
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
