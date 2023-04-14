package frc.robot.bindings;

import static frc.robot.Constants.OIConstants.*;
import static frc.robot.Ports.OperatorPorts.*;
// import static frc.robot.AutoTools.GridPositions.*;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ModuleConstants;
import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.CenterPoleCommand;
import frc.robot.commands.DefaultSwerveCommand;
import frc.robot.commands.DriveTurnCommand;
// import frc.robot.commands.OnTheFlyCommand;
import frc.robot.subsystems.SK23Drive;
import frc.robot.subsystems.SK23Vision;
import frc.robot.utils.filters.CubicDeadbandFilter;
import frc.robot.utils.filters.Filter;

public class SK23DriveBinder implements CommandBinder
{
    Optional<SK23Drive>  subsystem;
    Optional<SK23Vision> vision;

    // Driver Buttons
    private final Trigger robotCentric;
    private final Trigger autoBalance;
    private final Trigger rotateDSS;
    private final Trigger rotateGrid;
    private final Trigger rotateLeft;
    private final Trigger rotateRight;

    // Gyro Reset Buttons
    private final Trigger resetGyroDSS;
    private final Trigger resetGyroGrid;
    private final Trigger resetGyroLeft;
    private final Trigger resetGyroRight;

    // Buttons for On The Fly Driving
    // private final Trigger GridLeftModifier;
    // private final Trigger GridRightModifier;
    // private final Trigger GPLeftButton;
    // private final Trigger GPMiddleButton;
    // private final Trigger GPRightButton;

    private final Trigger slowmode;

    /**
     * The class that is used to bind all the commands for the drive subsystem
     * 
     * @param subsystem
     *            The required drive subsystem for the commands
     */
    public SK23DriveBinder(Optional<SK23Drive> subsystem, Optional<SK23Vision> vision)
    {
        this.subsystem  = subsystem;
        this.vision = vision;

        resetGyroDSS    = kResetGyroDSS.button;
        resetGyroGrid   = kResetGyroGrid.button;
        resetGyroLeft   = kResetGyroLeft.button;
        resetGyroRight  = kResetGyroRight.button;

        robotCentric    = kRobotCentricMode.button;
        autoBalance     = kAutoLevel.button;

        rotateDSS       = kRotateDSS.button;
        rotateGrid      = kRotateGrid.button;
        rotateLeft      = kRotateLeft.button;
        rotateRight     = kRotateRight.button;

        // GridLeftModifier    = kGridLeftModifier.button;
        // GridRightModifier   = kGridRightModifier.button;
        // GPLeftButton        = kGPLeftButton.button;
        // GPMiddleButton      = kGPMiddleButton.button;
        // GPRightButton       = kGPRightButton.button;

        slowmode = kSlowMode.button;
    }

    public void bindButtons()
    {
        if (subsystem.isPresent())
        {
            SK23Drive drive = subsystem.get();

            // Sets filters for driving axes
            kVelocityXPort.setFilter(new CubicDeadbandFilter(kDriveCoeff,
                kJoystickDeadband, DriveConstants.kMaxSpeedMetersPerSecond, true));

            kVelocityYPort.setFilter(new CubicDeadbandFilter(kDriveCoeff,
                kJoystickDeadband, DriveConstants.kMaxSpeedMetersPerSecond, true));

            kVelocityOmegaPort.setFilter(new CubicDeadbandFilter(kRotationCoeff, kJoystickDeadband,
                Math.toRadians(ModuleConstants.kMaxModuleAngularSpeedDegreesPerSecond), true));

            slowmode.
                onTrue(new InstantCommand(() -> {setGainCommand(kSlowModePercent);}, drive))
                .onFalse(new InstantCommand(() -> {setGainCommand(1);}, drive));

            // Resets gyro angles
            resetGyroDSS.onTrue(new InstantCommand(() -> {drive.setHeading(0);}));
            resetGyroGrid.onTrue(new InstantCommand(() -> {drive.setHeading(180);}));
            resetGyroLeft.onTrue(new InstantCommand(() -> {drive.setHeading(90);}));
            resetGyroRight.onTrue(new InstantCommand(() -> {drive.setHeading(270);}));

            // Advanced features
            autoBalance.whileTrue(new AutoBalanceCommand(
                () -> kVelocityOmegaPort.getFilteredAxis(), drive));
            rotateDSS.whileTrue(
                new DriveTurnCommand(
                    () -> kVelocityXPort.getFilteredAxis(),
                    () -> kVelocityYPort.getFilteredAxis(),
                    robotCentric::getAsBoolean, 0, drive));
            rotateGrid.and(robotCentric.negate()).whileTrue(
                new DriveTurnCommand(
                    () -> kVelocityXPort.getFilteredAxis(),
                    () -> kVelocityYPort.getFilteredAxis(),
                    robotCentric::getAsBoolean, 180, drive));
            rotateLeft.whileTrue(
                new DriveTurnCommand(
                    () -> kVelocityXPort.getFilteredAxis(),
                    () -> kVelocityYPort.getFilteredAxis(),
                    robotCentric::getAsBoolean, 90, drive));
            rotateRight.whileTrue(
                new DriveTurnCommand(
                    () -> kVelocityXPort.getFilteredAxis(),
                    () -> kVelocityYPort.getFilteredAxis(),
                    robotCentric::getAsBoolean, 270, drive));

            // Default command for driving
            drive.setDefaultCommand(
                new DefaultSwerveCommand(
                    () -> kVelocityXPort.getFilteredAxis(),
                    () -> kVelocityYPort.getFilteredAxis(),
                    () -> kVelocityOmegaPort.getFilteredAxis(),
                    robotCentric::getAsBoolean, drive));

            if(vision.isPresent())
            {
                SK23Vision limelight = vision.get();

                rotateGrid.and(robotCentric).whileTrue(
                    new CenterPoleCommand(
                        () -> kVelocityXPort.getFilteredAxis(),
                        kVisionOverride.button::getAsBoolean,
                        drive,
                        limelight));
            }

            // configureOTFCommands(drive);
        }
    }

    /**
     * Creates all the on the fly command bindings
     * 
     * @param drive The drive subsystem requirement for the commands.
     */
    // private void configureOTFCommands(SK23Drive drive)
    // {
    //     GridLeftModifier.and(GPLeftButton).whileTrue(new OnTheFlyCommand(LeftGrid_LeftCone, drive));
    //     GridLeftModifier.and(GPMiddleButton).whileTrue(new OnTheFlyCommand(LeftGrid_MiddleCube, drive));
    //     GridLeftModifier.and(GPRightButton).whileTrue(new OnTheFlyCommand(LeftGrid_RightCone, drive));

    //     GridLeftModifier.and(GridRightModifier).and(GPLeftButton).whileTrue(new OnTheFlyCommand(MiddleGrid_LeftCone, drive));
    //     GridLeftModifier.and(GridRightModifier).and(GPMiddleButton).whileTrue(new OnTheFlyCommand(MiddleGrid_MiddleCube, drive));
    //     GridLeftModifier.and(GridRightModifier).and(GPRightButton).whileTrue(new OnTheFlyCommand(MiddleGrid_RightCone, drive));

    //     GridRightModifier.and(GPLeftButton).whileTrue(new OnTheFlyCommand(RightGrid_LeftCone, drive));
    //     GridRightModifier.and(GPMiddleButton).whileTrue(new OnTheFlyCommand(RightGrid_MiddleCube, drive));
    //     GridRightModifier.and(GPRightButton).whileTrue(new OnTheFlyCommand(RightGrid_RightCone, drive));
    // }

    /**
     * Sets the gains on the filters for the joysticks
     * 
     * @param percent
     *            The percent value of the full output that should be allowed (value
     *            should be between 0 and 1)
     */
    public void setGainCommand(double percent)
    {
        Filter translation = new CubicDeadbandFilter(kDriveCoeff, kJoystickDeadband,
            DriveConstants.kMaxSpeedMetersPerSecond * percent, true);
        kVelocityXPort.setFilter(translation);
        kVelocityYPort.setFilter(translation);

        Filter rotation = new CubicDeadbandFilter(kDriveCoeff, kJoystickDeadband,
            Math.toRadians(DriveConstants.kMaxRotationDegreesPerSecond) * percent, true);
        kVelocityOmegaPort.setFilter(rotation);
    }
}
