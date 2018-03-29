/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Log class om aan te geven welke LO3 elementen nieuw, verwijderd of gewijzigd zijn.
 */
public class Lo3VerschillenLog {

    private final List<String> nieuweElementen;
    private final List<String> verwijderdeElementen;
    private final List<String> gewijzigdeElementen;

    /**
     * Defaul contructor.
     */
    public Lo3VerschillenLog() {
        nieuweElementen = new ArrayList<>();
        verwijderdeElementen = new ArrayList<>();
        gewijzigdeElementen = new ArrayList<>();
    }

    /**
     * Geef de waarde van nieuwe elementen.
     * @return the nieuweElementen
     */
    public final List<String> getNieuweElementen() {
        return nieuweElementen;
    }

    /**
     * Geef de waarde van verwijderde elementen.
     * @return the verwijderdeElementen
     */
    public final List<String> getVerwijderdeElementen() {
        return verwijderdeElementen;
    }

    /**
     * Geef de waarde van gewijzigde elementen.
     * @return the gewijzigdeElementen
     */
    public final List<String> getGewijzigdeElementen() {
        return gewijzigdeElementen;
    }

    /**
     * Voegt een nieuw element toe.
     * @param element String element
     */
    public final void addNieuwElement(final Lo3ElementEnum element) {
        nieuweElementen.add(element.getElementNummer());
    }

    /**
     * Voegt een gewijzigd element toe.
     * @param element String element
     */
    public final void addGewijzigdElement(final Lo3ElementEnum element) {
        gewijzigdeElementen.add(element.getElementNummer());
    }

    /**
     * Voegt een verwijderd element toe.
     * @param element String element
     */
    public final void addVerwijderdElement(final Lo3ElementEnum element) {
        verwijderdeElementen.add(element.getElementNummer());
    }
}
