/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import nl.bzk.brp.ecore.bmr.ObjectType;


public abstract class AbstractDomeinObject extends AbstractType {

    private ObjectType   objectType;
    private AbstractType superType;
    private AbstractType interface_;

    public AbstractDomeinObject(final ObjectType objectType) {
        super(objectType.getIdentifierCode());
        this.objectType = objectType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProperty(final BeanProperty property) {
        throw new UnsupportedOperationException();
    }

    public Set<String> getImports() {
        Set<String> resultaat = new TreeSet<String>();
        /*
         * Imports voor properties.
         */
        for (BeanProperty property : getProperties()) {
            if (property.getType().getQualifiedNaam().contains(".")) {
                resultaat.add(property.getType().getQualifiedNaam());
            }
            if (property.getConstraintDefinitie() != null) {
                resultaat.add(property.getConstraintDefinitie().getQualifiedNaam());
            }
            if (property.isCollectie()) {
                resultaat.add("java.util.List");
                resultaat.add("java.util.ArrayList");
                resultaat.add("java.util.Collections");
                resultaat.add(property.getOpposite().getType().getQualifiedNaam());
            }
            if (property.isVerplicht()) {
                resultaat.add("javax.validation.constraints.NotNull");
            }
        }
        /*
         * Import voor supertype.
         */
        if (getSuperType() != null) {
            resultaat.add(getSuperType().getQualifiedNaam());
        }
        /*
         * Import voor interface.
         */
        if (getInterface() != null) {
            resultaat.add(getInterface().getQualifiedNaam());
        }
        resultaat.addAll(internalGetImports());
        return resultaat;
    }

    public AbstractType getInterface() {
        return interface_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNaam() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(getClassNamePrefix()).append(super.getNaam());
        return resultaat.toString();
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPackageNaam() {
        return getPackage().getQualifiedNaam();
    }

    public AbstractType getSuperType() {
        return superType;
    }

    public List<BeanProperty> getVerplichteProperties() {
        Iterable<BeanProperty> resultaat = Iterables.filter(getProperties(), new Predicate<BeanProperty>() {

            @Override
            public boolean apply(final BeanProperty property) {
                return property.isVerplicht();
            }
        });
        Lists.newArrayList(resultaat);
        return Lists.newArrayList(resultaat);
    }

    public void setInterface(final AbstractType interface_) {
        this.interface_ = interface_;
    }

    public void setSuperType(final AbstractType superType) {
        this.superType = superType;
    }

    abstract protected String getClassNamePrefix();

    protected Collection<String> internalGetImports() {
        return Collections.emptySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getVolgnummer() {
        if (objectType != null) {
            return objectType.getVolgnummer();
        }
        return 0;
    }
}
