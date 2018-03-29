/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.isc.opschoner.service.OpschonerService;
import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Implementatieklasse voor JMX interface voor het starten van het opschonen van tellingen.
 */
@UseDynamicDomain
@ManagedResource(objectName = "nl.bzk.migratiebrp.isc:name=OPSCHONER", description = "JMX Service voor OPSCHONEN processen en berichten")
public class OpschonerJMXImpl implements OpschonerJMX {

    @Autowired
    private OpschonerService opschonerService;

    /**
     * Standaard wachttijd voor opschonen.
     */
    public static final Integer STANDAARD_WACHT_TIJD_IN_UREN = 27;

    private int aantalUrenSindsVerwerkt = STANDAARD_WACHT_TIJD_IN_UREN;

    /**
     * Zet de waarde van aantal uren sinds verwerkt.
     * @param aantalUrenSindsVerwerkt aantal uren sinds verwerkt
     */
    public final void setAantalUrenSindsVerwerkt(final int aantalUrenSindsVerwerkt) {
        this.aantalUrenSindsVerwerkt = aantalUrenSindsVerwerkt;
    }

    @Override
    @ManagedOperation(description = "Schoon de processen en berichten op.")
    public final void opschonen() {
        opschonen(aantalUrenSindsVerwerkt);
    }

    @Override
    @ManagedOperation(description = "Schoon de processen en berichten op.")
    public final void opschonen(final int aantalUren) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -aantalUren);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        final ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> MDCProcessor.startVerwerking().run(() -> opschonerService.opschonenProcessen(new Timestamp(cal.getTimeInMillis()), aantalUren)));
    }
}
