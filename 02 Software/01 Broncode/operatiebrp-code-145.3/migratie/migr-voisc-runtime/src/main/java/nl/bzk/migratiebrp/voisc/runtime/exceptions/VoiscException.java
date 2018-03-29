/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.exceptions;

/**
 * Basis van VOISC fouten.
 */
public class VoiscException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param foutmelding foutmelding
     */
    public VoiscException(final String foutmelding) {
        super(foutmelding);
    }

    /**
     * Constructor.
     * @param foutmelding foutmelding
     * @param oorzaak oorzaak
     */
    public VoiscException(final String foutmelding, final Throwable oorzaak) {
        super(foutmelding, oorzaak);
    }
}
