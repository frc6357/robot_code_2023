package frc.robot.subsystems;

import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.subsystems.general_subsystems.Arm;

public class SK23HighArm extends Arm{

    private CANSparkMax mainMotor = new CANSparkMax(0, MotorType.kBrushless);
    private CANSparkMax followerMotor = new CANSparkMax(0, MotorType.kBrushless);
    private MotorControllerGroup motors = new MotorControllerGroup(mainMotor, followerMotor);

    public SK23HighArm() {}

    @Override
    public void setRotationSpeed(double speed) {
        motors.set(speed);
    }

    @Override
    public double getPosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void extendArm(boolean extend) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isArmExtended() {
        return false;
    }
}
