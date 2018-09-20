/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.repository.jpa.historie;

import nl.bzk.copy.dataaccess.repository.historie.BetrokkenheidOuderschapHistorieRepository;
import nl.bzk.copy.dataaccess.repository.jpa.historie.AbstractGroepHistorieRepository;
import nl.bzk.copy.model.groep.operationeel.AbstractBetrokkenheidOuderschapGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.BetrokkenheidOuderschapHisModel;
import nl.bzk.copy.model.objecttype.operationeel.BetrokkenheidModel;
import org.springframework.stereotype.Repository;


/** JPA repository voor de tabel his_betrouderschap. */
@Repository("betrokkenheidOuderschapHistorieRepository")
public class BetrokkenheidOuderschapHistorieJpaRepository extends
    AbstractGroepHistorieRepository<BetrokkenheidModel, AbstractBetrokkenheidOuderschapGroep,
    BetrokkenheidOuderschapHisModel> implements BetrokkenheidOuderschapHistorieRepository
{

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "betrokkenheid";
    }

    @Override
    protected Class<BetrokkenheidOuderschapHisModel> getCLaagDomainClass() {
        return BetrokkenheidOuderschapHisModel.class;
    }

    @Override
    protected BetrokkenheidOuderschapHisModel maakNieuwHistorieRecord(final BetrokkenheidModel objectType,
            final AbstractBetrokkenheidOuderschapGroep groep)
    {
        // dual gebruik, als groep is null, haal de groep uit de huidige ALaag
        if (groep == null) {
            return new BetrokkenheidOuderschapHisModel(objectType.getBetrokkenheidOuderschap(), objectType);
        } else {
            return new BetrokkenheidOuderschapHisModel(groep, objectType);
        }
    }
}
