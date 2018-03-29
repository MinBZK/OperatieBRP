/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Sync queue kanaal.
 */
public final class PartijAltQueueKanaal extends LazyLoadingKanaal {

    /**
     * Kanaal naam.
     */
    public static final String KANAAL = "partij_alt";

    /**
     * Constructor.
     */
    public PartijAltQueueKanaal() {
        super(new Worker(),
                new Configuration(
                        "classpath:configuratie.xml",
                        "classpath:infra-jms-isc.xml",
                        "classpath:infra-container-partij.xml",
                        "classpath:infra-queues-isc-partij.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        @Inject
        @Named("iscJmsTemplate")
        private JmsTemplate jmsTemplate;
        @Inject
        @Named("partijVerzoek")
        private Destination uitgaandDestination;

        @Inject
        @Named("partijRegisterService")
        private RegisterHandler registerHandler;
        @Inject
        @Named("partijRegisterListener")
        private DefaultMessageListenerContainer partijRegisterListener;

        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public void voorTestcase(final TestCasusContext testCasus) {
            partijRegisterListener.start();
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            jmsTemplate.send(uitgaandDestination, new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    return session.createTextMessage(bericht.getInhoud());
                }
            });
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
            try {
                return Herhaal.herhaalOperatie(new Callable<Bericht>() {
                    @Override
                    public Bericht call() {
                        return registerHandler.getBericht();
                    }
                });
            } catch (final HerhaalException e) {
                throw new KanaalException("Geen partijregister ontvangen", e);
            }
        }

        @Override
        public List<Bericht> naTestcase(final TestCasusContext testcasus) {
            partijRegisterListener.stop();

            return registerHandler.getBerichten();
        }

        @Override
        protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
            return VergelijkXml.vergelijkXml(verwachteInhoud, ontvangenInhoud);
        }
    }
}
