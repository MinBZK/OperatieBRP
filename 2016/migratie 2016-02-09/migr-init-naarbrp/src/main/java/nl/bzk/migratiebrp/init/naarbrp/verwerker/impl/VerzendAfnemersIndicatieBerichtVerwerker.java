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
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AfnemersIndicatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.AfnemersindicatieBerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

/**
 * Verstuurt AfnemersindicatiesBerichten.
 */
public class VerzendAfnemersIndicatieBerichtVerwerker implements AfnemersindicatieBerichtVerwerker {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String MSG_ID_FORMAT = "%s-%s";

    private final AfnemersIndicatieRepository afnemersIndicatieRepository;
    private final List<AfnemersindicatiesBericht> afnemersIndicatieBerichten;
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
     * @param afnemersIndicatieRepository
     *            De AfnemersIndicatieRepository waaruit de afnemersindicaties moeten worden gelezen
     * @param verwerktStatus
     *            {@link ConversieResultaat} status die na afloop gezet moet worden.
     */
    public VerzendAfnemersIndicatieBerichtVerwerker(
        final Destination destination,
        final JmsTemplate jmsTemplate,
        final AfnemersIndicatieRepository afnemersIndicatieRepository,
        final ConversieResultaat verwerktStatus)
    {
        afnemersIndicatieBerichten = new ArrayList<>();
        this.verwerktStatus = verwerktStatus;
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.afnemersIndicatieRepository = afnemersIndicatieRepository;
    }

    @Override
    public final void voegBerichtToe(final AfnemersindicatiesBericht afnemersIndicatieBericht) {
        if (verzonden) {
            throw new IllegalStateException("Berichten zijn al verzonden");
        }
        afnemersIndicatieBerichten.add(afnemersIndicatieBericht);
    }

    @Override
    public final int aantalBerichten() {
        return afnemersIndicatieBerichten.size();
    }

    @Override
    public final void verwerkBerichten() {
        if (verzonden) {
            throw new IllegalStateException("Berichten zijn al verzonden");
        }
        verzonden = true;

        LOG.debug("Aantal te verwerken afnemersindicaties berichten: {}", afnemersIndicatieBerichten.size());
        if (!afnemersIndicatieBerichten.isEmpty()) {
            LOG.info("Versturen berichten");
            verstuurAfnemersIndicatieBericht(afnemersIndicatieBerichten);

            LOG.info("Update status naar {}", verwerktStatus);
            final List<Long> aNummers = new ArrayList<>(afnemersIndicatieBerichten.size());
            for (final AfnemersindicatiesBericht autorisatieBericht : afnemersIndicatieBerichten) {
                aNummers.add(autorisatieBericht.getAfnemersindicaties().getANummer());
            }
            afnemersIndicatieRepository.updateAfnemersIndicatiesBerichtStatus(aNummers, verwerktStatus);
        }
        LOG.info("Berichten verwerkt");
    }

    private void verstuurAfnemersIndicatieBericht(final List<AfnemersindicatiesBericht> afnemersIndicatiesBerichten) {
        jmsTemplate.execute(destination, new ProducerCallback<Object>() {
            @Override
            public Object doInJms(final Session session, final MessageProducer producer) throws JMSException {
                for (final AfnemersindicatiesBericht afnIndBericht : afnemersIndicatiesBerichten) {
                    final Message message = session.createTextMessage(afnIndBericht.format());
                    message.setStringProperty(
                        JMSConstants.BERICHT_REFERENTIE,
                        String.format(MSG_ID_FORMAT, SyncBerichtType.AFNEMERINDICATIE.getType(), afnIndBericht.getAfnemersindicaties().getANummer()));
                    producer.send(message);
                }
                return null;
            }
        });
    }
}
