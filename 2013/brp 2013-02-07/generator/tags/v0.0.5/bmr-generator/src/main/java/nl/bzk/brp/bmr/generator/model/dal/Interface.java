/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;

import nl.bzk.brp.ecore.bmr.ObjectType;


public class Interface extends EntiteitType {

    private static final String CLASS_NAME_PREFIX = "";

    private PersistentClass     implementation;

    public Interface(final ObjectType objectType) {
        super(objectType);
    }

    public PersistentClass getImplementation() {
        return implementation;
    }

    public String getMeervoudsNaam() {
        return getNaam() + "en";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<BeanProperty> getProperties() {
        return getSuperType().getProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExtensionPoint() {
        return true;
    }

    public void setImplementation(final PersistentClass implementation) {
        this.implementation = implementation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClassNamePrefix() {
        return CLASS_NAME_PREFIX;
    }
}
