/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.ArrayList;
import java.util.List;


/**
 * De representatie van de enum waarde zoals die door een Java generator kan worden gebruikt.
 */
public class EnumeratieWaarde extends AbstractJavaConstruct {

    /**
     * De parameters voor in de constructor van de enumeratie waarde.
     */
    private final List<EnumeratieConstructorParameter> constructieParameters = new ArrayList<EnumeratieConstructorParameter>();

    /**
     * Maan een nieuwe enumeratie waarde aan.
     *
     * @param naam de naam
     * @param javaDoc de javadoc
     */
    public EnumeratieWaarde(final String naam, final String javaDoc) {
        super(naam, javaDoc);
    }

    /**
     * Voeg een constructie parameter waarde toe aan deze enumeratie waarde.
     *
     * @param parameterWaarde de waarde van de parameter
     * @param isString of het een string is (krijgt '"'s eromheen) of niet (letterlijk overgenomen)
     */
    public void voegConstructorParameterToe(final String parameterWaarde, final boolean isString) {
        constructieParameters.add(new EnumeratieConstructorParameter(parameterWaarde, isString));
    }

    public List<EnumeratieConstructorParameter> getConstructorParameters() {
        return constructieParameters;
    }

}
