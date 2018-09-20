/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.sql;

import javax.sql.DataSource;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Klasse voor het uitvoeren van SQL op een datasource.
 */
public final class ExecuteSql {

    private static final String OPTION_CONFIG = "config";
    private static final String OPTION_DATASOURCE = "datasource";

    /**
     * Default constructor.
     */
    private ExecuteSql() {

    }

    /**
     * Main methode voor het uitvoeren van SQL.
     *
     * @param args
     *            De meegegeven argumenten.
     */
    public static void main(final String[] args) {

        // create Options object
        final Options options = new Options();
        options.addOption(OPTION_DATASOURCE, true, "Name of the datasource to use in the spring application context");
        options.addOption(OPTION_CONFIG, true, "Name of the spring application context config");

        // create the parser
        final CommandLineParser parser = new DefaultParser();
        final CommandLine line;
        try {
            // parse the command line arguments
            line = parser.parse(options, args);

            if (line.getArgList().isEmpty()) {
                throw new ParseException("Geen scripts aangegeven");
            }
        } catch (final ParseException e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ExecuteSql [options] script [script2 [script3 ...]]", options);
            return;
        }

        final String config = line.getOptionValue(OPTION_CONFIG, "classpath:test-beans.xml");
        final String datasourceName = line.getOptionValue(OPTION_DATASOURCE, "syncDalDataSource");

        final GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext();
        applicationContext.registerShutdownHook();
        applicationContext.load(config);
        applicationContext.refresh();

        final DataSource dataSource = (DataSource) applicationContext.getBean(datasourceName);

        final String[] scripts = line.getArgs();
        for (final String script : scripts) {
            SqlUtil.executeSqlFile(dataSource, script);
        }

        applicationContext.close();
    }
}
