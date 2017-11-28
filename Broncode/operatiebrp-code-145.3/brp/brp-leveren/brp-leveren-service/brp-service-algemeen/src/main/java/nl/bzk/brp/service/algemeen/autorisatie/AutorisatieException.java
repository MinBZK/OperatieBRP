/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import nl.bzk.brp.domain.algemeen.Melding;

/**
 * AutorisatieExceptie wordt gegooid als het autoriseren faalt.
 */
public final class AutorisatieException extends Exception {
    private static final long serialVersionUID = 5491597007178514351L;

    private final Melding melding;

    /**
     * Constructor.
     * @param melding de foutmeldig die optreedt tijdens autoriseren
     */
    public AutorisatieException(final Melding melding) {
        super(String.format("Autorisatiefout opgetreden: %s - %s", melding.getRegel().getCode(), melding.getRegel().getMelding()));
        this.melding = melding;
    }

    /**
     * @return de foutmelding
     */
    public Melding getMelding() {
        return melding;
    }
}
