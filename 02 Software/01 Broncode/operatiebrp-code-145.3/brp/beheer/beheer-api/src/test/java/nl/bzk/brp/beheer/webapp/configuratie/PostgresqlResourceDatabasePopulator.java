/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.CannotReadScriptException;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Orginele resource populator split de sql statements, maar kan niet omgaan met splitten van functions in postgres.
 */
public class PostgresqlResourceDatabasePopulator implements DatabasePopulator {

    private static final Logger LOG = LoggerFactory.getLogger();

    private List<Resource> scripts = new ArrayList<>();
    private String sqlScriptEncoding = null;

    @Override
    public void populate(Connection connection) throws ScriptException {
        Assert.notNull(connection, "Connection must not be null");
        for (Resource script : scripts) {
            LOG.info("Bezig om " + script.getFilename() + " te verwerken");
            EncodedResource encodedScript = new EncodedResource(script, this.sqlScriptEncoding);

            final StringBuilder sqlScript = new StringBuilder();
            try (LineNumberReader lnr = new LineNumberReader(encodedScript.getReader())) {
                String line;
                do {
                    line = lnr.readLine();
                    if (line != null) {
                        sqlScript.append(line);
                        sqlScript.append("\n");
                    }
                } while (line != null);
            } catch (final IOException ioe) {
                throw new CannotReadScriptException(encodedScript, ioe);
            }

            try {
                final Statement statement = connection.createStatement();
                statement.execute(sqlScript.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOG.info(script.getFilename() + " verwerkt");
        }
    }

    /**
     * Add a script to execute to initialize or clean up the database.
     * @param script the path to an SQL script (never {@code null})
     */
    public void addScript(Resource script) {
        Assert.notNull(script, "Script must not be null");
        scripts.add(script);
    }

    /**
     * Specify the encoding for the configured SQL scripts, if different from the
     * platform encoding.
     * @param sqlScriptEncoding the encoding used in scripts; may be {@code null}
     * or empty to indicate platform encoding
     * @see #addScript(Resource)
     */
    public void setSqlScriptEncoding(String sqlScriptEncoding) {
        this.sqlScriptEncoding = StringUtils.hasText(sqlScriptEncoding) ? sqlScriptEncoding : null;
    }

}
