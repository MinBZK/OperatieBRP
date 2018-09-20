/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class JavaPackage extends DomeinModelElement {

    private List<AbstractType>       types       = new ArrayList<AbstractType>();
    private Map<String, JavaPackage> subPackages = new TreeMap<String, JavaPackage>();
    private JavaPackage              containingPackage;

    public JavaPackage(final String naam) {
        super(naam.toLowerCase());
    }

    public JavaPackage addSubPackage(final JavaPackage subPackage) {
        subPackages.put(subPackage.getNaam(), subPackage);
        subPackage.setContainingPackage(this);
        return subPackage;
    }

    public AbstractType addType(final AbstractType type) {
        types.add(type);
        type.setPackage(this);
        return type;
    }

    public JavaPackage getContainingPackage() {
        return containingPackage;
    }

    public String getPad() {
        return getQualifiedNaam().replace(".", "/");
    }

    public String getQualifiedNaam() {
        StringBuilder resultaat = new StringBuilder();
        if (getContainingPackage() != null) {
            resultaat.append(getContainingPackage().getQualifiedNaam());
        }
        if ((resultaat.length() > 0) && (getNaam().length()) > 0) {
            resultaat.append(".");
        }
        resultaat.append(getNaam());
        return resultaat.toString();
    }

    public JavaPackage getSubPackage(final String naam) {
        return subPackages.get(naam);
    }

    public Collection<JavaPackage> getSubPackages() {
        return Collections.unmodifiableCollection(subPackages.values());
    }

    public Collection<AbstractType> getTypes() {
        return Collections.unmodifiableCollection(types);
    }

    /**
     * Geef een collectie van alle typen in dit package die extension points zijn.
     *
     * @return Een collectie van alle typen in dit package die extension points zijn.
     */
    public Collection<AbstractType> getExtensionPoints() {
        Collection<AbstractType> resultaat = new ArrayList<AbstractType>();
        for (AbstractType type : types) {
            if (type.isExtensionPoint()) {
                resultaat.add(type);
            }
        }
        return resultaat;
    }

    /**
     * Geef een collectie van alle typen in dit package en in alle subpackages die extension points zijn.
     *
     * @return Een collectie van alle typen in dit package en in alle subpackages die extension points zijn.
     */
    public Collection<AbstractType> getAllExtensionPoints() {
        /*
         * Een Set om dubbelen eruit te filteren.
         */
        Set<AbstractType> allExtensionPoints = new HashSet<AbstractType>();
        allExtensionPoints.addAll(getExtensionPoints());
        for (JavaPackage javaPackage : getSubPackages()) {
            allExtensionPoints.addAll(javaPackage.getAllExtensionPoints());
        }
        /*
         * Wel de oorspronkelijke volgorde uit het BMR herstellen.
         */
        List<AbstractType> resultaat = new ArrayList<AbstractType>(allExtensionPoints);
        Collections.sort(resultaat, new Comparator<AbstractType>() {

            @Override
            public int compare(final AbstractType o1, final AbstractType o2) {
                return o1.getVolgnummer().compareTo(o2.getVolgnummer());
            }
        });
        return resultaat;
    }

    public void setContainingPackage(final JavaPackage containingPackage) {
        this.containingPackage = containingPackage;
    }
}
