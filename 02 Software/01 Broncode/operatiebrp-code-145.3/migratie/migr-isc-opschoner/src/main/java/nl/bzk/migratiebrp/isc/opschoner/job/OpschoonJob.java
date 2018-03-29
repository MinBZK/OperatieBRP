/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.job;

import java.sql.Timestamp;
import java.util.Calendar;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.isc.opschoner.OpschonerJMXImpl;
import nl.bzk.migratiebrp.isc.opschoner.service.OpschonerService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Main klasse voor het uitvoeren van het opschoon proces.
 */
@DisallowConcurrentExecution
public final class OpschoonJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String PARAMETER_WACHTTIJD = "wachtPeriodeInUren";

    private static final Integer STANDAARD_WACHT_TIJD_IN_UREN = OpschonerJMXImpl.STANDAARD_WACHT_TIJD_IN_UREN;

    /**
     * Main methode, de meegegeven argumenten kan alleen 'wachtPeriodeInUren' zijn.
     * @param context De meegegeven context.
     * @throws JobExecutionException In het geval er een niet afgevangen exceptie optreedt.
     */
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        MDCProcessor.startVerwerking().run(() -> executeJob(context));
    }

    private void executeJob(final JobExecutionContext context) {
        LOG.debug("Start job: " + this.getClass().getName());
        final JobDataMap jdm = context.getMergedJobDataMap();
        final OpschonerService opschonerService = (OpschonerService) jdm.get("opschonerService");
        final String wachtPeriodeInUren = (String) jdm.get(PARAMETER_WACHTTIJD);

        final Integer wachtTijdInUren;

        try {
            wachtTijdInUren =
                    wachtPeriodeInUren != null && !"".equals(wachtPeriodeInUren) ? Integer.valueOf(wachtPeriodeInUren) : STANDAARD_WACHT_TIJD_IN_UREN;
        } catch (final NumberFormatException exception) {
            LOG.error("Ongeldig aantal uren meegegeven.");
            return;
        }

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -wachtTijdInUren);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, -1);
        final Timestamp datumTot = new Timestamp(cal.getTimeInMillis());

        LOG.info("Opschonen tot en met " + cal.getTime() + ".");

        opschonerService.opschonenProcessen(datumTot, wachtTijdInUren);

        LOG.debug("Einde job: " + this.getClass().getName());
    }
}
