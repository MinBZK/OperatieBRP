/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.actueel;

import javax.persistence.Embeddable;

import nl.bzk.copy.model.groep.logisch.BetrokkenheidOuderlijkGezagGroep;
import nl.bzk.copy.model.groep.logisch.basis.BetrokkenheidOuderlijkGezagGroepBasis;
import nl.bzk.copy.model.groep.operationeel.actueel.basis.AbstractBetrokkenheidOuderlijkGezagActGroepModel;


/**
 *
 */
@Embeddable
@SuppressWarnings("serial")
public class BetrokkenheidOuderlijkGezagGroepModel extends AbstractBetrokkenheidOuderlijkGezagActGroepModel implements
        BetrokkenheidOuderlijkGezagGroep
{

    /**
     * Default constructor tbv hibernate.
     */
    @SuppressWarnings("unused")
    public BetrokkenheidOuderlijkGezagGroepModel() {
        super();
    }

    /**
     * .
     *
     * @param betrokkenheidOuderlijkGezagGroepBasis
     *         BetrokkenheidOuderlijkGezagGroepBasis
     */
    public BetrokkenheidOuderlijkGezagGroepModel(
            final BetrokkenheidOuderlijkGezagGroepBasis betrokkenheidOuderlijkGezagGroepBasis)
    {
        super(betrokkenheidOuderlijkGezagGroepBasis);
    }
}
