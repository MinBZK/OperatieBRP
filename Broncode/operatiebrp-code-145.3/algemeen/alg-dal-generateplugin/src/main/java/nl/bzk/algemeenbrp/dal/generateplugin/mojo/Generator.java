/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.generateplugin.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.logging.Log;

/**
 * Generator.
 */
public final class Generator {

    private static final Pattern REPLACEMENT_PATTERN = Pattern.compile("\\/\\*\\* QUERY:(.*?)\\*\\/", Pattern.DOTALL);

    private final Log log;
    private final RecordProcessor recordProcessor;

    /**
     * Constructor.
     * @param log logger
     * @param recordProcessor record processor
     */
    public Generator(final Log log, final RecordProcessor recordProcessor) {
        this.log = log;
        this.recordProcessor = recordProcessor;
    }

    /**
     * Genereer op basis van een template.
     * @param dataSource data source
     * @param templateFile template
     * @param destinationDirectory doel directory
     * @param packageName doel package (sub directory)
     */
    public void generate(final DataSource dataSource, final File templateFile, final File destinationDirectory, final String packageName) {
        try (Connection connection = dataSource.getConnection()) {
            generate(connection, templateFile, destinationDirectory, packageName);
        } catch (final SQLException e) {
            log.error("Probleem tijdens opzetten database verbinding", e);
        }
    }

    private void generate(final Connection connection, final File templateFile, final File destinationDirectory, final String packageName) {
        try {
            final String template = readTemplate(templateFile);
            final String result = processTemplate(connection, template);
            writeResult(new File(destinationDirectory, convertToDirectory(packageName)), templateFile.getName(), result);
        } catch (final SQLException | IOException e) {
            log.error(String.format("Probleem tijdens verwerken template %s", templateFile.getName()), e);
        }
    }

    private String convertToDirectory(final String packageName) {
        return packageName.replace(".", File.separator);
    }

    private String processTemplate(final Connection connection, final String template) throws SQLException {
        final Matcher matcher = REPLACEMENT_PATTERN.matcher(template);
        int lastIndex = 0;
        final StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            result.append(template.substring(lastIndex, matcher.start()));
            lastIndex = matcher.end();

            final String query = matcher.group(1);
            final String queryResult = processQuery(connection, query);

            result.append(queryResult);
        }

        result.append(template.substring(lastIndex));
        return result.toString();
    }

    private String processQuery(final Connection connection, final String query) throws SQLException {
        final StringBuilder result = new StringBuilder();
        try (final PreparedStatement statement = connection.prepareStatement(query); final ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                recordProcessor.processRecord(result, resultSet);
            }
        }

        return result.toString();
    }

    private String readTemplate(final File templateFile) throws IOException {
        try (InputStream input = new FileInputStream(templateFile)) {
            return IOUtils.toString(input, "UTF-8");
        }
    }

    private void writeResult(final File destinationDirectory, final String templateName, final String result) throws IOException {
        FileUtils.forceMkdir(destinationDirectory);

        final File destination = new File(destinationDirectory, templateName);
        try (OutputStream output = new FileOutputStream(destination)) {
            IOUtils.write(result, output, "UTF-8");
        }
    }

    /**
     * Record processor: bepaalt de output die wordt gegenereerd op basis van een database record.
     */
    @FunctionalInterface
    public interface RecordProcessor {

        /**
         * Verwerk database record.
         * @param result generatie resultaat
         * @param resultSet database record
         * @throws SQLException bij sql fouten
         */
        void processRecord(final StringBuilder result, final ResultSet resultSet) throws SQLException;
    }
}
