/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Handler voor het {@link AfnemersindicatiesAntwoordBericht}.
 */
@Component
public final class AfnemersindicatieAntwoordHandler extends AbstractInitVullingLogHandler {

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
    public void verwerk(final AfnemersindicatiesAntwoordBericht antwoordBericht, final String messageId, final String correlationId) {
        final Long administratienummer = extractIdentifier(correlationId);
        final InitVullingAfnemersindicatie initvullingAfnemersindicatie = getLoggingService().zoekInitVullingAfnemerindicatie(administratienummer);
        if (initvullingAfnemersindicatie == null) {
            LOG.warn("[msg-id {}] Kon geen initvulling afnemersindicatie log vinden voor administratienummer: {}", messageId, administratienummer);
        } else {
            initvullingAfnemersindicatie.setConversieResultaat(antwoordBericht.getStatus().toString());
            initvullingAfnemersindicatie.setFoutmelding(antwoordBericht.getFoutmelding());
            initvullingAfnemersindicatie.verwerkResultaat(antwoordBericht.getLogging());
            getLoggingService().persisteerInitVullingAfnemerindicatie(initvullingAfnemersindicatie);
        }
    }
}
