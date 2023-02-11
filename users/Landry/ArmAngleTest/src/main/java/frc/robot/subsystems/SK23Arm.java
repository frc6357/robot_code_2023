package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports.ArmPorts;
import frc.robot.utils.armAngle.ArmAngleInternal;
import frc.robot.utils.armAngle.ArmAngleInternal.AngleMotorType;

/**
 * A class that represents the arm of the robot. Capable of moving the arm to a
 * specified
 * angle and reading the current angle of the arm.
 */
public class SK23Arm extends SubsystemBase {
    public static final int kRotationRatio = 1;
    public static final int kArmMotorP = 0;
    public static final int kArmMotorI = 0;
    public static final int kArmMotorD = 0;

    public static final double kHighCube = 0;
    public static final double kMidCube = 0;
    public static final double kLowCube = 0;
    public static final double kHighCone = 0;
    public static final double kLowCone = 0;

    public double setPoint;
    ArmAngleInternal mainMotor;

    public static enum ArmAngleEnum {
        /** Set the angle to reach the top cube node */
        HighCube,
        /** Set the angle to reach the middle cube node */
        MidCube,
        /** Set the angle to reach the bottom cube node */
        LowCube,
        /** Set the angle to reach the top cone node */
        HighCone,
        /** Set the angle to reach the bottom cone node */
        LowCone
    }

    public SK23Arm() {
        mainMotor = new ArmAngleInternal(AngleMotorType.SparkMax, ArmPorts.kMainMotor,
                kRotationRatio, kArmMotorP, kArmMotorI, kArmMotorD, ArmPorts.kLowerSwitch,
                ArmPorts.kUpperSwitch);
        SmartDashboard.putNumber("Set Point", getSetPoint());
        setPoint = getSetPoint();
    }

    public void setArmAngle(ArmAngleEnum angle) {
        switch (angle) {
            case HighCube:
                mainMotor.setAngle(kHighCube);
                break;
            case MidCube:
                mainMotor.setAngle(kMidCube);
                break;
            case LowCube:
                mainMotor.setAngle(kLowCube);
                break;
            case HighCone:
                mainMotor.setAngle(kHighCone);
                break;
            case LowCone:
                mainMotor.setAngle(kLowCone);
                break;

        }
    }

    public double getCurrentAngle() {
        return mainMotor.getCurrentAngle();
    }

    public double getSetPoint() {
        return mainMotor.getSetPoint();
    }

    public void extendArm(boolean extend) {
        DriverStation.reportWarning("High Arm cannot extend", true);
    }

    public boolean isArmExtended() {
        DriverStation.reportWarning("High Arm cannot extend", true);
        return false;
    }

    public void checkSensor() {
        if (mainMotor.isLowerAvailable() && mainMotor.isLowerReached()) {
            mainMotor.resetEncoder();
            mainMotor.stop();
        }

        if (mainMotor.isUpperAvailable() && mainMotor.isUpperReached()) {
            mainMotor.resetEncoder();
            mainMotor.stop();
        }
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Current Angle", getCurrentAngle());
        SmartDashboard.putBoolean("Lower Sensor", mainMotor.isLowerReached());
        SmartDashboard.putBoolean("Upper Sensor", mainMotor.isUpperReached());
        if(setPoint != SmartDashboard.getNumber("Set Point", getSetPoint())){
            mainMotor.setAngle(setPoint);
        }
        checkSensor();
    }
}
