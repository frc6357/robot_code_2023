//
//SK23RollerIntake: This is the system for manuipulating game pieces in the 2023 season
//
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;

public class SK23ClawIntake extends SubsystemBase
{
    //TODO: Get correct port numbers
    private final CANSparkMax leftMotor  =
            new CANSparkMax(Ports.IntakePorts.kFrontIntakeMotorPort, MotorType.kBrushless);
    private final CANSparkMax rightMotor =
            new CANSparkMax(Ports.IntakePorts.kBackTopIntakeMotorPort, MotorType.kBrushless);

    /** Creates a new SK23RollerIntake. */
    public SK23ClawIntake()
    {
        leftMotor.setInverted(true);
        rightMotor.setInverted(true);
    }

    /**
     * Sets the intake to intake game pieces. Changes the mode of intake based on whether
     * it is intaking a cube or cone
     */
    public void setLeftSpeed(double speed)
    {
        leftMotor.set(speed);
    }

    public void setRightSpeed(double speed)
    {
        rightMotor.set(speed);
    }

    public double getLeftSpeed()
    {
        return leftMotor.get();
    }

    public double getRightSpeed()
    {
        return rightMotor.get();
    }

}
