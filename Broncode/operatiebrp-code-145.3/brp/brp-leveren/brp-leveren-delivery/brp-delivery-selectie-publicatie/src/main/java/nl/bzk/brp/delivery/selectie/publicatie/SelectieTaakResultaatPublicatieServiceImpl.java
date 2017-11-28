/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.publicatie;

import static nl.bzk.brp.delivery.selectie.publicatie.PublicatieHelper.publiceer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.SelectieTaakResultaat;
import nl.bzk.brp.domain.internbericht.selectie.TypeResultaat;
import nl.bzk.brp.service.selectie.publicatie.SelectieTaakResultaatPublicatieService;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.stereotype.Component;

/**
 * SelectieTaakPublicatieServiceImpl.
 */
@Component
public final class SelectieTaakResultaatPublicatieServiceImpl implements SelectieTaakResultaatPublicatieService {

    private JsonStringSerializer serializer = new JsonStringSerializer();

    @Inject
    @Named("selectieTaakResultaatJmsTemplate")
    private JmsOperations selectieTaakResultaatJmsTemplate;

    private SelectieTaakResultaatPublicatieServiceImpl() {
    }

    @Override
    public void publiceerSelectieTaakResultaat(final SelectieTaakResultaat selectieTaakResultaat) {
        final ProducerCallback<Void> producerCallback = (final Session session, final MessageProducer producer) -> {
            final Message message = session.createTextMessage(serializer.serialiseerNaarString(selectieTaakResultaat));
            producer.send(message);
            return null;
        };
        publiceer(selectieTaakResultaatJmsTemplate, producerCallback, () -> "fout in verzenden berichten naar selectie taak resultaat queue");
    }

    @Override
    public void publiceerFout() {
        final SelectieTaakResultaat selectieTaakResultaat = new SelectieTaakResultaat();
        selectieTaakResultaat.setType(TypeResultaat.FOUT);
        publiceerSelectieTaakResultaat(selectieTaakResultaat);
    }
}
