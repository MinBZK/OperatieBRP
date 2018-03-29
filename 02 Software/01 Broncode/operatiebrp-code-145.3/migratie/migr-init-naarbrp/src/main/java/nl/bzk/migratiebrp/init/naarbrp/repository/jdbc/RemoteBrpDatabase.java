/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Enables connecting to foreign BRP database.
 */
@Component
public class RemoteBrpDatabase extends BasisJdbcRepository {
    private static final String FOREIGN_SERVER = "brp_server";

    private static final String FOREIGN_AUT_SCHEMA = "autaut";
    private static final String FOREIGN_KERN_SCHEMA = "kern";

    private static final String PERSOON_SRT_TABLE = "srtpers";
    private static final String PERSOON_TABLE = "pers";
    private static final String NADEREBIJHAARD_TABLE = "naderebijhaard";
    private static final String PARTIJ_TABLE = "partij";
    private static final String PARTIJROL_TABLE = "partijrol";
    private static final String DIENST_TABLE = "dienst";
    private static final String DIENSTBUNDEL_TABLE = "dienstbundel";
    private static final String SOORT_DIENST_TABLE = "srtdienst";
    private static final String LEVERINGSAUTORISATIE_TABLE = "levsautorisatie";
    private static final String TOEGANGLEVERINGSAUTORISATIE_TABLE = "toeganglevsautorisatie";

    @Value("${brp.jdbc.host:}")
    private String brpHost;

    @Value("${brp.jdbc.port:}")
    private String brpPort;

    @Value("${brp.jdbc.name:}")
    private String brpDatabase;

    @Value("${brp.jdbc.username:}")
    private String brpUsername;

    @Value("${brp.jdbc.password:}")
    private String brpPassword;

    @Value("${jdbc.database.username:}")
    private String gbavUsername;

    /**
     * Setup database to be able to connect to foreign BRP database.
     */
    public final void initialize() {
        setupForeignServer();
        createUserMapping();
        importForeignSchemas(FOREIGN_AUT_SCHEMA, DIENST_TABLE, DIENSTBUNDEL_TABLE, SOORT_DIENST_TABLE, LEVERINGSAUTORISATIE_TABLE,
                TOEGANGLEVERINGSAUTORISATIE_TABLE);
        importForeignSchemas(FOREIGN_KERN_SCHEMA, NADEREBIJHAARD_TABLE, PERSOON_SRT_TABLE, PERSOON_TABLE, PARTIJ_TABLE, PARTIJROL_TABLE);
    }

    private void setupForeignServer() {
        getJdbcTemplate().getJdbcOperations().execute(String.format("DROP SERVER IF EXISTS %s CASCADE", FOREIGN_SERVER));

        getJdbcTemplate().getJdbcOperations()
                .execute(String.format("CREATE SERVER %s FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host '%s', port '%s', dbname '%s')", FOREIGN_SERVER,
                        brpHost, brpPort, brpDatabase));
    }

    private void createUserMapping() {
        getJdbcTemplate().getJdbcOperations().execute(String.format("CREATE USER MAPPING FOR %s SERVER %s OPTIONS (user '%s', password '%s')", gbavUsername,
                FOREIGN_SERVER, brpUsername, brpPassword));
    }

    private void importForeignSchemas(final String foreignSchema, final String... tables) {
        getJdbcTemplate().getJdbcOperations().execute(String.format("IMPORT FOREIGN SCHEMA %s LIMIT TO (%s) FROM SERVER %s INTO %s", foreignSchema,
                Arrays.stream(tables).collect(Collectors.joining(",")), FOREIGN_SERVER, foreignSchema));
    }
}
