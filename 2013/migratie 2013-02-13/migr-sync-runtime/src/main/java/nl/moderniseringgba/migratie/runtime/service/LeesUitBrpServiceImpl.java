/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.sync.generated.AntwoordFormaatType;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;

import org.springframework.stereotype.Service;

/**
 * De default implementatie van de LeesUitBrpService.
 */
@Service
public final class LeesUitBrpServiceImpl implements LeesUitBrpService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private ConversieService conversieService;

    @Inject
    private BrpDalService brpDalService;

    /**
     * {@inheritDoc}
     */
    @Override
    public LeesUitBrpAntwoordBericht
            verwerkLeesUitBrpVerzoek(final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht) {
        LOG.info("[Bericht {}]: Query BRP (anummer={}) ...", leesUitBrpVerzoekBericht.getMessageId(),
                leesUitBrpVerzoekBericht.getANummer());
        LeesUitBrpAntwoordBericht result;
        try {
            final BrpPersoonslijst brpPersoonslijst =
                    brpDalService.bevraagPersoonslijst(leesUitBrpVerzoekBericht.getANummer());

            if (AntwoordFormaatType.LO_3.equals(leesUitBrpVerzoekBericht.getAntwoordFormaat())) {
                result =
                        new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(),
                                conversieService.converteerBrpPersoonslijst(brpPersoonslijst));
            } else {
                result = new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht, brpPersoonslijst);
            }
            // CHECKSTYLE:OFF - Catch exception voor het robuust afhandelen van exceptions op applicatie interface
            // niveau
        } catch (final Exception e) { // NOSONAR
            // CHECKSTYLE:ON
            LOG.error("[Bericht {}]: Fout bij het verwerken van het LeesUitBrpVerzoek:",
                    leesUitBrpVerzoekBericht.getMessageId(), e);
            result = new LeesUitBrpAntwoordBericht(leesUitBrpVerzoekBericht.getMessageId(), e.getMessage());
        }
        return result;
    }
}
