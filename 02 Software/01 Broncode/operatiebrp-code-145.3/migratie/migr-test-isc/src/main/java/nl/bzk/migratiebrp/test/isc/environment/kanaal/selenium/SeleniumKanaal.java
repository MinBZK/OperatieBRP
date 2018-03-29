/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.selenium;

import jp.vmi.selenium.selenese.utils.LoggerUtils;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

import org.codehaus.classworlds.ClassRealm;
import org.codehaus.classworlds.ClassWorld;
import org.codehaus.classworlds.DuplicateRealmException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Selenium kanaal.
 */
public final class SeleniumKanaal extends AbstractKanaal {

    private static final String SELENIUM_EXTENSION = ".selenium";
    private static final String HTMLUNIT = "htmlunit";
    private static final String PHANTOMJS = "phantomjs";
    private static final String SELENIUM = "selenium";
    private static final String SELENIUM_LIB = "selenium-lib";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final ClassWorld classWorld = new ClassWorld();
    private ClassRealm classRealm;
    private boolean initialized;

    private String baseUrl;
    private String kanaal;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
     */
    @Override
    public String getKanaal() {
        return kanaal;
    }

    private void initialize(final boolean provideAngular) throws KanaalException {
        if (!initialized) {
            try {
                classRealm = classWorld.newRealm(getKanaal());

                // Alle jars uit opschonen-lib toevoegen aan classrealm
                final File libDir = new File(SELENIUM_LIB);
                for (final File jarFile : libDir.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(final File dir, final String name) {
                        return name.endsWith(".jar");
                    }
                })) {
                    final URL url = jarFile.toURI().toURL();
                    classRealm.addConstituent(jarFile.toURI().toURL());
                    LOG.info("Adding librairy: {}", url);
                }

                // Add executor (and dependencies) to classrealm
                if (provideAngular) {
                    classRealm.addConstituent(findUrlForClass(SeleniumExecutor.class));
                    classRealm.addConstituent(findUrlForClass(ExecuteAsyncScriptCommand.class));
                } else {
                    classRealm.addConstituent(findUrlForClass(Main.class));
                    classRealm.addConstituent(findUrlForClass(LoggerFactory.class));
                }

                initialized = true;
            } catch (final
            DuplicateRealmException
                    | IOException
                    | SecurityException e) {
                throw new KanaalException("Kan classrealm niet initialiseren.", e);
            }
        }
    }

    private URL findUrlForClass(final Class<?> clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

    /**
     * Zet de waarde van kanaal.
     * @param kanaal kanaal naam
     */
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    /**
     * Zet de waarde van base url.
     * @param baseUrl base url
     */
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
        boolean result = false;
        final boolean provideAngular = "true".equalsIgnoreCase(verwachtBericht.getTestBericht().getTestBerichtProperty("selenium.angular"));
        initialize(provideAngular);
        final ClassLoader previousCl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classRealm.getClassLoader());

            if (provideAngular) {
                // Invoke main class

                final Class<?> executorClass = classRealm.loadClass(SeleniumExecutor.class.getCanonicalName());
                final Method executeMethod = executorClass.getMethod("execute", new Class<?>[]{File.class,
                        File.class,
                        String.class,
                        String.class,
                        Boolean.TYPE,
                        Boolean.TYPE,
                        Boolean.TYPE,
                        Boolean.TYPE,
                        Integer.class,
                        Integer.class,
                        Integer.class,});

                final File inputFile = verwachtBericht.getTestBericht().getExpectedFile();
                final File outputFile = verwachtBericht.getTestBericht().getOutputFile();
                final String driver = verwachtBericht.getTestBericht().getTestBerichtProperty(SELENIUM);
                final boolean screenShotAll = "all".equalsIgnoreCase(verwachtBericht.getTestBericht().getTestBerichtProperty("selenium.screenshot"));
                final boolean waitForElement =
                        !Boolean.FALSE.toString().equalsIgnoreCase(verwachtBericht.getTestBericht().getTestBerichtProperty("selenium.wait"));
                final boolean focusAndBlur =
                        !Boolean.FALSE.toString().equalsIgnoreCase(verwachtBericht.getTestBericht().getTestBerichtProperty("selenium.focus"));
                final Integer implicitWaitTimeout = verwachtBericht.getTestBericht().getTestBerichtPropertyAsInteger("selenium.timeout.implicitwait");
                final Integer pageLoadTimeout = verwachtBericht.getTestBericht().getTestBerichtPropertyAsInteger("selenium.timeout.pageload");
                final Integer scriptTimeout = verwachtBericht.getTestBericht().getTestBerichtPropertyAsInteger("selenium.timeout.script");

                result =
                        (Boolean) executeMethod.invoke(
                                null,
                                inputFile,
                                outputFile,
                                driver == null ? PHANTOMJS : driver,
                                baseUrl,
                                screenShotAll,
                                waitForElement,
                                focusAndBlur,
                                provideAngular,
                                implicitWaitTimeout,
                                pageLoadTimeout,
                                scriptTimeout);
            } else {
                // Invoke main class

                final String thisClass = SeleniumKanaal.class.getCanonicalName();
                final Class<?> mainClass = classRealm.loadClass(thisClass + "$Main");
                final Class<?> exitClass = classRealm.loadClass(thisClass + "$MainExitException");
                final Method mainMethod = mainClass.getMethod("main", new Class<?>[]{String[].class});

                // @formatter:off
                //java -jar selenese-runner.jar <option> ... <test-case|test-suite> ...
                //
                //--config (-c) <file>                    : load option information from file.
                //--driver (-d) <driver>                  : firefox (default) | chrome | ie | safari | htmlunit | phantomjs | remote | appium
                //                                              | FQCN-of-WebDriverFactory
                //--profile (-p) <name>                   : profile name (Firefox only *1)
                //--profile-dir (-P) <dir>                : profile directory (Firefox only *1)
                //--chrome-experimental-options <file>    : path to json file specify experimental options for chrome (Chrome only *1)
                //--chrome-extension <file>               : chrome extension file (multiple, Chrome only *1)
                //--proxy <proxy>                         : proxy host and port (HOST:PORT) (excepting IE)
                //--proxy-user <user>                     : proxy username (HtmlUnit only *2)
                //--proxy-password <password>             : proxy password (HtmlUnit only *2)
                //--no-proxy <hosts>                      : no-proxy hosts
                //--cli-args <arg>                        : add command line arguments at starting up driver (multiple)
                //--remote-url <url>                      : Remote test runner URL (Remote only)
                //--remote-platform <platform>            : Desired remote platform (Remote only)
                //--remote-browser <browser>              : Desired remote browser (Remote only)
                //--remote-version <browser-version>      : Desired remote browser version (Remote only)
                //--highlight (-H)                        : highlight locator always.
                //--screenshot-dir (-s) <dir>             : override captureEntirePageScreenshot directory.
                //--screenshot-all (-S) <dir>             : take screenshot at all commands to specified directory.
                //--screenshot-on-fail <dir>              : take screenshot on fail commands to specified directory.
                //--ignore-screenshot-command             : ignore captureEntirePageScreenshot command.
                //--baseurl (-b) <baseURL>                : override base URL set in selenese.
                //--firefox <path>                        : path to 'firefox' binary. (implies '--driver firefox')
                //--chromedriver <path>                   : path to 'chromedriver' binary. (implies '--driver chrome')
                //--iedriver <path>                       : path to 'IEDriverServer' binary. (implies '--driver ie')
                //--phantomjs <path>                      : path to 'phantomjs' binary. (implies '--driver phantomjs')
                //--xml-result <dir>                      : output XML JUnit results to specified directory.
                //--html-result <dir>                     : output HTML results to specified directory.
                //--timeout (-t) <timeout>                : set timeout (ms) for waiting. (default: 30000 ms)
                //--set-speed <speed>                     : same as executing setSpeed(ms) command first.
                //--height <height>                       : set initial height. (excluding mobile)
                //--width <width>                         : set initial width. (excluding mobile)
                //--define (-D) <key=value or key+=value> : define parameters for capabilities. (multiple)
                //--rollup <file>                         : define rollup rule by JavaScript. (multiple)
                //--cookie-filter <+RE|-RE>               : filter cookies to log by RE matching the name. ("+" is passing, "-" is ignoring)
                //--command-factory <FQCN>                : register user defined command factory. (See Note *3)
                //--no-exit                               : don't call System.exit at end.
                //--strict-exit-code                      : return strict exit code, reflected by selenese command results at end. (See Note *4)
                //--help (-h)                             : show this message.
                // @formatter:on

                final List<String> arguments = new ArrayList<>();

                if (HTMLUNIT.equalsIgnoreCase(verwachtBericht.getTestBericht().getTestBerichtProperty(SELENIUM))) {
                    // Use HTMLUnit
                    // File upload werkt niet de webdriver die we standaard gebruiken (phantomjs)
                    arguments.add("-d");
                    arguments.add(HTMLUNIT);
                } else {
                    // Use PhantomJS
                    arguments.add("--phantomjs");
                    final File phantomJsBinary = new File(new File(new File(SELENIUM_LIB), PHANTOMJS), detectPhantomJs());
                    phantomJsBinary.setExecutable(true, true);
                    arguments.add(phantomJsBinary.getAbsolutePath());
                }

                // Set base URL
                if (baseUrl == null || "".equals(baseUrl)) {
                    throw new KanaalException("Geen base url opgegeven voor selenium test (selenium.baseurl in properties).");
                }
                arguments.add("-b");
                arguments.add(baseUrl);

                // Setup screenshots
                arguments.add("--highlight");
                arguments.add("--screenshot-on-fail");
                arguments.add(verwachtBericht.getTestBericht().getOutputFile().getParentFile().getCanonicalPath());

                // Volgens de aanvullende specs dient de minimale resolutie 1280x1024 te zijn. Deze minimale resolutie stellen we dan ook in voor de
                // geautomatiseerde testen.
                arguments.add("--height");
                arguments.add("1024");
                arguments.add("--width");
                arguments.add("1280");

                // final File screenshotDir = new File(verwachtBericht.getTestBericht().getOutputFile().getParentFile(),
                // "selenium-screenshots");
                // screenshotDir.mkdirs();
                // arguments.add("--screenshot-all");
                // arguments.add(screenshotDir.getAbsolutePath());

                // Setup result
                arguments.add("--html-result");
                arguments.add(verwachtBericht.getTestBericht().getOutputFile().getCanonicalPath() + SELENIUM_EXTENSION);

                // Selenese input
                arguments.add(verwachtBericht.getTestBericht().getExpectedFile().getCanonicalPath());

                final String timeout;
                if (verwachtBericht.getTestBericht().getTestBerichtProperty("selenium.timeout") == null) {
                    timeout = "180000";
                } else {
                    timeout = verwachtBericht.getTestBericht().getTestBerichtProperty("selenium.timeout");
                }
                arguments.add("--timeout=" + timeout);

                LOG.info("Executing selenium runner with arguments: {}", arguments);

                try {
                    mainMethod.invoke(null, new Object[]{arguments.toArray(new String[]{})});
                    throw new KanaalException("Selenium test niet beeindigd via verwachte exit");
                } catch (final InvocationTargetException e) {
                    if (exitClass.isAssignableFrom(e.getCause().getClass())) {
                        final Method getMethod = exitClass.getMethod("getExitCode", new Class<?>[]{});
                        result = (int) getMethod.invoke(e.getCause(), new Object[]{}) == 0;
                    } else {
                        throw e;
                    }
                }

            }
        } catch (final Exception e /* Alles catchen voor robustheid van de testcode, we rethrowen als KanaalException */) {
            throw new KanaalException("Probleem met uitvoeren methode", e);
        } finally {
            // Clear cache
            quit();

            Thread.currentThread().setContextClassLoader(previousCl);
        }

        return maakResultaatBericht(verwachtBericht, result);
    }

    private void quit() {
        try {
            final Class<?> managerClass = classRealm.loadClass("jp.vmi.selenium.webdriver.WebDriverManager");
            final Method getInstanceMethod = managerClass.getMethod("getInstance", new Class<?>[]{});
            final Object manager = getInstanceMethod.invoke(null, new Object[]{});
            final Method quitAllDriversMethod = managerClass.getMethod("quitAllDrivers", new Class<?>[]{});
            quitAllDriversMethod.invoke(manager, new Object[]{});
        } catch (final ReflectiveOperationException e) {
            // Ignore
            LOG.error("Probleem met opruimen webdrivers", e);
        }
    }

    private Bericht maakResultaatBericht(final Bericht verwachtBericht, final boolean succes) {
        final Bericht result = new Bericht();

        final String directoryName = verwachtBericht.getTestBericht().getOutputFile().getName() + SELENIUM_EXTENSION;

        result.setInhoud("<html><body><p/>Resultaat: " + succes + "<p/><a href='" + directoryName + "/index.html'>Selenium results</a></body></html>");

        return result;
    }

    @Override
    protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
        return ontvangenInhoud.contains("<p/>Resultaat: true<p/>");
    }

    private String detectPhantomJs() {
        final String osName = System.getProperty("os.name").toLowerCase();

        final String result;
        if (osName.indexOf("win") >= 0) {
            result = "windows\\phantomjs.exe";
        } else if (osName.indexOf("mac") >= 0) {
            result = "macosx/phantomjs";
        } else if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") > 0) {
            // TODO: Detectie 32-bit of 64-bit (Jenkins is 64-bit dus op de buildserver gaat het nu goed)
            result = "linux-x86_64/phantomjs";
        } else {
            throw new IllegalStateException("Could not determine OS type to run phantomjs.");
        }

        return result;
    }

    /**
     * Selenium runner.
     */
    public static final class Main extends jp.vmi.selenium.selenese.Main {

        @Override
        protected void exit(final int exitCode) {
            LOG.info("Selenium exitting with exitcode: " + exitCode);
            throw new MainExitException(exitCode);
        }

        /**
         * Main.
         * @param args argumenten
         */
        public static void main(final String[] args) {
            LoggerUtils.initLogger();
            new Main().run(args);
        }
    }

    /**
     * Selenium exit catcher.
     */
    public static final class MainExitException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        private final int exitCode;

        private MainExitException(final int exitCode) {
            super("Selenium test returned with exit code: " + exitCode);
            this.exitCode = exitCode;
        }

        /**
         * Geef de waarde van exit code.
         * @return exit code
         */
        public int getExitCode() {
            return exitCode;
        }
    }
}
