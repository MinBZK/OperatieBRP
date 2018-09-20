/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.groep.logisch.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonNationaliteitStandaardGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractPersoonNationaliteitStandaardActGroepModel;


/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PersoonNationaliteitStandaardGroepModel extends AbstractPersoonNationaliteitStandaardActGroepModel implements
        PersoonNationaliteitStandaardGroep
{

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    private PersoonNationaliteitStandaardGroepModel() {
        super();
    }

    /**
     * .
     *
     * @param persoonNationaliteitStandaardGroepBasis PersoonNationaliteitStandaardGroepBasis
     */
    public PersoonNationaliteitStandaardGroepModel(
            final PersoonNationaliteitStandaardGroepBasis persoonNationaliteitStandaardGroepBasis)
    {
        super(persoonNationaliteitStandaardGroepBasis);
    }
}
