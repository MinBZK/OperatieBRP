/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PersistentObjectFactory extends AbstractType {

    private static final Logger LOGGER            = LoggerFactory.getLogger(PersistentObjectFactory.class);

    private final static String CLASS_NAME_PREFIX = "Persistent";
    private FactoryInterface    factoryInterface;

    public PersistentObjectFactory(final FactoryInterface factoryInterface) {
        super(buildNaam(factoryInterface.getNaam()));
        this.factoryInterface = factoryInterface;
    }

    private static String buildNaam(final String naam) {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(CLASS_NAME_PREFIX);
        resultaat.append(naam);
        return resultaat.toString();
    }

    @Override
    public void addProperty(final BeanProperty property) {
        throw new UnsupportedOperationException();
    }

    public Set<String> getImports() {
        Set<String> resultaat = new TreeSet<String>();
        resultaat.add(getFactoryInterface().getQualifiedNaam());
        for (AbstractType type : getFactoryInterface().getPackage().getAllExtensionPoints()) {
            LOGGER.debug("{} toevoegen voor imports van {}", type.getQualifiedNaam(), this.getNaam());
            resultaat.add(type.getQualifiedNaam());
        }
        return resultaat;
    }

    @Override
    public String getPackageNaam() {
        return getPackage().getQualifiedNaam();
    }

    @Override
    public boolean isEntiteit() {
        return false;
    }

    @Override
    public Collection<BeanProperty> getProperties() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return the factoryInterface
     */
    public FactoryInterface getFactoryInterface() {
        return factoryInterface;
    }

}
