/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.selectie;

import javax.inject.Inject;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.selectie.lezer.status.SelectieTaakResultaatOntvanger;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.springframework.stereotype.Service;

/**
 * Stub implementatie voor {@link SelectieTaakResultaatPublicatieService}
 */
@Service
final class SelectieTaakResultaatPublicatieServiceStub implements SelectieTaakResultaatPublicatieService {

    @Inject
    private SelectieTaakResultaatOntvanger selectieTaakResultaatOntvanger;

    @Override
    public void publiceerSelectieTaakResultaat(final SelectieTaakResultaat selectieTaakResultaat) {
        selectieTaakResultaatOntvanger.ontvang(selectieTaakResultaat);
    }

    @Override
    public void publiceerFout() {
        final SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.FOUT);
        publiceerSelectieTaakResultaat(selectieTaakResultaat);
    }
}
