/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.jpa;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.dal.operationeel.His_RelatieRepository;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.His_Relatie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Relatie;
import nl.bzk.brp.pocmotor.util.DatumEnTijdUtil;


public class RelatieOGMRepositoryImpl implements RelatieOGMRepositoryCustom {

    @Inject
    private His_RelatieRepository his_relatieRepository;

    @Override
    public void persisteerRelatieHisEntiteit(Relatie relatie) {
        final His_Relatie hisRelatie = new His_Relatie();
        hisRelatie.setRelatie(relatie);
        hisRelatie.setDatumTijdRegistratie(DatumEnTijdUtil.nu());
        hisRelatie.setDatumAanvang(relatie.getDatumAanvang());
        hisRelatie.setGemeenteAanvang(relatie.getGemeenteAanvang());
        hisRelatie.setWoonplaatsAanvang(relatie.getWoonplaatsAanvang());
        hisRelatie.setBuitenlandsePlaatsAanvang(relatie.getBuitenlandsePlaatsAanvang());
        hisRelatie.setBuitenlandseRegioAanvang(relatie.getBuitenlandseRegioAanvang());
        hisRelatie.setLandAanvang(relatie.getLandAanvang());
        hisRelatie.setOmschrijvingLocatieAanvang(relatie.getOmschrijvingLocatieAanvang());
        his_relatieRepository.save(hisRelatie);
    }
}
