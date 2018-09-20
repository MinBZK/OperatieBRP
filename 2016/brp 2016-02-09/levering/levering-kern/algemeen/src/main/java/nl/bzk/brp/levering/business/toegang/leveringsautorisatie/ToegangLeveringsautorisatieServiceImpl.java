/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.leveringsautorisatie;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache.LeveringAutorisatieCache;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * De implementatie van de toegang leveringsautorisatie service {@link ToegangLeveringsautorisatieService}.
 */
@Service
public class ToegangLeveringsautorisatieServiceImpl implements ToegangLeveringsautorisatieService {

    @Inject
    private LeveringAutorisatieCache leveringAutorisatieCache;

    @Override
    public final List<ToegangLeveringsautorisatie> geefGeldigeLeveringsautorisaties() {
        return leveringAutorisatieCache.geefGeldigeToegangleveringsautorisaties();
    }

    @Override
    public final Leveringsautorisatie geefLeveringautorisatie(final int leveringautorisatieId) {
        final Leveringsautorisatie leveringsautorisatie = leveringAutorisatieCache.geefLeveringsautorisatie(leveringautorisatieId);
        if (leveringsautorisatie == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return leveringsautorisatie;
    }

    @Override
    public final Leveringsautorisatie geefLeveringautorisatieZonderControle(final int leveringautorisatieId) {
        return leveringAutorisatieCache.geefLeveringsautorisatieZonderControle(leveringautorisatieId);
    }

    @Override
    public final boolean bestaatLeveringautorisatie(final int leveringsautorisatieId) {
        return leveringAutorisatieCache.geefLeveringsautorisatieZonderControle(leveringsautorisatieId) != null;
    }

    @Override
    public final ToegangLeveringsautorisatie geefToegangLeveringsautorisatieOpZonderControle(final int leveringsautorisatieId, final int partijCode) {
        return leveringAutorisatieCache.geefToegangleveringautorisatieZonderControle(leveringsautorisatieId, partijCode);
    }

    @Override
    public final ToegangLeveringsautorisatie geefToegangleveringautorisaties(final int leveringautorisatieId, final int partijCode) {
        return leveringAutorisatieCache.geefToegangleveringsautorisatie(leveringautorisatieId, partijCode);
    }

    @Override
    public final ToegangLeveringsautorisatie geefToegangleveringautorisatieZonderControle(final Integer iD) {
        return leveringAutorisatieCache.geefToegangleveringautorisatieZonderControle(iD);
    }

    public final boolean bestaatDienst(final int dienstId) {
        return leveringAutorisatieCache.geefDienst(dienstId) != null;
    }

}
