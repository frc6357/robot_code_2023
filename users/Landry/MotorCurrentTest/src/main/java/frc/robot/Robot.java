// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.filters.CubicDeadbandFilter;


/**
 * This sample program shows how to control a motor using a joystick. In the operator control part
 * of the program, the joystick is read and the value is written to the motor.
 *
 * <p>Joystick analog values range from -1 to 1 and motor controller inputs also range from -1 to 1
 * making it easy to work together.
 *
 * <p>In addition, the encoder value of an encoder connected to ports 0 and 1 is consistently sent
 * to the Dashboard.
 */
public class Robot extends TimedRobot {
  private static final int kMotorPort = 40;
  private static final int kJoystickPort = 0;

  private CANSparkMax m_motor;
  private FilteredJoystick m_joystick;
  private RelativeEncoder m_encoder;

  @Override
  public void robotInit() {
    m_motor = new CANSparkMax(kMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    m_joystick = new FilteredJoystick(kJoystickPort);
    m_joystick.setFilter(1, new CubicDeadbandFilter(1.0, 0.1, 1.0, true));
    m_encoder = m_motor.getEncoder();
    final JoystickButton xButton = new JoystickButton(m_joystick,Constants.xButton);
    final JoystickButton yButton = new JoystickButton(m_joystick,Constants.yButton);
    
  }

  /*
   * The RobotPeriodic function is called every control packet no matter the
   * robot mode.
   */
  @Override
  public void robotPeriodic() {
    double motor_velocity = m_encoder.getVelocity();
    SmartDashboard.putNumber("Velocity", motor_velocity);
    double motor_current = m_motor.getOutputCurrent();
    SmartDashboard.putNumber("Current", motor_current);
  }

  @Override
  public void teleopPeriodic() {
    m_motor.set(Constants.Constant_Speed);
    //TODO - add start/stop button for setting motor speed
  }
}
