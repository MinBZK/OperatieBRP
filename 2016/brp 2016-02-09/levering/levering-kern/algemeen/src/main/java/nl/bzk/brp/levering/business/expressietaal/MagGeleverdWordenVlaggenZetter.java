/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.expressietaal;

import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.model.basis.Attribuut;

/**
 * Dit is de interface die de MagGeleverdWorden vlaggen zet.
 */
public interface MagGeleverdWordenVlaggenZetter {

    /**
     * Zet de magGeleverdWorden vlaggen voor alle elementen die de expressie teruggeeft. Dit gebeurt recursief, omdat
     * onder een expressie een lijst van expressies kan hangen.
     *
     * @param element De expressie.
     * @param waarde  De waarde waarop de vlaggen gezet moeten worden.
     * @return Een lijst van alle geraakte attributen.
     */
    List<Attribuut> zetMagGeleverdWordenVlaggenOpWaarde(final Expressie element, final boolean waarde);
}
