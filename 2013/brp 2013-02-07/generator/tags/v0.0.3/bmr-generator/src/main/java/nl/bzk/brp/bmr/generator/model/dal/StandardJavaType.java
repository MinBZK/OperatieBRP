/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.ecore.bmr.AttribuutType;


public class StandardJavaType extends AbstractType {

    private static Map<String, String> javaTypes = new HashMap<String, String>();
    static {
        javaTypes.put("geheel getal", "Integer");
        javaTypes.put("decimaal getal", "Long");
        javaTypes.put("tekst", "String");
        javaTypes.put("onbeperkte tekst", "String");
        javaTypes.put("ongestructureerde data", "byte[]");
        javaTypes.put("datum", "java.util.Calendar");
        javaTypes.put("tijd", "java.util.Calendar");
        javaTypes.put("datum/tijd", "java.util.Calendar");
        javaTypes.put("ip adres", "String");
        javaTypes.put("groot geheel getal", "Long");
        javaTypes.put("klein geheel getal", "Integer");
        javaTypes.put("numerieke code", "String");
        javaTypes.put("datum met onzekerheid", "Integer");
        javaTypes.put("boolean", "Boolean");
    }

    private AttribuutType              type;
    private ConstraintDefinitie        constraintDefinitie;

    public StandardJavaType(final AttribuutType type) {
        super(type.getIdentifierCode());
        this.type = type;
        if ((type.getMaximumLengte() != null) || (type.getMinimumLengte() != null)
            || (type.getAantalDecimalen() != null))
        {
            this.constraintDefinitie = new ConstraintDefinitie(type, this);
        }
    }

    public StandardJavaType(final String naam) {
        super(naam);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProperty(final BeanProperty property) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNaam() {
        String fullTypeName = null;
        if (type != null) {
            fullTypeName = javaType();
        } else {
            fullTypeName = super.getNaam();
        }
        return fullTypeName.substring(fullTypeName.lastIndexOf('.') + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPackageNaam() {
        String fullTypeName = null;
        if (type != null) {
            fullTypeName = javaType();
        } else {
            fullTypeName = getNaam();
        }
        int packageClassSeparator = fullTypeName.lastIndexOf('.');
        if (packageClassSeparator > 0) {
            return fullTypeName.substring(0, packageClassSeparator);
        } else {
            return "";
        }
    }

    public AttribuutType getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEntiteit() {
        return false;
    }

    /**
     * De Java representatie van dit basistype.
     *
     * @return de Java representatie van dit basistype.
     */
    public String javaType() {
        return javaTypes.get(type.getType().getNaam().toLowerCase());
    }

    @Override
    public Collection<BeanProperty> getProperties() {
        return null;
    }

    /**
     * @return the constraintDefinitie
     */
    public ConstraintDefinitie getConstraintDefinitie() {
        return constraintDefinitie;
    }
}
