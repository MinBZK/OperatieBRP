/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.basis;


/** Standaard exceptie voor fouten die optreden binnen een generator. */
public class GeneratorExceptie extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Standaard (lege) constructor. */
    public GeneratorExceptie() {
        super();
    }

    /**
     * Constructor die het bericht en de oorzaak van de exceptie zet.
     *
     * @param message het bericht/melding van de fout.
     * @param cause een onderliggende fout/exceptie die de oorzaak was van deze exceptie.
     */
    public GeneratorExceptie(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor die het bericht van de exceptie zet.
     *
     * @param message het bericht/melding van de fout.
     */
    public GeneratorExceptie(final String message) {
        super(message);
    }

    /**
     * Constructor die de oorzaak van de exceptie zet.
     *
     * @param cause een onderliggende fout/exceptie die de oorzaak was van deze exceptie.
     */
    public GeneratorExceptie(final Throwable cause) {
        super(cause);
    }

}
