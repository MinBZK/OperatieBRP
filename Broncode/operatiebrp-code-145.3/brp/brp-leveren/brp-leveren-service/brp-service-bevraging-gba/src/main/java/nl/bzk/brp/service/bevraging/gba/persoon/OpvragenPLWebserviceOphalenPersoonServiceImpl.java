/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
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
final class OpvragenPLWebserviceOphalenPersoonServiceImpl extends AbstractZoekPersoonOphalenPersoonServiceImpl<OpvragenPLWebserviceVerzoek> {
    @Override
    protected void valideerAantalZoekResultaten(final List<Persoonslijst> persoonslijsten, final Autorisatiebundel autorisatiebundel,
                                                final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters) throws StapMeldingException {
        if (persoonslijsten.isEmpty()) {
            throw new StapMeldingException(Regel.R1403);
        }
        if (persoonslijsten.size() > 1) {
            throw new StapMeldingException((Regel.R2289));
        }
    }
}
