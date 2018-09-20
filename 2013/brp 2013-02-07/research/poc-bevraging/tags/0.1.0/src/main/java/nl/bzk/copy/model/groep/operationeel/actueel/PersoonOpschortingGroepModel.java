/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.copy.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonOpschortingGroepBasis;
import nl.bzk.copy.model.groep.operationeel.actueel.basis.AbstractPersoonOpschortingActGroepModel;

/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PersoonOpschortingGroepModel extends AbstractPersoonOpschortingActGroepModel
        implements PersoonOpschortingGroep
{

    /**
     * .
     *
     * @param groep .
     */
    public PersoonOpschortingGroepModel(final PersoonOpschortingGroepBasis groep) {
        super(groep);
    }

    /**
     * .
     */
    @SuppressWarnings("unused")
    public PersoonOpschortingGroepModel() {
    }
}
