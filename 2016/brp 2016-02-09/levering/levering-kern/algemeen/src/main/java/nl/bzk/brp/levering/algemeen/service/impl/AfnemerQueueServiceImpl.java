/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.levering.algemeen.service.AfnemerQueueService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache.LeveringAutorisatieCache;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import org.springframework.stereotype.Service;


/**
 * De Class AfnemerQueueServiceImpl, deze service klasse dient voor het ophalen van de namen van JMS afnemer queues. //FIXME wordt enkel gebruikt in
 * verzending, verplaatsen....
 */
@Service
public class AfnemerQueueServiceImpl implements AfnemerQueueService {

    @Inject
    private LeveringAutorisatieCache leveringAutorisatieCache;

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Override
    public final List<PartijCodeAttribuut> haalPartijCodesWaarvoorGeleverdMoetWorden() {
        leveringAutorisatieCache.herlaad();
        final List<ToegangLeveringsautorisatie> toegangLeveringsautorisaties = toegangLeveringsautorisatieService.geefGeldigeLeveringsautorisaties();
        final Set<PartijCodeAttribuut> uniekePartijCodes = new HashSet<>();
        for (final ToegangLeveringsautorisatie toegangLeveringsautorisatie : toegangLeveringsautorisaties) {
            uniekePartijCodes.add(toegangLeveringsautorisatie.getGeautoriseerde().getPartij().getCode());
        }

        return new ArrayList<>(uniekePartijCodes);
    }

}
