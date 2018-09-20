/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;


/**
 * Indicaties bij een persoon.
 *
 *
 *
 */
public interface PersoonIndicatieBasis extends ObjectType {

    /**
     * Retourneert Persoon van Persoon \ Indicatie.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Soort van Persoon \ Indicatie.
     *
     * @return Soort.
     */
    SoortIndicatie getSoort();

    /**
     * Retourneert Standaard van Persoon \ Indicatie.
     *
     * @return Standaard.
     */
    PersoonIndicatieStandaardGroep getStandaard();

}
