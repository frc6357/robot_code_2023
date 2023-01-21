package frc.robot.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the subsystem control values as imported from the subsystem control
 * JSON file. This is made for the 2022 season
 */
public class SubsystemControls
{

    private final boolean intake;
    private final boolean launcher;
    private final boolean transfer;
    private final boolean complexClimb;
    private final boolean simpleClimb;
    private final boolean vision;
    private final boolean gearshift;
    private final boolean climbtest;

    /**
     * Constructs a new SubsystemControls object with the given subsystem presence.
     * 
     * @param intake
     *            indicates if the intake system is present and should be enabled
     * @param launcher
     *            indicates if the launcher system is present and should be enabled
     * @param transfer
     *            indictes if the indexer system is present and should be enabled
     * @param complexClimb
     *            indicates if the complexClimb system is present and should be enabled
     * @param simpleClimb
     *            indicates if the simpleClimb system is present and should be enabled
     * @param vision
     *           indicates if the vision system is present and should be enabled
     * @param gearshift
     *           indicates if the gearshift system is present and should be enabled
     * @param climbtest 
     * indicates if the climb subsystem is in testing, activates buttons for each step
     */
    public SubsystemControls(@JsonProperty(required = true, value = "intake")
                                boolean intake,
                            @JsonProperty(required = true, value = "transfer")
                                boolean transfer,
                            @JsonProperty(required = true, value = "launcher")
                                boolean launcher,
                            @JsonProperty(required = true, value = "complexClimb")
                                boolean complexClimb,
                            @JsonProperty(required = true, value = "simpleClimb")
                                boolean simpleClimb,
                            @JsonProperty(required = true, value = "vision")
                                boolean vision,
                            @JsonProperty(required = true, value = "gearshift")
                                boolean gearshift,
                            @JsonProperty(required = true, value = "climbtest")
                                boolean climbtest)
    {
        this.intake = intake;
        this.launcher = launcher;
        this.transfer = transfer;
        this.complexClimb = complexClimb;
        this.simpleClimb = simpleClimb;
        this.vision = vision;
        this.gearshift = gearshift;
        this.climbtest = climbtest;
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
     * Returns true if the launcher system is indicated as present and should be enabled.
     * 
     * @return true if the launcher system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isLauncherPresent()
    {
        return launcher;
    }

    /**
     * Returns true if the transfer system is indicated as present and should be enabled.
     * 
     * @return true if the transfer system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isTransferPresent()
    {
        return transfer;
    }

    /**
     * Returns true if the complexClimb system is indicated as present and should be enabled.
     * 
     * @return true if the complexClimb system is indicated as present and should be enabled;
     *         false otherwise
     */
        public boolean isComplexClimbPresent()
    {
        return complexClimb;
    }

    /**
     * Returns true if the climb system is indicated as present and should be enabled.
     * 
     * @return true if the climb system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isSimpleClimbPresent()
    {
        return simpleClimb;
    }

    /**
     * Returns true if the climb system is indicated as present and should be enabled.
     * 
     * @return true if the climb system is indicated as present and should be enabled;
     *         false otherwise
     */
        public boolean isVisionPresent()
    {
        return vision;
    }

    /**
     * Returns true if the drivetrain geatshift system is indicated as present and should be enabled.
     * 
     * @return true if the gearshift system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isGearshiftPresent()
    {
        return gearshift;
    }

    /**
     * Returns true if the climbtest system is indicated as present and should be enabled.
     * 
     * @return true if the climbtest system is indicated as present and should be enabled;
     *         false otherwise
     */
    public boolean isClimbtestPresent()
    {
        return climbtest;
    }
}
