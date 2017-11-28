/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.generateplugin.mojo;

import static nl.bzk.algemeenbrp.dal.generateplugin.mojo.Utils.INDENTATION;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Enum mojo.
 */
@Mojo(name = "generate-enumerations")
public final class GenerateEnumerationsMojo extends AbstractGenerateMojo {

    @Override
    protected void processRecord(final StringBuilder result, final ResultSet resultSet) throws SQLException {
        final String javaDoc = resultSet.getString(1);
        final String javaNameBase = resultSet.getString(2);
        final String javaStatement = resultSet.getString(3);

        // Javadoc
        result.append(INDENTATION).append("/** ").append(javaDoc).append(" */\n");

        // Statement
        result.append(INDENTATION).append(Utils.convertToJavaEnumName(javaNameBase)).append(javaStatement);

        // Seperator
        if (resultSet.isLast()) {
            result.append(";\n");
        } else {
            result.append(",\n");
        }
    }
}
