/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.setup;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;

public class GenerateDBUnitDTD {

    private GenerateDBUnitDTD() {
    }

    public static void main(final String[] args) throws Exception {
        // database connection
        Class.forName("org.postgresql.Driver");
        final Connection jdbcConnection =
                DriverManager.getConnection("jdbc:postgresql://localhost/BRP/kern", "postgres", "postgres");
        final IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        connection.getConfig().setProperty("http://www.dbunit.org/features/qualifiedTableNames", true);

        // write DTD file
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("d:\\brp_kern.dtd"));
    }
}
