/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.jndi;

import java.util.Enumeration;
import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jndi.JndiTemplate;

/**
 * Klasse voor JNDI configuratie.
 */
public final class JndiConfiguration implements BeanFactoryPostProcessor {

    private static final String VALUE_SPLITTER = ",";

    private static final Logger LOG = LoggerFactory.getLogger();

    private Properties properties;
    private String name = "jndiTemplate";

    /**
     * Zet de waarde van environment.
     * @param environmentProperties environment
     */
    public void setEnvironment(final Properties environmentProperties) {
        properties = environmentProperties;
    }

    /**
     * Zet de waarde van name.
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) {
        final Properties[] props = splitProperties(properties);
        LOG.info("Configuring " + props.length + " jndi templates (base name '" + name + "') ...");

        for (int i = 0; i < props.length; i++) {
            final String beanName = name + (i == 0 ? "" : Integer.toString(i));
            final JndiTemplate jndiTemplate = new JndiTemplate(props[i]);

            beanFactory.registerSingleton(beanName, jndiTemplate);
        }
    }

    private Properties[] splitProperties(final Properties meerwaardigeProperties) {
        final int aantal = getAantal(meerwaardigeProperties);

        final Properties[] result = new Properties[aantal];
        for (int i = 0; i < aantal; i++) {
            result[i] = new Properties();
        }

        final Enumeration<?> namesEnum = meerwaardigeProperties.propertyNames();
        while (namesEnum.hasMoreElements()) {
            final String propertyNaam = (String) namesEnum.nextElement();

            setProperties(result, propertyNaam, meerwaardigeProperties.getProperty(propertyNaam));
        }

        return result;

    }

    private void setProperties(final Properties[] result, final String propertyNaam, final String value) {
        final String[] values = value.split(VALUE_SPLITTER);

        if (values.length == 1) {
            for (final Properties element : result) {
                element.setProperty(propertyNaam, values[0]);
            }
        } else if (values.length > 1) {
            for (int i = 0; i < values.length; i++) {
                result[i].setProperty(propertyNaam, values[i]);
            }
        }

    }

    private int getAantal(final Properties props) {
        int max = 1;

        final Enumeration<?> namesEnum = props.propertyNames();
        while (namesEnum.hasMoreElements()) {
            final String propertyNaam = (String) namesEnum.nextElement();
            final String value = props.getProperty(propertyNaam);
            final int aantal = value.split(VALUE_SPLITTER).length;

            if (aantal > 1) {
                if (max > 1 && aantal != max) {
                    throw new RuntimeException("Inconsistente hoeveelheden parameters");
                } else {
                    max = aantal;
                }
            }
        }
        return max;
    }
}
