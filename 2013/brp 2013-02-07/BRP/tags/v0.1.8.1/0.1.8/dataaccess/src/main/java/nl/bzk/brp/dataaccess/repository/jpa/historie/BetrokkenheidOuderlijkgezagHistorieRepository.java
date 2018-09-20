/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.groep.operationeel.historisch.BetrokkenheidOuderlijkGezagHisModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import org.springframework.stereotype.Repository;


/** JPA repository voor de tabel his_betrouderlijkgezag. */
@Repository("betrokkenheidOuderlijkgezagHistorieRepository")
public class BetrokkenheidOuderlijkgezagHistorieRepository extends
    AbstractGroepHistorieRepository<BetrokkenheidModel, BetrokkenheidOuderlijkGezagHisModel>
{

    @Override
    protected BetrokkenheidOuderlijkGezagHisModel maakNieuwHistorieRecord(final BetrokkenheidModel objectType) {
        return new BetrokkenheidOuderlijkGezagHisModel(objectType.getBetrokkenheidOuderlijkGezag(), objectType);
    }

    @Override
    protected String padNaarALaagEntiteitInCLaagEntiteit() {
        return "betrokkenheid";
    }

    @Override
    protected Class<BetrokkenheidOuderlijkGezagHisModel> getCLaagDomainClass() {
        return BetrokkenheidOuderlijkGezagHisModel.class;
    }
}
