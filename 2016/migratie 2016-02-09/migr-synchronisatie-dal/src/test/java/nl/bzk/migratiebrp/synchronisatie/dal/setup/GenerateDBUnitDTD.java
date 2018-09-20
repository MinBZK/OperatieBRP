/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.setup;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdWriter;

public class GenerateDBUnitDTD {

    private GenerateDBUnitDTD() {
    }

    public static void main(final String[] args) throws Exception {
        // database connection
        Class.forName("org.postgresql.Driver");
        try (Connection jdbcConnection = DriverManager.getConnection("jdbc:postgresql://localhost/brp", "migratie", "M1gratie")) {
            final IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
            connection.getConfig().setProperty("http://www.dbunit.org/features/qualifiedTableNames", true);

            // write DTD file
            final FlatDtdWriter datasetWriter = new FlatDtdWriter(new OutputStreamWriter(new FileOutputStream("brp_kern_2.dtd")));
            datasetWriter.setContentModel(FlatDtdWriter.CHOICE);
            datasetWriter.write(connection.createDataSet());
        }
    }
}
