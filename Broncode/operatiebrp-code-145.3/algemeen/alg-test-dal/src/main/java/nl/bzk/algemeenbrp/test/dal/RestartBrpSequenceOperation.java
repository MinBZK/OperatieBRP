/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.test.dal;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.statement.IBatchStatement;
import org.dbunit.database.statement.IStatementFactory;

public class RestartBrpSequenceOperation {

    private final long restartValue;

    private static final String[] TABELLEN_ZONDER_SEQ = {"prot.levsaantekpers"};

    RestartBrpSequenceOperation(final long restartValue) {
        this.restartValue = restartValue;
    }

    /**
     *
     *
     * @param qualifiedTableName
     *            qualified table name, waarin precies 1 '.' voor moet komen.
     *
     * @return Het alter sequence commando.
     */
    protected String getAlterSequenceCommand(final String qualifiedTableName) {
        final StringBuilder seqNameBuilder = new StringBuilder();
        final int indexVanPunt = qualifiedTableName.indexOf('.');
        seqNameBuilder.append(qualifiedTableName.subSequence(0, indexVanPunt + 1));
        seqNameBuilder.append("seq_");
        seqNameBuilder.append(qualifiedTableName.subSequence(indexVanPunt + 1, qualifiedTableName.length()));
        final String sequenceName = seqNameBuilder.toString();
        return String.format("ALTER SEQUENCE %s RESTART WITH %s;", sequenceName, restartValue);
    }

    public void execute(final IDatabaseConnection connection, final List<String> qualifiedTableNames) throws SQLException {
        final IStatementFactory statementFactory = (IStatementFactory) connection.getConfig().getProperty(DatabaseConfig.PROPERTY_STATEMENT_FACTORY);
        final IBatchStatement statement = statementFactory.createBatchStatement(connection);
        try {
            int count = 0;

            for (final String tableName : qualifiedTableNames) {
                if (!Arrays.asList(TABELLEN_ZONDER_SEQ).contains(tableName)) {
                statement.addBatch(getAlterSequenceCommand(tableName));
                count++;
                }
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
