package frc.robot.subsystems.general_subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Arm extends SubsystemBase{
    
    public abstract void setRotationSpeed(double speed);

    public abstract double getPosition();

    public abstract void extendArm(boolean extend);

    public abstract boolean isArmExtended();



}
