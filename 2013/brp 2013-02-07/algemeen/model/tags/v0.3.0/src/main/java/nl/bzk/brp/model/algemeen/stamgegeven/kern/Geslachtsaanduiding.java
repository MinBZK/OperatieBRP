/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * De mogelijke aanduiding van het geslacht van een Persoon.
 *
 *
 *
 */
public enum Geslachtsaanduiding {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", "Dummy"),
    /**
     * Man.
     */
    MAN("M", "Man", ""),
    /**
     * Vrouw.
     */
    VROUW("V", "Vrouw", ""),
    /**
     * Onbekend.
     */
    ONBEKEND("O", "Onbekend", "");

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor Geslachtsaanduiding
     * @param naam Naam voor Geslachtsaanduiding
     * @param omschrijving Omschrijving voor Geslachtsaanduiding
     */
    private Geslachtsaanduiding(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Code van Geslachtsaanduiding.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Geslachtsaanduiding.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Geslachtsaanduiding.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
