/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAutorisatie;

import org.springframework.stereotype.Component;

/**
 * Handler voor het {@link AutorisatieAntwoordBericht}.
 */
@Component
public final class AutorisatieAntwoordHandler extends BasisInitVullingLogHandler implements AntwoordHandler<AutorisatieAntwoordBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public void verwerk(final AutorisatieAntwoordBericht antwoordBericht, final String messageId, final String correlationId) {

        for (AutorisatieAntwoordRecordType record : antwoordBericht.getAutorisatieTabelRegels()) {
            final InitVullingAutorisatie initVullingAutorisatie = getLoggingService().zoekInitVullingAutorisatie(record.getAutorisatieId());

            if (initVullingAutorisatie == null) {
                final Integer afnemerCode = Integer.valueOf(extractIdentifier(correlationId));
                LOG.warn("Kon geen logs (initvul.initvullingresult_aut) vinden voor autorisatie id {} (afnemer: {})", record.getAutorisatieId(), afnemerCode);
            } else {
                initVullingAutorisatie.setConversieResultaat(record.getStatus().toString());
                initVullingAutorisatie.setConversieMelding(record.getFoutmelding());
                getLoggingService().persisteerInitVullingAutorisatie(initVullingAutorisatie);
            }
        }
    }
}
