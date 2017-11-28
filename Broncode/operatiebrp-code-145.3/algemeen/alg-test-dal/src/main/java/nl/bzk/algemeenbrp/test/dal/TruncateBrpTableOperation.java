/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.test.dal;

import java.sql.SQLException;
import java.util.List;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.statement.IBatchStatement;
import org.dbunit.database.statement.IStatementFactory;

public class TruncateBrpTableOperation {

    TruncateBrpTableOperation() {
    }

    public void execute(final IDatabaseConnection connection, final List<String> qualifiedTableNames) throws SQLException {
        final IStatementFactory statementFactory = (IStatementFactory) connection.getConfig().getProperty(DatabaseConfig.PROPERTY_STATEMENT_FACTORY);
        final IBatchStatement statement = statementFactory.createBatchStatement(connection);
        try {
            int count = 0;

            for (final String tableName : qualifiedTableNames) {
                statement.addBatch("DELETE FROM " + tableName);
                count++;
            }
            if (count > 0) {
                statement.executeBatch();
                statement.clearBatch();
            }
        } finally {
            statement.close();
        }
    }
}
