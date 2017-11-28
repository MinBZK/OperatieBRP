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
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.selectie.schrijver.SelectieFragmentSchrijfTaakVerwerkerService;
import org.springframework.stereotype.Component;

/**
 * SelectieSchrijfTaakQueueMessageListener.
 */
@Component
final class SelectieSchrijfTaakQueueMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final JsonStringSerializer JSON_STRING_SERIALISEERDER = new JsonStringSerializer();

    private SelectieFragmentSchrijfTaakVerwerkerService selectieFragmentSchrijfTaakVerwerkerService;

    /**
     * Constructor.
     * @param selectieFragmentSchrijfTaakVerwerkerService {@link SelectieFragmentSchrijfTaakVerwerkerService}
     */
    @Inject
    public SelectieSchrijfTaakQueueMessageListener(final SelectieFragmentSchrijfTaakVerwerkerService selectieFragmentSchrijfTaakVerwerkerService) {
        this.selectieFragmentSchrijfTaakVerwerkerService = selectieFragmentSchrijfTaakVerwerkerService;
    }

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        try {
            LOGGER.debug("onMessage");
            final TextMessage textMessage = (TextMessage) message;
            final String text = textMessage.getText();
            final SelectieFragmentSchrijfBericht
                    selectieSchrijfTaak =
                    JSON_STRING_SERIALISEERDER.deserialiseerVanuitString(text, SelectieFragmentSchrijfBericht.class);
            selectieFragmentSchrijfTaakVerwerkerService.verwerk(selectieSchrijfTaak);
        } catch (JMSException e) {
            LOGGER.error("error on message", e);
        }
    }
}

