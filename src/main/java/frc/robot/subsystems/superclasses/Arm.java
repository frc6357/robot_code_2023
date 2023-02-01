package frc.robot.subsystems.superclasses;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Arm extends SubsystemBase {

    /**
     * Sets the motor speeds to allow for arm rotation
     * 
     * @param speed A value from -1.0 to 1.0 to set the output power and direction
     *              of the rotation
     */
    public abstract void setRotationSpeed(double speed);

    /**
     * Determines the current orientation of the arm
     * 
     * @return The angle of the arm in degrees
     */
    public abstract double getPosition();

    /**
     * Extends or retracts the arm to a set position
     * 
     * @param extend Whether or not the arm should be extended
     */
    public abstract void extendArm(boolean extend);

    /**
     * 
     * @return Whether or not the arm is in an extended position
     */
    public abstract boolean isArmExtended();

}
