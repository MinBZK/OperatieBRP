/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;


public class FactoryInterface extends AbstractType {

    public FactoryInterface() {
        super("DomeinObjectFactory");
    }

    @Override
    public void addProperty(final BeanProperty property) {
        throw new UnsupportedOperationException();
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

    public Set<String> getImports() {
        Set<String> resultaat = new TreeSet<String>();
        for (AbstractType type : getPackage().getAllExtensionPoints()) {
            if (type instanceof Interface) {
                resultaat.add(type.getQualifiedNaam());
            }
        }
        return resultaat;
    }
}
