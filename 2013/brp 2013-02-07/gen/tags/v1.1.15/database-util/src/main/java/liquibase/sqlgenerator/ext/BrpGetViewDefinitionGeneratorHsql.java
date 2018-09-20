/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.GetViewDefinitionGeneratorHsql;
import liquibase.statement.core.GetViewDefinitionStatement;

/**
 * Nieuwe versie van de {@link GetViewDefinitionGeneratorHsql} class, daar deze Liquibase class enigszins is verouderd
 * en nog de 'oude' view definitie van Postgres volgt. Voor de versie van PostgreSQL die binnen dit project gebruikt
 * wordt is een andere "View Definition" nodig en die wordt middels deze class geboden.
 * <p>
 * Merk op dat het package van deze generator vast is (liquibase.sqlgenerator.ext) omdat Liquibase anders deze
 * extensie niet vindt.
 * </p>
 */
public class BrpGetViewDefinitionGeneratorHsql extends GetViewDefinitionGeneratorHsql {

    private static final int PRIORITEIT_TOEVOEGING = 5;

    @Override
    public int getPriority() {
        return PRIORITY_DATABASE + PRIORITEIT_TOEVOEGING;
    }

    @Override
    public Sql[] generateSql(final GetViewDefinitionStatement statement, final Database database,
        final SqlGeneratorChain sqlGeneratorChain)
    {
        try {
            return new Sql[]{
                new UnparsedSql(
                    "SELECT VIEW_DEFINITION FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = '" + statement
                        .getViewName() + "' AND TABLE_SCHEMA='" + database
                        .convertRequestedSchemaToSchema(statement.getSchemaName()) + "'")
            };
        } catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}
