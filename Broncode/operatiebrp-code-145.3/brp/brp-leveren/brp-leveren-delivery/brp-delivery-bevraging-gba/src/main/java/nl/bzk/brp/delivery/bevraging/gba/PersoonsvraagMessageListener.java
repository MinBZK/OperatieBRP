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
import nl.bzk.brp.gba.domain.bevraging.Persoonsantwoord;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.gba.domain.bevraging.PersoonsvraagQueue;
import nl.bzk.brp.service.bevraging.gba.generiek.AdhocvraagVerwerker;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * MessageListener voor het afhandelen van GBA Ad hoc (persoons)vragen.
 */
@Component
public final class PersoonsvraagMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final JmsTemplate antwoordTemplate;
    private final AdhocvraagVerwerker<Persoonsvraag, Persoonsantwoord> verwerker;

    /**
     * Constructor.
     * @param antwoordTemplate jms template voor versturen van antwoord
     * @param verwerker persoons vraag verwerker
     */
    @Inject
    public PersoonsvraagMessageListener(@Named("gbaAdhocVraagAntwoordTemplate") final JmsTemplate antwoordTemplate,
                                        final AdhocvraagVerwerker<Persoonsvraag, Persoonsantwoord> verwerker) {
        this.antwoordTemplate = antwoordTemplate;
        this.verwerker = verwerker;
    }

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());

        MessageParser parser = new MessageParser(message);
        String berichtReferentieVerzoek = parser.getBerichtReferentie();
        Persoonsvraag persoonsvraag = parser.parseVerzoek(Persoonsvraag.class);
        LOGGER.info("Persoonsvraag ontvangen: {}", new JsonStringSerializer().serialiseerNaarString(persoonsvraag));
        Persoonsantwoord antwoord = verwerker.verwerk(persoonsvraag, berichtReferentieVerzoek);
        LOGGER.info("Persoonsantwoord te versturen: {}", new JsonStringSerializer().serialiseerNaarString(antwoord));
        verstuurAntwoord(antwoord, parser);
    }

    private void verstuurAntwoord(final Persoonsantwoord antwoord, final MessageParser messageParser) {
        antwoordTemplate.send(PersoonsvraagQueue.ANTWOORD.getQueueNaam(), session -> messageParser.composeAntwoord(antwoord, session));
    }
}
