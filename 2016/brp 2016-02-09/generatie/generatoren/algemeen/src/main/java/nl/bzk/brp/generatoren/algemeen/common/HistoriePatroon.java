/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

/**
 * Een enum voor de historie patronen.
 * Vervangt uiteindelijk {@link nl.bzk.brp.generatoren.algemeen.common.SoortHistorie}
 */
public enum HistoriePatroon {

    /** Geen historie*/
    GEEN("G"),
    /** Formele historie */
    FORMEEL("F"),
    /** Formele historie en materiele historie */
    FORMEEL_MATERIEEL("F+M"),
    /** Formele bestaansperiode */
    BESTAANSPERIODE_FORMEEL("F1"),
    /** Formele historie en (impliciet) materiele bestaansperiode */
    BESTAANSPERIODE_FORMEEL_IMPLICIETMATERIEEL("F+iM1"),
    /** Formele en materiele bestaansperiode */
    BESTAANSPERIODE_FORMEEL_MATERIEEL("F+M1"),
    /** Bestaansperiode stamgegeven */
    BESTAANSPERIODE_STAMGEGEVEN("P");

    private final String patroon;

    HistoriePatroon(final String patroon) {
        this.patroon = patroon;
    }

    public String getPatroon() {
        return patroon;
    }
}
