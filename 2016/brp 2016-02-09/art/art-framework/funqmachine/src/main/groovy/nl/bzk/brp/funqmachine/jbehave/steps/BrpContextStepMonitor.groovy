package nl.bzk.brp.funqmachine.jbehave.steps

import org.apache.commons.lang.StringUtils
import org.jbehave.core.context.Context
import org.jbehave.core.context.ContextView
import org.jbehave.core.steps.ContextStepMonitor
import org.jbehave.core.steps.StepMonitor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class BrpContextStepMonitor extends ContextStepMonitor {
    private Logger logger = LoggerFactory.getLogger(BrpContextStepMonitor)

    private final Context context;
    private final ContextView view;
    private String currentScenario;

    public BrpContextStepMonitor(final Context context, final ContextView view, final StepMonitor delegate) {
        super(context, view, delegate);
        this.context = context;
        this.view = view;
    }

    @Override
    public void performing(final String step, final boolean dryRun) {
        super.performing(step, dryRun);

        if (currentScenario == null || !context.getCurrentScenario().equals(currentScenario)) {
            currentScenario = context.getCurrentScenario();
            String currentStory = context.getCurrentStory();

            logger.debug '---------------------------------'
            logger.debug 'Start scenario: {}', StringUtils.chomp(currentScenario)
            logger.debug 'van story     : {}', StringUtils.chomp(currentStory)
            logger.debug '---------------------------------'

        }
    }
}
