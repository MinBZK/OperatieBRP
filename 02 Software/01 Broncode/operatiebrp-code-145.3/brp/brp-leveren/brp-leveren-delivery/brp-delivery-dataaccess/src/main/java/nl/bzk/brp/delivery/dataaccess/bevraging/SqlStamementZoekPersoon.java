/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;

/**
 * SqlStamementZoekPersoon.
 */
public final class SqlStamementZoekPersoon {

    private final String sql;
    private final List<Object> parameters;

    /**
     * @param sql sql
     * @param parameters parameters
     */
    public SqlStamementZoekPersoon(final String sql, final List<Object> parameters) {
        this.sql = sql;
        this.parameters = Collections.unmodifiableList(parameters);
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public String getSql() {
        return sql;
    }

    @Override
    public String toString() {
        final String format = sql.replaceAll("\\?", "%s");
        final Object[] parametersConverted = parameters.stream().map(p -> p instanceof String ? "'" + p + "'" : p).collect(Collectors.toList()).toArray();
        return new BasicFormatterImpl().format(String.format(format, parametersConverted));
    }
}
