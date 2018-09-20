/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.Groep;


/**
 * Vorm van historie: alleen formeel. Immers, een persoonskaart is wel-of-niet geconverteerd, en een persoonskaart
 * 'verhuist' niet mee (c.q.: de plaats van een persoonskaart veranderd in principe niet).
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface PersoonPersoonskaartGroepBasis extends Groep {

    /**
     * Retourneert Gemeente persoonskaart van Persoonskaart.
     *
     * @return Gemeente persoonskaart.
     */
    Partij getGemeentePersoonskaart();

    /**
     * Retourneert Persoonskaart volledig geconverteerd? van Persoonskaart.
     *
     * @return Persoonskaart volledig geconverteerd?.
     */
    JaNee getIndicatiePersoonskaartVolledigGeconverteerd();

}
