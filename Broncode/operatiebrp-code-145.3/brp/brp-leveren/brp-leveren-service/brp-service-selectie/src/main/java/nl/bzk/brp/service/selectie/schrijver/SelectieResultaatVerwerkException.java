/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import nl.bzk.brp.service.selectie.algemeen.SelectieException;

/**
 * Exceptie voor verwerking van selectieresultaten.
 */
public class SelectieResultaatVerwerkException extends SelectieException {

    /**
     * Constructor.
     * @param cause oorzaak
     */
    public SelectieResultaatVerwerkException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * @param message melding
     * @param cause oorzaak
     */
    public SelectieResultaatVerwerkException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
