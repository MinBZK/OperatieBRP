/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

/**
 * Runtime exceptie, voor wanneer evaluatie fout gaat.
 */
public class ExpressieRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -7449167885429714642L;

    /**
     * Evaluatiefoutmelding "Incorrecte expressie".
     * Tijdens de evaluatie is een incorrecte expressie ontstaan, bijvoorbeeld omdat een functie met de verkeerde
     * argumenten wordt aangeroepen. Het gaat om fouten die niet tijdens parsing ontdekt zijn.
     */
    public static final String INCORRECTE_EXPRESSIE = "Incorrecte expressie";

    /**
     * Constructor met melding.
     *
     * @param melding de foutmelding
     */
    public ExpressieRuntimeException(final String melding) {
        super(melding);
    }
}
