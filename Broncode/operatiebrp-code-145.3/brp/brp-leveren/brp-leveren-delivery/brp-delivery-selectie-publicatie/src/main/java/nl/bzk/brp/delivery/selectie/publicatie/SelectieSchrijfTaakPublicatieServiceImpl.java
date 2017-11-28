/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.publicatie;

import static nl.bzk.brp.delivery.selectie.publicatie.PublicatieHelper.publiceer;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.service.algemeen.util.LeveringConstanten;
import nl.bzk.brp.service.selectie.verwerker.SelectieSchrijfTaakPublicatieService;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.stereotype.Component;

/**
 * SelectieSchrijfTaakPublicatieServiceImpl.
 */
@Component
public final class SelectieSchrijfTaakPublicatieServiceImpl implements SelectieSchrijfTaakPublicatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private JsonStringSerializer serializer = new JsonStringSerializer();

    @Inject
    @Named("selectieSchrijfTaakJmsTemplate")
    private JmsOperations selectieTaakJmsTemplate;

    private SelectieSchrijfTaakPublicatieServiceImpl() {
    }

    @Override
    public void publiceerSchrijfTaken(List<SelectieFragmentSchrijfBericht> schrijfTaken) {
        LOGGER.info("publiceer selectie schrijf taken");
        final ProducerCallback<Void> producerCallback = (final Session session, final MessageProducer producer) -> {
            for (final SelectieFragmentSchrijfBericht selectieTaak : schrijfTaken) {
                LOGGER.debug("publiceer selectie schrijf taak");
                final Message message = session.createTextMessage(serializer.serialiseerNaarString(selectieTaak));
                message.setStringProperty(LeveringConstanten.JMS_MESSAGEGROUP_HEADER, String.valueOf(selectieTaak.getSelectietaakId()));
                producer.send(message);
            }
            return null;
        };
        publiceer(selectieTaakJmsTemplate, producerCallback,
                () -> "fout in verzenden berichten naar selectie schrijf taak queue");
    }
}

