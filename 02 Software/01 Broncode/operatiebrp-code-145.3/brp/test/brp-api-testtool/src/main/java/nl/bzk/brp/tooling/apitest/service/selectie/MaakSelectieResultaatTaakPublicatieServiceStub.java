/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.selectie;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.service.selectie.lezer.job.MaakSelectieResultaatTaakPublicatieService;
import nl.bzk.brp.service.selectie.schrijver.MaakSelectieResultaatTaakVerwerkerService;
import org.springframework.stereotype.Service;

/**
 * Stub implementatie voor {@link  MaakSelectieResultaatTaakPublicatieService}.
 */
@Service
final class MaakSelectieResultaatTaakPublicatieServiceStub implements MaakSelectieResultaatTaakPublicatieService {

    @Inject
    private MaakSelectieResultaatTaakVerwerkerService maakSelectieResultaatTaakVerwerkerServiceImpl;
    @Inject
    private MaakSelectieResultaatTaakVerwerkerService afnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl;

    private MaakSelectieResultaatTaakPublicatieServiceStub() {

    }

    @Override
    public void publiceerMaakSelectieResultaatTaken(List<MaakSelectieResultaatTaak> maakSelectieResultaatTaken) {
        for (final MaakSelectieResultaatTaak taak : maakSelectieResultaatTaken) {
            if (taak.getSoortSelectie() == SoortSelectie.STANDAARD_SELECTIE) {
                maakSelectieResultaatTaakVerwerkerServiceImpl.verwerk(taak);
            } else {
                afnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl.verwerk(taak);
            }
        }
    }

    @Override
    public void publiceerMaakSelectieGeenResultaatNetwerkTaak(List<MaakSelectieResultaatTaak> maakSelectieGeenResultaatNetwerkTaken) {
        for (final MaakSelectieResultaatTaak taak : maakSelectieGeenResultaatNetwerkTaken) {
            if (taak.getSoortSelectie() == SoortSelectie.STANDAARD_SELECTIE) {
                maakSelectieResultaatTaakVerwerkerServiceImpl.verwerk(taak);
            } else {
                afnemerindicatieMaakSelectieResultaatTaakVerwerkerServiceImpl.verwerk(taak);
            }
        }
    }
}
