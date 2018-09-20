/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Stap die de duur van onderliggende stappen bijhoudt.
 */
@Component
public class TimingStap implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimingStap.class);
    private StopWatch timer;

    /**
     * constructor.
     */
    public TimingStap() {
    }

    @Override
    public boolean postprocess(final Context context, final Exception exception) {
        timer.stop();

        LOGGER.info(timer.shortSummary());
        context.put(ContextParameterNames.TIMING, timer.shortSummary());

        return CONTINUE_PROCESSING;
    }

    @Override
    public boolean execute(final Context context) throws Exception {
        timer = new StopWatch("Timing");
        timer.start();
        return CONTINUE_PROCESSING;
    }
}
