/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.job.resultaat;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.selectie.lezer.status.SelectieTaakResultaatOntvanger;
import org.springframework.stereotype.Component;

/**
 * SelectieTaakResultaatQueueMessageListener.
 */
@Component
final class SelectieTaakResultaatQueueMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final JsonStringSerializer JSON_STRING_SERIALISEERDER = new JsonStringSerializer();

    private SelectieTaakResultaatOntvanger selectieTaakResultaatOntvanger;

    /**
     * Constructor.
     * @param selectieTaakResultaatOntvanger {@link SelectieTaakResultaatOntvanger}
     */
    @Inject
    public SelectieTaakResultaatQueueMessageListener(SelectieTaakResultaatOntvanger selectieTaakResultaatOntvanger) {
        this.selectieTaakResultaatOntvanger = selectieTaakResultaatOntvanger;
    }

    @Override
    public void onMessage(final Message message) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());
        try {
            LOGGER.debug("onMessage");
            final TextMessage textMessage = (TextMessage) message;
            final String text = textMessage.getText();
            final SelectieTaakResultaat selectieTaakResultaat = JSON_STRING_SERIALISEERDER.deserialiseerVanuitString(text, SelectieTaakResultaat.class);
            selectieTaakResultaatOntvanger.ontvang(selectieTaakResultaat);
        } catch (JMSException e) {
            LOGGER.error("error on message", e);
        }
    }
}

