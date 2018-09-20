/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols.solvers;

import java.util.List;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.Groep;

/**
 * Generiek interface voor classes die specifieke groepen uit een (root)object ophalen. Voor elke groep moet een
 * GroepGetter bestaan.
 */
public interface GroepGetter {

    /**
     * Haalt de groep op uit het BRP-object.
     *
     * @param brpObject Object waaruit de groep opgehaald moet worden.
     * @return De groep of NULL, indien niet gevonden.
     */
    Groep getGroep(final BrpObject brpObject);

    /**
     * Geeft een lijst van alle 'historische' groepen van het BRP-object, indien beschikbaar.
     *
     * @param brpObject Object waaruit de groepen opgehaald moet worden.
     * @return Lijst van gevonden historische groepen, of NULL indien niet van toepassing.
     */
    List<Groep> getHistorischeGroepen(final BrpObject brpObject);
}
