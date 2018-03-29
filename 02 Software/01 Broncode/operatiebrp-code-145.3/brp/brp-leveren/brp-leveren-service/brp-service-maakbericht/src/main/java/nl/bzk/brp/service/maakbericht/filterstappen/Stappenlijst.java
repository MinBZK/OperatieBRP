/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.Arrays;

/**
 * Stappenlijst bevat de lijst met maak bericht stappen.
 */
public final class Stappenlijst {

    private final MaakBerichtStap[] stappen;

    /**
     * Constructor.
     * @param stappen lijst met stappen.
     */
    public Stappenlijst(final MaakBerichtStap... stappen) {
        this.stappen = stappen;
    }

    /**
     * @return de stappen
     */
    public MaakBerichtStap[] getStappen() {
        return Arrays.copyOf(stappen, stappen.length);
    }
}
