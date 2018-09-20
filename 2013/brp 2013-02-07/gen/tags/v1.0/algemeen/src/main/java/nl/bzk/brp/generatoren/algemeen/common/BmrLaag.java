/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

/** Enumeratie van de lagen die het BMR onderkent. */
public enum BmrLaag {

    /** Logische laag. */
    LOGISCH(1749),
    /** Operationele laag. */
    OPERATIONEEL(1751);

    private int waardeInBmr;

    /**
     * Constructor die de waarde (unieke id) van de laag in het BMR zet.
     *
     * @param waardeInBmr de waarde (unieke id) van de laag in het BMR.
     */
    private BmrLaag(final int waardeInBmr) {
        this.waardeInBmr = waardeInBmr;
    }

    /**
     * Retourneert de waarde (unieke id) van de laag in het BMR.
     *
     * @return de waarde (unieke id) van de laag in het BMR.
     */
    public int getWaardeInBmr() {
        return waardeInBmr;
    }
}
