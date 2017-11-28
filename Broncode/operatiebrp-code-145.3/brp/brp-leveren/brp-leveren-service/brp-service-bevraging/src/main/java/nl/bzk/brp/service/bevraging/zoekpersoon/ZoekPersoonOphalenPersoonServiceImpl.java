/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoon;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonOphalenPersoonServiceImpl;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import org.springframework.stereotype.Service;

/**
 * ZoekPersoonOphalenZoekPersoonServiceImpl.
 */
@Service
final class ZoekPersoonOphalenPersoonServiceImpl extends AbstractZoekPersoonOphalenPersoonServiceImpl<ZoekPersoonVerzoek> {
    @Override
    protected void valideerAantalZoekResultaten(final List<Persoonslijst> gefilterdePersoonslijstSet, final Autorisatiebundel autorisatiebundel,
                                                final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters)
            throws StapMeldingException {
        Integer maxAantalZoekResultaten = autorisatiebundel.getDienst().getMaximumAantalZoekresultaten();
        if (maxAantalZoekResultaten == null) {
            maxAantalZoekResultaten = MAX_RESULTS_DEFAULT;
        }
        if (gefilterdePersoonslijstSet.size() > maxAantalZoekResultaten) {
            throw new StapMeldingException(Regel.R2289);
        }
    }
}
