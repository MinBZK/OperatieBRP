/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Resultaat;

/**
 * Deze interface bevat statische factory methods voor het construeren van resultaat objecten.
 */
public interface Resultaten {
    /**
     * Maakt een resultaat object adhv een AntwoordBerichtResultaat.
     * @param resultaat AntwoordBerichtResultaat
     * @param args kunnen gebruikt worden om placeholders in de resultaat omschrijving te vervangen
     * @return resultaat object
     */
    static Resultaat of(final AntwoordBerichtResultaat resultaat, final String... args) {
        Resultaat r = new Resultaat();
        r.setLetter(resultaat.getLetter());
        r.setCode(resultaat.getCode());
        r.setOmschrijving(resultaat.getOmschrijving(args));
        return r;
    }
}
