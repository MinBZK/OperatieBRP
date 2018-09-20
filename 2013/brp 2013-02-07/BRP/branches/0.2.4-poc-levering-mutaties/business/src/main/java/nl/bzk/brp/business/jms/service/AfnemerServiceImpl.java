/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.special.ActieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class AfnemerServiceImpl implements AfnemerService {

    @Inject
    private ReferentieDataRepository dataRepository;

    @Inject
    private ActieRepository actieRepository;

    @Inject
    private PersoonRepository persoonRepository;

    /** {@inheritDoc} */
    @Override
    public List<Short> getGeinteresseerdeAfnemers(final Long adminstratieveHandelingId) {
        List<Short> result = new ArrayList<Short>();

        List<Partij> partijen = dataRepository.vindAllePartijen();

        List<String> bsns = actieRepository.bepaalBetrokkenBsnsVanActie(adminstratieveHandelingId);

        for (String bsn : bsns) {
            PersoonModel persoon = persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(bsn));
            Set<PersoonAdresModel> adressen = persoon.getAdressen();

            PersoonAdresModel woonAdres = null;
            for (PersoonAdresModel adres : adressen) {
                if (adres.getGegevens().getSoort() == FunctieAdres.WOONADRES) {
                    woonAdres = adres;
                    break;
                }
            }

            if (woonAdres == null) {
                continue;
            }

            for (Partij partij : partijen) {
                if (woonAdres.getGegevens().getGemeente() == partij) {
                    result.add(partij.getId());
                }
            }
        }

        return result;
    }
}
