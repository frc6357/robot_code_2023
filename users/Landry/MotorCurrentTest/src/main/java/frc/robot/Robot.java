
package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.filters.CubicDeadbandFilter;

/**
 * This program allows you to control a singular motor and graph
 * the correlation between the current and the velocity in order
 * to determine the force exerted on the motor through the use of
 * current.
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
    Globals.averageCurrent = (Globals.averageCurrent + motor_current) / 2;
    SmartDashboard.putNumber("Average Current", Globals.averageCurrent);
    SmartDashboard.putNumber("Current", motor_current);
  }

  @Override
  public void teleopPeriodic() {
    if (m_joystick.getRawButtonPressed(Constants.xButton)) {
      m_motor.set(Constants.Constant_Speed);// When pressed the motor turns on
    }
    if (m_joystick.getRawButtonPressed(Constants.yButton)) {
      m_motor.set(0.0); // When released the motor turns off
    }
  }
}
