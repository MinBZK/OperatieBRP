/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.levering.environment;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.test.perf.levering.bericht.TestBericht;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Test omgeving (abstract).
 */
public final class TestEnvironment {

    private static final String UNSUPPORTED_OPERATION_MELDING = "TODO: Moet geimplementeerd worden op activemq queue";

    private static final long VERWACHT_AANTAL_LEVERINGSBERICHTEN = 1200000L;

    private static final int DERTIG_LOOPS = 30;

    private static final long MINUTEN_PER_UUR = 60L;

    private static final long SECONDEN_PER_MINUUT = 60L;

    private static final long MILLIS = 1000L;

    private static final long LOOP_WACHTTIJD = 10L;

    private static final long LOOP_WACHTTIJD_IN_MILLIS = LOOP_WACHTTIJD * MILLIS;

    private static final Long INITIEEL_VERZONDEN_LEVERINGSBERICHTEN_COUNTER = 0L;

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final AtomicLong MESSAGE_ID = new AtomicLong();

    @Inject
    @Named("leveringOntvangstQueue")
    private Destination leveringOntvangst;

    @Inject
    private JmsTemplate jmsTemplate;

    /**
     * Verzend een bericht.
     *
     * @param testBericht
     *            testBericht
     */
    public void verzendBericht(final TestBericht testBericht) {
        jmsTemplate.send(leveringOntvangst, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(testBericht.getInhoud());
                final String messageId = "TST" + MESSAGE_ID.getAndIncrement();
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);
                message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, "3000200");
                message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, testBericht.getLo3Gemeente());
                return message;
            }
        });
    }

    /**
     * Voer stappen voor de testcase uit.
     */
    public void beforeTestCase() {
        // Clear queues via JMX
        // try {
        // for (final JndiTemplate jndiTemplate : jndiTemplates) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MELDING);
        // final InitialContext ctx = new InitialContext(jndiTemplate.getContext().getEnvironment());
        // final RMIAdaptor rmiAdaptor = (RMIAdaptor) ctx.lookup(RMI_ADAPTOR_JNDI_NAME);
        // if (Long.valueOf((Integer) rmiAdaptor.getAttribute(new
        // ObjectName(LEVERING_ONTVANGST_QUEUE_OBJECT_NAME), MESSAGE_COUNT_ATTRIBUTE)) > 0) {
        // LOG.error("Message on queue before test case (queue = levering.ontvangst)!!!!");
        // rmiAdaptor.invoke(new ObjectName(LEVERING_ONTVANGST_QUEUE_OBJECT_NAME),
        // REMOVE_ALL_MESSAGES_OPERATION, null, null);
        // }
        // if (Long.valueOf((Integer) rmiAdaptor.getAttribute(new ObjectName(VOSPG_VERZENDEN_QUEUE_OBJECT_NAME),
        // MESSAGE_COUNT_ATTRIBUTE)) > 0) {
        // LOG.error("Message on queue before test case (queue = vospg.verzenden)!!!!");
        // rmiAdaptor.invoke(new ObjectName(VOSPG_VERZENDEN_QUEUE_OBJECT_NAME), REMOVE_ALL_MESSAGES_OPERATION,
        // null, null);
        // }
        // }

        // } catch (final
        // NamingException
        // | AttributeNotFoundException
        // | InstanceNotFoundException
        // | MalformedObjectNameException
        // | MBeanException
        // | ReflectionException
        // | IOException e)
        // {
        // e.printStackTrace();
        // }
        //
        // initieelVerzondenLeveringsberichtenCounter = getVerzondenLeveringsberichtenCounter();
    }

    /**
     * Voer stappen na de testcase uit.
     *
     * @param wanted
     *            Aantal verwachte berichten.
     * @param started
     *            Starttijd.
     * @throws InterruptedException
     *             kan worden gegooid bij aanroepen van Thread.sleep().
     */
    public void afterTestCase(final long wanted, final Date started) throws InterruptedException {
        // Wanted number of messages
        LOG.info(new Date() + "; Aantal te verzenden leveringsberichten: " + wanted);
        LOG.info(started + "; Verzenden leveringsberichten gestart om " + started);

        long current = 0;
        long previous;

        // give ten minute warmup initially
        int loopsCurrentSame = -DERTIG_LOOPS;

        while (current < wanted) {
            previous = current;
            final long verzondenLeveringsberichtenCounter = getVerzondenLeveringsberichtenCounter() - INITIEEL_VERZONDEN_LEVERINGSBERICHTEN_COUNTER;
            final long gemiddeldeSnelheidInterval = (verzondenLeveringsberichtenCounter - previous) / LOOP_WACHTTIJD;
            final long looptijd = Math.max(1, (System.currentTimeMillis() - started.getTime()) / MILLIS);
            final long gemiddeldeSnelheidTest = Math.max(1, verzondenLeveringsberichtenCounter / looptijd);
            final long verwachteDuur = wanted / gemiddeldeSnelheidTest + LOOP_WACHTTIJD;
            final long verwachteDuurUren = TimeUnit.SECONDS.toHours(verwachteDuur);
            final long verwachteDuurMinuten = TimeUnit.SECONDS.toMinutes(verwachteDuur) - TimeUnit.SECONDS.toHours(verwachteDuur) * MINUTEN_PER_UUR;
            final long verwachteDuurSeconde = TimeUnit.SECONDS.toSeconds(verwachteDuur) - TimeUnit.SECONDS.toMinutes(verwachteDuur) * SECONDEN_PER_MINUUT;
            final long verwachteDuurProductie = VERWACHT_AANTAL_LEVERINGSBERICHTEN / gemiddeldeSnelheidTest;
            final long verwachteDuurUrenProductie = TimeUnit.SECONDS.toHours(verwachteDuurProductie);
            final long verwachteDuurMinutenProductie =
                    TimeUnit.SECONDS.toMinutes(verwachteDuurProductie) - TimeUnit.SECONDS.toHours(verwachteDuurProductie) * MINUTEN_PER_UUR;
            final long verwachteDuurSecondeProductie =
                    TimeUnit.SECONDS.toSeconds(verwachteDuurProductie) - TimeUnit.SECONDS.toMinutes(verwachteDuurProductie) * SECONDEN_PER_MINUUT;
            final long verwachteResterendeTijd = (wanted - verzondenLeveringsberichtenCounter) / gemiddeldeSnelheidTest;
            final Timestamp verwachteEindtijd = new Timestamp(System.currentTimeMillis() + verwachteResterendeTijd * MILLIS + LOOP_WACHTTIJD_IN_MILLIS);

            LOG.info("\n\n"
                     + new Date()
                     + "; Verzonden leveringsberichten tot nu toe: "
                     + verzondenLeveringsberichtenCounter
                     + "\nGemiddelde snelheid interval: "
                     + gemiddeldeSnelheidInterval
                     + " leveringsberichten/s. "
                     + "\nGemiddelde snelheid test: "
                     + gemiddeldeSnelheidTest
                     + " leveringsberichten/s."
                     + "\nVerwachte duur: "
                     + verwachteDuur
                     + " seconde"
                     + "("
                     + verwachteDuurUren
                     + " uren  "
                     + verwachteDuurMinuten
                     + " minuten  "
                     + verwachteDuurSeconde
                     + " seconde )"
                     + "\nVerwachte duur productie set (1.200.000 leveringsberichten): "
                     + verwachteDuurProductie
                     + " seconde ("
                     + verwachteDuurUrenProductie
                     + " uren "
                     + verwachteDuurMinutenProductie
                     + " minuten "
                     + verwachteDuurSecondeProductie
                     + " seconde)"
                     + "\nVerwachte eindtijd: "
                     + verwachteEindtijd);

            if (current == verzondenLeveringsberichtenCounter) {
                loopsCurrentSame++;
            } else {
                current = verzondenLeveringsberichtenCounter;
                loopsCurrentSame = 0;
            }

            if (loopsCurrentSame >= DERTIG_LOOPS) {
                LOG.info(new Date() + "; Geen leveringsberichten verzonden (in vijf minuten); pauzeren ...");
                break;
            }

            // Check each 10 seconds
            Thread.sleep(LOOP_WACHTTIJD_IN_MILLIS);
        }

        LOG.info(new Date() + "; Geëindigd met: " + current);

    }

    /**
     * Geef de waarde van verzonden leveringsberichten counter.
     *
     * @return Het aantal verzonden leveringsberichten.
     */
    public Long getVerzondenLeveringsberichtenCounter() {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MELDING);
        //
        // try {
        // long totaalAantalVerzondenLeveringsberichten = 0;
        //
        // for (final JndiTemplate jndiTemplate : jndiTemplates) {
        // final InitialContext ctx = new InitialContext(jndiTemplate.getContext().getEnvironment());
        // final RMIAdaptor rmiAdaptor = (RMIAdaptor) ctx.lookup(RMI_ADAPTOR_JNDI_NAME);
        // totaalAantalVerzondenLeveringsberichten +=
        // Long.valueOf((Integer) rmiAdaptor.getAttribute(new ObjectName(VOSPG_VERZENDEN_QUEUE_OBJECT_NAME),
        // MESSAGE_COUNT_ATTRIBUTE));
        // }
        // return totaalAantalVerzondenLeveringsberichten;
        //
        // } catch (final
        // NamingException
        // | AttributeNotFoundException
        // | InstanceNotFoundException
        // | MalformedObjectNameException
        // | MBeanException
        // | ReflectionException
        // | IOException e)
        // {
        // e.printStackTrace();
        // return 0L;
        // }
    }

    /**
     * Controleer of het aantal beeindigde leveringsberichten gelijk is aan het verwachte aantal.
     *
     * @param verwachtAantal
     *            Het verwachte aantal.
     * @return of het aantal beeindigde leveringsberichten gelijk is aan het verwachte aantal.
     */
    public boolean verifieerBeeindigdeLeveringsberichten(final long verwachtAantal) {
        if (verwachtAantal == getVerzondenLeveringsberichtenCounter() - INITIEEL_VERZONDEN_LEVERINGSBERICHTEN_COUNTER) {
            LOG.info("Verwacht aantal verzonden leveringsberichten is correct.");
        } else {
            LOG.info("Verwacht aantal verzonden leveringsberichten: " + verwachtAantal);
            LOG.info("Initieel aantal verzonden leveringsberichten: " + INITIEEL_VERZONDEN_LEVERINGSBERICHTEN_COUNTER);
            LOG.info("Huidig aantal verzonden leveringsberichten: " + getVerzondenLeveringsberichtenCounter());
            LOG.info("Aantal verzonden leveringsberichten in deze test: "
                     + (getVerzondenLeveringsberichtenCounter() - INITIEEL_VERZONDEN_LEVERINGSBERICHTEN_COUNTER));
        }

        // Clear queues via JMX
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MELDING);
        // try {
        //
        // for (final JndiTemplate jndiTemplate : jndiTemplates) {
        // final InitialContext ctx = new InitialContext(jndiTemplate.getContext().getEnvironment());
        // final RMIAdaptor rmiAdaptor = (RMIAdaptor) ctx.lookup(RMI_ADAPTOR_JNDI_NAME);
        // if (Long.valueOf((Integer) rmiAdaptor.getAttribute(new ObjectName(LEVERING_ONTVANGST_QUEUE_OBJECT_NAME),
        // MESSAGE_COUNT_ATTRIBUTE)) > 0) {
        // LOG.info("Opschonen queue na test case (queue = levering.ontvangst)...");
        // rmiAdaptor.invoke(new ObjectName(LEVERING_ONTVANGST_QUEUE_OBJECT_NAME), REMOVE_ALL_MESSAGES_OPERATION, null,
        // null);
        // }
        // if (Long.valueOf((Integer) rmiAdaptor.getAttribute(new ObjectName(VOSPG_VERZENDEN_QUEUE_OBJECT_NAME),
        // MESSAGE_COUNT_ATTRIBUTE)) > 0) {
        // LOG.info("Opschonen queue na test case (queue = vospg.verzenden)...");
        // rmiAdaptor.invoke(new ObjectName(VOSPG_VERZENDEN_QUEUE_OBJECT_NAME), REMOVE_ALL_MESSAGES_OPERATION, null,
        // null);
        // }
        // }
        //
        // } catch (final
        // NamingException
        // | AttributeNotFoundException
        // | InstanceNotFoundException
        // | MalformedObjectNameException
        // | MBeanException
        // | ReflectionException
        // | IOException e)
        // {
        // e.printStackTrace();
        // }

        // return result;
    }
}
