/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.generateplugin.mojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Generate mojo.
 */
@Mojo(name = "generate")
public final class GenerateMojo extends AbstractGenerateMojo {

    @Override
    protected void processRecord(final StringBuilder result, final ResultSet resultSet) throws SQLException {
        result.append(resultSet.getString(1)).append("\n");
    }
}
