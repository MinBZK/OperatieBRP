/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonOverlijdenGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractPersoonOverlijdenActGroepModel;

/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PersoonOverlijdenGroepModel extends AbstractPersoonOverlijdenActGroepModel implements PersoonOverlijdenGroep {
    /**
     * defult constrctor for hibernate.
     */
    private PersoonOverlijdenGroepModel() {
    }

    /**
     * copy constructor.
     * @param groep die ander object.
     */
    public PersoonOverlijdenGroepModel(final PersoonOverlijdenGroepBasis groep) {
        super(groep);
    }

}
