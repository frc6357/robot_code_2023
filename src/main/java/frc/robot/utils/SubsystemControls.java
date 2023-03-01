package frc.robot.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the subsystem control values as imported from the subsystem control
 * JSON file. This is made for the 2022 season
 */
public class SubsystemControls
{

    private final boolean drive;
    private final boolean arm;
    private final boolean intake;
    private final boolean camera;
    private final boolean vision;

    /**
     * 
     * @param drive
     *            indicates if the drive subsystem is present and should be enabled
     * @param arm
     *            indicates if the arm system is present and should be enabled
     * @param intake
     *            indicates if the intake system is present and should be enabled
     * @param camera
     *            indicates if the driver camera is present and should be enables
     * @param vision
     *            indicates if the vision system is present and should be enabled
     */
    public SubsystemControls(
        @JsonProperty(required = true, value = "drive")     boolean drive,
        @JsonProperty(required = true, value = "arm")       boolean arm,
        @JsonProperty(required = true, value = "intake")    boolean intake,
        @JsonProperty(required = true, value = "camera")    boolean camera,
        @JsonProperty(required = true, value = "vision")    boolean vision)
    {
        this.drive = drive;
        this.arm = arm;
        this.intake = intake;
        this.camera = camera;
        this.vision = vision;
    }

    /**
     * Returns true if the drivetrain is indicated as present and should be enabled.
     * 
     * @return true if the drivatrain is indicated as present and should be enabled; false
     *         otherwise
     */
    public boolean isDrivePresent()
    {
        return drive;
    }

    /**
     * Returns true if the arm system is indicated as present and should be enabled.
     * 
     * @return true if the arm system is indicated as present and should be enabled; false
     *         otherwise
     */
    public boolean isArmPresent()
    {
        return arm;
    }

    /**
     * Returns true if the intake system is indicated as present and should be enabled.
     * 
     * @return true if the intake system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isIntakePresent()
    {
        return intake;
    }

    /**
     * Returns true if the driver camera is indicated as present and should be enabled.
     * 
     * @return true if the driver camera is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isCameraPresent()
    {
        return camera;
    }

    /**
     * Returns true if the vision system is indicated as present and should be enabled.
     * 
     * @return true if the vision system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isVisionPresent()
    {
        return vision;
    }
}
