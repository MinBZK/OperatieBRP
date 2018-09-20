/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;
import java.util.TreeSet;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import nl.bzk.brp.ecore.bmr.ObjectType;


public class BasisPersistentClass extends EntiteitType {

    private static final class BeanPropertyIsManyToOnePredicate implements Predicate<BeanProperty> {

        @Override
        public boolean apply(final BeanProperty property) {
            return property.isManyToOne();
        }
    }

    private static final class BeanPropertyIsOneToManyPredicate implements Predicate<BeanProperty> {

        @Override
        public boolean apply(final BeanProperty property) {
            return property.isCollectie();
        }
    }

    private static final String CLASS_NAME_PREFIX = "AbstractPersistent";

    public BasisPersistentClass(final ObjectType objectType) {
        super(objectType);
    }

    public String getDatabaseNaam() {
        return getObjectType().getIdentifierDB();
    }

    public BeanProperty getIdProperty() {
        for (BeanProperty property : getProperties()) {
            if (property.isIdentifier()) {
                return property;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<BeanProperty> getProperties() {
        return getInterface().getProperties();
    }

    public String getSchemaNaam() {
        return getObjectType().getSchema().getNaam();
    }

    public String getSequenceGeneratorName() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(getObjectType().getIdentifierCode().toUpperCase());
        return resultaat.toString();
    }

    /**
     * Leidt de naam van de gebruikte sequence in de database af van het object type.
     *
     * @param objectType het object type waarvan de sequence naam wordt afgeleid.
     * @return de sequence naam.
     */
    public String getSequenceNaam() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(getObjectType().getSchema().getNaam());
        resultaat.append(".seq_");
        resultaat.append(getObjectType().getIdentifierDB());
        return resultaat.toString();
    }

    private boolean columnAnnotatieNodig() {
        return Iterables.any(getProperties(), new Predicate<BeanProperty>() {

            @Override
            public boolean apply(final BeanProperty property) {
                if (property.isCollectie() || property.isManyToOne()) {
                    return false;
                }
                return !property.getDatabaseNaam().equalsIgnoreCase(property.getNaam());
            }
        });
    }

    private Iterable<BeanProperty> getManyToOneProperties() {
        return Iterables.filter(getProperties(), new BeanPropertyIsManyToOnePredicate());
    }

    private Iterable<BeanProperty> getOneToManyProperties() {
        return Iterables.filter(getProperties(), new BeanPropertyIsOneToManyPredicate());
    }

    private boolean heeftManyToOneProperties() {
        return Iterables.any(getProperties(), new BeanPropertyIsManyToOnePredicate());
    }

    private boolean heeftOneToManyProperties() {
        return Iterables.any(getProperties(), new BeanPropertyIsOneToManyPredicate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClassNamePrefix() {
        return CLASS_NAME_PREFIX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<String> internalGetImports() {
        Collection<String> resultaat = new TreeSet<String>();
        if (getSuperType() == null) {
            /*
             * Annotaties rondom het mappen van de ID property.
             */
            resultaat.add("javax.persistence.Access");
            resultaat.add("javax.persistence.AccessType");
            resultaat.add("javax.persistence.GeneratedValue");
            resultaat.add("javax.persistence.GenerationType");
            resultaat.add("javax.persistence.Id");
            resultaat.add("javax.persistence.SequenceGenerator");
        } else {
            /*
             * Als er een supertype is wordt de ID daarvan geërft.
             */
        }
        if (columnAnnotatieNodig()) {
            resultaat.add("javax.persistence.Column");
        }
        if (heeftManyToOneProperties()) {
            resultaat.add("javax.persistence.JoinColumn");
            resultaat.add("javax.persistence.ManyToOne");
        }
        if (heeftOneToManyProperties()) {
            resultaat.add("javax.persistence.OneToMany");
        }
        resultaat.add(getInterface().getQualifiedNaam());
        /*
         * ManyToOne properties worden gemapt naar hun persistent class, dus die moeten ook geïmporteerd worden, naast
         * hun interfaces die al in AbstractDomeinObject.getImports() toegevoegd worden. En ook voor OneToMany's.
         */
        for (BeanProperty property : Iterables.concat(getManyToOneProperties(), getOneToManyProperties())) {
            resultaat.add(((Interface) property.getType()).getImplementation().getQualifiedNaam());
        }
        return resultaat;
    }
}
