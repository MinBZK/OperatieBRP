/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Een mogelijk Soort van een bericht..
 */
public enum SoortBericht {

    /** DUMMY. */
    DUMMY(""),
    /** Afstamming InschrijvingAangifteGeboorte Bijhouding v0100. */
    AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING_V0100("Afstamming InschrijvingAangifteGeboorte Bijhouding v0100"),
    /** Afstamming InschrijvingAangifteGeboorte BijhoudingResponse v0100. */
    AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDINGRESPONSE_V0100("Afstamming InschrijvingAangifteGeboorte BijhoudingResponse v0100"),
    /** Migratie Verhuizing Bijhouding v0100. */
    MIGRATIE_VERHUIZING_BIJHOUDING_V0100("Migratie Verhuizing Bijhouding v0100"),
    /** Migratie Verhuizing BijhoudingResponse v0100. */
    MIGRATIE_VERHUIZING_BIJHOUDINGRESPONSE_V0100("Migratie Verhuizing BijhoudingResponse v0100"),
    /** OpvragenPersoonVraag. */
    OPVRAGENPERSOONVRAAG("OpvragenPersoonVraag"),
    /** OpvragenPersoonAntwoord. */
    OPVRAGENPERSOONANTWOORD("OpvragenPersoonAntwoord");

    /** naam. */
    private String naam;

    /**
     * Constructor.
     *
     * @param naam de naam
     *
     */
    private SoortBericht(final String naam) {
        this.naam = naam;
    }

    /**
     * De naam van het soort bericht..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

}
