/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoord;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingProtocollering;

import org.springframework.stereotype.Component;

/**
 * Handler voor het {@link ProtocolleringAntwoordBericht}.
 */
@Component
public final class ProtocolleringAntwoordHandler extends BasisInitVullingLogHandler implements AntwoordHandler<ProtocolleringAntwoordBericht> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public void verwerk(final ProtocolleringAntwoordBericht antwoordBericht, final String messageId, final String correlationId) {
        for (final ProtocolleringAntwoord protocolleringAntwoord : antwoordBericht.getProtocolleringAntwoord()) {
            final long activiteitId = protocolleringAntwoord.getActiviteitId();
            final InitVullingProtocollering initVullingProtocollering = getLoggingService().zoekInitVullingProtocollering(protocolleringAntwoord
                    .getActiviteitId());
            if (initVullingProtocollering == null) {
                LOG.warn("Kon geen logs vinden voor protocollering data met activiteit id: {}", activiteitId);
            } else {
                initVullingProtocollering.setConversieResultaat(protocolleringAntwoord.getStatus().toString());
                initVullingProtocollering.setFoutmelding(protocolleringAntwoord.getFoutmelding());
                getLoggingService().persisteerInitVullingProtocollering(initVullingProtocollering);
            }
        }
    }
}
