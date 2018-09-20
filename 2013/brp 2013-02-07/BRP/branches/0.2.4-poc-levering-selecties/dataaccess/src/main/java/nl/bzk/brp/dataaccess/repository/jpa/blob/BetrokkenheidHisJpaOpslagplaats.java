/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.blob;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.dataaccess.repository.blob.BetrokkenheidHisOpslagplaats;
import nl.bzk.brp.dataaccess.repository.historie.BetrokkenheidOuderlijkgezagHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.BetrokkenheidOuderschapHistorieRepository;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.pojo.BetrokkenheidHisModel;
import org.springframework.stereotype.Repository;

/**
 * TODO: Add documentation
 */
@Repository
public class BetrokkenheidHisJpaOpslagplaats implements BetrokkenheidHisOpslagplaats {

    @Inject
    @Named("betrokkenheidOuderschapHistorieRepository")
    private BetrokkenheidOuderschapHistorieRepository ouderschapHistorieJpaRepository;

    @Inject
    @Named("betrokkenheidOuderlijkgezagHistorieRepository")
    private BetrokkenheidOuderlijkgezagHistorieRepository ouderlijkgezagHistorieJpaRepository;

    @Override
    public BetrokkenheidHisModel leesHistorie(final BetrokkenheidModel betrokkenheid) {

        BetrokkenheidHisModel hisModel = new BetrokkenheidHisModel(betrokkenheid.getId());
        hisModel.setBetrokkenheid(betrokkenheid);

        if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol()) {
            hisModel.addAllOuderschapHisModel(ouderschapHistorieJpaRepository.haalopHistorie(betrokkenheid, true));
            hisModel.addAllOuderlijkGezagHisModel(
                    ouderlijkgezagHistorieJpaRepository.haalopHistorie(betrokkenheid, true));
        }

        return hisModel;
    }

    @Override
    public List<BetrokkenheidHisModel> leesHistorie(final PersoonModel persoon) {
        List<BetrokkenheidHisModel> result = new ArrayList<BetrokkenheidHisModel>();

        for (BetrokkenheidModel betrokkenheid: persoon.getBetrokkenheden()) {
            result.add(this.leesHistorie(betrokkenheid));
        }

        return result;
    }
}

