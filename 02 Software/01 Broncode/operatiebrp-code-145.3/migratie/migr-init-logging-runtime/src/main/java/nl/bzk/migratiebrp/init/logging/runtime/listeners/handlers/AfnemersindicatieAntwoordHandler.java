/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.runtime.listeners.handlers;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesAntwoordBericht;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatie;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.InitVullingAfnemersindicatieStapel;
import org.springframework.stereotype.Component;

/**
 * Handler voor het {@link AfnemersindicatiesAntwoordBericht}.
 */
@Component
public final class AfnemersindicatieAntwoordHandler extends BasisInitVullingLogHandler implements AntwoordHandler<AfnemersindicatiesAntwoordBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public void verwerk(final AfnemersindicatiesAntwoordBericht antwoordBericht, final String messageId, final String correlationId) {
        final String administratienummer = extractIdentifier(correlationId);
        final InitVullingAfnemersindicatie initvullingAfnemersindicatie = getLoggingService().zoekInitVullingAfnemerindicatie(administratienummer);
        if (initvullingAfnemersindicatie == null) {
            LOG.error("[msg-id {}] Kon geen initvulling afnemersindicatie log vinden voor administratienummer: {}", messageId, administratienummer);
        } else {
            initvullingAfnemersindicatie.setBerichtResultaat("VERWERKT");
            for (AfnemersindicatieAntwoordRecordType afnemerindicatieAntwoord : antwoordBericht.getAfnemersindicaties()) {
                InitVullingAfnemersindicatieStapel
                        afnemerindicatieLog =
                        zoekStapel(initvullingAfnemersindicatie, (short) afnemerindicatieAntwoord.getStapelNummer());
                if (afnemerindicatieLog == null) {
                    LOG.error("[msg-id {}] Kon geen initvulling afnemersindicatie stapel log vinden voor administratienummer: {}, stapelnummer: {}", messageId,
                            administratienummer, afnemerindicatieAntwoord.getStapelNummer());
                } else {
                    afnemerindicatieLog.setConversieResultaat(afnemerindicatieAntwoord.getStatus().toString());
                    afnemerindicatieLog.setConversieMelding(afnemerindicatieAntwoord.getFoutmelding());
                }
            }
            getLoggingService().persisteerInitVullingAfnemerindicatie(initvullingAfnemersindicatie);
        }
    }

    private InitVullingAfnemersindicatieStapel zoekStapel(InitVullingAfnemersindicatie initvullingAfnemersindicatie, short stapelNummer) {
        for (InitVullingAfnemersindicatieStapel stapel : initvullingAfnemersindicatie.getStapels()) {
            if (stapel.getStapelPk().getStapelNr() == stapelNummer) {
                return stapel;
            }
        }
        return null;
    }
}
