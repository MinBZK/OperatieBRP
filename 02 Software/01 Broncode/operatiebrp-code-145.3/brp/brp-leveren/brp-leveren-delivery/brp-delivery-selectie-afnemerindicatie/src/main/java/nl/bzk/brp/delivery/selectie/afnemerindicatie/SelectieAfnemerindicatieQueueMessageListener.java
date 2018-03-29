/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.afnemerindicatie;

import com.google.common.collect.Lists;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.selectie.afnemerindicatie.VerwerkAfnemerindicatieService;
import org.springframework.stereotype.Component;

/**
 * SelectieAfnemerindicatieQueueMessageListener.
 */
@Component
public final class SelectieAfnemerindicatieQueueMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final JsonStringSerializer JSON_STRING_SERIALISEERDER = new JsonStringSerializer();

    private VerwerkAfnemerindicatieService verwerkAfnemerindicatieService;

    /**
     * Constructor.
     * @param verwerkAfnemerindicatieService {@link VerwerkAfnemerindicatieService}
     */
    @Inject
    public SelectieAfnemerindicatieQueueMessageListener(VerwerkAfnemerindicatieService verwerkAfnemerindicatieService) {
        this.verwerkAfnemerindicatieService = verwerkAfnemerindicatieService;
    }

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        LOGGER.debug("onMessage");
        final TextMessage textMessage = (TextMessage) message;

        try {
            final String text = textMessage.getText();
            final SelectieAfnemerindicatieTaak[]
                    verzoeken = JSON_STRING_SERIALISEERDER.deserialiseerVanuitString(text, SelectieAfnemerindicatieTaak[].class);
            verwerkAfnemerindicatieService.verwerk(Lists.newArrayList(verzoeken));
        } catch (JMSException e) {
            LOGGER.error("error on message", e);
        }
    }
}

