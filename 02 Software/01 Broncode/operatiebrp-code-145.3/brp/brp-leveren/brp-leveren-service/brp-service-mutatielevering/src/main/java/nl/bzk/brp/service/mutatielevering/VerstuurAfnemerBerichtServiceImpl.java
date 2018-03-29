/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import org.springframework.stereotype.Component;

/**
 * Callback implementatie van {@link VerwerkHandelingService.VerstuurAfnemerBerichtService}, welke ervoor zorgt dat de resulterende {@link Mutatiebericht}en uit
 * de verwerking in dit geval op een JMS Queue gezet worden.
 */
@Component
final class VerstuurAfnemerBerichtServiceImpl implements VerwerkHandelingService.VerstuurAfnemerBerichtService {

    @Inject
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    private VerstuurAfnemerBerichtServiceImpl() {

    }

    @Override
    public void verstuurBerichten(final List<Mutatiebericht> berichten) {
        if (berichten.isEmpty()) {
            return;
        }
        final List<AfnemerBericht> afnemerBerichten = new ArrayList<>(berichten.size());
        for (final Mutatiebericht mutatiebericht : berichten) {
            final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = mutatiebericht.getMutatielevering().getAutorisatiebundel()
                    .getToegangLeveringsautorisatie();
            afnemerBerichten.add(new AfnemerBericht(mutatiebericht.getStuurgegevensBericht(),
                    toegangLeveringsautorisatie));

        }
        plaatsAfnemerBerichtService.plaatsAfnemerberichten(afnemerBerichten);
    }
}
