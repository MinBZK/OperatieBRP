/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * Exceptie wrapper voor fouten die optreden in verwerkingstappen.
 */
public class StapException extends Exception {

    private static final long serialVersionUID = 4315790859178034811L;

    /**
     * @param message message
     */
    public StapException(final String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause cause
     */
    public StapException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause cause
     */
    public StapException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creert een nieuwe melding obv een algemene foutmelding.
     * @return een melding object.
     */
    public final Melding maakFoutMelding() {
        return maakAlgemeneFout();
    }

    /**
     * Creert een nieuwe melding obv een algemene foutmelding.
     * @return een melding object.
     */
    public static Melding maakAlgemeneFout() {
        return new Melding(SoortMelding.FOUT, Regel.ALG0001, Regel.ALG0001.getMelding());
    }

}
