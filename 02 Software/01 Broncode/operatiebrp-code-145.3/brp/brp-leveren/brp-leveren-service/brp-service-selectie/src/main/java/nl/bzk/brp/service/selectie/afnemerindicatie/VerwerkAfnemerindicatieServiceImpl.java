/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.afnemerindicatie;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.springframework.stereotype.Service;

/**
 * Verwerk zowel het plaatsen als het verwijderen van afnemerindicaties op basis van een collectie van {@link SelectieAfnemerindicatieTaak}.
 */
@Service
final class VerwerkAfnemerindicatieServiceImpl implements VerwerkAfnemerindicatieService {

    private static final Logger LOG = LoggerFactory.getLogger();
    @Inject
    private OnderhoudAfnemerindicatieSelectieService onderhoudAfnemerindicatieService;
    @Inject
    private SelectieTaakResultaatPublicatieService selectieTaakResultaatPublicatieService;

    private VerwerkAfnemerindicatieServiceImpl() {
    }

    @Override
    public void verwerk(final Collection<SelectieAfnemerindicatieTaak> verzoeken) {
        if (verzoeken.isEmpty()) {
            return;
        }
        try {
            verwerkVerzoeken(verzoeken);
        } catch (Exception e) {
            LOG.error("fout bij verwerken afnemerindicatie selectie verzoek", e);
            selectieTaakResultaatPublicatieService.publiceerFout();
        }
    }

    private void verwerkVerzoeken(Collection<SelectieAfnemerindicatieTaak> verzoeken) throws BlobException {
        final List<OnderhoudAfnemerindicatieResultaat> resultaten = onderhoudAfnemerindicatieService.verwerk(verzoeken);
        final Set<Integer> takenMetBestand = resultaten.stream().filter(OnderhoudAfnemerindicatieResultaat::isBestand).map(
                OnderhoudAfnemerindicatieResultaat::getSelectieTaakId).collect(Collectors.toSet());
        for (SelectieAfnemerindicatieTaak verzoek : verzoeken) {
            // Per verzoek publiceren is niet echt efficient. Kan ook in 1 keer.
            publiceerVerzoekVerwerkt(verzoek, takenMetBestand);
        }
    }

    private void publiceerVerzoekVerwerkt(final SelectieAfnemerindicatieTaak verzoek, final Set<Integer> takenMetBestand) {
        final SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.AFNEMERINDICATIE_VERWERKT);
        selectieTaakResultaat.setTaakId(verzoek.getSelectieTaakId());
        selectieTaakResultaat.setSchrijfTaken(takenMetBestand.contains(verzoek.getSelectieTaakId()) ? 1 : 0);
        selectieTaakResultaatPublicatieService.publiceerSelectieTaakResultaat(selectieTaakResultaat);
    }
}
