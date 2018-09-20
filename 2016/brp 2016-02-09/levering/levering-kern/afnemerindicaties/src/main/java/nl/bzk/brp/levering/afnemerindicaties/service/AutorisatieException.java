/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;

/**
 * Algemene authenticatiefout.
 */
public class AutorisatieException extends Exception {

    private final Regel regel;

    /**
     *
     */
    public AutorisatieException() {
        this(Regel.AUTH0001);
    }

    /**
     * AuthenticatieFout obv regel
     * @param regel de regel
     */
    public AutorisatieException(final Regel regel) {
        this.regel = regel;
    }

    public final Regel getRegel() {
        return regel;
    }
}
