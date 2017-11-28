/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.springframework.stereotype.Component;

/**
 * SelectieFragmentSchrijfTaakVerwerkerServiceImpl.
 */
@Component
final class SelectieFragmentSchrijfTaakVerwerkerServiceImpl implements SelectieFragmentSchrijfTaakVerwerkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private SelectieFragmentWriter selectieFragmentWriter;

    @Inject
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    private SelectieFragmentSchrijfTaakVerwerkerServiceImpl() {
    }

    @Override
    public void verwerk(final SelectieFragmentSchrijfBericht selectieSchrijfTaak) {
        try {
            LOGGER.debug("selectiefragmentschrijftaak ontvangen: " + selectieSchrijfTaak.getDienstId());
            selectieFragmentWriter.verwerk(selectieSchrijfTaak);
            //stukje boekhouding
            final SelectieTaakResultaat selectieResultaat = new SelectieTaakResultaat();
            selectieResultaat.setTaakId(selectieSchrijfTaak.getSelectietaakId());
            selectieResultaat.setType(TypeResultaat.SCHRIJF);
            selectieTaakResultaatPublicatieService.publiceerSelectieTaakResultaat(selectieResultaat);
        } catch (Exception e) {
            LOGGER.error("fout verwerken selectiefragmentschrijftaak", e);
            selectieTaakResultaatPublicatieService.publiceerFout();
        }
    }
}
