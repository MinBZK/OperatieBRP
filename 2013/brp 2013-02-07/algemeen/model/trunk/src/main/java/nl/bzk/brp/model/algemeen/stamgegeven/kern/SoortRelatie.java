/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Classificatie van de Relatie.
 *
 *
 *
 */
public enum SoortRelatie {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("-1", "Dummy", "Dummy"),
    /**
     * Huwelijk.
     */
    HUWELIJK("H", "Huwelijk", ""),
    /**
     * Geregistreerd partnerschap.
     */
    GEREGISTREERD_PARTNERSCHAP("G", "Geregistreerd partnerschap", ""),
    /**
     * Het betreft hier de familierechtelijke betrekking tussen Ouder(s) en Kind..
     */
    FAMILIERECHTELIJKE_BETREKKING("F", "Familierechtelijke betrekking",
            "Het betreft hier de familierechtelijke betrekking tussen Ouder(s) en Kind."),
    /**
     * Erkenning ongeboren vrucht.
     */
    ERKENNING_ONGEBOREN_VRUCHT("E", "Erkenning ongeboren vrucht", ""),
    /**
     * Ontkenning ouderschap ongeboren vrucht.
     */
    ONTKENNING_OUDERSCHAP_ONGEBOREN_VRUCHT("O", "Ontkenning ouderschap ongeboren vrucht", ""),
    /**
     * Naamskeuze ongeboren vrucht.
     */
    NAAMSKEUZE_ONGEBOREN_VRUCHT("N", "Naamskeuze ongeboren vrucht", "");

    private final String code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor SoortRelatie
     * @param naam Naam voor SoortRelatie
     * @param omschrijving Omschrijving voor SoortRelatie
     */
    private SoortRelatie(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Code van Soort relatie.
     *
     * @return Code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Soort relatie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort relatie.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
