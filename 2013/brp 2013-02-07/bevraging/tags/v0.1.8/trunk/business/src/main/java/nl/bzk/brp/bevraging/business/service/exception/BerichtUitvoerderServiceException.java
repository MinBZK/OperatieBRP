/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.exception;

/**
 * Runtime exceptie die optreedt als er onoverkomelijke fouten optreden in de
 * {@link nl.bzk.brp.bevraging.business.service.BerichtUitvoerderService}.
 */
public class BerichtUitvoerderServiceException extends RuntimeException {

    /** Serial version id. */
    private static final long serialVersionUID = -313437928544208520L;

    /**
     * Creeert een nieuwe runtime exceptie zonder foutmeldingsbericht en zonder oorzaaksexceptie.
     */
    public BerichtUitvoerderServiceException() {
        super();
    }

    /**
     * Creeert een nieuwe runtime exceptie met het opgegeven foutmeldingsbericht, maar zonder oorzaaksexceptie.
     *
     * @param message het foutmeldingsbericht.
     */
    public BerichtUitvoerderServiceException(final String message) {
        super(message);
    }

    /**
     * Creeert een nieuwe runtime exceptie met het opgegeven foutmeldingsbericht en oorzaaksexceptie.
     *
     * @param message het foutmeldingsbericht.
     * @param cause de exceptie die ten grondslag lag aan deze exceptie.
     */
    public BerichtUitvoerderServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creeert een nieuwe runtime exceptie met het opgegeven oorzaaksexceptie, maar zonder specifiek
     * foutmeldingsbericht.
     *
     * @param cause de exceptie die ten grondslag lag aan deze exceptie.
     */
    public BerichtUitvoerderServiceException(final Throwable cause) {
        super(cause);
    }

}
