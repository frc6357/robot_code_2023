package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/**
 * This constructor initializes the port 5800 on the roborio for reading UDP packets.
 * Disables blocking so it can be used in periodic
 */
public class SK23Vision extends SubsystemBase
{
    private NetworkTable limelight;

    public SK23Vision()
    {
        limelight = NetworkTableInstance.getDefault().getTable("limelight");

        setDriverCamMode();
    }

    public void setDriverCamMode()
    {
        limelight.getEntry("camMode").setNumber(1);
    }

    public void setVisionMode()
    {
        limelight.getEntry("camMode").setNumber(0);
    }

}