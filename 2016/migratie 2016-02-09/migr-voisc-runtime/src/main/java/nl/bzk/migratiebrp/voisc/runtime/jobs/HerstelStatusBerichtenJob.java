/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.jobs;

import java.util.Calendar;
import java.util.Date;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import nl.bzk.migratiebrp.voisc.runtime.VoiscService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * Herstel berichten.
 */
@DisallowConcurrentExecution
public final class HerstelStatusBerichtenJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) {
        try (MDCCloser verwerkingCloser = MDC.startVerwerking()) {
            LOGGER.debug("Start job: " + this.getClass().getName());
            final JobDataMap jdm = jobExecutionContext.getMergedJobDataMap();
            final VoiscService voiscService = (VoiscService) jdm.get("voiscService");
            final int aantalUrenSindsVerwerkt = Integer.parseInt(jdm.get("aantalUrenSindsInVerwerking").toString());

            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR_OF_DAY, -aantalUrenSindsVerwerkt);
            final Date ouderDan = cal.getTime();
            voiscService.herstellenVoiscBerichten(ouderDan);
            LOGGER.debug("Einde job: " + this.getClass().getName());
        }
    }
}
