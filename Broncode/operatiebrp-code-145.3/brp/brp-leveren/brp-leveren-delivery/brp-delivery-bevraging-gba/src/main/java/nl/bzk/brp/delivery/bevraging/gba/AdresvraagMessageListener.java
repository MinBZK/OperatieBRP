/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.gba.domain.bevraging.Adresantwoord;
import nl.bzk.brp.gba.domain.bevraging.Adresvraag;
import nl.bzk.brp.gba.domain.bevraging.AdresvraagQueue;
import nl.bzk.brp.service.bevraging.gba.generiek.AdhocvraagVerwerker;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * MessageListener voor het afhandelen van GBA Ad hoc adresvragen.
 */
@Component
public final class AdresvraagMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final JmsTemplate antwoordTemplate;
    private final AdhocvraagVerwerker<Adresvraag, Adresantwoord> verwerker;

    /**
     * Constructor.
     * @param antwoordTemplate JMS antwoordtemplate
     * @param verwerker adres vraag verwerker
     */
    @Inject
    public AdresvraagMessageListener(@Named("gbaAdhocVraagAntwoordTemplate") final JmsTemplate antwoordTemplate,
                                     final AdhocvraagVerwerker<Adresvraag, Adresantwoord> verwerker) {
        this.antwoordTemplate = antwoordTemplate;
        this.verwerker = verwerker;
    }

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        
        MessageParser parser = new MessageParser(message);
        String berichtReferentieVerzoek = parser.getBerichtReferentie();
        Adresvraag adresvraag = parser.parseVerzoek(Adresvraag.class);
        LOGGER.info("Adresvraag ontvangen: {}", new JsonStringSerializer().serialiseerNaarString(adresvraag));
        Adresantwoord antwoord = verwerker.verwerk(adresvraag, berichtReferentieVerzoek);
        LOGGER.info("Adresantwoord te versturen: {}", new JsonStringSerializer().serialiseerNaarString(antwoord));
        verstuurAntwoord(antwoord, parser);
    }

    private void verstuurAntwoord(final Adresantwoord antwoord, final MessageParser messageParser) {
        antwoordTemplate.send(AdresvraagQueue.ANTWOORD.getQueueNaam(), session -> messageParser.composeAntwoord(antwoord, session));
    }
}
