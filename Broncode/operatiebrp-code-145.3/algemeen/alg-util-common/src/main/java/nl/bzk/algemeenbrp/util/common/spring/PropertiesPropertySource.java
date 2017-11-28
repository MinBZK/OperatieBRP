/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

/**
 * Properties file based property resource.
 */
public final class PropertiesPropertySource extends PropertySource<String> {

    private final Properties properties;

    /**
     * Constructor; uses given properties (can be used to create a mutable property source;
     * reference is used directly).
     *
     * @param name property source name
     * @param properties properties
     */
    public PropertiesPropertySource(final String name, final Properties properties) {
        super(name);

        this.properties = properties;
        if (this.properties == null) {
            throw new IllegalArgumentException("Properties verplicht.");
        }
    }

    /**
     * Constructor; reads property file.
     *
     * @param name property source name
     * @param propertiesResource properties resource to read
     */
    public PropertiesPropertySource(final String name, final Resource propertiesResource) {
        super(name);

        if (propertiesResource == null) {
            throw new IllegalArgumentException("Properties verplicht.");
        }

        properties = new Properties();
        try (InputStream input = propertiesResource.getInputStream()) {
            properties.load(input);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Properties konden niet geladen worden.", e);
        }
    }

    @Override
    public Object getProperty(final String name) {
        return properties.getProperty(name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PropertiesPropertySource)) {
            return false;
        }

        final PropertiesPropertySource rhs = (PropertiesPropertySource) obj;
        return new EqualsBuilder().append(name, rhs.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(name).toHashCode();
    }
}
