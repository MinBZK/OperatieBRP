/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.selectie;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.service.selectie.lezer.SelectieTaakPublicatieService;
import nl.bzk.brp.service.selectie.verwerker.SelectieTaakVerwerker;
import org.springframework.stereotype.Service;

/**
 * Stub implemenetatie voor {@link SelectieTaakPublicatieService}
 */
@Service
final class SelectieTaakPublicatieServiceStub implements SelectieTaakPublicatieService {

    @Inject
    private SelectieTaakVerwerker selectieTaakVerwerker;

    @Override
    public void publiceerSelectieTaak(final List<SelectieVerwerkTaakBericht> selectieTaken) {
        selectieTaken.forEach(selectieTaakVerwerker::verwerkSelectieTaak);
    }
}
