/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.domein.ProefSynchronisatieBericht;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.ProefSynchronisatieRepository;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.BerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

/**
 * Verstuurt proefsynchronisatie berichten.
 */
public final class JMSProefSynchronisatieBerichtVerwerker extends AbstractProefSynchronisatieBerichtVerwerker implements
        BerichtVerwerker<ProefSynchronisatieBericht>
{

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String KON_BERICHTEN_NIET_VERZENDEN = "Kon berichten niet verzenden.";

    private final Destination destination;
    private final JmsTemplate jmsTemplate;

    /**
     * Constructor.
     *
     * @param executor
     *            {@link ThreadPoolExecutor}
     * @param destination
     *            {@link Destination} queue waarop berichten geplaatst moeten worden.
     * @param jmsTemplate
     *            {@link JmsTemplate}
     * @param proefSynchronisatieRepository
     *            De repository waaruit de proefsynchronisatie berichten moeten worden gelezen
     */
    public JMSProefSynchronisatieBerichtVerwerker(
        final ThreadPoolExecutor executor,
        final Destination destination,
        final JmsTemplate jmsTemplate,
        final ProefSynchronisatieRepository proefSynchronisatieRepository)
    {
        super(executor, proefSynchronisatieRepository);
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    protected List<ProefSynchronisatieBericht> verstuurProefSynchronisatieBerichten(final List<ProefSynchronisatieBericht> teVersturenBerichten) {
        try {
            Herhaal.herhaalOperatie(new VerstuurProefSynchronisatieBerichtAanroep(teVersturenBerichten));
        } catch (final HerhaalException e) {
            LOG.error(KON_BERICHTEN_NIET_VERZENDEN, e);
            return Collections.emptyList();
        }
        return teVersturenBerichten;
    }

    /**
     * Een callable object voor het asynchroon versturen van proefsynchronisatie berichten.
     */
    private final class VerstuurProefSynchronisatieBerichtAanroep implements Callable<Object> {
        private final List<ProefSynchronisatieBericht> proefSynchronisatieBerichten;

        private VerstuurProefSynchronisatieBerichtAanroep(final List<ProefSynchronisatieBericht> proefSynchronisatieBerichten) {
            this.proefSynchronisatieBerichten = proefSynchronisatieBerichten;
        }

        @Override
        public Object call() {
            jmsTemplate.execute(destination, new ProducerCallback<Object>() {
                @Override
                public Object doInJms(final Session session, final MessageProducer producer) throws JMSException {
                    for (final ProefSynchronisatieBericht proefSynchronisatieBericht : proefSynchronisatieBerichten) {
                        final TextMessage message = session.createTextMessage(proefSynchronisatieBericht.getBericht());
                        message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, String.valueOf(proefSynchronisatieBericht.getBerichtId()));
                        if (proefSynchronisatieBericht.getAfzender() != null) {
                            message.setStringProperty(
                                JMSConstants.BERICHT_ORIGINATOR,
                                new DecimalFormat("0000").format(proefSynchronisatieBericht.getAfzender()));
                        }
                        message.setStringProperty(
                            JMSConstants.BERICHT_MS_SEQUENCE_NUMBER,
                            String.valueOf(proefSynchronisatieBericht.getMsSequenceNumber()));
                        producer.send(destination, message);
                    }
                    return null;
                }
            });
            return null;
        }
    }
}
