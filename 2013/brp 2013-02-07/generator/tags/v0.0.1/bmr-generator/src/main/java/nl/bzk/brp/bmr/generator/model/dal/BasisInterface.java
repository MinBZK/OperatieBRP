/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.bzk.brp.ecore.bmr.ObjectType;


public class BasisInterface extends EntiteitType {

    static final String               CLASS_NAME_PREFIX = "Basis";

    private Map<String, BeanProperty> properties        = new LinkedHashMap<String, BeanProperty>();

    public BasisInterface(final ObjectType objectType) {
        super(objectType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProperty(final BeanProperty property) {
        properties.put(property.getNaam(), property);
        property.setContainingType(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClassNamePrefix() {
        return CLASS_NAME_PREFIX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<BeanProperty> getProperties() {
        return Collections.unmodifiableCollection(properties.values());
    }
}
