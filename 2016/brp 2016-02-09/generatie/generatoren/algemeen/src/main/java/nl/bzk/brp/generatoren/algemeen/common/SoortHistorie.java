/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

/**
 * Een enum voor de verschillende soorten historie.
 */
public enum SoortHistorie {

    /** Geen historie. */
    GEEN('G'),
    /** Bestaansperiode. */
    BESTAANSPERIODE('P'),
    /** Formele historie. */
    FORMEEL('F'),
    /** Materiele historie (beiden). */
    MATERIEEL('B');

    private char historieVastleggen;

    /**
     * Constructor.
     *
     * @param historieVastleggen het character in het BMR voor historie vastleggen
     */
    private SoortHistorie(final char historieVastleggen) {
        this.historieVastleggen = historieVastleggen;
    }

    public char getHistorieVastleggen() {
        return historieVastleggen;
    }

}
