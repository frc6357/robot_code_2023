//
//SK23RollerIntake: This is the system for manuipulating game pieces in the 2023 season
//
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.Constants.GamePieceEnum;

public class SK23ClawIntake extends SubsystemBase
{
    //TODO: Get correct port numbers
    private final CANSparkMax frontMotor   =
            new CANSparkMax(Ports.IntakePorts.kFrontIntakeMotorPort, MotorType.kBrushless);
    private final CANSparkMax rearTopMotor =
            new CANSparkMax(Ports.IntakePorts.kBackTopIntakeMotorPort, MotorType.kBrushless);

    /** Creates a new SK23RollerIntake. */
    public SK23ClawIntake()
    {
        frontMotor.setInverted(true);
        rearTopMotor.setInverted(true);
    }

    /**
     * Sets the intake to intake game pieces. Changes the mode of intake based on whether
     * it is intaking a cube or cone
     */
    public void setFrontRollerSpeed(double speed)
    {
        frontMotor.set(speed);
    }

    public void setRearTopRollerSpeed(double speed)
    {
        rearTopMotor.set(speed);
    }

    public double getFrontRollerSpeed()
    {
        return frontMotor.get();
    }

    public double getRearTopRollerSpeed()
    {
        return rearTopMotor.get();
    }

}
