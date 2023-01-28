package frc.robot.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the subsystem control values as imported from the subsystem
 * control
 * JSON file. This is made for the 2022 season
 */
public class SubsystemControls {

    private final boolean rotateArm;
    private final boolean extendArm;
    private final boolean intake;
    private final boolean vision;

    /**
     * Constructs a new SubsystemControls object with the given subsystem presence.
     * 
     * @param rotateArm
     *                  indicates if the rotating arm system is present and should
     *                  be enabled
     * @param extendArm
     *                  indicates if the arm extension system is present and should
     *                  be enabled
     * @param intake
     *                  indictes if the intake system is present and should be
     *                  enabled
     * @param vision
     *                  indicates if the vision system is present and should be
     *                  enabled
     */
    public SubsystemControls(
            @JsonProperty(required = true, value = "rotateArm") boolean rotateArm,
            @JsonProperty(required = true, value = "extendArm") boolean extendArm,
            @JsonProperty(required = true, value = "intake")    boolean intake,
            @JsonProperty(required = true, value = "vision")    boolean vision) {
        this.rotateArm = rotateArm;
        this.extendArm = extendArm;
        this.intake = intake;
        this.vision = vision;
    }

    /**
     * Returns true if the rotating arm system is indicated as present and should be
     * enabled.
     * 
     * @return true if the rotating arm system is indicated as present and should be
     *         enabled; false otherwise
     */
    public boolean isRotatingArmPresent() {
        return rotateArm;
    }

    /**
     * Returns true if the extending arm system is indicated as present and should
     * be enabled.
     * 
     * @return true if the extending arm system is indicated as present and should
     *         be enabled; false otherwise
     */
    public boolean isExtendingArmPresent() {
        return extendArm;
    }

    /**
     * Returns true if the intake system is indicated as present and should be
     * enabled.
     * 
     * @return true if the intake system is indicated as present and should be
     *         enabled; false otherwise
     */
    public boolean isIntakePresent() {
        return intake;
    }

    /**
     * Returns true if the climb system is indicated as present and should be
     * enabled.
     * 
     * @return true if the climb system is indicated as present and should be
     *         enabled; false otherwise
     */
    public boolean isVisionPresent() {
        return vision;
    }
}
