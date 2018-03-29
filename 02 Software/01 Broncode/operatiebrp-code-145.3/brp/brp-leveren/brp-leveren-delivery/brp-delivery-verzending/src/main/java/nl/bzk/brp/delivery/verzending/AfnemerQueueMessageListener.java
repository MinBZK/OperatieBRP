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
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;

/**
 * JMS listener voor synchronisatie berichten.
 */
final class AfnemerQueueMessageListener extends AbstractVerzendingMessageListener {

    @Inject
    private Verzending.VerzendingService verzendingService;

    private AfnemerQueueMessageListener() {
    }

    @Override
    void verzendBericht(final String bericht) {
        // dit gooit een SerialisatieExceptie
        final SynchronisatieBerichtGegevens berichtGegevens =
                new JsonStringSerializer().deserialiseerVanuitString(bericht, SynchronisatieBerichtGegevens.class);

        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING.getVeld(),
                String.valueOf(berichtGegevens.getProtocolleringOpdracht() == null ? null
                        : berichtGegevens.getProtocolleringOpdracht().getAdministratieveHandelingId()));
        mdcMap.put(LeveringVeld.MDC_REFERENTIE_NUMMER.getVeld(),
                String.valueOf(berichtGegevens.getArchiveringOpdracht().getReferentienummer()));
        mdcMap.put(LeveringVeld.MDC_TOEGANG_LEVERINGSAUTORISATIE_ID.getVeld(),
                berichtGegevens.getProtocolleringOpdracht() == null ? null
                        : String.valueOf(berichtGegevens.getProtocolleringOpdracht().getToegangLeveringsautorisatieId()));

        MDC.voerUit(mdcMap, () -> verzendingService.verwerkSynchronisatieBericht(berichtGegevens));
    }

    @Override
    String getInkomendBerichtLogMsg() {
        return "afnemerbericht";
    }
}
