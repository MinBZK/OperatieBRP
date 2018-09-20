/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.groep.operationeel.AbstractBetrokkenheidOuderschapGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.basis.AbstractBetrokkenheidOuderschapHisModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;


/** User implementatie van BetrokkenheidOuderschapHisMdl.. */
@Entity
@Table(schema = "kern", name = "his_betrouder")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class BetrokkenheidOuderschapHisModel extends AbstractBetrokkenheidOuderschapHisModel {

    /** Default constructor tbv hibernate. */
    @SuppressWarnings("unused")
    private BetrokkenheidOuderschapHisModel() {
        super();
    }

    /**
     * .
     *
     * @param abstractBetrokkenheidOuderschapGroep BetrokkenheidOuderschapGroepBasis
     * @param betrokkenheidModel .
     */
    public BetrokkenheidOuderschapHisModel(
        final AbstractBetrokkenheidOuderschapGroep abstractBetrokkenheidOuderschapGroep,
        final BetrokkenheidModel betrokkenheidModel)
    {
        super(abstractBetrokkenheidOuderschapGroep, betrokkenheidModel);
    }

    @Override
    public MaterieleHistorie kopieer() {
        return new BetrokkenheidOuderschapHisModel(this, getBetrokkenheid());
    }
}
