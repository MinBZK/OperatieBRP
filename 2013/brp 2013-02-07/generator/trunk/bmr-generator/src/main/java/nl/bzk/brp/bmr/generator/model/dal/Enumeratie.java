/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.ecore.bmr.ObjectType;


public class Enumeratie extends AbstractDomeinObject {

    private static final String CLASS_NAME_PREFIX = "";

    private List<BeanProperty>  properties        = new ArrayList<BeanProperty>();

    public Enumeratie(final ObjectType objectType) {
        super(objectType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProperty(final BeanProperty property) {
        properties.add(property);
        property.setContainingType(this);
    }

    /**
     * Vertaal een willekeurige string naar een bruikbare enum waarde.
     *
     * @param s de string om een enum waarde van te maken.
     * @return een string die een geldige enum waarde is.
     */
    public String enumValue(final String s) {
        int len = s.length();
        int pos = 0;
        for (int i = 0; i < len; i++, pos++) {
            if (!Character.isJavaIdentifierPart(s.charAt(i))) {
                break;
            }
        }

        StringBuffer resultaat = new StringBuffer(len);
        resultaat.append(s.substring(0, pos).toUpperCase());

        for (int i = pos; i < len; i++) {
            char c = s.charAt(i);
            if (Character.isJavaIdentifierPart(c)) {
                resultaat.append(Character.toUpperCase(c));
            } else if (c != '?') {
                resultaat.append('_');
            }
        }
        return resultaat.toString().replace("__", "_");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<BeanProperty> getProperties() {
        return Collections.unmodifiableCollection(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEntiteit() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClassNamePrefix() {
        return CLASS_NAME_PREFIX;
    }
}
