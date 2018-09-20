/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;

/**
 * Enumeratie die de soort van een {@link Melding} aangeeft.
 */
public enum SoortMelding {

    /** Info geeft aan dat een melding puur informatief is. */
    INFO("I"),
    /** Een waarschuwing geeft aan dat er mogelijk een fout of aandachtspunt is, maar dat alles wel goed is gegaan. */
    WAARSCHUWING("W"),
    /** Een overrulebare fout geeft aan dat er iets fout is gegaan, maar dat de fout overruled kan worden. */
    FOUT_OVERRULEBAAR("O"),
    /** Een onoverrulebare fout geeft aan dat er iets fout is gegaan en dat deze fout niet kan worden overruled. */
    FOUT_ONOVERRULEBAAR("F");

    private final String code;

    /**
     * Standaard constructor die direct de code van de soort melding initialiseert.
     *
     * @param code de code van de melding soort.
     */
    private SoortMelding(final String code) {
        this.code = code;
    }

    /**
     * Retourneert de code van de melding soort.
     *
     * @return de code van de melding soort.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert de naam van de melding soort.
     *
     * @return de naam van de melding soort.
     */
    public String getNaam() {
        return this.name();
    }

}
