/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.naamgeving;

import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.metaregister.model.GeneriekElement;

/**
 * Basis implementatie van de {@link NaamgevingStrategie} met enkele algemene helper methodes die gebruikt worden in
 * de verschillende specifieke implementaties van de {@link NaamgevingStrategie} interface.
 */
public abstract class AbstractNaamgevingStrategie implements NaamgevingStrategie {

    /**
     * Retourneert het schema voor een generiek element. Dit is het schema (ook wel bloemblaadje) waartoe het element
     * behoort.
     *
     * @param element het element.
     * @return het schema waartoe het element behoort.
     */
    protected final String getSchemaVoorElement(final GeneriekElement element) {
        return element.getSchema().getNaam().toLowerCase();
    }

    /**
     * Concateneert een package met een sub-package.
     *
     * @param basisPackage het basis package waaraan het subpackage moet worden toegevoegd.
     * @param subpackageNaam het subpackage dat moet worden toegevoegd.
     * @return het nieuwe geconcateneerde package.
     */
    protected final String concatPackages(final GeneratiePackage basisPackage, final String subpackageNaam) {
        return String.format("%s.%s", basisPackage.getPackage(), subpackageNaam);
    }

    /** {@inheritDoc} */
    @Override
    public final JavaType getJavaTypeVoorElement(final GeneriekElement element) {
        return new JavaType(bepaalJavaTypeNaamVoorElement(element), bepaalPackageNaamVoorElement(element));
    }

    /**
     * Bepaal de package naam voor het element.
     *
     * @param element het element
     * @return de package naam
     */
    protected abstract String bepaalPackageNaamVoorElement(final GeneriekElement element);

    /**
     * Bepaal het java type voor voor het element.
     *
     * @param element het element
     * @return de java type naam
     */
    protected abstract String bepaalJavaTypeNaamVoorElement(final GeneriekElement element);

}
