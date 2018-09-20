/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime.service;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.ConversieService;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.Lo3SyntaxControle;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Service;

/**
 * De default implementatie van de ConversieService.
 */
@Service
public final class ConverteerServiceImpl implements ConverteerService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3SyntaxControle syntaxControle;

    @Inject
    private ConversieService conversieService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ConverteerNaarBrpAntwoordBericht verwerkConverteerNaarBrpVerzoek(
            final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoekBericht) {
        LOG.info("[Bericht {} Correlatie {}]: Converteer LO3 conversie model naar BRP conversie model ...",
                converteerNaarBrpVerzoekBericht.getMessageId(), converteerNaarBrpVerzoekBericht.getCorrelationId());

        ConverteerNaarBrpAntwoordBericht result;

        try {
            final BrpPersoonslijst brpPersoonslijst =
                    conversieService.converteerLo3Persoonslijst(new Lo3PersoonslijstParser().parse(syntaxControle
                            .controleer(Lo3Inhoud.parseInhoud(converteerNaarBrpVerzoekBericht
                                    .getLo3BerichtAsTeletexString()))));
            result =
                    new ConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekBericht.getMessageId(),
                            brpPersoonslijst);
            // CHECKSTYLE:OFF - Catch exception voor het robuust afhandelen van exceptions op applicatie interface
            // niveau
        } catch (final Exception e) { // NOSONAR
            // CHECKSTYLE:ON
            LOG.info("[Bericht {}]: Fout bij converteren van LO3 naar BRP:",
                    converteerNaarBrpVerzoekBericht.getMessageId(), e);
            result =
                    new ConverteerNaarBrpAntwoordBericht(converteerNaarBrpVerzoekBericht.getMessageId(),
                            e.getMessage());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConverteerNaarLo3AntwoordBericht verwerkConverteerNaarLo3Verzoek(
            final ConverteerNaarLo3VerzoekBericht converteerNaarLo3VerzoekBericht) {
        LOG.info("[Bericht {} Correlatie {}]: Converteer BRP conversie model naar LO3 conversie model ...",
                converteerNaarLo3VerzoekBericht.getMessageId(), converteerNaarLo3VerzoekBericht.getCorrelationId());

        ConverteerNaarLo3AntwoordBericht result;

        try {
            result =
                    new ConverteerNaarLo3AntwoordBericht(converteerNaarLo3VerzoekBericht.getMessageId(),
                            conversieService.converteerBrpPersoonslijst(converteerNaarLo3VerzoekBericht
                                    .getBrpPersoonslijst()));
            // CHECKSTYLE:OFF - Catch exception voor het robuust afhandelen van exceptions op applicatie interface
            // niveau
        } catch (final Exception e) { // NOSONAR
            // CHECKSTYLE:ON
            LOG.info("[Bericht {}]: Fout bij converteren van BRP naar LO3:",
                    converteerNaarLo3VerzoekBericht.getMessageId(), e);
            result =
                    new ConverteerNaarLo3AntwoordBericht(converteerNaarLo3VerzoekBericht.getMessageId(),
                            e.getMessage());
        }
        return result;
    }

}
