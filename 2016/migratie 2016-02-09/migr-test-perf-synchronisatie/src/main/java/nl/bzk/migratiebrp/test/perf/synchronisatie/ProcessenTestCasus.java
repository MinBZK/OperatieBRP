/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.test.perf.synchronisatie.bericht.TestBericht;
import nl.bzk.migratiebrp.test.perf.synchronisatie.environment.TestEnvironment;
import nl.bzk.migratiebrp.util.common.Kopieer;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal.Strategie;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import nl.bzk.migratiebrp.voisc.spd.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.apache.commons.io.IOUtils;

/**
 * Test casus: processen.
 */
public final class ProcessenTestCasus extends TestCasus {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String BERICHTEN_ZIP = "berichten.zip";
    private static final FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File file) {
            return file.isFile()
                   && !file.getName().contains(".DS_Store")
                   && !file.getName().contains(BERICHTEN_ZIP)
                   && !file.getName().contains("properties");
        }
    };
    private static final String KAN_INPUT_NIET_LEZEN = "Kan input niet lezen.";
    private final File inputDirectory;
    private final TestEnvironment environment;
    private Date started;
    private long aantal;

    /**
     * Constructor.
     *
     * @param thema
     *            thema
     * @param naam
     *            naam
     * @param outputFolder
     *            output folder
     * @param expectedFolder
     *            expected folder
     * @param input
     *            input
     * @param environment
     *            environment
     */
    protected ProcessenTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final File input,
        final TestEnvironment environment)
    {
        super(thema, naam, outputFolder, expectedFolder);
        inputDirectory = input;
        this.environment = environment;
    }

    /**
     * Geef de waarde van name.
     *
     * @return name
     */
    public String getName() {
        return inputDirectory.getName();
    }

    /**
     * Geef de waarde van aantal.
     *
     * @return aantal
     */
    public long getAantal() {
        return aantal;
    }

    /**
     * Geef de waarde van started.
     *
     * @return started
     */
    public Date getStarted() {
        return Kopieer.utilDate(started);
    }

    @Override
    public ProcessenTestResultaat run() {
        final ProcessenTestResultaat result = new ProcessenTestResultaat(getThema(), getNaam());

        final MailboxClient mailboxServerProxy;

        LOG.info("Executing test case: " + inputDirectory.getName());
        try {
            environment.beforeTestCase();
            mailboxServerProxy = environment.initializeMailboxServerProxy();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        final List<Runnable> verwerkers = new ArrayList<>();

        final List<File> files = Arrays.asList(inputDirectory.listFiles(FILE_FILTER));
        Collections.sort(files);
        for (final File file : files) {
            verwerkers.add(new BestandVerwerker(file, environment, mailboxServerProxy));
        }

        voegBerichtenZipAanVerwerker(mailboxServerProxy, verwerkers);

        started = new Date();
        final long start = System.currentTimeMillis();
        aantal = 0;

        try {
            voerTestUit(result, mailboxServerProxy, verwerkers, start);

        } catch (final Exception e) {
            LOG.info("Exception in test case: " + e.getMessage());
            result.setDuration(System.currentTimeMillis() - start);
            result.setAantal(aantal);

            final Foutmelding fout = new Foutmelding("Onverwachte fout", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.ISC);

            result.setResultaat(new TestStap(TestStatus.EXCEPTIE, e.getMessage(), htmlFout, null));
        }

        LOG.info("Test case '" + inputDirectory.getName() + "': " + result.getResultaat().getStatus() + " -> " + result.getResultaat().getOmschrijving());

        return result;
    }

    private void voerTestUit(final ProcessenTestResultaat result, final MailboxClient mailboxServerProxy, final List<Runnable> verwerkers, final long start)
        throws InterruptedException
    {
        LOG.info("Start: " + started);

        final ExecutorService executorService = Executors.newFixedThreadPool(16);

        // SSL verbinding opbouwen
        mailboxServerProxy.connect();

        for (final Runnable verwerker : verwerkers) {
            aantal++;
            executorService.execute(verwerker);
        }

        executorService.shutdown();
        while (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
            LOG.error("Sending messages ...");
        }

        // Logout
        try {
            mailboxServerProxy.logOff();
        } catch (final SpdProtocolException e) {
            throw new RuntimeException("Fout bij uitloggen na versturen bericht.", e);
        }

        LOG.info("Beëindigen test case ...");
        result.setAantal(aantal);
        LOG.info("Aantal: {}", aantal);

        LOG.info("Wachten op voltooien van het verzenden van testgevallen...");
        environment.afterTestCase(aantal, started);

        LOG.info("Beëindigd op: " + new Date());

        final long seconds = berekenDuur(result, start);

        final BigDecimal speed = BigDecimal.valueOf(aantal).divide(BigDecimal.valueOf(seconds), 2, RoundingMode.HALF_DOWN);

        LOG.info("Processen per seconde: " + speed);

        if (environment.verifieerBeeindigdeProcessen(aantal)) {
            result.setResultaat(new TestStap(TestStatus.OK));
        } else {
            result.setResultaat(new TestStap(TestStatus.NOK));
            final Foutmelding foutmelding =
                    new Foutmelding("Niet alle testgevallen zijn correct verwerkt. Raadpleeg het log voor meer details.", new IllegalStateException());
            result.setFoutmelding(foutmelding);
        }
    }

    private long berekenDuur(final ProcessenTestResultaat result, final long start) {
        result.setDuration(System.currentTimeMillis() - start);
        final int aantalMilliSeconden = 1000;
        final int aantalMinutenOfSeconden = 60;

        final long seconds = result.getDuration() / aantalMilliSeconden;
        final long duurUren = TimeUnit.SECONDS.toHours(seconds);
        final long duurMinuten = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * aantalMinutenOfSeconden;
        final long duurSeconde = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * aantalMinutenOfSeconden;
        LOG.info("Totale duur (seconde): " + seconds + " (" + duurUren + " uren " + duurMinuten + " minuten " + duurSeconde + " seconde)");
        return seconds;
    }

    private void voegBerichtenZipAanVerwerker(final MailboxClient mailboxServerProxy, final List<Runnable> verwerkers) {
        final File berichtenZip = new File(inputDirectory, BERICHTEN_ZIP);
        if (berichtenZip.exists()) {
            final ZipFile zipFile;
            try {
                zipFile = new ZipFile(berichtenZip);
            } catch (final IOException e) {
                throw new RuntimeException("Kan zipfile niet openen.");
            }

            final Enumeration<? extends ZipEntry> entryEnumeration = zipFile.entries();
            while (entryEnumeration.hasMoreElements()) {
                final ZipEntry entry = entryEnumeration.nextElement();
                verwerkers.add(new ZipEntryVerwerker(zipFile, entry, environment, mailboxServerProxy));
            }
        }
    }

    /**
     * Abstract verwerker implementatie.
     */
    private abstract static class AbstractVerwerker implements Runnable {
        private final TestEnvironment environment;
        private final MailboxClient mailboxServerProxy;

        /**
         * Default constructor.
         *
         * @param environment
         *            De Environment.
         * @param mailboxServerProxy
         *            De mailbox server.
         */
        public AbstractVerwerker(final TestEnvironment environment, final MailboxClient mailboxServerProxy) {
            this.environment = environment;
            this.mailboxServerProxy = mailboxServerProxy;
        }

        protected abstract TestBericht read();

        @Override
        public final void run() {
            final TestBericht testBericht = read();
            try {
                final long basisVertragingMillis = 1000;
                final int maximumAantalPogingen = 10;

                new Herhaal(basisVertragingMillis, maximumAantalPogingen, Strategie.LINEAIR).herhaal(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            environment.verzendBericht(testBericht, mailboxServerProxy);
                        } catch (final Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (final HerhaalException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Implementatie verwerker voor bestanden.
     */
    private static final class BestandVerwerker extends AbstractVerwerker {

        private final File file;

        /**
         * Default constructor.
         *
         * @param file
         *            Het te gebruiken bestand.
         * @param environment
         *            De Environment.
         * @param mailboxServerProxy
         *            De mailbox server.
         */
        public BestandVerwerker(final File file, final TestEnvironment environment, final MailboxClient mailboxServerProxy) {
            super(environment, mailboxServerProxy);
            this.file = file;
        }

        @Override
        public TestBericht read() {
            LOG.info(" - (file) " + file.getName());
            try {
                return new TestBericht(IOUtils.toString(new FileInputStream(file)), file.getName());
            } catch (final IOException e) {
                throw new RuntimeException(KAN_INPUT_NIET_LEZEN, e);
            }

        }
    }

    /**
     * Implementatie verwerker voor zip-bestanden.
     */
    private static final class ZipEntryVerwerker extends AbstractVerwerker {

        private final ZipFile zipFile;
        private final ZipEntry entry;

        /**
         * Default constructor.
         *
         * @param zipFile
         *            Het te gebruiken zip-bestand.
         * @param entry
         *            De entry van het zip-bestand.
         * @param environment
         *            De Environment.
         * @param mailboxServerProxy
         *            De mailbox server.
         */
        public ZipEntryVerwerker(final ZipFile zipFile, final ZipEntry entry, final TestEnvironment environment, final MailboxClient mailboxServerProxy) {
            super(environment, mailboxServerProxy);
            this.zipFile = zipFile;
            this.entry = entry;
        }

        @Override
        public TestBericht read() {
            LOG.info(" - (zip) " + entry.getName());
            try {
                return new TestBericht(IOUtils.toString(zipFile.getInputStream(entry)), entry.getName());
            } catch (final IOException e) {
                throw new RuntimeException(KAN_INPUT_NIET_LEZEN, e);
            }

        }
    }

}
