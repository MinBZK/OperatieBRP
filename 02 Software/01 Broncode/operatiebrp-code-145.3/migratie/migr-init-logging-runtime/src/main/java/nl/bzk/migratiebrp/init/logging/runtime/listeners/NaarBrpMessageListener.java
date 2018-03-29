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
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers.AntwoordHandler;

/**
 * Verwerk een fout die terug komt van de sync naar BRP.
 */
public final class NaarBrpMessageListener extends AbstractMessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private AntwoordHandler<SynchroniseerNaarBrpAntwoordBericht> synchroniseerNaarBrpAntwoordHandler;
    private AntwoordHandler<AutorisatieAntwoordBericht> autorisatieAntwoordHandler;
    private AntwoordHandler<AfnemersindicatiesAntwoordBericht> afnemersindicatieAntwoordHandler;
    private AntwoordHandler<ProtocolleringAntwoordBericht> protocolleringAntwoordHandler;

    /**
     * Constructor.
     * @param synchroniseerNaarBrpAntwoordHandler synchroniseerNaarBrpAntwoordHandler
     * @param autorisatieAntwoordHandler autorisatieAntwoordHandler
     * @param afnemersindicatieAntwoordHandler afnemersindicatieAntwoordHandler
     * @param protocolleringAntwoordHandler protocolleringAntwoordHandler
     */
    @Inject
    public NaarBrpMessageListener(final AntwoordHandler<SynchroniseerNaarBrpAntwoordBericht> synchroniseerNaarBrpAntwoordHandler,
                                  final AntwoordHandler<AutorisatieAntwoordBericht> autorisatieAntwoordHandler,
                                  final AntwoordHandler<AfnemersindicatiesAntwoordBericht> afnemersindicatieAntwoordHandler,
                                  final AntwoordHandler<ProtocolleringAntwoordBericht> protocolleringAntwoordHandler) {
        this.synchroniseerNaarBrpAntwoordHandler = synchroniseerNaarBrpAntwoordHandler;
        this.autorisatieAntwoordHandler = autorisatieAntwoordHandler;
        this.afnemersindicatieAntwoordHandler = afnemersindicatieAntwoordHandler;
        this.protocolleringAntwoordHandler = protocolleringAntwoordHandler;
    }

    @Override
    protected void verwerkBericht(final String bericht, final String messageId, final String correlationId) {
        LOG.debug("[msg-id {}] Verwerk antwoord bericht (corr-id: {})", messageId, correlationId);
        LOG.debug("Berichtinhoud: {}", bericht);
        try {
            final SyncBericht syncBericht = SyncBerichtFactory.SINGLETON.getBericht(bericht);
            if (syncBericht instanceof SynchroniseerNaarBrpAntwoordBericht) {
                // Initiele vulling: personen
                synchroniseerNaarBrpAntwoordHandler.verwerk((SynchroniseerNaarBrpAntwoordBericht) syncBericht, messageId, correlationId);
            } else if (syncBericht instanceof AutorisatieAntwoordBericht) {
                // Initiele vulling: autorisatie
                autorisatieAntwoordHandler.verwerk((AutorisatieAntwoordBericht) syncBericht, messageId, correlationId);
            } else if (syncBericht instanceof AfnemersindicatiesAntwoordBericht) {
                // Initiele vulling: afnemersindicaties
                afnemersindicatieAntwoordHandler.verwerk((AfnemersindicatiesAntwoordBericht) syncBericht, messageId, correlationId);
            } else if (syncBericht instanceof ProtocolleringAntwoordBericht) {
                // Initiele vulling: protocollering
                protocolleringAntwoordHandler.verwerk((ProtocolleringAntwoordBericht) syncBericht, messageId, correlationId);
            } else {
                LOG.warn("Geen verwerker voor bericht van type: {}\n{}", syncBericht.getClass().getName(), syncBericht);
            }
        } catch (final RuntimeException e) {
            LOG.error("[msg-id {}] Error opgetreden bij het verwerken van het bericht.", messageId, e);
            throw e;
        }
        LOG.debug("[msg-id {}] Gereed met verwerken antwoord bericht (corr-id: {})", messageId, correlationId);
    }
}
