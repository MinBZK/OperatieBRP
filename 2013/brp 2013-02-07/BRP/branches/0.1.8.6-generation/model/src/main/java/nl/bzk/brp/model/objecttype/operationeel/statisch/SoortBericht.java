/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Een mogelijk Soort van een bericht.
 * @version 1.0.18.
 */
public enum SoortBericht {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
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

    /** De naam van het soort bericht. */
    private final String naam;

    /**
     * Constructor.
     *
     * @param naam De naam van het soort bericht.
     *
     */
    private SoortBericht(final String naam) {
        this.naam = naam;
    }

    /**
     * @return De naam van het soort bericht.
     */
    public String getNaam() {
        return naam;
    }

}
