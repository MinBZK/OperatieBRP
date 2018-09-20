/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.selenium;

import com.thoughtworks.selenium.webdriven.ElementFinder;
import com.thoughtworks.selenium.webdriven.JavascriptLibrary;
import com.thoughtworks.selenium.webdriven.SeleneseCommand;
import com.thoughtworks.selenium.webdriven.commands.FireNamedEvent;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.Parser;
import jp.vmi.selenium.selenese.Runner;
import jp.vmi.selenium.selenese.Selenese;
import jp.vmi.selenium.selenese.Selenese.Type;
import jp.vmi.selenium.selenese.TestCase;
import jp.vmi.selenium.selenese.TestSuite;
import jp.vmi.selenium.selenese.command.AbstractCommand;
import jp.vmi.selenium.selenese.command.ArgumentType;
import jp.vmi.selenium.selenese.command.BuiltInCommand;
import jp.vmi.selenium.selenese.command.CommandListIterator;
import jp.vmi.selenium.selenese.command.ICommand;
import jp.vmi.selenium.selenese.inject.Binder;
import jp.vmi.selenium.selenese.result.Result;
import jp.vmi.selenium.selenese.subcommand.ISubCommand;
import jp.vmi.selenium.selenese.subcommand.WDCommand;
import jp.vmi.selenium.webdriver.DriverOptions;
import jp.vmi.selenium.webdriver.DriverOptions.DriverOption;
import jp.vmi.selenium.webdriver.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Klasse voor het uitvoeren van Selenium.
 */
public final class SeleniumExecutor {

    private static final int STANDAARD_SCRIPT_TIMEOUT = 30;

    private static final int STANDAARD_PAGINA_LAADTIJD = 30;

    private static final int STANDAARD_WACHTTIJD = 10;

    private static final int MILISECONDEN_FACTOR = 1000;

    private static final String SELENIUM_LIBRARY = "selenium-lib";

    private static final String IDENTIFIER_CHROME_DRIVER = "chrome";

    private static final String IDENTIFIER_PHANTOMJS_DRIVER = "phantomjs";

    private static final String COMMAND_BLUR = "blur";

    private static final String COMMAND_FOCUS = "focus";

    private static final String COMMAND_OPEN = "open";

    private static final String COMMAND_TYPE = "type";

    private static final String COMMAND_CLICK = "click";

    private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumExecutor.class);

    private static final JavascriptLibrary JAVASCRIPT_LIBRARY = new JavascriptLibrary();

    private static final Set<String> FOCUS_COMMANDS = new HashSet<>(Arrays.asList(COMMAND_CLICK, COMMAND_TYPE));
    private static final Set<String> BLUR_COMMANDS = new HashSet<>(Arrays.asList(COMMAND_TYPE));
    private static final Set<String> ANGULAR_COMMANDS = new HashSet<>(Arrays.asList(COMMAND_OPEN, COMMAND_TYPE, COMMAND_CLICK));

    /**
     * Private constructor.
     */
    private SeleniumExecutor() {
        // Niet instantieerbaar.
    }

    /**
     * Execute methode voor selenium.
     *
     * @param input
     *            Het input bestand.
     * @param outputFile
     *            Het output bestand.
     * @param driver
     *            De te gebruiken selenium driver.
     * @param baseUrl
     *            De basis URL.
     * @param screenshotAll
     *            Indicator of van elke stap een screenshot gemaakt moet worden.
     * @param waitForElement
     *            Indicator of er gewacht moet worden totdat een element aanwezig is.
     * @param focusAndBlur
     *            Indicator of focus en blur toegepast dienen te worden.
     * @param waitForAngular
     *            Indicator of Angular wordt gebruikt.
     * @param implicitWaitTimeout
     *            Standaard wachttijd.
     * @param pageLoadTimeout
     *            Timeout voor het laden van de pagina.
     * @param scriptTimeout
     *            Timeout voor scripts.
     * @return True indien succesvol uitgevoerd, false in andere situaties.
     */
    public static boolean execute(
        final File input,
        final File outputFile,
        final String driver,
        final String baseUrl,
        final boolean screenshotAll,
        final boolean waitForElement,
        final boolean focusAndBlur,
        final boolean waitForAngular,
        final Integer implicitWaitTimeout,
        final Integer pageLoadTimeout,
        final Integer scriptTimeout)
    {
        LOGGER.info(
            "Executing selenium (input={}, output={}, driver={}, baseurl={}, screenshotAll={}, waitForElement={}, focusAndBlur={}, waitForAngular={}",
            new Object[] {input, outputFile, driver, baseUrl, screenshotAll, waitForElement, focusAndBlur, waitForAngular });
        final WebDriverManager manager = WebDriverManager.getInstance();
        manager.setWebDriverFactory(driver);

        final DriverOptions options = new DriverOptions();
        if (IDENTIFIER_PHANTOMJS_DRIVER.equalsIgnoreCase(driver)) {
            final File phantomJsBinary =
                    new File(new File(new File(SELENIUM_LIBRARY), IDENTIFIER_PHANTOMJS_DRIVER), detectOperatingSystem(IDENTIFIER_PHANTOMJS_DRIVER));
            options.set(DriverOption.PHANTOMJS, phantomJsBinary.getAbsolutePath());
        } else if (IDENTIFIER_CHROME_DRIVER.equalsIgnoreCase(driver)) {
            final File phantomJsBinary = new File(new File(new File(SELENIUM_LIBRARY), IDENTIFIER_CHROME_DRIVER), detectOperatingSystem("chromedriver"));
            options.set(DriverOption.CHROMEDRIVER, phantomJsBinary.getAbsolutePath());

        }
        options.set(DriverOption.WIDTH, "1000");
        options.set(DriverOption.HEIGHT, "700");

        manager.setDriverOptions(options);

        final Runner runner = new Runner();
        final WebDriver webDriver = manager.get();

        // Manage timeouts
        final Timeouts timeouts = webDriver.manage().timeouts();
        timeouts.implicitlyWait(implicitWaitTimeout == null ? STANDAARD_WACHTTIJD : implicitWaitTimeout.intValue(), TimeUnit.SECONDS);
        timeouts.pageLoadTimeout(pageLoadTimeout == null ? STANDAARD_PAGINA_LAADTIJD : pageLoadTimeout.intValue(), TimeUnit.SECONDS);
        timeouts.setScriptTimeout(scriptTimeout == null ? STANDAARD_SCRIPT_TIMEOUT : scriptTimeout.intValue(), TimeUnit.SECONDS);

        runner.setDriver(webDriver);
        runner.setHighlight(true);

        final File screenshotDirectory = new File(outputFile.getParent(), outputFile.getName() + ".selenium-screenshots");
        screenshotDirectory.mkdirs();
        if (screenshotAll) {
            runner.setScreenshotAllDir(screenshotDirectory.getAbsolutePath());
        } else {
            runner.setScreenshotOnFailDir(screenshotDirectory.getAbsolutePath());
        }

        runner.setOverridingBaseURL(baseUrl);

        final File resultDirectory = new File(outputFile.getParent(), outputFile.getName() + ".selenium");
        resultDirectory.mkdirs();
        runner.setHtmlResultDir(resultDirectory.getAbsolutePath());

        runner.setTimeout(implicitWaitTimeout == null ? STANDAARD_WACHTTIJD : implicitWaitTimeout.intValue() * MILISECONDEN_FACTOR);
        runner.setInitialSpeed(0);
        runner.setPrintStream(System.out);

        // {@link Runner#run(String[])}
        final Selenese selenese = Parser.parse(input.getAbsolutePath(), runner.getCommandFactory());
        if (Type.TEST_CASE != selenese.getType()) {
            throw new IllegalArgumentException("Input should be a selenium testcase (not a testsuite) ...");
        }

        final TestCase testCase = adaptTestCase(runner, (TestCase) selenese, waitForElement, focusAndBlur, waitForAngular);
        final TestSuite testSuite = Binder.newTestSuite(null, "default");
        testSuite.addSelenese(testCase);

        final Result result = testSuite.execute(null, runner);

        runner.finish();

        manager.quitAllDrivers();

        return !result.isFailed();
    }

    private static String detectOperatingSystem(final String executableName) {
        final String osName = System.getProperty("os.name").toLowerCase();

        final String result;
        if (osName.indexOf("win") >= 0) {
            result = "windows\\" + executableName + ".exe";
        } else if (osName.indexOf("mac") >= 0) {
            result = "macosx/" + executableName;
        } else if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") > 0) {
            // TODO: Detectie 32-bit of 64-bit (Jenkins is 64-bit dus op de buildserver gaat het nu goed)
            result = "linux-x86_64/" + executableName;
        } else {
            throw new IllegalStateException("Could not determine OS type.");
        }

        return result;
    }

    /**
     * Testcase adapten door extra commands toe te voegen zodat de AOP zoals die gebruikt wordt in de Selenese Runner (
     * {@link Binder}). nog steeds werkt.
     *
     * @param selenese
     *            testcase
     * @param waitForElement
     *            indicatie of bij type/click elementen gewacht moet worden tot het element er is
     * @param focusAndBlur
     *            indicatie of bij type/click commands eerst een focus en daarna een blur uitgevoerd moet worden
     * @param waitForAngular
     *            indicatie of na een commando gewacht moet worden op AngularJS
     * @return
     */
    private static TestCase adaptTestCase(
        final Context context,
        final TestCase selenese,
        final boolean waitForElement,
        final boolean focusAndBlur,
        final boolean waitForAngular)
    {
        final TestCase result = Binder.newTestCase(selenese.getFilename(), selenese.getName(), selenese.getBaseURL());

        final CommandListIterator commandListIterator = selenese.getCommandList().listIterator();

        while (commandListIterator.hasNext()) {
            final ICommand command = commandListIterator.next();

            if (waitForElement) {
                final ICommand waitForElementCommand = determineWaitForElementCommand(context, command);
                if (waitForElementCommand != null) {
                    result.addCommand(waitForElementCommand);
                }
            }

            if (focusAndBlur) {
                final ICommand focusCommand = determineFocusCommand(context, command);
                if (focusCommand != null) {
                    result.addCommand(focusCommand);
                }
            }

            result.addCommand(command);

            if (focusAndBlur) {
                final ICommand blurCommand = determineBlurCommand(context, command);
                if (blurCommand != null) {
                    result.addCommand(blurCommand);
                }
            }
            if (waitForAngular) {
                final ICommand waitForAngularCommand = determineWaitForAngularCommand(context, command);
                if (waitForAngularCommand != null) {
                    result.addCommand(waitForAngularCommand);
                }
            }
        }

        return result;
    }

    private static ICommand determineWaitForElementCommand(final Context context, final ICommand command) {
        final String locator = determineLocator(command);
        return locator == null ? null : context.getCommandFactory().newCommand(command.getIndex(), "waitForElementPresent", locator);
    }

    private static ICommand determineFocusCommand(final Context context, final ICommand command) {
        final String locator = determineLocator(command);
        if (locator != null && command instanceof AbstractCommand && FOCUS_COMMANDS.contains(getCommandName(command).toLowerCase())) {
            return context.getCommandFactory().newCommand(command.getIndex(), COMMAND_FOCUS, locator);
        } else {
            return null;
        }
    }

    private static ICommand determineBlurCommand(final Context context, final ICommand command) {
        final String locator = determineLocator(command);
        if (locator != null && command instanceof AbstractCommand && BLUR_COMMANDS.contains(getCommandName(command).toLowerCase())) {
            final ElementFinder elementFinder = context.getElementFinder();
            final SeleneseCommand<?> seleneseCommand = new FireNamedEvent(elementFinder, JAVASCRIPT_LIBRARY, COMMAND_FOCUS);
            final WDCommand subCommand = new WDCommand(seleneseCommand, COMMAND_BLUR, ArgumentType.LOCATOR);
            try {
                final Constructor<BuiltInCommand> constructor =
                        BuiltInCommand.class.getDeclaredConstructor(Integer.TYPE, String.class, String[].class, ISubCommand.class, Boolean.TYPE);
                constructor.setAccessible(true);
                return constructor.newInstance(command.getIndex(), COMMAND_BLUR, new String[] {locator }, subCommand, false);
            } catch (final ReflectiveOperationException e) {
                throw new IllegalArgumentException("Kon blur command niet maken.");
            }
        } else {
            return null;
        }
    }

    private static ICommand determineWaitForAngularCommand(final Context context, final ICommand command) {
        if (command instanceof AbstractCommand && ANGULAR_COMMANDS.contains(getCommandName(command).toLowerCase())) {
            return new ExecuteAsyncScriptCommand(
                command.getIndex(),
                "waitForAngular",
                "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(arguments[arguments.length - 1]);");
        } else {
            return null;
        }
    }

    private static String determineLocator(final ICommand command) {
        final String[] locators = command.convertLocators(command.getArguments());
        return locators == null || locators.length == 0 ? null : locators[0];
    }

    private static String getCommandName(final ICommand command) {
        try {
            final Field nameField = AbstractCommand.class.getDeclaredField("name");
            nameField.setAccessible(true);
            return (String) nameField.get(command);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan naam van AbstractCommand niet bepalen.", e);
        }
    }

}
