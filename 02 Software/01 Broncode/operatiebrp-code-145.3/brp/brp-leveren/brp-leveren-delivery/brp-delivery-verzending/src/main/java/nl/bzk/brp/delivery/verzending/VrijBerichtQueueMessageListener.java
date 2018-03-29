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
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;

/**
 * JMS listener voor vrije berichten.
 */
final class VrijBerichtQueueMessageListener extends AbstractVerzendingMessageListener {

    @Inject
    private Verzending.VerzendingService verzendingService;

    private VrijBerichtQueueMessageListener() {
    }

    @Override
    public void verzendBericht(final String bericht) {
        // dit gooit een SerialisatieExceptie
        final VrijBerichtGegevens berichtGegevens = new JsonStringSerializer().deserialiseerVanuitString(bericht, VrijBerichtGegevens.class);
        final Map<String, String> mdcMap = Maps.newHashMap();
        if (berichtGegevens.getArchiveringOpdracht() != null) {
            mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), String.valueOf(berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId()));
        }

        MDC.voerUit(mdcMap, () -> verzendingService.verwerkVrijBericht(berichtGegevens));
    }

    @Override
    public String getInkomendBerichtLogMsg() {
        return "vrij bericht";
    }
}
