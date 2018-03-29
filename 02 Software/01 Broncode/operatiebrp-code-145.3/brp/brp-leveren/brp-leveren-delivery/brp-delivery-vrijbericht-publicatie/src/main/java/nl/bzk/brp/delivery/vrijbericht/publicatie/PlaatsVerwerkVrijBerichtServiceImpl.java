/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht.publicatie;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.util.LeveringConstanten;
import nl.bzk.brp.service.vrijbericht.PlaatsVerwerkVrijBerichtService;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * PlaatsVerwerkVrijBerichtServiceImpl.
 */
@Service
public final class PlaatsVerwerkVrijBerichtServiceImpl implements PlaatsVerwerkVrijBerichtService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final JsonStringSerializer serializer = new JsonStringSerializer();

    @Inject
    @Named("vrijBerichtJmsTemplate")
    private JmsTemplate vrijBerichtJmsTemplate;

    private PlaatsVerwerkVrijBerichtServiceImpl() {
    }

    @Override
    public void plaatsVrijBericht(final VrijBerichtGegevens vrijBerichtGegevens) {
        try {
            vrijBerichtJmsTemplate.execute((final Session session, final MessageProducer producer) -> {

                final Partij partij = vrijBerichtGegevens.getPartij();
                final Stelsel stelsel = vrijBerichtGegevens.getStelsel();
                final Message message = session.createTextMessage(serializer.serialiseerNaarString(vrijBerichtGegevens));
                message.setStringProperty(LeveringConstanten.JMS_MESSAGEGROUP_HEADER,
                        String.valueOf(partij.getCode()));
                LOGGER.info("Zet bericht op de queue voor vrij bericht ontvanger {} en kanaal {}", partij.getNaam(), stelsel);
                producer.send(message);

                return null;
            });
        } catch (final JmsException e) {
            LOGGER.error("fout in verzenden berichten naar vrije berichten queue", e);
            throw new BrpServiceRuntimeException(e);
        }
    }
}
