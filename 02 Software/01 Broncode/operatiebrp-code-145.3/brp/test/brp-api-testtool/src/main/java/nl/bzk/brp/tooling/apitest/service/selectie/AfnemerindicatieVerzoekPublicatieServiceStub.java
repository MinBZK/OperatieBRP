/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.selectie;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.service.selectie.afnemerindicatie.VerwerkAfnemerindicatieService;
import nl.bzk.brp.service.selectie.verwerker.AfnemerindicatieVerzoekPublicatieService;
import org.springframework.stereotype.Service;

/**
 * Stub voor {@link AfnemerindicatieVerzoekPublicatieService}.
 */
@Service
final class AfnemerindicatieVerzoekPublicatieServiceStub implements AfnemerindicatieVerzoekPublicatieService {

    @Inject
    private VerwerkAfnemerindicatieService verwerkAfnemerindicatieService;

    private AfnemerindicatieVerzoekPublicatieServiceStub() {

    }

    @Override
    public void publiceerAfnemerindicatieVerzoeken(@Nonnull final Collection<SelectieAfnemerindicatieTaak> verzoeken) {
        verwerkAfnemerindicatieService.verwerk(verzoeken);
    }
}
