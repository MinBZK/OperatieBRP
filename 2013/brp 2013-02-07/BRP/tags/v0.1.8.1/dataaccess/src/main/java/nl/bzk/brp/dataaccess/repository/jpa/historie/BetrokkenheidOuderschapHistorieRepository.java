/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.historisch.BetrokkenheidOuderschapHisModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import org.springframework.stereotype.Repository;


/** JPA repository voor de tabel his_betrouderschap. */
@Repository("betrokkenheidOuderschapHistorieRepository")
public class BetrokkenheidOuderschapHistorieRepository extends
    AbstractGroepHistorieRepository<BetrokkenheidModel, BetrokkenheidOuderschapHisModel>
{

    @Override
    protected BetrokkenheidOuderschapHisModel maakNieuwHistorieRecord(final BetrokkenheidModel objectType) {
        return new BetrokkenheidOuderschapHisModel(objectType.getBetrokkenheidOuderschap(), objectType);
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "betrokkenheid";
    }

    @Override
    protected Class<BetrokkenheidOuderschapHisModel> getCLaagDomainClass() {
        return BetrokkenheidOuderschapHisModel.class;
    }
}
