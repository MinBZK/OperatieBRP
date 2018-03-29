/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.jobs;

import java.util.Calendar;
import java.util.Date;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.runtime.VoiscService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;

/**
 * Job om berichten uit de database te verwijderen als ze aan bepaalde voorwaarden voldoen. 2 weken oud ('verwerktDt')
 * statussen: SENT_ISC | SENT_MAILBOX | IGNORED
 */
@DisallowConcurrentExecution
public final class OpschonenVerwerkteBerichtenJob extends AbstractVoiscServiceJob {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    protected void execute(final JobDataMap jdm, final VoiscService voiscService) {
        final int aantalUrenSindsVerwerkt = Integer.parseInt(jdm.get("aantalUrenSindsVerwerkt").toString());

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -aantalUrenSindsVerwerkt);
        final Date ouderDan = cal.getTime();

        LOG.info("Controleren op berichten van voor {}.", ouderDan);
        voiscService.opschonenVoiscBerichten(ouderDan);
    }
}
