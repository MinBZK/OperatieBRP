/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.isc.environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.test.perf.isc.bericht.TestBericht;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Test omgeving (abstract).
 */
public final class TestEnvironment {

    private static final int HONDERD_LOOPS = 100;

    private static final long MILLIS = 3000L;

    private static final long LOOP_WACHTTIJD = 10L;

    private static final long LOOP_WACHTTIJD_IN_MILLIS = LOOP_WACHTTIJD * MILLIS;

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final DecimalFormat EREF_FORMAT = new DecimalFormat("000000");
    private static final SimpleDateFormat PREFIX_FORMAT = new SimpleDateFormat("HHmmss");
    private static final String EREF_PREFIX = PREFIX_FORMAT.format(new Date());

    /* TIMEOUT */
    private static final int TIMEOUT = 500;

    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("voiscOntvangstQueue")
    private Destination voiscOntvangst;

    @Inject
    private JdbcTemplate jdbcTemplate;

    private Long initEndedProcessCount = 0L;

    /**
     * Genereer (max lengte 12) ID. Let op: enkel uniek binnen JVM (implementatie dmv AtomicInteger).
     * @return id
     */
    public String generateId() {
        return EREF_PREFIX + EREF_FORMAT.format(COUNTER.getAndIncrement());
    }

    /**
     * Verzend een bericht.
     * @param testBericht testBericht
     */
    public void verzendBericht(final TestBericht testBericht) {
        jmsTemplate.send(voiscOntvangst, new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = session.createTextMessage(testBericht.getInhoud());
                final String messageId = generateId();
                message.setStringProperty(JMSConstants.BERICHT_MS_SEQUENCE_NUMBER, messageId);
                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, messageId);
                message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, testBericht.getLo3Gemeente());
                return message;
            }
        });
    }

    /**
     * Voer stappen voor de testcase uit.
     */
    public void beforeTestCase() {
        // Clear queues
        clearQueue(voiscOntvangst);

        initEndedProcessCount = getEndedProcessCount();
    }

    private void clearQueue(final Destination destination) {
        final long timeout = jmsTemplate.getReceiveTimeout();

        try {
            jmsTemplate.setReceiveTimeout(TIMEOUT);
            while (jmsTemplate.receive(destination) != null) {
                LOG.error("Message on queue before test case!!!!");
            }
        } finally {
            jmsTemplate.setReceiveTimeout(timeout);
        }
    }

    /**
     * Dump voisc.ontvangst queue naar outputDirectory.
     * @param outputDirectory De outputDirectory waarheen gedumpt wordt.
     * @return true als er iets gedumpt is.
     */
    public boolean dumpQueues(final File outputDirectory) {
        return !dumpQueue(voiscOntvangst, outputDirectory, "9999-uit-voisc-");
    }

    /**
     * Dump queue naar file.
     * @param destination De JMX Destination waarvan we berichten lezen
     * @param outputDirectory De outputDirectory waarheen gedumpt wordt.
     * @param prefix De file prefix voor de dump.
     * @return true als er niets te dumpen viel.
     */
    private boolean dumpQueue(final Destination destination, final File outputDirectory, final String prefix) {
        final long timeout = jmsTemplate.getReceiveTimeout();

        long volgnummer = 0;

        try {
            jmsTemplate.setReceiveTimeout(TIMEOUT);
            Message message;
            while ((message = jmsTemplate.receive(destination)) != null) {
                LOG.info("Onverwacht bericht op queue na testcase");

                final File outputFile = new File(outputDirectory, prefix + volgnummer++ + ".txt");

                outputFile.getParentFile().mkdirs();
                try (FileOutputStream fis = new FileOutputStream(outputFile)) {
                    final PrintWriter writer = new PrintWriter(fis);
                    writer.print(((TextMessage) message).getText());
                    writer.close();
                } catch (final IOException e) {
                    LOG.info("Probleem bij wegschrijven bericht.", e);
                } catch (final JMSException e) {
                    LOG.info("Probleem bij versturen bericht.", e);
                }
            }
        } finally {
            jmsTemplate.setReceiveTimeout(timeout);
        }

        return volgnummer == 0;
    }

    /**
     * Voer stappen na de testcase uit.
     * @param wanted Aantal verwachte berichten.
     * @param started Starttijd.
     * @throws InterruptedException kan worden gegooid bij aanroepen van TimeUnit.MILLISECONDS.sleep().
     */
    public void afterTestCase(final long wanted, final Date started) throws InterruptedException {
        // Wanted number of messages
        LOG.info(new Date() + "; Wanted: " + wanted);
        LOG.info(started + "; Started");

        long current = 0;

        // give ten minute warmup initially
        int loopsCurrentSame = -HONDERD_LOOPS;

        while (current < wanted) {
            final long processCount = getEndedProcessCount() - initEndedProcessCount;
            LOG.info(new Date() + "; Processes ended so far: " + processCount);

            if (current == processCount) {
                loopsCurrentSame++;
            } else {
                current = processCount;
                loopsCurrentSame = 0;
            }

            if (loopsCurrentSame >= HONDERD_LOOPS) {
                LOG.info(new Date() + "; No processes ended (in ten minutes); breaking ...");
                break;
            }

            // Check each 10 seconds
            TimeUnit.MILLISECONDS.sleep(LOOP_WACHTTIJD_IN_MILLIS);
        }

        LOG.info(new Date() + "; Ending with: " + current);
    }

    /**
     * Geef de waarde van ended process count.
     * @return Het aantal beeindigde processen.
     */
    public Long getEndedProcessCount() {
        try {
            final String sql = "select count(*) from mig_extractie_proces where proces_naam = 'uc202' and einddatum is not null";
            return jdbcTemplate.queryForObject(sql, Long.class);
        } catch (final DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Controleer of het aantal beeindigde processen gelijk is aan het verwachte aantal.
     * @param expectedAmount Het verwachte aantal.
     * @return of het aantal beeindigde processen gelijk is aan het verwachte aantal.
     */
    public boolean verifyEndedProcesses(final long expectedAmount) {
        boolean result;

        if (expectedAmount == getEndedProcessCount() - initEndedProcessCount) {
            LOG.info("Amount of ended processes is correct");
            result = true;
        } else {
            LOG.info("Expected amount of ended processes: " + expectedAmount);
            LOG.info("Initial amount of ended processes: " + initEndedProcessCount);
            LOG.info("Current amount of ended processes: " + getEndedProcessCount());
            LOG.info("Amount of ended processes during this test: " + (getEndedProcessCount() - initEndedProcessCount));
            result = false;
        }

        return result;
    }
}
