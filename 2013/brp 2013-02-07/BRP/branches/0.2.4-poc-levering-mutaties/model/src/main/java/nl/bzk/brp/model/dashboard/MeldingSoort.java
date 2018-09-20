/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

/**
 * Soort foutmelding uit het response bericht.
 */
public enum MeldingSoort {

    /** Info geeft aan dat een melding puur informatief is. */
    INFORMATIE(),
    /** Een waarschuwing geeft aan dat er mogelijk een fout of aandachtspunt is, maar dat alles wel goed is gegaan. */
    WAARSCHUWING(),
    /** Een overrulebare fout geeft aan dat er iets fout is gegaan, maar dat de fout overruled kan worden. */
    OVERRULEBAAR(),
    /** Een onoverrulebare fout geeft aan dat er iets fout is gegaan en dat deze fout niet kan worden overruled. */
    FOUT(),

    /**
     * Overrulemelding.
     */
    GENEGEERDE_FOUT();

    private String naam;

    public String getNaam() {
        return naam;
    }

}
