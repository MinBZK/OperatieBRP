/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.jpa;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.dal.operationeel.His_BetrokkenheidOuderRepository;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_BetrokkenheidOuder;
import nl.bzk.brp.pocmotor.util.DatumEnTijdUtil;

public class BetrokkenheidOGMRepositoryImpl implements BetrokkenheidOGMRepositoryCustom {

    @Inject
    private His_BetrokkenheidOuderRepository his_betrokkenheidOuderRepository;


    @Override
    public void persisteerHisBetrokkenheidOuder(Betrokkenheid betr) {
        final His_BetrokkenheidOuder hisBetrOuder = new His_BetrokkenheidOuder();
        hisBetrOuder.setBetrokkenheid(betr);
        hisBetrOuder.setDatumAanvangGeldigheid(DatumEnTijdUtil.vandaag());
        hisBetrOuder.setDatumTijdRegistratie(DatumEnTijdUtil.nu());
        hisBetrOuder.setIndicatieOuder(betr.getIndicatieOuder());
        his_betrokkenheidOuderRepository.save(hisBetrOuder);
    }
}
