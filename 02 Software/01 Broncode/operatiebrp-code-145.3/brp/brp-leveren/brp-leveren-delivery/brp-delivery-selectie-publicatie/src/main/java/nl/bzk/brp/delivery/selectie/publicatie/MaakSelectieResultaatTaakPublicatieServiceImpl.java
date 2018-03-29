/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.publicatie;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.service.algemeen.util.LeveringConstanten;
import nl.bzk.brp.service.selectie.lezer.job.MaakSelectieResultaatTaakPublicatieService;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.stereotype.Component;

/**
 * MaakSelectieResultaatTaakPublicatieServiceImpl.
 */
@Component
public final class MaakSelectieResultaatTaakPublicatieServiceImpl implements MaakSelectieResultaatTaakPublicatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private JsonStringSerializer serializer = new JsonStringSerializer();

    @Inject
    @Named("maakSelectieResultaatTaakJmsTemplate")
    private JmsOperations maakSelectieResultaatTemplate;
    @Inject
    @Named("maakSelectieGeenResultaatNetwerkTaakJmsTemplate")
    private JmsOperations maakSelectieGeenResultaatNetwerkTemplate;

    private MaakSelectieResultaatTaakPublicatieServiceImpl() {
    }

    @Override
    public void publiceerMaakSelectieResultaatTaken(List<MaakSelectieResultaatTaak> maakSelectieResultaatTaken) {
        LOGGER.info("publiceer maak selectie resultaat taken");
        final ProducerCallback<Void> producerCallback = (final Session session, final MessageProducer producer) -> {
            for (final MaakSelectieResultaatTaak maakSelectieResultaatTaak : maakSelectieResultaatTaken) {
                LOGGER.debug("publiceer maak selectie resultaat taak");
                final String
                        groupId =
                        maakSelectieResultaatTaak.getSelectieRunId() + "_" + maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId() + "_"
                                + maakSelectieResultaatTaak.getDienstId();
                final Message message = session.createTextMessage(serializer.serialiseerNaarString(maakSelectieResultaatTaak));
                message.setStringProperty(LeveringConstanten.JMS_MESSAGEGROUP_HEADER, groupId);
                producer.send(message);
            }
            return null;
        };
        PublicatieHelper.publiceer(maakSelectieResultaatTemplate, producerCallback,
                () -> "fout in verzenden berichten naar maak selectie resultaat taak queue");
    }

    @Override
    public void publiceerMaakSelectieGeenResultaatNetwerkTaak(List<MaakSelectieResultaatTaak> maakSelectieGeenResultaatNetwerkTaken) {
        LOGGER.info("publiceer maak selectie resultaat taken");
        final ProducerCallback<Void> producerCallback = (final Session session, final MessageProducer producer) -> {
            LOGGER.debug("publiceer maak selectie resultaat taak");
            for (final MaakSelectieResultaatTaak maakSelectieGeenResultaatNetwerkTaak : maakSelectieGeenResultaatNetwerkTaken) {
                final String
                        groupId =
                        maakSelectieGeenResultaatNetwerkTaak.getSelectieRunId() + "_" + maakSelectieGeenResultaatNetwerkTaak
                                .getToegangLeveringsAutorisatieId()
                                + "_"
                                + maakSelectieGeenResultaatNetwerkTaak.getDienstId();
                final Message message = session.createTextMessage(serializer.serialiseerNaarString(maakSelectieGeenResultaatNetwerkTaak));
                message.setStringProperty(LeveringConstanten.JMS_MESSAGEGROUP_HEADER, groupId);
                producer.send(message);
            }
            return null;
        };
        PublicatieHelper.publiceer(maakSelectieGeenResultaatNetwerkTemplate, producerCallback,
                () -> "fout in verzenden berichten naar maak selectie geen resultaat netwerk taak queue");
    }
}

