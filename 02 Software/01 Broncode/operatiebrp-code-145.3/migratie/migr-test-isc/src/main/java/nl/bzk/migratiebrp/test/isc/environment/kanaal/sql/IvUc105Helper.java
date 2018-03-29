/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

/**
 * Helper klasse voor UC105.
 */
public final class IvUc105Helper {
    private final String scriptResource;
    private final DataSource source;
    private final DataSource destination;

    /**
     * Default constructor.
     * @param scriptResource Het te gebruiken script
     * @param source De bron datasource
     * @param destination De doel datasource
     */
    public IvUc105Helper(final String scriptResource, final DataSource source, final DataSource destination) {
        this.scriptResource = scriptResource;
        this.source = source;
        this.destination = destination;
    }

    /**
     * Voer het script uit tegen de bron- en doeldatasource.
     * @return Het resultaat.
     * @throws IOException In het geval er tijdens het lezen van het script wat fout gaat.
     */
    public String execute() throws IOException {
        final List<String> sourceScript = leesScript(scriptResource);
        final List<String> destinationScript = normaliseerScript(executeScript(source, sourceScript));
        executeScript(destination, destinationScript);

        final StringBuilder resultaat = new StringBuilder();
        for (final String destinationStatement : destinationScript) {
            resultaat.append(destinationStatement).append("\n");
        }
        return resultaat.toString();
    }

    private List<String> leesScript(final String resource) throws IOException {
        final String input;
        try (InputStream is = IvUc105Helper.class.getResourceAsStream(resource)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource '" + resource + "' bestaat niet.");
            }
            input = IOUtils.toString(is, Charset.defaultCharset());
        }

        // Verwijderen psql regels (beginnen met '\p')
        final String sqlScript = input.replaceAll("(?m)^\\\\p.*$", "");

        final List<String> statements = new LinkedList<>();
        ScriptUtils.splitSqlScript(sqlScript, ';', statements);

        return statements;
    }

    private List<String> executeScript(final DataSource dataSource, final List<String> statements) {
        final List<String> resultaat = new ArrayList<>();

        final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        for (final String statement : statements) {
            if (statement.matches("(?is)^\\s*SELECT.*$")) {
                final List<String> statementResultaat = jdbc.queryForList(statement, String.class);
                resultaat.addAll(statementResultaat);
            } else {
                jdbc.execute(statement);
            }
        }

        return resultaat;
    }

    private List<String> normaliseerScript(final List<String> scriptRegels) {
        Iterator<String> iterator = scriptRegels.iterator();
        final List<String> resultaat = new ArrayList<>(scriptRegels.size());
        while (iterator.hasNext()) {
            final String statement = iterator.next();
            // Voor inserts nemen we alles samen tot een statement
            if (statement.startsWith("insert")) {
                bepaalInsertStatement(resultaat, iterator, statement);
            } else {
                resultaat.add(statement);
            }
        }

        return resultaat;
    }

    private void bepaalInsertStatement(List<String> resultaat, Iterator<String> iterator, String statement) {
        int containingInserts = 0;
        StringBuilder statementBuilder = new StringBuilder(statement);
        while (iterator.hasNext()) {
            final String nextStatement = iterator.next();
            if (!";".equals(nextStatement)) {
                statementBuilder.append(nextStatement);
                containingInserts++;
            } else {
                break;
            }
        }
        if (containingInserts > 0) {
            statementBuilder.append(";");
            resultaat.add(statementBuilder.toString());
        }
    }

}
