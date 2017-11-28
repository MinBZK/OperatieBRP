/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.builders.BerichtBuilder;
import org.springframework.stereotype.Service;

/**
 * MaakBijgehoudenPersoonServiceImpl.
 */
@Service
final class BijgehoudenPersoonFactoryImpl implements BijgehoudenPersoonFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public List<BijgehoudenPersoon> maakBijgehoudenPersonen(final List<Berichtgegevens> berichtgegevens) {
        return maakBijgehoudenPersonenLijst(berichtgegevens);
    }

    private List<BijgehoudenPersoon> maakBijgehoudenPersonenLijst(final List<Berichtgegevens> berichtgegevensLijst) {
        //maak berichten
        final AtomicInteger communicatieIDVolledig = new AtomicInteger(1);
        final AtomicInteger communicatieIDMutatie = new AtomicInteger(1);
        final List<BijgehoudenPersoon> bijgehoudenPersonen = new ArrayList<>();
        for (final Berichtgegevens berichtgegevens : berichtgegevensLijst) {
            if (berichtgegevens.isLeegBericht() && berichtgegevens.getAutorisatiebundel().getLeveringsautorisatie().getStelsel() != Stelsel.GBA) {
                LOGGER.debug("Bericht is bepaald als LEEG, geen levering voor autorisatie [{}] en persoon [{}]",
                        berichtgegevens.getAutorisatiebundel().getLeveringsautorisatie().getNaam(),
                        berichtgegevens.getPersoonslijst().getId());
                continue;
            }
            final int communicatieId = berichtgegevens.isVolledigBericht()
                    ? communicatieIDVolledig.incrementAndGet() : communicatieIDMutatie.incrementAndGet();
            final BerichtElement berichtElement = new BerichtBuilder(berichtgegevens, communicatieId).build(berichtgegevens.getPersoonslijst()
                    .getMetaObject());
            final BijgehoudenPersoon bijgehoudenPersoon = new BijgehoudenPersoon.Builder(berichtgegevens.getPersoonslijst(), berichtElement)
                    .metCommunicatieId(communicatieId)
                    .metVolledigBericht(berichtgegevens.isVolledigBericht())
                    .build();
            bijgehoudenPersonen.add(bijgehoudenPersoon);
        }
        return bijgehoudenPersonen;
    }
}
