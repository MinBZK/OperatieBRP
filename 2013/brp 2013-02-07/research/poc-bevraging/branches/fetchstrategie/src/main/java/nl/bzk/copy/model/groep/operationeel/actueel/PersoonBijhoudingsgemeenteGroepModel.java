/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.copy.model.groep.logisch.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonBijhoudingsgemeenteGroepBasis;
import nl.bzk.copy.model.groep.operationeel.actueel.basis.AbstractPersoonBijhoudingsgemeenteActGroepModel;

/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PersoonBijhoudingsgemeenteGroepModel extends AbstractPersoonBijhoudingsgemeenteActGroepModel
        implements PersoonBijhoudingsgemeenteGroep
{
    /**
     * copy constructor.
     *
     * @param bijhoudingsgemeenteGroepBasis de ander object.
     */
    public PersoonBijhoudingsgemeenteGroepModel(
            final PersoonBijhoudingsgemeenteGroepBasis bijhoudingsgemeenteGroepBasis)
    {
        super(bijhoudingsgemeenteGroepBasis);
    }

    /**
     * hibernate constructor.
     */
    @SuppressWarnings("unused")
    public PersoonBijhoudingsgemeenteGroepModel() {
    }
}
