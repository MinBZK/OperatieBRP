/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.setup;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.synchronisatie.dal.util.brpkern.DBUnitBrpUtil;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlWriter;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

public class GenerateDBUnitXML {

    private GenerateDBUnitXML() {
    }

    public static void main(final String[] args) throws Exception {
        // database connection
        Class.forName("org.postgresql.Driver");
        try (Connection jdbcConnection = DriverManager.getConnection("jdbc:postgresql://localhost/brp", "migratie", "M1gratie")) {
            final IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
            final DatabaseConfig config = connection.getConfig();
            config.setProperty("http://www.dbunit.org/features/qualifiedTableNames", true);
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());

            final List<String> databaseSchemas = DBUnitBrpUtil.getSchemas();

            for (final String databaseSchema : databaseSchemas) {
                final List<String> stamTabellen = DBUnitBrpUtil.getStamtabellen(databaseSchema);

                if (stamTabellen.isEmpty()) {
                    continue;
                }

                // Omgekeerde volgorde om rekening te houden met data dependencies tussen tabellen
                Collections.reverse(stamTabellen);

                final String[] datasetTabellen = stamTabellen.toArray(new String[stamTabellen.size()]);
                for (int i = 0; i < datasetTabellen.length; i++) {
                    datasetTabellen[i] = databaseSchema + "." + datasetTabellen[i];
                }

                // export database schema
                final IDataSet schemaDataSet = connection.createDataSet(datasetTabellen);

                final FlatXmlWriter datasetWriter = new FlatXmlWriter(new FileOutputStream("brpStamgegevens-" + databaseSchema + ".xml"));
                datasetWriter.setIncludeEmptyTable(false);
                datasetWriter.setDocType("brp_kern.dtd");
                datasetWriter.write(schemaDataSet);
            }
        }
    }
}
