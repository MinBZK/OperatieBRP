/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.exception;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import org.junit.Test;

public class NietVerwijderBareProcesInstantieExceptionTest {

    private static final String MESSAGE = "Proces kon niet worden verwijderd";
    private static final Timestamp DATUM_LAATSTE_ACTIVITEIT = new Timestamp(System.currentTimeMillis());
    private static final NullPointerException THROWABLE = new NullPointerException("Nullpointer opgetreden.");

    @Test
    public void testExceptieZonderThrowable() {
        final NietVerwijderbareProcesInstantieException exceptie =
                new NietVerwijderbareProcesInstantieException(MESSAGE, DATUM_LAATSTE_ACTIVITEIT);

        assertEquals("Message is ongelijk.", MESSAGE, exceptie.getMessage());
        assertEquals("Datum laatste activiteit is ongelijk.", DATUM_LAATSTE_ACTIVITEIT, exceptie.getLaatsteActiviteitDatum());

    }

    @Test
    public void testExceptieMetThrowable() {
        final NietVerwijderbareProcesInstantieException exceptie =
                new NietVerwijderbareProcesInstantieException(MESSAGE, THROWABLE, DATUM_LAATSTE_ACTIVITEIT);

        assertEquals("Message is ongelijk.", MESSAGE, exceptie.getMessage());
        assertEquals("Throwable is ongelijk.", THROWABLE, exceptie.getCause());
        assertEquals("Datum laatste activiteit is ongelijk.", DATUM_LAATSTE_ACTIVITEIT, exceptie.getLaatsteActiviteitDatum());

    }
}
