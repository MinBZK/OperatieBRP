/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonOphalenPersoonServiceImpl;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import org.springframework.stereotype.Service;


/**
 * Ophalen persoon service voor GBA Ad hoc (persoons)vraag.
 */
@Service
final class OngeprotocolleerdPersoonsvraagOphalenPersoonServiceImpl
        extends AbstractZoekPersoonOphalenPersoonServiceImpl<OngeprotocolleerdPersoonsvraagVerzoek> {

    private final PersoonsvraagOphalenPersoonServiceImpl persoonsvraagOphalenPersoonService;

    @Inject
    OngeprotocolleerdPersoonsvraagOphalenPersoonServiceImpl(final PersoonsvraagOphalenPersoonServiceImpl persoonsvraagOphalenPersoonService) {
        this.persoonsvraagOphalenPersoonService = persoonsvraagOphalenPersoonService;
    }

    @Override
    protected void valideerAantalZoekResultaten(final List<Persoonslijst> persoonslijsten, final Autorisatiebundel autorisatiebundel,
                                                final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters) throws StapMeldingException {
        persoonsvraagOphalenPersoonService.valideerAantalZoekResultaten(persoonslijsten, autorisatiebundel, zoekBereikParameters);
    }
}
