/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers.AntwoordHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk een terug conversie bericht.
 */
public final class TerugConversieMessageListener extends AbstractMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private AntwoordHandler<LeesUitBrpAntwoordBericht> leesUitBrpAntwoordHandler;

    /**
     * Constructor.
     * @param leesUitBrpAntwoordHandler leesUitBrpAntwoordHandler
     */
    @Inject
    public TerugConversieMessageListener(final AntwoordHandler<LeesUitBrpAntwoordBericht> leesUitBrpAntwoordHandler) {
        this.leesUitBrpAntwoordHandler = leesUitBrpAntwoordHandler;
    }

    @Override
    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    public void verwerkBericht(final String bericht, final String messageId, final String correlationId) {
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
            if (syncBericht instanceof LeesUitBrpAntwoordBericht) {
                // Initiele vulling: personen
                leesUitBrpAntwoordHandler.verwerk((LeesUitBrpAntwoordBericht) syncBericht, messageId, correlationId);
            } else {
                LOG.warn("Geen verwerker voor bericht van type: {}\n{}", syncBericht.getClass().getName(), syncBericht);
            }
        } catch (final RuntimeException e) {
            LOG.error("Error opgetreden bij het uitlezen van het response bericht.", e);
            throw e;
        }
    }
}
