package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SK23Intake;

public class AutoExtendCommand extends CommandBase
{
    private SK23Intake intake;

    public AutoExtendCommand(SK23Intake intake)
    {
        this.intake = intake;
    }

    @Override
    public void initialize()
    {
        intake.extendIntake();
    }

    @Override
    public boolean isFinished()
    {
        return intake.isExtended();
    }
}
