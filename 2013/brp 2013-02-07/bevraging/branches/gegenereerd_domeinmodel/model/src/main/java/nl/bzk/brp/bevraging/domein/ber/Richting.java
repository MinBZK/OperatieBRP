/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.ber;

/**
 * De richting gezien vanuit de centrale voorzieningen van de BRP.
 *
 * De centrale voorzieningen wisselen berichten uit. Berichten die vanuit de centrale voorzieningen van de BRP
 * "naar buiten gaan", hebben de richting uitgaand; berichten die de BRP binnenkomen hebben de richting "ingaand".
 */
public enum Richting {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0. Deze waarde is dan ook puur een dummy waarde en dient dan ook niet gebruikt te
     * worden.
     */
    DUMMY(null),
    /**
     * Ingaand bericht.
     */
    INGAAND("Naar de centrale voorzieningen van de BRP toe."),
    /**
     * Van de centrale voorzieningen van de BRP af.
     */
    UITGAAND("Van de centrale voorzieningen van de BRP af");

    private String naam;

    public String getNaam() {
        return this.naam;
    }

    /**
     * Private constructor ten behoeve van eenmalige initialisatie.
     *
     * @param naam de naam van het soort bericht.
     */
    private Richting(final String naam) {
        this.naam = naam;
    }
}
