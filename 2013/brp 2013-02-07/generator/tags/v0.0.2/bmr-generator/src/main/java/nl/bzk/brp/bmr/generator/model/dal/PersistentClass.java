/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.ArrayList;
import java.util.Collection;

import nl.bzk.brp.ecore.bmr.ObjectType;


public class PersistentClass extends BasisPersistentClass {

    private static final String CLASS_NAME_PREFIX = "Persistent";

    public PersistentClass(final ObjectType objectType) {
        super(objectType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<BeanProperty> getProperties() {
        return getInterface().getProperties();
    }

    public Collection<BeanProperty> getHistorieStatussen() {
        Collection<BeanProperty> resultaat = new ArrayList<BeanProperty>();
        for (BeanProperty property : getProperties()) {
            if (property.isStatusHistorieIndicator()) {
                resultaat.add(property);
            }
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExtensionPoint() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClassNamePrefix() {
        return CLASS_NAME_PREFIX;
    }
}
