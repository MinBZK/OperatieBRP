/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.adres;

import java.util.List;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonOphalenPersoonServiceImpl;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import org.springframework.stereotype.Service;


/**
 * Ophalen persoon service voor GBA Ad hoc (adres)vraag.
 */
@Service
final class AdresvraagOphalenPersoonServiceImpl extends AbstractZoekPersoonOphalenPersoonServiceImpl<AdresvraagVerzoek> {

    @Override
    protected void valideerAantalZoekResultaten(final List<Persoonslijst> persoonslijsten, final Autorisatiebundel autorisatiebundel,
                                                final ZoekPersoonGeneriekVerzoek.ZoekBereikParameters zoekBereikParameters) throws StapMeldingException {
        if (persoonslijsten.size() > 1 && (bevatNullBag(persoonslijsten) || !bevatGelijkeBag(persoonslijsten))) {
            throw new StapMeldingException(Regel.R2392);
        }
    }

    private boolean bevatGelijkeBag(final List<Persoonslijst> persoonslijsten) {
        return persoonslijsten.stream().map(this::bagAanduiding).distinct().count() == 1;
    }

    private boolean bevatNullBag(final List<Persoonslijst> persoonslijsten) {
        return persoonslijsten.stream().map(this::bagAanduiding).filter(o -> !o.isPresent()).count() > 0;
    }

    private Optional<String> bagAanduiding(final Persoonslijst persoonslijst) {
        return persoonslijst.getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING));
    }
}
