/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.steps;


import java.util.Objects;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.jbehave.core.context.Context;
import org.jbehave.core.context.ContextView;
import org.jbehave.core.steps.ContextStepMonitor;
import org.jbehave.core.steps.StepMonitor;

/**
 * Context step monitor voor de BRP.
 */
public class BrpContextStepMonitor extends ContextStepMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Context context;
    private String currentScenario;

    /**
     * Constructor.
     * @param context de context
     * @param view de context view
     * @param delegate de step monitor delegate
     */
    public BrpContextStepMonitor(final Context context, final ContextView view, final StepMonitor delegate) {
        super(context, view, delegate);
        this.context = context;
    }

    @Override
    public void performing(final String step, final boolean dryRun) {
        super.performing(step, dryRun);

        if (currentScenario == null || !Objects.equals(context.getCurrentScenario(), currentScenario)) {
            currentScenario = context.getCurrentScenario();
            final String currentStory = context.getCurrentStory();

            LOGGER.info("---------------------------------");
            LOGGER.info(String.format("Start scenario: %s", currentScenario));
            LOGGER.info(String.format("van story     : %s", currentStory));
            LOGGER.info("---------------------------------");
        }
    }
}
