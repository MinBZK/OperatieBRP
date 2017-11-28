/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

/**
 * Exceptie die gegooid kan worden bij het maken van antwoordberichten.
 */
public class MaakAntwoordBerichtException extends RuntimeException {

    private static final long serialVersionUID = 6082989983866109797L;

    /**
     * Maakt een nieuwe MaakAntwoordBerichtException.
     * @param message de exceptiemelding
     * @param cause de oorzaak
     */
    public MaakAntwoordBerichtException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
