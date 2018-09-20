/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonImmigratieGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractPersoonImmigratieActGroepModel;

/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PersoonImmigratieGroepModel extends AbstractPersoonImmigratieActGroepModel
    implements PersoonImmigratieGroep
{

    /**
     * Copy constructor voor groep.
     *
     * @param groep De te kopieren groep.
     */
    public PersoonImmigratieGroepModel(final PersoonImmigratieGroepBasis groep) {
        super(groep);
    }

    /**
     * .
     */
    @SuppressWarnings("unused")
    private PersoonImmigratieGroepModel() {
    }
}
