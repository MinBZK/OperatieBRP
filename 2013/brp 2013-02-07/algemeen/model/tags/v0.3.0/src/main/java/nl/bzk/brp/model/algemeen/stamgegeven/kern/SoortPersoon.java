/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Classificatie van Personen.
 *
 * Zie ook toelichting bij het attribuut Soort, bij Persoon.
 *
 *
 *
 */
public enum SoortPersoon {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", "Dummy"),
    /**
     * Ingeschrevene.
     */
    INGESCHREVENE("I", "Ingeschrevene", ""),
    /**
     * Niet ingeschrevene.
     */
    NIET_INGESCHREVENE("N", "Niet ingeschrevene", ""),
    /**
     * Alternatieve realiteit.
     */
    ALTERNATIEVE_REALITEIT("A", "Alternatieve realiteit", "");

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor SoortPersoon
     * @param naam Naam voor SoortPersoon
     * @param omschrijving Omschrijving voor SoortPersoon
     */
    private SoortPersoon(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Code van Soort persoon.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Soort persoon.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort persoon.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
