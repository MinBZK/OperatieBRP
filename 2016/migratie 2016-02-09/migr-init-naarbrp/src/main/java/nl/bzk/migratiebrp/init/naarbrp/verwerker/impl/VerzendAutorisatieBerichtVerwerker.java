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
import nl.bzk.migratiebrp.bericht.model.sync.SyncBerichtType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AutorisatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.AutorisatieBerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

/**
 * Verstuurt AutorisatieBerichten.
 */
public class VerzendAutorisatieBerichtVerwerker implements AutorisatieBerichtVerwerker {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String MSG_ID_FORMAT = "%s-%s";

    private final AutorisatieRepository autorisatieRepository;
    private final List<AutorisatieBericht> autorisatieBerichten;
    private final ConversieResultaat verwerktStatus;
    private final Destination destination;
    private final JmsTemplate jmsTemplate;
    private boolean verzonden = false;

    /**
     * Constructor.
     *
     * @param destination
     *            {@link Destination} queue waarop berichten geplaatst moeten worden.
     * @param jmsTemplate
     *            {@link JmsTemplate}
     * @param autorisatieRepository
     *            De AutorisatieRepository waaruit de autorisatie berichten moeten worden gelezen
     * @param verwerktStatus
     *            {@link ConversieResultaat} status die na afloop gezet moet worden.
     */
    public VerzendAutorisatieBerichtVerwerker(
        final Destination destination,
        final JmsTemplate jmsTemplate,
        final AutorisatieRepository autorisatieRepository,
        final ConversieResultaat verwerktStatus)
    {
        autorisatieBerichten = new ArrayList<>();
        this.verwerktStatus = verwerktStatus;
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.autorisatieRepository = autorisatieRepository;
    }

    @Override
    public final void voegBerichtToe(final AutorisatieBericht autorisatieBericht) {
        if (verzonden) {
            throw new IllegalStateException("Berichten zijn al verzonden");
        }
        autorisatieBerichten.add(autorisatieBericht);
    }

    @Override
    public final int aantalBerichten() {
        return autorisatieBerichten.size();
    }

    @Override
    public final void verwerkBerichten() {
        if (verzonden) {
            throw new IllegalStateException("Berichten zijn al verzonden");
        }
        verzonden = true;

        LOG.info("Aantal te verwerken autorisatie berichten: {}", autorisatieBerichten.size());
        if (!autorisatieBerichten.isEmpty()) {
            LOG.info("Versturen berichten");
            verstuurAutorisatieBericht(autorisatieBerichten);

            LOG.info("Update status naar {}", verwerktStatus);
            final List<Integer> afnemerCodes = new ArrayList<>(autorisatieBerichten.size());
            for (final AutorisatieBericht autorisatieBericht : autorisatieBerichten) {
                afnemerCodes.add(autorisatieBericht.getAutorisatie().getAfnemersindicatie());
            }
            autorisatieRepository.updateAutorisatieBerichtStatus(afnemerCodes, verwerktStatus);
        }
        LOG.info("Berichten verwerkt");
    }

    private void verstuurAutorisatieBericht(final List<AutorisatieBericht> teVersturenBerichten) {
        jmsTemplate.execute(destination, new ProducerCallback<Object>() {
            @Override
            public Object doInJms(final Session session, final MessageProducer producer) throws JMSException {
                LOG.info("aantal te versturen berichten: {}", teVersturenBerichten.size());
                for (final AutorisatieBericht autorisatieBericht : teVersturenBerichten) {
                    final Message message = session.createTextMessage(autorisatieBericht.format());
                    message.setStringProperty(
                        JMSConstants.BERICHT_REFERENTIE,
                        String.format(MSG_ID_FORMAT, SyncBerichtType.AUTORISATIE.getType(), autorisatieBericht.getMessageId()));
                    producer.send(message);
                }
                LOG.info("Berichten verstuurd");
                return null;
            }
        });
    }
}
