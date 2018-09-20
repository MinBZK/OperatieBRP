/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.art.util.main;

import java.io.IOException;
import java.sql.SQLException;

import nl.bzk.brp.art.util.backup.LogicalBackup;
import nl.bzk.brp.sierratestdatagenerator.SierraTestdataGenerator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;


/**
 * Util runner is the maincalss in this jar, so we can run different other utilities using the same jar file.
 * Java supports only one main class (java -jar <jarfilename>).
 * You can run different man classes (java -jar -cp <jarfilename> nl.mypath.MyClass and
 *      java -jar -cp <jarfilename> nl.otherpath.Otherclass)
 * but is cumbersome (classpath in manifest is completely ignored ea.)
 */
public final class UtilRunner {
    /** . */
    private UtilRunner() {
    }

    /**
     * .
     * @param url .
     * @param userName .
     * @param password .
     * @throws SQLException .
     * @throws ClassNotFoundException .
     */
    private void runBackup(final String url, final String userName, final String password)
        throws SQLException, ClassNotFoundException
    {
        LogicalBackup runner = new LogicalBackup(url, userName, password);
        runner.run();
    }

    /**
     * .
     * @param excelDir .
     * @param sqlFile .
     * @throws IOException .
     */
    private void runConversion(final String excelDir, final String sqlFile) throws IOException {
        SierraTestdataGenerator runner = new SierraTestdataGenerator();
        runner.run(excelDir, sqlFile);
    }
    /**
     * .
     * @param args .
     * @throws Exception .
     */
    public static void main(final String[] args) throws Exception {
        Options options = CommandLineUtil.createOptions();
        CommandLine line = CommandLineUtil.parse(options, args);

        UtilRunner runner = new UtilRunner();
        if (line.hasOption(CommandLineUtil.COMMAND_BACKUP)) {
            runner.runBackup(
                    line.getOptionValue(CommandLineUtil.OPTION_DATABASE),
                    line.getOptionValue(CommandLineUtil.OPTION_USERNAME),
                    line.getOptionValue(CommandLineUtil.OPTION_PASSWORD)
            );
        } else if (line.hasOption(CommandLineUtil.COMMAND_CONVERT)) {
            runner.runConversion(
                    line.getOptionValue(CommandLineUtil.OPTION_EXCEL_DIR),
                    line.getOptionValue(CommandLineUtil.OPTION_SQL)
            );
        }

    }
}
