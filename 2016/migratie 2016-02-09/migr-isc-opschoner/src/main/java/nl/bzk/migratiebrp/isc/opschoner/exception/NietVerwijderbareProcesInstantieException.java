/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.exception;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Exceptieklasse voor de exceptie die wordt gegooid wanneer een proces instantie niet verwijderbaar is.
 * 
 */
public final class NietVerwijderbareProcesInstantieException extends Exception {

    private static final long serialVersionUID = 1L;

    private final Timestamp laatsteActiviteitDatum;

    /**
     * Constructor.
     * 
     * @param message
     *            message
     * @param laatsteActiviteitDatum
     *            laatsteActiviteitDatum
     */
    public NietVerwijderbareProcesInstantieException(final String message, final Timestamp laatsteActiviteitDatum) {
        super(message);
        this.laatsteActiviteitDatum = Kopieer.timestamp(laatsteActiviteitDatum);
    }

    /**
     * Constructor.
     * 
     * @param message
     *            message
     * @param cause
     *            cause
     * @param laatsteActiviteitDatum
     *            laatsteActiviteitDatum
     */
    public NietVerwijderbareProcesInstantieException(final String message, final Throwable cause, final Timestamp laatsteActiviteitDatum) {
        super(message, cause);
        this.laatsteActiviteitDatum = Kopieer.timestamp(laatsteActiviteitDatum);
    }

    /**
     * Geef de waarde van laatste activiteit datum.
     *
     * @return laatste activiteit datum
     */
    public Timestamp getLaatsteActiviteitDatum() {
        return Kopieer.timestamp(laatsteActiviteitDatum);
    }
}
