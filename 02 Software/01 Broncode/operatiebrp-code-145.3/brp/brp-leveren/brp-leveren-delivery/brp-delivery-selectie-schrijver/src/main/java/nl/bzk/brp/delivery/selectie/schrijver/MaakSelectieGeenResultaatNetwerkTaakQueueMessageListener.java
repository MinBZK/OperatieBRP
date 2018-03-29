/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.delivery.selectie.schrijver.gba.SelectieGeenResultaatGbaBerichtWriter;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import org.springframework.stereotype.Component;

/**
 * MaakSelectieResultaatTaakQueueMessageListener.
 */
@Component
final class MaakSelectieGeenResultaatNetwerkTaakQueueMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final JsonStringSerializer JSON_STRING_SERIALISEERDER = new JsonStringSerializer();

    @Inject
    private SelectieGeenResultaatGbaBerichtWriter selectieGeenResultaatGbaBerichtWriter;

    private MaakSelectieGeenResultaatNetwerkTaakQueueMessageListener() {

    }

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        try {
            LOGGER.debug("onMessage");
            LOGGER.info("Geen personen vallen binnen de selectie Sv11 wordt uitgestuurd.");
            final TextMessage textMessage = (TextMessage) message;
            final String text = textMessage.getText();
            final MaakSelectieResultaatTaak
                    maakSelectieResultaatTaak =
                    JSON_STRING_SERIALISEERDER.deserialiseerVanuitString(text, MaakSelectieResultaatTaak.class);
            selectieGeenResultaatGbaBerichtWriter.verstuurSv11Bericht(maakSelectieResultaatTaak);
        } catch (JMSException e) {
            LOGGER.error("error on message", e);
        }
    }

}
