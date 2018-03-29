/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;
import nl.bzk.migratiebrp.test.isc.environment.check.Check;
import nl.bzk.migratiebrp.test.isc.environment.check.CheckConfig;
import nl.bzk.migratiebrp.test.isc.environment.check.CheckConfigReader;
import nl.bzk.migratiebrp.test.isc.environment.check.CheckConfigReaderImpl;
import nl.bzk.migratiebrp.test.isc.environment.check.CheckContext;
import nl.bzk.migratiebrp.test.isc.environment.check.CheckFactory;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.injection.Calculator;
import nl.bzk.migratiebrp.test.isc.environment.injection.CalculatorFactory;
import nl.bzk.migratiebrp.test.isc.environment.injection.Config;
import nl.bzk.migratiebrp.test.isc.environment.injection.ConfigReader;
import nl.bzk.migratiebrp.test.isc.environment.injection.ConfigReaderImpl;
import nl.bzk.migratiebrp.test.isc.environment.injection.Context;
import nl.bzk.migratiebrp.test.isc.environment.injection.Extractor;
import nl.bzk.migratiebrp.test.isc.environment.injection.ExtractorFactory;
import nl.bzk.migratiebrp.test.isc.environment.injection.Injector;
import nl.bzk.migratiebrp.test.isc.environment.injection.InjectorFactory;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.jbpm.JbpmHelperKanaal;
import nl.bzk.migratiebrp.test.isc.exception.TestException;
import nl.bzk.migratiebrp.test.isc.exception.TestNokException;

/**
 * Testenvironment impl.
 */
public final class EnvironmentImpl implements TestEnvironment {

    private static final String ERROR_INKOMEND_BERICHT_NIET_TOEGESTAAN = "Inkomend bericht op kanaal %s niet toegestaan volgens configuratie.";
    private static final String ERROR_UITGAAND_BERICHT_NIET_TOEGESTAAN = "Uitgaand bericht op kanaal %s niet toegestaan volgens configuratie.";

    private static final String KANAAL_FUNCTIE_VOOR = "voor";
    private static final String KANAAL_FUNCTIE_NA = "na";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Map<String, Kanaal> kanalen;

    @Inject
    private Correlator correlator;

    private final ConfigReader configReader = new ConfigReaderImpl();
    private final CheckConfigReader checkConfigReader = new CheckConfigReaderImpl();
    private final ExtractorFactory extractorFactory = new ExtractorFactory();
    private final CalculatorFactory calculatorFactory = new CalculatorFactory();
    private final InjectorFactory injectorFactory = new InjectorFactory();
    private final CheckFactory checkFactory = new CheckFactory();

    /**
     * Constructor.
     * @param kanalen alle kanelen
     */
    @Inject
    public EnvironmentImpl(final Kanaal[] kanalen) {
        this.kanalen = new TreeMap<>();
        final StringBuilder logKanalen = new StringBuilder("Kanalen: ");
        for (final Kanaal kanaal : kanalen) {
            final String naam = kanaal.getKanaal().toLowerCase();
            if (!this.kanalen.isEmpty()) {
                logKanalen.append(", ");
            }
            logKanalen.append(naam);
            if (this.kanalen.put(naam, kanaal) != null) {
                throw new IllegalStateException(
                        "Meerdere kanalen met de naam '" + naam + "': " + kanaal.getClass().getName() + " en " + this.kanalen.get(naam).getClass().getName());
            }
        }

        LOG.info(logKanalen.toString());
    }

    private Kanaal getKanaal(final String kanaal) {
        return kanalen.get(kanaal.toLowerCase());
    }

    /**
     * Geef de waarde van jbpm helper kanaal.
     * @return jbpm helper kanaal
     */
    public JbpmHelperKanaal getJbpmHelperKanaal() {
        return (JbpmHelperKanaal) getKanaal(JbpmHelperKanaal.KANAAL);
    }

    /**
     * Geef de waarde van correlator.
     * @return correlator
     */
    public Correlator getCorrelator() {
        return correlator;
    }

    @Override
    public void beforeTestCase(final ProcessenTestCasus testCase) {
        correlator.reset();
        final TestCasusContext testCaseContext = maakTestCasusContext(testCase);

        final ExecutorService executorService = Executors.newFixedThreadPool(8);
        try {
            // Create a list of callables (so we can catch the exceptions)
            final List<Callable<Exception>> callables =
                    kanalen.values()
                            .stream()
                            .filter(kanaal -> checkAllows(testCase.getTestcaseConfiguratie(), kanaal, KANAAL_FUNCTIE_VOOR))
                            .map(kanaal -> (Callable<Exception>) () -> {
                                try {
                                    LOG.info("Before testcase: {}", kanaal.getKanaal());
                                    kanaal.voorTestcase(testCaseContext);
                                    return null;
                                } catch (Exception e) {
                                    LOG.warn("Failed", e);
                                    return e;
                                }
                            })
                            .collect(Collectors.toList());

            // Invoke all futures (waits until all callables are done)
            final List<Future<Exception>> futures;
            try {
                futures = executorService.invokeAll(callables);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            // Check all results
            final List<Throwable> exceptions =
                    futures.stream().map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException e) {
                            return e.getCause();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return null;
                        }
                    }).filter(exception -> exception != null).collect(Collectors.toList());

            // Exception when anything went wrong
            if (!exceptions.isEmpty()) {
                throw new IllegalStateException("Testcase initialization failed (" + exceptions.size() + " failures)");
            }
        } finally {
            executorService.shutdownNow();
        }
    }

    @Override
    public Set<Long> bepaalAlleProcessen() {
        return getJbpmHelperKanaal().bepaalAlleProcessen(correlator.getAlleBerichtReferenties());
    }

    @Override
    public boolean controleerProcesBeeindigd(final Long procesId) {
        return getJbpmHelperKanaal().controleerProcesBeeindigd(procesId);
    }

    @Override
    public boolean afterTestCase(final ProcessenTestCasus testCase) {
        final TestCasusContext testCaseContext = maakTestCasusContext(testCase);

        final ExecutorService executorService = Executors.newFixedThreadPool(8);
        final LongAdder aantalBerichten = new LongAdder();
        try {
            // Create a list of callables (so we can catch the exceptions)
            final List<Callable<Exception>> callables =
                    kanalen.values()
                            .stream()
                            .filter(kanaal -> checkAllows(testCase.getTestcaseConfiguratie(), kanaal, KANAAL_FUNCTIE_NA))
                            .map(kanaal -> (Callable<Exception>) () -> {
                                try {
                                    LOG.info("After testcase {}", kanaal);
                                    final List<Bericht> kanaalBerichten = kanaal.naTestcase(testCaseContext);
                                    int aantalKanaalBerichten = 0;
                                    if (kanaalBerichten != null) {
                                        for (final Bericht bericht : kanaalBerichten) {
                                            aantalKanaalBerichten++;
                                            LOG.info("Onverwacht bericht ontvangen op kanaal {}:\n{}", kanaal.getKanaal(), bericht.getInhoud());
                                            LOG.info("Message ID: {}, correlatie ID: {}", bericht.getBerichtReferentie(), bericht.getCorrelatieReferentie());
                                            LOG.info("Verzendende partij: {}, ontvangende partij: {}", bericht.getVerzendendePartij(),
                                                    bericht.getOntvangendePartij());

                                            String bestandsnaam = "9999-inuit-" + kanaal.getKanaal() + "-" + aantalKanaalBerichten;
                                            output(new File(testCase.getOutputDirectory(), bestandsnaam), bericht.getInhoud());

                                            if (bericht.getVerzendendePartij() != null || bericht.getOntvangendePartij() != null) {
                                                output(new File(testCase.getOutputDirectory(), bestandsnaam + ".properties"),
                                                        "verzendendePartij=" + bericht.getVerzendendePartij() +
                                                                "\nontvangendePartij=" + bericht.getOntvangendePartij());
                                            }
                                        }
                                    }
                                    aantalBerichten.add(aantalKanaalBerichten);
                                    return null;
                                } catch (Exception e) {
                                    LOG.warn("Failure", e);
                                    return e;
                                }
                            })
                            .collect(Collectors.toList());

            // Invoke all futures (waits until all callables are done)
            final List<Future<Exception>> futures;
            try {
                futures = executorService.invokeAll(callables);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

            // Check all results
            final List<Throwable> exceptions =
                    futures.stream().map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException e) {
                            return e.getCause();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return null;
                        }
                    }).filter(exception -> exception != null).collect(Collectors.toList());

            // Exception when anything went wrong
            if (!exceptions.isEmpty()) {
                throw new IllegalStateException("Testcase initialization failed (" + exceptions.size() + " failures)");
            }
        } finally {
            executorService.shutdownNow();
        }

        return aantalBerichten.longValue() == 0;
    }

    @Override
    public void verwerkBericht(final ProcessenTestCasus processenTestCasus, final TestBericht testBericht) throws TestException {
        final Kanaal kanaal = getKanaal(testBericht.getKanaal());
        if (kanaal == null) {
            throw new TestException(String.format("Kanaal '%s' is onbekend.", testBericht.getKanaal()));
        }

        final TestCasusContext testCaseContext = maakTestCasusContext(processenTestCasus);

        final Bericht bericht = mapTestBericht(testBericht, kanaal);
        if (testBericht.getUitgaand()) {
            if (!checkAllows(processenTestCasus.getTestcaseConfiguratie(), kanaal, "uit")) {
                throw new TestException(String.format(ERROR_UITGAAND_BERICHT_NIET_TOEGESTAAN, testBericht.getKanaal()));
            }
            verwerkCalculateFile(processenTestCasus, testBericht);
            verwerkInjectFile(processenTestCasus, testBericht, bericht);
            output(testBericht.getOutputFile(), bericht.getInhoud());
            try {
                kanaal.verwerkUitgaand(testCaseContext, bericht);
            } catch (final KanaalException e) {
                if (!bericht.isMagFalen()) {
                    throw new TestException("Probleem bij verwerken uitgaand bericht.", e);
                }
            }
            verwerkExtractFile(processenTestCasus, testBericht, bericht);

        } else {
            if (!checkAllows(processenTestCasus.getTestcaseConfiguratie(), kanaal, "in")) {
                throw new TestException(String.format(ERROR_INKOMEND_BERICHT_NIET_TOEGESTAAN, testBericht.getKanaal()));
            }

            verwerkCalculateFile(processenTestCasus, testBericht);
            verwerkInjectFile(processenTestCasus, testBericht, bericht);
            output(testBericht.getExpectedFile(), bericht.getInhoud());
            try {
                final Bericht ontvangenBericht = kanaal.verwerkInkomend(testCaseContext, bericht);
                verwerkExtractFile(processenTestCasus, testBericht, ontvangenBericht);
                if (ontvangenBericht != null) {
                    output(testBericht.getOutputFile(), ontvangenBericht.getInhoud());

                    // Controleer inhoud
                    if (!kanaal.controleerInkomend(testCaseContext, bericht, ontvangenBericht)) {
                        if (bericht.isMagFalen()) {
                            LOG.info("Stap gefaald, maar geconfigureerd met magFalen=true");
                        }else {
                            throw new TestNokException("Bericht voldoet niet aan verwachting");
                        }
                    }
                }
                verwerkCheckFile(testBericht, ontvangenBericht);

            } catch (final KanaalException e) {
                if (!bericht.isMagFalen()) {
                    throw new TestException("Probleem bij verwerken inkomend bericht.", e);
                }
            }
        }
    }

    private TestCasusContext maakTestCasusContext(final ProcessenTestCasus processenTestCasus) {
        final TestCasusContext result =
                new TestCasusContext(
                        processenTestCasus.getTestcaseConfiguratie(),
                        processenTestCasus.getInputDirectory(),
                        processenTestCasus.getOutputDirectory());
        // TODO
        return result;
    }

    private Bericht mapTestBericht(final TestBericht testBericht, final Kanaal kanaal) {
        final Bericht bericht = new Bericht(testBericht);
        bericht.setBerichtReferentie(testBericht.getVolgnummer().toString());
        if (testBericht.getHerhaalnummer() != null) {
            bericht.setBerichtReferentie(testBericht.getHerhaalnummer().toString());
        }

        if (testBericht.getCorrelatienummer() != null) {
            bericht.setCorrelatieReferentie(testBericht.getCorrelatienummer().toString());
        }
        bericht.setInhoud(testBericht.getInhoud());
        bericht.setOntvangendePartij(
                testBericht.getOntvangendePartij() == null ? kanaal.getStandaardOntvangendePartij() : testBericht.getOntvangendePartij());
        bericht.setVerzendendePartij(
                testBericht.getVerzendendePartij() == null ? kanaal.getStandaardVerzendendePartij() : testBericht.getVerzendendePartij());
        bericht.setMoederActiviteitType(testBericht.getTestBerichtPropertyAsInteger("moederActiviteitType"));
        bericht.setActiviteitType(testBericht.getTestBerichtPropertyAsInteger("activiteitType"));
        bericht.setActiviteitSubtype(testBericht.getTestBerichtPropertyAsInteger("activiteitSubtype"));
        bericht.setActiviteitDatum(testBericht.getActiviteitDatum());
        bericht.setActiviteitToestand(testBericht.getActiviteitToestand());
        bericht.setOinOndertekenaar(testBericht.getOinOndertekenaar());
        bericht.setOinTransporteur(testBericht.getOinTransporteur());
        bericht.setMagFalen(testBericht.isMagFalen());
        bericht.setMsSequenceNumber(testBericht.getTestBerichtProperty("msSequenceNumber"));
        bericht.setRequestNonReceiptNotification(Boolean.valueOf(testBericht.getTestBerichtProperty("requestNonReceiptNotification")));
        return bericht;
    }

    private boolean checkAllows(final Properties configuratie, final Kanaal kanaal, final String functie) {
        final String kanaalDefault = configuratie.getProperty(kanaal.getKanaal().toLowerCase(), "false");
        final String value = configuratie.getProperty(kanaal.getKanaal().toLowerCase() + "." + functie.toLowerCase(), kanaalDefault);
        return "true".equalsIgnoreCase(value);
    }

    private void output(final File file, final String inhoud) {
        FileOutputStream fis = null;
        try {
            file.getParentFile().mkdirs();
            fis = new FileOutputStream(file);
            final PrintWriter writer = new PrintWriter(fis);
            writer.print(inhoud);
            writer.close();

        } catch (final IOException e) {
            LOG.info("Fout bij output van verzonden bestand", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (final IOException e) {
                    LOG.debug("Kan expected output niet sluiten.", e);
                }
            }
        }
    }

    private void verwerkCalculateFile(final ProcessenTestCasus processenTestCasus, final TestBericht testBericht) throws TestException {
        final List<Config> configs;
        try {
            configs = configReader.readConfig(new File(testBericht.getInputFile().getParentFile(), testBericht.getInputFile().getName() + ".calculate"));
        } catch (final IOException e) {
            throw new TestException("Kan .extract bestand niet lezen voor test bericht. ", e);
        }

        for (final Config config : configs) {
            final Calculator calculator = calculatorFactory.getCalculator(config.getType());
            if (calculator == null) {
                throw new TestException(String.format("Calculator van type '%s' is onbekend.", config.getType()));
            }

            final Context calculatorContext = new Context(correlator, getJbpmHelperKanaal());
            final String value = calculator.calculate(calculatorContext, processenTestCasus.getContext().getValues(), config.getKey());
            LOG.info("Calculated value {} for variable {}", value, config.getName());
            processenTestCasus.getContext().set(config.getName(), value);
        }
    }

    private void verwerkExtractFile(final ProcessenTestCasus processenTestCasus, final TestBericht testBericht, final Bericht ontvangenBericht)
            throws TestException {
        final List<Config> configs;
        try {
            configs = configReader.readConfig(new File(testBericht.getInputFile().getParentFile(), testBericht.getInputFile().getName() + ".extract"));
        } catch (final IOException e) {
            throw new TestException("Kan .extract bestand niet lezen voor test bericht. ", e);
        }

        for (final Config config : configs) {
            final Extractor extractor = extractorFactory.getExtractor(config.getType());
            if (extractor == null) {
                throw new TestException(String.format("Extractor van type '%s' is onbekend.", config.getType()));
            }

            final Context extractionContext = new Context(correlator, getJbpmHelperKanaal());
            final String value = extractor.extract(extractionContext, ontvangenBericht, config.getKey());
            LOG.info("Extracted value {} for variable {}", value, config.getName());
            processenTestCasus.getContext().set(config.getName(), value);
        }
    }

    private void verwerkInjectFile(final ProcessenTestCasus processenTestCasus, final TestBericht testBericht, final Bericht teVersturenBericht)
            throws TestException {
        final List<Config> configs;
        try {
            configs = configReader.readConfig(new File(testBericht.getInputFile().getParentFile(), testBericht.getInputFile().getName() + ".inject"));
        } catch (final IOException e) {
            throw new TestException("Kan .inject bestand niet lezen voor test bericht.", e);
        }

        for (final Config config : configs) {
            final Injector injector = injectorFactory.getInjector(config.getType());
            if (injector == null) {
                throw new TestException("Injector van type '" + config.getType() + "' is onbekend.");
            }

            final Context injectionContext = new Context(correlator, getJbpmHelperKanaal());
            final String value = processenTestCasus.getContext().get(config.getName());
            if (value == null) {
                throw new TestException("Waarde voor variabele '" + config.getName() + "' is <null>, injection niet mogelijk.");
            }
            injector.inject(injectionContext, teVersturenBericht, config.getKey(), value);
        }
    }

    private void verwerkCheckFile(final TestBericht testBericht, final Bericht ontvangenBericht) throws TestException {
        final List<CheckConfig> configs;
        try {
            configs = checkConfigReader.readConfig(new File(testBericht.getInputFile().getParentFile(), testBericht.getInputFile().getName() + ".check"));
        } catch (final IOException e) {
            throw new TestException("Kan .extract bestand niet lezen voor test bericht.", e);
        }

        for (final CheckConfig config : configs) {
            final Check check = checkFactory.getCheck(config.getType());
            if (check == null) {
                throw new TestException(String.format("Check van type '%s' is onbekend.", config.getType()));
            }

            final CheckContext checkContext = new CheckContext();
            if (!check.check(checkContext, ontvangenBericht, testBericht, config.getConfig())) {
                throw new TestException(String.format("Check van type '%s' (met config '%s') is gefaald.", config.getType(), config.getConfig()));
            }
        }
    }
}
