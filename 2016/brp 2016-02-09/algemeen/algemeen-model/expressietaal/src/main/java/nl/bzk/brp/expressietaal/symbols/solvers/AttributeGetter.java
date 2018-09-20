/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols.solvers;

import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Generiek interface voor classes die specifieke attributen of attribuutwaarden uit een (root)object ophalen. Voor elk attribuut (in Attributes) moet een
 * AttributeGetter bestaan.
 */
public interface AttributeGetter {

    /**
     * Haalt de waarde van een attribuut op uit het BRP-object.
     *
     * @param brpObject Object waaruit de waarde opgehaald moet worden.
     * @return De waarde van het attribuut of NULL, indien het attribuut niet bestaat.
     */
    Expressie getAttribuutWaarde(final BrpObject brpObject);

    /**
     * Haalt een attribuut op uit het BRP-object.
     *
     * @param brpObject Object waaruit het attribuut opgehaald moet worden.
     * @return Het attribuut of NULL, indien niet gevonden.
     */
    Attribuut getAttribuut(final BrpObject brpObject);

    /**
     * Geeft een lijst van alle 'historische' attributen van het BRP-object, indien beschikbaar.
     *
     * @param brpObject Object waaruit de attributen opgehaald moet worden.
     * @return Lijst van gevonden historische attributen, of NULL indien niet van toepassing.
     */
    List<Attribuut> getHistorischeAttributen(final BrpObject brpObject);
}
