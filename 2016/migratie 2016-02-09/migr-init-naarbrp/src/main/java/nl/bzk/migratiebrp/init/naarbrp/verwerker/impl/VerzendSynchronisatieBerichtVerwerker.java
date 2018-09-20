/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.verwerker.impl;

import java.util.ArrayList;
import java.util.List;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.GbavRepository;
import nl.bzk.migratiebrp.init.naarbrp.service.bericht.SyncNaarBrpBericht;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.SynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

/**
 * Verstuurt Lo3Berichten.
 */
public class VerzendSynchronisatieBerichtVerwerker implements SynchronisatieBerichtVerwerker {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final GbavRepository gbavRepository;

    private final List<SyncNaarBrpBericht> lo3Berichten;
    private final ConversieResultaat verwerktStatus;
    private final Destination destination;
    private final JmsTemplate jmsTemplate;
    private boolean verzonden = false;

    /**
     * Constructor.
     *
     * @param queueBatchSize
     *            Queue batch size
     * @param destination
     *            {@link Destination} queue waarop berichten geplaatst moeten worden.
     * @param jmsTemplate
     *            {@link JmsTemplate}
     * @param gbavRepository
     *            De GbavRepository waaruit de PLen moeten worden gelezen
     * @param verwerktStatus
     *            {@link ConversieResultaat} status die na afloop gezet moet worden.
     */
    public VerzendSynchronisatieBerichtVerwerker(
        final Destination destination,
        final JmsTemplate jmsTemplate,
        final GbavRepository gbavRepository,
        final ConversieResultaat verwerktStatus)
    {
        lo3Berichten = new ArrayList<>();
        this.verwerktStatus = verwerktStatus;
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.gbavRepository = gbavRepository;
    }

    @Override
    public final void voegBerichtToe(final SyncNaarBrpBericht bericht) {
        if (verzonden) {
            throw new IllegalStateException("Berichten zijn al verzonden");
        }
        lo3Berichten.add(bericht);
    }

    @Override
    public final int aantalBerichten() {
        return lo3Berichten.size();
    }

    @Override
    public final void verwerkBerichten() {
        if (verzonden) {
            throw new IllegalStateException("Berichten zijn al verzonden");
        }
        verzonden = true;

        LOG.debug("Aantal te verwerken persoons berichten: {}", lo3Berichten.size());
        if (!lo3Berichten.isEmpty()) {
            LOG.info("Versturen berichten");
            verstuurBericht(lo3Berichten);

            LOG.info("Update status naar {}", verwerktStatus);
            final List<Long> aNummers = new ArrayList<>(lo3Berichten.size());
            for (final SyncNaarBrpBericht naarBrpBericht : lo3Berichten) {
                aNummers.add(naarBrpBericht.getANummer());
            }
            gbavRepository.updateLo3BerichtStatus(aNummers, verwerktStatus);
        }
        LOG.info("Berichten verwerkt");
    }

    private void verstuurBericht(final List<SyncNaarBrpBericht> teVersturenBerichten) {
        jmsTemplate.execute(destination, new ProducerCallback<Object>() {
            @Override
            public Object doInJms(final Session session, final MessageProducer producer) throws JMSException {
                for (final SyncNaarBrpBericht lo3Bericht : teVersturenBerichten) {
                    final Message message = session.createTextMessage(lo3Bericht.getBericht());
                    message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, lo3Bericht.getMessageId());
                    producer.send(message);
                }
                return null;
            }
        });
    }
}
