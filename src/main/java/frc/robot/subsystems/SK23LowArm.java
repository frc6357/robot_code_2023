// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants.LowArmConstants;
import frc.robot.Ports.LowArmPorts;
import frc.robot.subsystems.superclasses.Arm;

//
// TODO: What is this class for? Add a comment describing what it is so that people
// picking up the code can understand what it's for.
//
public abstract class SK23LowArm extends Arm {
    private CANSparkMax mainMotor = new CANSparkMax(LowArmPorts.kMainMotor, MotorType.kBrushless);
    private RelativeEncoder mainEncoder = mainMotor.getEncoder();
    private CANSparkMax followerMotor = new CANSparkMax(LowArmPorts.kFollowerMotor, MotorType.kBrushless);
    private MotorControllerGroup motors = new MotorControllerGroup(mainMotor, followerMotor);
    
  /** Creates a new ExampleSubsystem. */
  public SK23LowArm() {
    mainEncoder.setPosition(LowArmConstants.kArmPositionOffsetDegrees);
    mainEncoder.setPositionConversionFactor(LowArmConstants.kDegreesPerPulse);
    mainEncoder.setVelocityConversionFactor(LowArmConstants.kDegreesPerPulse);
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
  public abstract void extendArm(boolean extend);

  @Override
  public abstract boolean isArmExtended();
}
