/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.datasource;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

/**
 * Wrapper om de standaard Tomcat datasource factory die properties kan verwerken die naar Systeem variabelen wijzen.
 *
 * Gebaseerd op Tomcat versie 7.0.67.
 */
public final class CustomDataSourceFactory implements ObjectFactory {

    private static final String[] ALL_PROPERTIES;

    static {
        try {
            final Field field = BasicDataSourceFactory.class.getDeclaredField("ALL_PROPERTIES");
            field.setAccessible(true);
            ALL_PROPERTIES = (String[]) field.get(null);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Object getObjectInstance(final Object obj, final Name name, final Context nameCtx, final Hashtable<?, ?> environment) throws Exception {
        // We only know how to deal with <code>javax.naming.Reference</code>s
        // that specify a class name of "javax.sql.DataSource"
        if (obj == null || !(obj instanceof Reference)) {
            return null;
        }
        final Reference ref = (Reference) obj;
        if (!"javax.sql.DataSource".equals(ref.getClassName())) {
            return null;
        }

        final Properties properties = new Properties();
        for (int i = 0; i < ALL_PROPERTIES.length; i++) {
            final String propertyName = ALL_PROPERTIES[i];
            final RefAddr ra = ref.get(propertyName);
            if (ra != null) {
                final String propertyValue = ra.getContent().toString();
                properties.setProperty(propertyName, verwerkSystemProperty(propertyValue));
            }
        }

        return BasicDataSourceFactory.createDataSource(properties);
    }

    /**
     * Zoek combinaties van '${' en '}' en vervang deze met de gegeven Systeem environment waarde.
     * @param value waarde
     * @return verwerkte waarde
     */
    private String verwerkSystemProperty(final String value) {
        String result = value;

        while (true) {
            int startIndex = result.indexOf("${", 0);
            if (startIndex == -1) {
                break;
            }

            final int endIndex = result.indexOf('}', startIndex);
            if (endIndex == -1) {
                throw new IllegalArgumentException();
            }

            final String variableName = result.substring(startIndex + 2, endIndex);
            final String variabelValue = System.getenv(variableName);

            result = result.substring(0, startIndex) + variabelValue + result.substring(endIndex + 1);
        }

        return result;
    }

}
