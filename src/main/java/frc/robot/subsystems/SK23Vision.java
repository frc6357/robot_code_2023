package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This class sets up all the functions needed to get the information from the limelight
 * for vision data and processing
 */
public class SK23Vision extends SubsystemBase
{
    /** NetworkTable containing all information sent by the limelight */
    private NetworkTable limelight;

    /**
     * Creates a vision class that interacts with the limelight vision data from the
     * NetworkTables
     */
    public SK23Vision()
    {
        limelight = NetworkTableInstance.getDefault().getTable("limelight");

        setDriverCamMode();
    }

    /**
     * @return Whether or not a vision target is present in the frame
     */
    public boolean isTargetPresent()
    {
        return limelight.getEntry("tv").getDouble(0) == 1;
    }

    /**
     * Horizontal offset from the configured crosshair to the target for that pipline.
     * 
     * @return Offset between -29.8 to 29.8 degrees
     */
    public double getHorizontalOffset()
    {
        return limelight.getEntry("tx").getDouble(0);
    }

    /**
     * Vertical offset from the configured crosshair to the target for that pipline.
     * 
     * @return Offset between -24.85 to 24.85
     */
    public double getVerticalOffset()
    {
        return limelight.getEntry("ty").getDouble(0);
    }

    /**
     * Sets the pipeline of the limelight to use the retroreflective tape on the cone
     * poles. Turns on the LED lights.
     */
    public void setPoleMode()
    {
        setVisionMode();
        limelight.getEntry("pipeline").setNumber(1);
    }

    /**
     * Sets the pipeline and settings to be used as a driver camera rather than a vision
     * processing camera
     */
    public void setDriverCamMode()
    {
        limelight.getEntry("camMode").setNumber(1);
        limelight.getEntry("pipeline").setNumber(0);
    }

    /**
     * Sets the processing mode of the limelight to be a vision processor
     */
    private void setVisionMode()
    {
        limelight.getEntry("camMode").setNumber(0);
    }

}