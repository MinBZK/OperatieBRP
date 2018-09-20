/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers.AfnemersindicatieAntwoordHandler;
import nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers.AutorisatieAntwoordHandler;
import nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers.SynchroniseerNaarBrpAntwoordHandler;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Verwerk een fout die terug komt van de sync naar BRP.
 */
public final class NaarBrpMessageListener extends AbstractMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private SynchroniseerNaarBrpAntwoordHandler synchroniseerNaarBrpAntwoordHandler;
    @Inject
    private AutorisatieAntwoordHandler autorisatieAntwoordHandler;
    @Inject
    private AfnemersindicatieAntwoordHandler afnemersindicatieAntwoordHandler;

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    protected void verwerkBericht(final String bericht, final String messageId, final String correlationId) {
        LOG.debug("[msg-id {}] Verwerk antwoord bericht (corr-id: {})", messageId, correlationId);
        LOG.debug("Berichtinhoud: {}", bericht);
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);

            // Initiele vulling: personen
            if (syncBericht instanceof SynchroniseerNaarBrpAntwoordBericht) {
                synchroniseerNaarBrpAntwoordHandler.verwerk((SynchroniseerNaarBrpAntwoordBericht) syncBericht, messageId, correlationId);

                // SynchronisatieFout is vervallen. Bericht staat nu, na retry, op de DLQ.
                // } else if (syncBericht instanceof SynchronisatieFoutBericht) {
                // synchronisatieFoutHandler.verwerk((SynchronisatieFoutBericht) syncBericht, messageId, correlationId);

                // Initiele vulling: autorisatie
            } else if (syncBericht instanceof AutorisatieAntwoordBericht) {
                autorisatieAntwoordHandler.verwerk((AutorisatieAntwoordBericht) syncBericht, messageId, correlationId);

                // Initiele vulling: afnemersindicaties
            } else if (syncBericht instanceof AfnemersindicatiesAntwoordBericht) {
                afnemersindicatieAntwoordHandler.verwerk((AfnemersindicatiesAntwoordBericht) syncBericht, messageId, correlationId);
            } else {
                LOG.warn("Geen verwerker voor bericht van type: {}\n{}", syncBericht.getClass().getName(), syncBericht);
            }

        } catch (final Exception e) {
            LOG.error("[msg-id {}] Error opgetreden bij het verwerken van het bericht.", messageId, e);
        }
        LOG.debug("[msg-id {}] Gereed met verwerken antwoord bericht (corr-id: {})", messageId, correlationId);

    }

}
