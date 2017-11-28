/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Bepaalt of gedeblokkeerde meldingen getoond mogen worden.
 */
@Component
@Bedrijfsregel(Regel.R1608)
final class AutoriseerGedeblokkeerdeMeldingenServiceImpl implements MaakBerichtStap {

    @Inject
    private LeveringsAutorisatieCache leveringsAutorisatieCache;

    private AutoriseerGedeblokkeerdeMeldingenServiceImpl() {

    }

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = berichtgegevens.getAutorisatiebundel().getToegangLeveringsautorisatie();
        final boolean metBijhouderRol = toegangLeveringsautorisatie.getGeautoriseerde().getRol() != Rol.AFNEMER;
        final boolean regelcodeGeautoriseerd = metBijhouderRol && leveringsAutorisatieCache.geefGeldigeElementen(
                toegangLeveringsautorisatie,
                berichtgegevens.getAutorisatiebundel().getDienst()).stream()
                .anyMatch(attribuutElement -> attribuutElement.getElement() == Element.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEREGEL_REGELCODE);
        berichtgegevens.setGeautoriseerdVoorGedeblokkeerdeMeldingen(regelcodeGeautoriseerd);

    }
}
