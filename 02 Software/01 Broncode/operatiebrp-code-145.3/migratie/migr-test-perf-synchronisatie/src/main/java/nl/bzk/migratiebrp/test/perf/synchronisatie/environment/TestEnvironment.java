/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie.environment;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.management.MBeanServerConnection;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.perf.synchronisatie.bericht.TestBericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;

import org.junit.Assert;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Test omgeving.
 */
public final class TestEnvironment {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String CONTROLE_SQL =
            "select count(*) from mig_extractieprocessen where procesNaam = 'uc202' and einddatum is not null " + "and wachtstartdatum is null";
    private static final String ERROR_MAILBOX_NIET_GEVONDEN = "Mailbox voor instantie '%s' niet gevonden.";

    private static final int DERTIG_LOOPS = 30;

    private static final long MINUTEN_PER_UUR = 60L;

    private static final long SECONDEN_PER_MINUUT = 60L;

    private static final long MILLIS = 1000L;

    private static final long LOOP_WACHTTIJD = 10L;

    private static final long LOOP_WACHTTIJD_IN_MILLIS = LOOP_WACHTTIJD * MILLIS;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private MailboxRepository mailboxRepository;

    @Inject
    private MailboxClient mailboxServerProxy;

    private Long initieelBeeindigdeProcessenCounter = 0L;

    @Inject
    @Named("mailboxJmxConnector")
    private MBeanServerConnection connection;

    /**
     * Mailbox proxy initializator.
     * @return Mailbox client.
     */
    public synchronized MailboxClient initializeMailboxServerProxy() {
        return mailboxServerProxy;
    }

    /**
     * Verzend een testbericht met behulp van de mailbox client.
     * @param testBericht Het te verzenden bericht.
     * @param mailboxClient De mailbox client.
     */
    public synchronized void verzendBericht(final TestBericht testBericht, final MailboxClient mailboxClient) {
        // We moeten een bericht versturen via de mailbox van de bronInstantie
        LOG.info(
                "Start versturen bericht# "
                        + testBericht.getVolgnummer()
                        + " namens instantie {} naar instantie {}.",
                testBericht.getVerzendendePartij(),
                testBericht.getOntvangendePartij());
        final Mailbox mailbox = mailboxRepository.getMailboxByPartijcode(testBericht.getVerzendendePartij());
        LOG.info("Verwerk uitgaand bericht voor mailbox: {}", mailbox.getMailboxnr());
        Assert.assertNotNull(String.format(ERROR_MAILBOX_NIET_GEVONDEN, testBericht.getVerzendendePartij()), mailbox);

        final Mailbox recipient = mailboxRepository.getMailboxByPartijcode(testBericht.getOntvangendePartij());
        Assert.assertNotNull(String.format(ERROR_MAILBOX_NIET_GEVONDEN, testBericht.getOntvangendePartij()), mailbox);

        final nl.bzk.migratiebrp.voisc.database.entities.Bericht mailboxBericht = new nl.bzk.migratiebrp.voisc.database.entities.Bericht();
        mailboxBericht.setMessageId(testBericht.getVolgnummer().toString());
        mailboxBericht.setBerichtInhoud(testBericht.getInhoud());
        mailboxBericht.setOriginator(mailbox.getMailboxnr());
        mailboxBericht.setRecipient(recipient.getMailboxnr());
        mailboxBericht.setStatus(StatusEnum.SENDING_TO_MAILBOX);

        try (final Connection mailboxConnection = mailboxClient.connect()) {
            mailboxClient.logOn(mailboxConnection, mailbox);

            // Versturen berichten naar Mailbox
            mailboxClient.putMessage(mailboxConnection, mailboxBericht);
        } catch (final VoaException e) {
            throw new RuntimeException("Fout bij versturen bericht.", e);
        }
    }

    /**
     * Voer stappen voor de testcase uit.
     */
    public synchronized void beforeTestCase() {

        try {
            initieelBeeindigdeProcessenCounter = jdbcTemplate.queryForObject(CONTROLE_SQL, Long.class);
        } catch (final DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param wanted Aantal verwachte berichten.
     * @param started Starttijd.
     * @throws InterruptedException kan worden gegooid bij aanroepen van TimeUnit.MILLISECONDS.sleep().
     */
    public void afterTestCase(final long wanted, final Date started) throws InterruptedException {
        // Gewenste aantal berichten
        LOG.info(new Date() + "; Aantal te beeindigen processen: " + wanted);
        LOG.info(started + "; Starten processen gestart om " + started);

        long current = 0;
        long previous;

        // give ten minute warmup initially
        int loopsCurrentSame = -DERTIG_LOOPS;

        while (current < wanted) {
            previous = current;
            final long verzondenLeveringsberichtenCounter = getBeeindigdeProcessenCounter() - initieelBeeindigdeProcessenCounter;
            final long gemiddeldeSnelheidInterval = (verzondenLeveringsberichtenCounter - previous) / LOOP_WACHTTIJD;
            final long looptijd = Math.max(1, (System.currentTimeMillis() - started.getTime()) / MILLIS);
            final long gemiddeldeSnelheidTest = Math.max(1, verzondenLeveringsberichtenCounter / looptijd);
            final long verwachteResterendeTijd = (wanted - verzondenLeveringsberichtenCounter) / gemiddeldeSnelheidTest;
            final long verwachteResterendeDuurUren = TimeUnit.SECONDS.toHours(verwachteResterendeTijd);
            final long verwachteResterendeDuurMinuten =
                    TimeUnit.SECONDS.toMinutes(verwachteResterendeTijd) - TimeUnit.SECONDS.toHours(verwachteResterendeTijd) * MINUTEN_PER_UUR;
            final long verwachteResterendeDuurSeconde =
                    TimeUnit.SECONDS.toSeconds(verwachteResterendeTijd) - TimeUnit.SECONDS.toMinutes(verwachteResterendeTijd) * SECONDEN_PER_MINUUT;
            final Timestamp verwachteEindtijd = new Timestamp(System.currentTimeMillis() + verwachteResterendeTijd * MILLIS + LOOP_WACHTTIJD_IN_MILLIS);

            LOG.info(
                    "\n\n"
                            + new Date()
                            + "; Beeindigde processen interval: "
                            + (verzondenLeveringsberichtenCounter - previous)
                            + "\nBeeindigde processen tot nu toe: "
                            + verzondenLeveringsberichtenCounter
                            + "\nGemiddelde snelheid interval: "
                            + gemiddeldeSnelheidInterval
                            + " Beeindigde processen/s. "
                            + "\nGemiddelde snelheid test: "
                            + gemiddeldeSnelheidTest
                            + " Beeindigde processen/s."
                            + "\nVerwachte resterende duur: "
                            + verwachteResterendeTijd
                            + " seconde"
                            + "("
                            + verwachteResterendeDuurUren
                            + " uren "
                            + verwachteResterendeDuurMinuten
                            + " minuten "
                            + verwachteResterendeDuurSeconde
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
                LOG.info(new Date() + "; Geen beeindigde processen gevonden (in vijf minuten); pauzeren ...");
                break;
            }

            // Check each 10 seconds
            TimeUnit.MILLISECONDS.sleep(LOOP_WACHTTIJD_IN_MILLIS);
        }

        LOG.info(new Date() + "; Geëindigd met: " + current);

    }

    /**
     * Geef de waarde van beeindigde processen counter.
     * @return hoeveel processen er al beeindigd zijn.
     */
    public Long getBeeindigdeProcessenCounter() {
        try {
            return jdbcTemplate.queryForObject(CONTROLE_SQL, Long.class);
        } catch (final DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifieer of het aantal beeindigde processen kleiner is dan het verwachte aantal.
     * @param verwachtAantal Het verwachte aantal
     * @return true als het aantal beeindigde processen kleiner is dan het verwachte aantal.
     */
    public boolean verifieerBeeindigdeProcessen(final long verwachtAantal) {
        boolean result;

        if (verwachtAantal == getBeeindigdeProcessenCounter() - initieelBeeindigdeProcessenCounter) {
            LOG.info("Verwacht aantal verzonden beeindigde testgevallen is correct.");
            result = true;
        } else {
            LOG.info("Verwacht aantal beeindigde processen: " + verwachtAantal);
            LOG.info("Initieel aantal beeindigde processen: " + initieelBeeindigdeProcessenCounter);
            LOG.info("Huidig aantal beeindigde processen: " + getBeeindigdeProcessenCounter());
            LOG.info("Aantal beeindigde processen in deze test: " + (getBeeindigdeProcessenCounter() - initieelBeeindigdeProcessenCounter));
            result = false;
        }

        return result;
    }
}
