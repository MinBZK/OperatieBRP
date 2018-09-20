/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Handler voor het {@link AutorisatieAntwoordBericht}.
 */
@Component
public final class AutorisatieAntwoordHandler extends AbstractInitVullingLogHandler {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Verwerk het bericht.
     *
     * @param antwoordBericht
     *            bericht
     * @param messageId
     *            message id
     * @param correlationId
     *            correlation id
     */
    public void verwerk(final AutorisatieAntwoordBericht antwoordBericht, final String messageId, final String correlationId) {
        final Integer afnemerCode = extractIdentifier(correlationId).intValue();
        final InitVullingAutorisatie log = getLoggingService().zoekInitVullingAutorisatie(afnemerCode);
        if (log == null) {
            LOG.warn("Kon geen logs vinden voor afnemercode: {}", afnemerCode);
        } else {
            log.setConversieResultaat(antwoordBericht.getStatus().toString());
            log.setFoutmelding(antwoordBericht.getFoutmelding());
            getLoggingService().persisteerInitVullingAutorisatie(log);
        }
    }
}
