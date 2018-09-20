/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;

import nl.bzk.brp.ecore.bmr.AttribuutType;


public class ConstraintDefinitie extends AbstractType {

    private AttribuutType    attribuutType;
    private StandardJavaType standardJavaType;

    public ConstraintDefinitie(final AttribuutType attribuutType, final StandardJavaType standardJavaType) {
        super(attribuutType.getIdentifierCode());
        this.attribuutType = attribuutType;
        this.standardJavaType = standardJavaType;
    }

    @Override
    public void addProperty(final BeanProperty property) {
    }

    /**
     * @return the attribuutType
     */
    public AttribuutType getAttribuutType() {
        return attribuutType;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.bmr.generator.model.dal.DomeinModelElement#getNaam()
     */
    @Override
    public String getNaam() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(super.getNaam());
        return resultaat.toString();
    }

    @Override
    public String getPackageNaam() {
        return getPackage().getQualifiedNaam();
    }

    @Override
    public Collection<BeanProperty> getProperties() {
        return null;
    }

    public CharSequence getSizeAnnotatie() {
        StringBuilder resultaat = new StringBuilder();
        CharSequence minimumLengteArgument = getLengteArgument("min", getAttribuutType().getMinimumLengte());
        CharSequence maximumLengteArgument = getLengteArgument("max", getAttribuutType().getMaximumLengte());
        if (minimumLengteArgument != null) {
            resultaat.append(minimumLengteArgument);
        }
        if ((minimumLengteArgument != null) && (maximumLengteArgument != null)) {
            resultaat.append(", ");
        }
        if (maximumLengteArgument != null) {
            resultaat.append(maximumLengteArgument);
        }
        if (resultaat.length() > 0) {
            resultaat.insert(0, "@Size(").append(")");
        }
        return resultaat;
    }

    /**
     * @return the standardJavaType
     */
    public StandardJavaType getStandardJavaType() {
        return standardJavaType;
    }

    @Override
    public boolean isEntiteit() {
        return false;
    }

    private CharSequence getLengteArgument(final String grens, final Integer waarde) {
        StringBuilder resultaat = null;
        if (waarde != null) {
            resultaat = new StringBuilder();
            resultaat.append(grens).append(" = ").append(String.valueOf(waarde));
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getVolgnummer() {
        if (attribuutType != null) {
            return attribuutType.getVolgnummer();
        }
        return 0;
    }
}
