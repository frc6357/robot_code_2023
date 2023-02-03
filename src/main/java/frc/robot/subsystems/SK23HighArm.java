package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants.HighArmConstants;
import frc.robot.Ports.HighArmPorts;
import frc.robot.subsystems.superclasses.Arm;

/**
 * A version of the arm subsystem in which the pivot point of the arm is located
 * at a relatively high point. This causes the only motion of the arm to be a
 * rotational motion controlled by N motors. This arm will rotate to set
 * positions to score the game piece.
 */
public class SK23HighArm extends Arm {

    private CANSparkMax mainMotor = new CANSparkMax(HighArmPorts.kMainMotor, MotorType.kBrushless);
    private RelativeEncoder mainEncoder = mainMotor.getEncoder();
    private CANSparkMax followerMotor = new CANSparkMax(HighArmPorts.kFollowerMotor, MotorType.kBrushless);
    private MotorControllerGroup motors = new MotorControllerGroup(mainMotor, followerMotor);

    // TODO: The encoder in the NEO is relative rather than absolute so you need to
    // have a reference that you know represents a specific arm angle. This allows you
    // to set the zero point and remove encoder drift as the class is used. Think about
    // supporting a limit switch or other sensor that lets you know when the arm is at
    // the zero position.
    
    /**
     * Creates a new High Pivot arm using Neo Brushless Motors.
     */
    public SK23HighArm() {
        mainEncoder.setPosition(HighArmConstants.kArmPositionOffsetDegrees);
        mainEncoder.setPositionConversionFactor(HighArmConstants.kDegreesPerPulse);
        mainEncoder.setVelocityConversionFactor(HighArmConstants.kDegreesPerPulse);
    }

    @Override
    public void setRotationSpeed(double speed) {
        motors.set(speed);
    }

    @Override
    public double getPosition() {
        return mainEncoder.getPosition();
    }

    @Override
    public void extendArm(boolean extend) {
        DriverStation.reportWarning("High Arm cannot extend", true);
    }

    @Override
    public boolean isArmExtended() {
        DriverStation.reportWarning("High Arm cannot extend", true);
        return false;
    }
}
