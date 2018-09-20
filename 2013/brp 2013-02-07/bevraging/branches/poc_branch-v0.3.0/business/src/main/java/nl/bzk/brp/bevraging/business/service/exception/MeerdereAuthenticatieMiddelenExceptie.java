/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.exception;

/**
 * Runtime exceptie die optreedt als er meerdere authenticatiemiddelen worden gevonden, terwijl dit niet zou mogen
 * zijn.
 * @see nl.bzk.brp.bevraging.business.service.impl.AuthenticatieServiceImpl
 */
public class MeerdereAuthenticatieMiddelenExceptie extends RuntimeException {

    /** Serial version id. */
    private static final long serialVersionUID = 1572874452803101636L;

    /**
     * Creeert een nieuwe runtime exceptie zonder foutmeldingsbericht en zonder oorzaaksexceptie.
     */
    public MeerdereAuthenticatieMiddelenExceptie() {
        super();
    }

    /**
     * Creeert een nieuwe runtime exceptie met het opgegeven foutmeldingsbericht, maar zonder oorzaaksexceptie.
     *
     * @param message het foutmeldingsbericht.
     */
    public MeerdereAuthenticatieMiddelenExceptie(final String message) {
        super(message);
    }

    /**
     * Creeert een nieuwe runtime exceptie met het opgegeven foutmeldingsbericht en oorzaaksexceptie.
     *
     * @param message het foutmeldingsbericht.
     * @param cause de exceptie die ten grondslag lag aan deze exceptie.
     */
    public MeerdereAuthenticatieMiddelenExceptie(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creeert een nieuwe runtime exceptie met het opgegeven oorzaaksexceptie, maar zonder specifiek
     * foutmeldingsbericht.
     *
     * @param cause de exceptie die ten grondslag lag aan deze exceptie.
     */
    public MeerdereAuthenticatieMiddelenExceptie(final Throwable cause) {
        super(cause);
    }

}
