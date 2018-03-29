/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.jms.afnemer;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.service.algemeen.BrpServiceRuntimeException;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.util.LeveringConstanten;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Service;

/**
 * Service vh om een queue plaatsen van afnemerberichten.
 */
@Service
public final class PlaatsAfnemerBerichtServiceImpl implements PlaatsAfnemerBerichtService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final JsonStringSerializer serializer = new JsonStringSerializer();

    @Inject
    @Named("afnemersJmsTemplate")
    private JmsOperations afnemersJmsTemplate;

    private PlaatsAfnemerBerichtServiceImpl() {
    }

    @Override
    public void plaatsAfnemerberichten(final List<AfnemerBericht> afnemerBerichten) {
        try {
            afnemersJmsTemplate.execute((final Session session, final MessageProducer producer) -> {
                for (final AfnemerBericht afnemerBericht : afnemerBerichten) {
                    final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens = afnemerBericht.getSynchronisatieBerichtGegevens();
                    final Message message = session.createTextMessage(serializer.serialiseerNaarString(synchronisatieBerichtGegevens));
                    message.setStringProperty(LeveringConstanten.JMS_MESSAGEGROUP_HEADER,
                            String.valueOf(synchronisatieBerichtGegevens.getArchiveringOpdracht().getOntvangendePartijId()));
                    final ToegangLeveringsAutorisatie toegang = afnemerBericht.getToegangLeveringsAutorisatie();
                    LOGGER.info("Zet bericht op de queue voor afnemer {} en kanaal {}",
                            toegang.getGeautoriseerde().getPartij().getNaam(),
                            toegang.getLeveringsautorisatie().getStelsel());
                    producer.send(message);
                }
                return null;
            });
        } catch (final JmsException e) {
            LOGGER.error("fout in verzenden berichten naar afnemer queue", e);
            throw new BrpServiceRuntimeException(e);
        }
    }
}
