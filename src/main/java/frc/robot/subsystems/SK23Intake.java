//
//SK23RollerIntake: This is the system for manuipulating game pieces in the 2023 season
//
package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.kIntakeCurrentLimit;
import static frc.robot.Ports.IntakePorts.kBackTopIntakeMotorPort;
import static frc.robot.Ports.IntakePorts.kFrontIntakeMotorPort;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.GamePieceEnum;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Ports.IntakePorts;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

public class SK23Intake extends SubsystemBase
{
    // The solonoid used for extending or retracting intake
    ArmAngleInternal intakeExtender = new ArmAngleInternal(AngleMotorType.SparkMax, IntakePorts.kIntakeExtendMotor, IntakeConstants.kGearRatio, IntakeConstants.kIntakeMotorP, IntakeConstants.kIntakeMotorI, IntakeConstants.kIntakeMotorD, IntakeConstants.kIntakeMotorIZone);
    // The motor responsible for controlling the front roller close the ground
    private final CANSparkMax insideMotor =
            new CANSparkMax(kFrontIntakeMotorPort, MotorType.kBrushless);
    // The motor responsible for controlling the back roller and the front roller far above the ground
    private final CANSparkMax outerMotor =
            new CANSparkMax(kBackTopIntakeMotorPort, MotorType.kBrushless);

    private GamePieceEnum GPState = GamePieceEnum.Cone;

    Boolean pastGPState;
    Boolean pastIntakeState;
    Boolean pastOuttakeState;
    Boolean pastExtendState;

    /** Creates a new SK23RollerIntake. */
    public SK23Intake()
    {
        insideMotor.setInverted(true);
        outerMotor.setInverted(true);

        insideMotor.setSmartCurrentLimit(kIntakeCurrentLimit);
        outerMotor.setSmartCurrentLimit(kIntakeCurrentLimit);

        intakeExtender.resetEncoder();

        pastGPState = getGamePieceState() == GamePieceEnum.Cone;
        pastIntakeState = isIntaking();
        pastOuttakeState = isOuttaking();
        pastExtendState = isExtended();
        double currentAngle = intakeExtender.getCurrentAngle();
        double targetAngle = intakeExtender.getTargetAngle();

        SmartDashboard.putNumber("Intake Current Angle", currentAngle);
        SmartDashboard.putNumber("Intake Target Angle", targetAngle);
        SmartDashboard.putBoolean("Intake State", pastGPState);
        SmartDashboard.putBoolean("Intaking", pastIntakeState);
        SmartDashboard.putBoolean("Outtaking", pastOuttakeState);
        SmartDashboard.putBoolean("Intake Extended", pastExtendState);

    }

    /**
     * Extends the intake to allow for gamepiece pick up
     */
    public void extendIntake()
    {
        intakeExtender.setTargetAngle(IntakeConstants.kExtendAngle); //Positive angle moves upward and Negative angle moves downward
    }

    /**
     * Retracts the intake for safer driving
     */
    public void retractIntake()
    {
        intakeExtender.setTargetAngle(IntakeConstants.kRetractAngle); //Positive angle moves upward and Negative angle moves downward
    }

    public void setTargetAngle(double angle){
        intakeExtender.setTargetAngle(angle);
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

    /**
     * Sets the intake state to either cone, cube, or none
     * 
     * @param gp
     *            The desired game piece
     */
    public void setGamePieceState(GamePieceEnum gp)
    {
        GPState = gp;
    }

    /**
     * Gets the desired game piece the intake wants to manipulate
     * 
     * @param gp
     *            The desired game piece (Cone, Cube, or None)
     */
    public GamePieceEnum getGamePieceState()
    {
        return GPState;
    }

    public boolean isIntaking()
    {
        if (getGamePieceState() == GamePieceEnum.Cone)
        {
            return getFrontRollerSpeed() > 0;
        }
        else
        {
            return getFrontRollerSpeed() < 0;
        }

    }

    public boolean isOuttaking()
    {
        if (getGamePieceState() == GamePieceEnum.Cone)
        {
            return getFrontRollerSpeed() < 0;
        }
        else
        {
            return getFrontRollerSpeed() > 0;
        }

    }

    public boolean isExtended()
    {
        return intakeExtender.getCurrentAngle() == IntakeConstants.kExtendAngle;
    }

    @Override
    public void periodic()
    {

        double currentAngle = intakeExtender.getCurrentAngle();
        double targetAngle = intakeExtender.getTargetAngle();

        SmartDashboard.putNumber("Intake Current Angle", currentAngle);
        SmartDashboard.putNumber("Intake Target Angle", targetAngle);

        Boolean curGPState = getGamePieceState() == GamePieceEnum.Cone;
        if(curGPState != pastGPState){
            SmartDashboard.putBoolean("Intake State", curGPState);
            pastGPState = curGPState;
        }

        Boolean curIntakeState = isIntaking();
        if(curIntakeState != pastIntakeState){
            SmartDashboard.putBoolean("Intaking", curIntakeState);
            pastIntakeState = curIntakeState;
        }
        
        Boolean curOuttakeState = isOuttaking();
        if(curOuttakeState != pastOuttakeState){
            SmartDashboard.putBoolean("Outtaking", curOuttakeState);
            pastOuttakeState = curOuttakeState;
        }
        
        Boolean curExtendState = isExtended();
        if(curExtendState != pastExtendState){
            SmartDashboard.putBoolean("Intake Extended", curExtendState);
            pastExtendState = curExtendState;
        }
        
    }

}
