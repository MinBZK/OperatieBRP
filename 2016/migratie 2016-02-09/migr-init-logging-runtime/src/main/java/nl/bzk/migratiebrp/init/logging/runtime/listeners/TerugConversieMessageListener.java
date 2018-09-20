/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers.LeesUitBrpAntwoordBerichtHandler;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk een terug conversie bericht.
 */
public final class TerugConversieMessageListener extends AbstractMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private LeesUitBrpAntwoordBerichtHandler leesUitBrpAntwoordHandler;

    @Override
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    @SuppressWarnings("checkstyle:illegalcatch")
    protected void verwerkBericht(final String bericht, final String messageId, final String correlationId) {
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);

            // Initiele vulling: personen
            if (syncBericht instanceof LeesUitBrpAntwoordBericht) {
                leesUitBrpAntwoordHandler.verwerk((LeesUitBrpAntwoordBericht) syncBericht, messageId, correlationId);

                // SynchronisatieFout is vervallen. Bericht staat nu, na retry, op de DLQ.
                // } else if (syncBericht instanceof SynchronisatieFoutBericht) {
                // synchronisatieFoutHandler.verwerk((SynchronisatieFoutBericht) syncBericht, messageId, correlationId);

            } else {
                LOG.warn("Geen verwerker voor bericht van type: {}\n{}", syncBericht.getClass().getName(), syncBericht);
            }
        } catch (final Exception e) {
            LOG.error("Error opgetreden bij het uitlezen van het response bericht.", e);
        }
    }

}
