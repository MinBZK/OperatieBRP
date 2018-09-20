/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsverantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonBijhoudingsverantwoordelijkheidGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractPersoonBijhoudingsverantwoordelijkheidActGroepModel;

/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class PersoonBijhoudingsverantwoordelijkheidGroepModel
    extends AbstractPersoonBijhoudingsverantwoordelijkheidActGroepModel
    implements PersoonBijhoudingsverantwoordelijkheidGroep
{
    /**
     * Constructor die op basis van een (blauwdruk) groep een nieuwe instantie creeert en alle velden direct
     * initialiseert naar de waardes uit de opgegeven (blauwdruk) groep.
     *
     * @param bijhoudingsverantwoordelijkheidGroepBasis de (blauwdruk) groep met de te gebruiken waardes.
     */
    public PersoonBijhoudingsverantwoordelijkheidGroepModel(
        final PersoonBijhoudingsverantwoordelijkheidGroepBasis bijhoudingsverantwoordelijkheidGroepBasis)
    {
        super(bijhoudingsverantwoordelijkheidGroepBasis);
    }

    /** Standaard (lege) constructor. */
    public PersoonBijhoudingsverantwoordelijkheidGroepModel() {
    }

}
