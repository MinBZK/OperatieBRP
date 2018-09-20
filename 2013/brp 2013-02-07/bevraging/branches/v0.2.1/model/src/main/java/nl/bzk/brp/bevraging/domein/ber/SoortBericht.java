/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.ber;

/**
 * Soort bericht.
 */
public enum SoortBericht {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0. Deze waarde is dan ook puur een dummy waarde en dient dan ook niet gebruikt te
     * worden.
     */
    DUMMY(null),
    /**
     * OpvragenPersoonAntwoord bericht.
     */
    OPVRAGEN_PERSOON_VRAAG("OpvragenPersoonVraag"),
    /**
     * OpvragenPersoonAntwoord bericht.
     */
    OPVRAGEN_PERSOON_ANTWOORD("OpvragenPersoonAntwoord");

    private String naam;

    public String getNaam() {
        return this.naam;
    }

    /**
     * Private constructor ten behoeve van eenmalige initialisatie.
     *
     * @param naam de naam van het soort bericht.
     */
    private SoortBericht(final String naam) {
        this.naam = naam;
    }
}
