//
//SK23RollerIntake: This is the system for manuipulating game pieces in the 2023 season
//
package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Ports.IntakePorts.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.GamePieceEnum;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

public class SK23Intake extends SubsystemBase
{
    // The motor used for extending or retracting intake
    ArmAngleInternal intakeExtender =
            new ArmAngleInternal(AngleMotorType.SparkMax, kIntakeExtendMotor, kGearRatio,
                kIntakeMotorP, kIntakeMotorI, kIntakeMotorD, kIntakeMotorIZone,
                MinOutput, MaxOutput);

    // The motor responsible for controlling the front roller close the ground
    private final CANSparkMax insideMotor =
            new CANSparkMax(kFrontIntakeMotorPort, MotorType.kBrushless);
    // The motor responsible for controlling the back roller and the front roller far above the ground
    private final CANSparkMax outerMotor =
            new CANSparkMax(kBackTopIntakeMotorPort, MotorType.kBrushless);

    // Which game piece the intake is ready to manipulate
    private GamePieceEnum GPState = GamePieceEnum.Cube;

    // Booleans for SmartDashboard
    private boolean pastGPState;
    private boolean pastIntakeState;
    private boolean pastOuttakeState;
    private boolean pastExtendState;

    /** Creates a new SK23RollerIntake. */
    public SK23Intake()
    {
        resetEncoder(kStartAngle);

        insideMotor.setInverted(true);
        outerMotor.setInverted(true);

        insideMotor.setSmartCurrentLimit(kIntakeCurrentLimit);
        outerMotor.setSmartCurrentLimit(kIntakeCurrentLimit);

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
     * Resets the arm encoder to read zero at the current position
     */
    public void resetEncoder()
    {
        intakeExtender.resetEncoder();
    }

    /**
     * Resets position of encoder to given angle
     * 
     * @param angle
     *          The desired angle to reset the position to
     */
    public void resetEncoder(double angle)
    {
        intakeExtender.resetEncoder(angle);
    }

    /**
     * Extends the intake to allow for gamepiece pick up from the floor
     */
    public void extendIntake()
    {
        setTargetAngle(kExtendAngle);
    }

    /*
     * Moves the intake down by a redefined angle basied around its current position
     */

    public void incrementIntakeDown(){
        
        setTargetAngle(getCurrentAngle() + kIncrementDownAngle);
    }


    /**
     * Retracts the intake for safer driving
     */
    public void retractIntake()
    {
        setTargetAngle(kRetractAngle);
    }

    /**
     * Extends the intake to allow for gamepiece pick up from the substation
     */
    public void substationIntake()
    {
        setTargetAngle(kSubstationAngle);
    }

    /**
     * Sets the intake angle to the desired position
     * 
     * @param angle
     *            The angle in degrees with 0º being fully retracted and the angle
     *            becoming more negative as it extends
     */
    public void setTargetAngle(double angle){
        intakeExtender.setTargetAngle(angle);
    }

    /**
     * Sets the intake to intake game pieces of the motor that controls the front roller.
     * Changes the mode of intake based on whether it is intaking a cube or cone
     * 
     * @param speed
     *            The speed of the motor in percent from -1 to 1
     */
    public void setInnerRollerSpeed(double speed)
    {
        insideMotor.set(speed);
    }

    /**
     * Sets the intake to intake game pieces of the motor that controls the top and rear
     * roller. Changes the mode of intake based on whether it is intaking a cube or cone
     * 
     * @param speed
     *            The speed of the motor in percent from -1 to 1
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

    /**
     * @return Whether or not the intake motor speeds are set to intake with the current
     *         game piece state
     */
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

    /**
     * @return Whether or not the intake motor speeds are set to outtake with the current
     *         game piece state
     */
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

    public double getCurrentAngle(){
        return intakeExtender.getCurrentAngle();
    }

    public double getTargetAngle(){
        return intakeExtender.getTargetAngle();
    }

    public boolean isExtended()
    {
        return intakeExtender.getCurrentAngle() == kExtendAngle;
    }

    @Override
    public void periodic()
    {

        double currentAngle = intakeExtender.getCurrentAngle();
        double targetAngle = intakeExtender.getTargetAngle();

        SmartDashboard.putNumber("Intake Current Angle", currentAngle);
        SmartDashboard.putNumber("Intake Target Angle", targetAngle);

        boolean curGPState = getGamePieceState() == GamePieceEnum.Cone;
        if(curGPState != pastGPState){
            SmartDashboard.putBoolean("Intake State", curGPState);
            pastGPState = curGPState;
        }

        boolean curIntakeState = isIntaking();
        if(curIntakeState != pastIntakeState){
            SmartDashboard.putBoolean("Intaking", curIntakeState);
            pastIntakeState = curIntakeState;
        }
        
        boolean curOuttakeState = isOuttaking();
        if(curOuttakeState != pastOuttakeState){
            SmartDashboard.putBoolean("Outtaking", curOuttakeState);
            pastOuttakeState = curOuttakeState;
        }
        
        boolean curExtendState = isExtended();
        if(curExtendState != pastExtendState){
            SmartDashboard.putBoolean("Intake Extended", curExtendState);
            pastExtendState = curExtendState;
        }
        
    }

}
