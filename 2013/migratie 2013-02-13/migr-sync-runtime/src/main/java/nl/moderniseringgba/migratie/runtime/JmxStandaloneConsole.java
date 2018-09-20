/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.runtime;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jminix.console.application.MiniConsoleApplication;
import org.jminix.console.tool.StandaloneMiniConsole;
import org.jminix.server.RmiServerConnectionProvider;

/**
 * Klasse waarmee JMX MBeans via een light-weight webserver worden getoond.
 */
public final class JmxStandaloneConsole {

    private static final String JMX_HOST_OPTION_LABEL = "jh";
    private static final String JMX_PORT_OPTION_LABEL = "jp";
    private static final String CONSOLE_PORT_OPTION_LABEL = "cp";

    private JmxStandaloneConsole() {

    }

    /**
     * @param args
     *            argumenten waarmee de klasse wordt gestart.
     */
    public static void main(final String[] args) {
        final Options options = setupOptions();
        final CommandLineParser parser = new GnuParser();
        try {
            final CommandLine line = parser.parse(options, args);
            final String jmxHostname = line.getOptionValue(JMX_HOST_OPTION_LABEL);
            final String jmxPort = line.getOptionValue(JMX_PORT_OPTION_LABEL);
            final String consolePort = line.getOptionValue(CONSOLE_PORT_OPTION_LABEL);

            final StringBuilder url = new StringBuilder();
            url.append("service:jmx:rmi:///jndi/rmi://");
            url.append(jmxHostname).append(":").append(jmxPort);
            url.append("/jmxrmi");

            final RmiServerConnectionProvider provider = new RmiServerConnectionProvider();
            provider.setServiceUrl(url.toString());

            final MiniConsoleApplication app = new MiniConsoleApplication();
            app.setServerConnectionProvider(provider);

            new StandaloneMiniConsole(Integer.valueOf(consolePort), app);
            System.out.println("Console gestart");
        } catch (final ParseException e) {
            e.printStackTrace();
            showUsage(options);
        }
    }

    private static void showUsage(final Options options) {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("JmxStandaloneConsole", options);
    }

    private static Options setupOptions() {
        OptionBuilder.withArgName("hostnaam/ipadres");
        OptionBuilder.hasArg();
        OptionBuilder.isRequired();
        OptionBuilder.withDescription("Hostnaam of IP-adres van de JMX server");
        final Option jmxHostnameIpAdres = OptionBuilder.create(JMX_HOST_OPTION_LABEL);

        OptionBuilder.withArgName("jmx poortnummer");
        OptionBuilder.hasArg();
        OptionBuilder.isRequired();
        OptionBuilder.withDescription("Poortnummer van de JMX server");
        final Option jmxPort = OptionBuilder.create(JMX_PORT_OPTION_LABEL);

        OptionBuilder.withArgName("console poortnummer");
        OptionBuilder.hasArg();
        OptionBuilder.isRequired();
        OptionBuilder.withDescription("Poortnummer waarop de console benaderbaar is");
        final Option consolePort = OptionBuilder.create(CONSOLE_PORT_OPTION_LABEL);

        final Options options = new Options();
        options.addOption(jmxHostnameIpAdres);
        options.addOption(jmxPort);
        options.addOption(consolePort);
        return options;
    }
}
