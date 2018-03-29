/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.jobs;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.voisc.runtime.VoiscService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * Basis voisc job.
 */
public abstract class AbstractVoiscServiceJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final void execute(final JobExecutionContext jobExecutionContext) {
        MDCProcessor.startVerwerking().run(() -> {
            LOGGER.debug("Start job: " + this.getClass().getName());
            final JobDataMap jdm = jobExecutionContext.getMergedJobDataMap();
            final VoiscService voiscService = (VoiscService) jdm.get("voiscService");

            execute(jdm, voiscService);
            LOGGER.debug("Einde job: " + this.getClass().getName());
        });
    }

    /**
     * Execute methode voor de voisc service.
     * @param jdm De job data map
     * @param voiscService De uit te voeren voisc service
     */
    protected abstract void execute(JobDataMap jdm, VoiscService voiscService);
}
