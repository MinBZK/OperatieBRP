/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.publicatie;

import static nl.bzk.brp.delivery.selectie.publicatie.PublicatieHelper.publiceer;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.service.selectie.verwerker.AfnemerindicatieVerzoekPublicatieService;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link AfnemerindicatieVerzoekPublicatieService}.
 */
@Service
public final class AfnemerindicatieVerzoekPublicatieServiceImpl implements AfnemerindicatieVerzoekPublicatieService {

    private JsonStringSerializer serializer = new JsonStringSerializer();

    @Inject
    private JmsOperations selectieAfnemerindicatieVerzoekJmsTemplate;

    private AfnemerindicatieVerzoekPublicatieServiceImpl() {
    }

    @Override
    public void publiceerAfnemerindicatieVerzoeken(@Nonnull final Collection<SelectieAfnemerindicatieTaak> verzoeken) {
        if (!verzoeken.isEmpty()) {
            final ProducerCallback<Void> producerCallback = (final Session session, final MessageProducer producer) -> {
                final Message message = session.createTextMessage(serializer.serialiseerNaarString(verzoeken));
                producer.send(message);
                return null;
            };
            publiceer(selectieAfnemerindicatieVerzoekJmsTemplate, producerCallback,
                    () -> "fout in verzenden berichten naar selectie taak queue");
        }
    }
}
