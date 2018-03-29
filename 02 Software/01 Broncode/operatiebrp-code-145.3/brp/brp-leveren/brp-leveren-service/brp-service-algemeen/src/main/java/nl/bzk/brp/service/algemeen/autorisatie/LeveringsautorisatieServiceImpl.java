/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de leveringsautorisatie service {@link LeveringsautorisatieService}.
 */
@Service
final class LeveringsautorisatieServiceImpl implements LeveringsautorisatieService {

    @Inject
    private LeveringsAutorisatieCache leveringAutorisatieCache;

    private LeveringsautorisatieServiceImpl() {

    }

    @Override
    public List<ToegangLeveringsAutorisatie> geefToegangLeveringsAutorisaties() {
        return leveringAutorisatieCache.geefAlleToegangleveringsautorisaties();
    }

    @Override
    public Leveringsautorisatie geefLeveringautorisatie(final int leveringautorisatieId) {
        return leveringAutorisatieCache.geefLeveringsautorisatie(leveringautorisatieId);
    }

    @Override
    public ToegangLeveringsAutorisatie geefToegangLeveringsAutorisatie(final int leveringautorisatieId, final String partijCode) {
        return leveringAutorisatieCache.geefToegangLeveringsautorisatie(leveringautorisatieId, partijCode);
    }

    @Override
    public ToegangLeveringsAutorisatie geefToegangLeveringsAutorisatie(final int toegangLeveringsautorisatieId) {
        return leveringAutorisatieCache.geefToegangLeveringsautorisatie(toegangLeveringsautorisatieId);
    }

    @Override
    public List<ToegangLeveringsAutorisatie> geefToegangLeveringAutorisaties(final Partij partij) {
        return leveringAutorisatieCache.geefToegangleveringautorisatiesVoorGeautoriseerdePartij(partij.getCode());
    }

    @Override
    public boolean bestaatDienst(final int dienstId) {
        return leveringAutorisatieCache.geefDienst(dienstId) != null;
    }

}
