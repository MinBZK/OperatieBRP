/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De wijze waarop een Partij betrokken is bij een Onderzoek.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PartijOnderzoekBasis extends BrpObject {

    /**
     * Retourneert Partij van Partij \ Onderzoek.
     *
     * @return Partij.
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Onderzoek van Partij \ Onderzoek.
     *
     * @return Onderzoek.
     */
    Onderzoek getOnderzoek();

    /**
     * Retourneert Standaard van Partij \ Onderzoek.
     *
     * @return Standaard.
     */
    PartijOnderzoekStandaardGroep getStandaard();

}
