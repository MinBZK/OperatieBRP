/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Gegevens over het geslacht van een persoon.
 *
 * Verplicht aanwezig bij persoon
 *
 * Beide vormen van historie: geslacht(saanduiding) kan in de werkelijkheid veranderen, dus materieel bovenop de formele
 * historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonGeslachtsaanduidingGroepBasis extends Groep {

    /**
     * Retourneert Geslachtsaanduiding van Geslachtsaanduiding.
     *
     * @return Geslachtsaanduiding.
     */
    GeslachtsaanduidingAttribuut getGeslachtsaanduiding();

}
