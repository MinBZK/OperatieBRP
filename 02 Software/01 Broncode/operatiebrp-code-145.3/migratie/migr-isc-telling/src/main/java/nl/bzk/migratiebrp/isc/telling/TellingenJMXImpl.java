/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.isc.telling.runtime.TellingenRuntimeService;
import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Implementatieklasse voor JMX interface voor het starten van het bijwerken van tellingen.
 */
@UseDynamicDomain
@ManagedResource(objectName = "nl.bzk.migratiebrp.isc:name=TELLING", description = "JMX Service voor TELLING processen en berichten")
public final class TellingenJMXImpl implements TellingenJMX {

    private static final int DEFAULT_AANTAL_UREN = 27;

    private TellingenRuntimeService tellingenService;
    private int aantalUrenSindsVerwerkt = DEFAULT_AANTAL_UREN;

    /**
     * Zet de tellingen service.
     * @param tellingenService De te zetten tellingen service
     */
    @Required
    public void setTellingenService(final TellingenRuntimeService tellingenService) {
        this.tellingenService = tellingenService;
    }

    /**
     * Zet de waarde van aantal uren sinds verwerkt.
     * @param aantalUrenSindsVerwerkt aantal uren sinds verwerkt
     */
    public void setAantalUrenSindsVerwerkt(final int aantalUrenSindsVerwerkt) {
        this.aantalUrenSindsVerwerkt = aantalUrenSindsVerwerkt;
    }

    /**
     * JMX service voor het bijwerken van tellingen.
     */
    @Override
    @ManagedOperation(description = "Werk tellingen bij.")
    public void bijwerkenTellingen() {
        bijwerkenTellingen(aantalUrenSindsVerwerkt);
    }

    /**
     * JMX service voor het bijwerken van de telling met een aangepast aan uren sinds verwerking.
     * @param aantalUren het aantal uren sinds verwerking
     */
    @Override
    @ManagedOperation(description = "Werk tellingen bij (aangepaste aantal uren sinds verwerking).")
    public void bijwerkenTellingen(final int aantalUren) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -aantalUren);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        final ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> MDCProcessor.startVerwerking().run(() -> tellingenService.werkLopendeTellingenBij(new Timestamp(cal.getTimeInMillis()))));
    }
}
