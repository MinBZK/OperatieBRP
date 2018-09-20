/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handler voor het {@link SynchroniseerNaarBrpAntwoordBericht}.
 */
@Component
public final class SynchroniseerNaarBrpAntwoordHandler extends AbstractInitVullingLogHandler {
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Verwerk het bericht.
     *
     * @param antwoord
     *            bericht
     * @param messageId
     *            message id
     * @param correlationId
     *            correlation id
     */
    public void verwerk(final SynchroniseerNaarBrpAntwoordBericht antwoord, final String messageId, final String correlationId) {
        final Long administratienummer = extractIdentifier(correlationId);
        final InitVullingLog log = getLoggingService().zoekInitVullingLog(administratienummer);
        if (log == null) {
            LOG.warn(KON_GEEN_LOG_VINDEN_VOOR_MESSAGE_ID_CORRELATION_ID, messageId, correlationId);
        } else {
            LOG.debug("Status in antwoordbericht {}", antwoord.getStatus().toString());
            fillLog(log, null, antwoord.getStatus().toString());
            getLoggingService().bepalenEnOpslaanVerschillen(log);
        }
    }

}
