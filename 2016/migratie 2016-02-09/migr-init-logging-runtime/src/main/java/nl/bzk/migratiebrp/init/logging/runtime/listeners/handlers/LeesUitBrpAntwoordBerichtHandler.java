/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.serialize.XmlEncoding;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import nl.bzk.migratiebrp.util.common.EncodingConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import org.springframework.stereotype.Component;

/**
 * Handler voor het {@link LeesUitBrpAntwoordBericht}.
 */
@Component
public final class LeesUitBrpAntwoordBerichtHandler extends AbstractInitVullingLogHandler {
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Verwerk het {@link LeesUitBrpAntwoordBericht}.
     * 
     * @param antwoord
     *            bericht
     * @param messageId
     *            message id
     * @param correlationId
     *            correlation id
     */
    public void verwerk(final LeesUitBrpAntwoordBericht antwoord, final String messageId, final String correlationId) {
        final Long administratienummer = extractIdentifier(correlationId);
        final InitVullingLog log = getLoggingService().zoekInitVullingLog(administratienummer);
        if (log == null) {
            LOG.warn(KON_GEEN_LOG_VINDEN_VOOR_MESSAGE_ID_CORRELATION_ID, messageId, correlationId);
        } else {
            try {
                Logging.initContext();
                LoggingContext.reset();
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                XmlEncoding.encode(antwoord.getLo3PersoonslijstFromXml(), baos);

                log.setBerichtNaTerugConversie(baos.toString(EncodingConstants.CHARSET_NAAM));
                getLoggingService().bepalenEnOpslaanVerschillen(log);
            } catch (final UnsupportedEncodingException e) {
                throw new IllegalStateException("Standaard charset dient ondersteund te zijn.", e);
            } finally {
                Logging.destroyContext();
            }
        }
    }
}
