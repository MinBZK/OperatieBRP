/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingssituatieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Personen die betrokken zijn bij de verwerking van een bijhoudingsplan.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface BijhoudingsplanPersoonBasis extends BrpObject {

    /**
     * Retourneert Bijhoudingsplan van Bijhoudingsplan \ Persoon.
     *
     * @return Bijhoudingsplan.
     */
    Bijhoudingsplan getBijhoudingsplan();

    /**
     * Retourneert Persoon van Bijhoudingsplan \ Persoon.
     *
     * @return Persoon.
     */
    Integer getPersoonId();

    /**
     * Retourneert Bijhoudingspartij van Bijhoudingsplan \ Persoon.
     *
     * @return Bijhoudingspartij.
     */
    Short getBijhoudingspartijId();

    /**
     * Retourneert Situatie van Bijhoudingsplan \ Persoon.
     *
     * @return Situatie.
     */
    BijhoudingssituatieAttribuut getSituatie();

}
