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
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.service.selectie.lezer.SelectieTaakPublicatieService;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.stereotype.Component;

/**
 * SelectieTaakPublicatieServiceImpl.
 */
@Component
public final class SelectieTaakPublicatieServiceImpl implements SelectieTaakPublicatieService {

    private JsonStringSerializer serializer = new JsonStringSerializer();

    @Inject
    @Named("selectieTaakJmsTemplate")
    private JmsOperations selectieTaakJmsTemplate;

    private SelectieTaakPublicatieServiceImpl() {
    }

    @Override
    public void publiceerSelectieTaak(final List<SelectieVerwerkTaakBericht> selectieTaken) {
        final ProducerCallback<Void> producerCallback = (final Session session, final MessageProducer producer) -> {
            for (final SelectieVerwerkTaakBericht selectieTaak : selectieTaken) {
                final Message message = session.createTextMessage(serializer.serialiseerNaarString(selectieTaak));
                producer.send(message);
            }
            return null;
        };
        publiceer(selectieTaakJmsTemplate, producerCallback, () -> "fout in verzenden berichten naar selectie taak queue");
    }
}
