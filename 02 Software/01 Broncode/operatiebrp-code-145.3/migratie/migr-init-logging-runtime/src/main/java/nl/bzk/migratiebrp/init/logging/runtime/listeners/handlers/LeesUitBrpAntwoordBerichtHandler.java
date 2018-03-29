/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingLog;
import org.springframework.stereotype.Component;

/**
 * Handler voor het {@link LeesUitBrpAntwoordBericht}.
 */
@Component
public final class LeesUitBrpAntwoordBerichtHandler extends BasisInitVullingLogHandler implements AntwoordHandler<LeesUitBrpAntwoordBericht>{
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public void verwerk(final LeesUitBrpAntwoordBericht antwoord, final String messageId, final String correlationId) {
        final String administratienummer = extractIdentifier(correlationId);
        final InitVullingLog log = getLoggingService().zoekInitVullingLog(administratienummer);
        if (log == null) {
            LOG.warn(KON_GEEN_LOG_VINDEN_VOOR_MESSAGE_ID_CORRELATION_ID, messageId, correlationId);
        } else {
            if (StatusType.OK == antwoord.getStatus()) {
                try {
                    Logging.initContext();
                    LoggingContext.reset();
                    log.setBerichtNaTerugConversie(antwoord.getStringFromXml());
                    getLoggingService().bepalenEnOpslaanVerschillen(log);
                } finally {
                    Logging.destroyContext();
                }
            } else {
                LOG.warn("Fout bij terugconversie gemeld");
                log.setFoutmelding(antwoord.getMelding());
                getLoggingService().persisteerInitVullingLog(log);
            }
        }
    }
}
