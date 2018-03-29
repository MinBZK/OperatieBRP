/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;

/**
 * JMS listener voor bijhoudingsnotificatie berichten.
 */
final class BijhoudingsNotificatieQueueMessageListener extends AbstractVerzendingMessageListener {

    @Inject
    private Verzending.VerzendingService verzendingService;

    private BijhoudingsNotificatieQueueMessageListener() {

    }

    @Override
    void verzendBericht(final String bericht) {
        // dit gooit een SerialisatieExceptie
        final BijhoudingsplanNotificatieBericht bijhoudingsPlanNotificatieBericht = new JsonStringSerializer()
                .deserialiseerVanuitString(bericht, BijhoudingsplanNotificatieBericht.class);

        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(),
                String.valueOf(bijhoudingsPlanNotificatieBericht.getZendendePartijCode()));
        mdcMap.put(LeveringVeld.MDC_REFERENTIE_NUMMER.getVeld(),
                String.valueOf(bijhoudingsPlanNotificatieBericht.getReferentieNummer()));

        MDC.voerUit(mdcMap, () -> verzendingService.verwerkBijhoudingsNotificatieBericht(bijhoudingsPlanNotificatieBericht));
    }

    @Override
    String getInkomendBerichtLogMsg() {
        return "bijhoudingsnotificatiebericht";
    }
}
