/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.afnemerindicatie.GeneriekeOnderhoudAfnemerindicatieStappen;
import nl.bzk.brp.service.algemeen.afnemerindicatie.VerwijderAfnemerindicatieParams;
import org.springframework.stereotype.Service;

/**
 * Serviceimplementatie voor het verwijderen van een afnemerindicatie.
 */
@Service
final class VerwijderAfnemerIndicatieServiceImpl implements OnderhoudAfnemerindicatie.VerwijderAfnemerIndicatieService {

    @Inject
    private GeneriekeOnderhoudAfnemerindicatieStappen.VerwijderAfnemerindicatie verwijderAfnemerindicatieAfnemerindicatie;

    private VerwijderAfnemerIndicatieServiceImpl() {

    }

    @Override
    public void verwijderAfnemerindicatie(final OnderhoudResultaat verzoek) throws StapException {

        final Autorisatiebundel autorisatiebundel = verzoek.getAutorisatiebundel();
        final Dienst dienst = AutAutUtil.zoekDienst(autorisatiebundel.getLeveringsautorisatie(), SoortDienst.VERWIJDERING_AFNEMERINDICATIE);

        verwijderAfnemerindicatieAfnemerindicatie.voerStapUit(new VerwijderAfnemerindicatieParams(
                autorisatiebundel.getToegangLeveringsautorisatie(),
                verzoek.getPersoonslijst(), dienst.getId()
        ));
    }
}
