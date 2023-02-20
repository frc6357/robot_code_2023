package frc.robot.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the subsystem control values as imported from the subsystem control
 * JSON file. This is made for the 2022 season
 */
public class SubsystemControls
{

    private final boolean arm;
    private final boolean intake;
    private final boolean vision;

    /**
     * Constructs a new SubsystemControls object with the given subsystem presence.
     * 
     * @param arm
     *            indicates if the arm system is present and should be enabled
     * @param intake
     *            indictes if the intake system is present and should be enabled
     * @param vision
     *            indicates if the vision system is present and should be enabled
     */
    public SubsystemControls(@JsonProperty(required = true, value = "arm")
    boolean arm, @JsonProperty(required = true, value = "intake")
    boolean intake, @JsonProperty(required = true, value = "vision")
    boolean vision)
    {
        this.arm = arm;
        this.intake = intake;
        this.vision = vision;
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
