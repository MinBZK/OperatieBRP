/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Collection;


public abstract class AbstractType extends DomeinModelElement {

    private JavaPackage pakkage;

    public AbstractType(final String naam) {
        super(naam);
    }

    public abstract void addProperty(BeanProperty property);

    public abstract String getPackageNaam();

    public String getQualifiedNaam() {
        StringBuilder resultaat = new StringBuilder();
        if (!getPackageNaam().isEmpty()) {
            resultaat.append(getPackageNaam()).append(".");
        }
        resultaat.append(getNaam());
        return resultaat.toString();
    }

    /**
     * Zegt of dit type een entiteittype is. Anders is het impliciet een valuetype.
     *
     * @return <code>true</code> als dit type een entiteittype is, en anders <code>false</code>
     */
    public abstract boolean isEntiteit();

    public void setPackage(final JavaPackage pakkage) {
        this.pakkage = pakkage;
    }

    public abstract Collection<BeanProperty> getProperties();

    public boolean isExtensionPoint() {
        return false;
    }

    /**
     * Nuttig voor het behouden van de oorspronkelijke volgorde in het BMR van elementen. Niet voor alle subtypen van
     * toepassing. Daarom deze default implementatie.
     *
     * @return Het volgnummer van het oorspronkelijke element in het BMR.
     */
    public Integer getVolgnummer() {
        return 0;
    }

    public JavaPackage getPackage() {
        return pakkage;
    }

    public String getPad() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(getPackage().getPad()).append("/").append(getNaam()).append(".java");
        return resultaat.toString();
    }
}
