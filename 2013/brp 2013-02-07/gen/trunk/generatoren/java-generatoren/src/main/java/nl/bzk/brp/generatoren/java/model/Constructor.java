/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

/**
 * Een Constructor klasse die een Java constructor klasse voorstelt.
 */
public class Constructor extends JavaFunctie implements Cloneable {

    /**
     * Constructor om een java constructor object te construeren.
     * @param naam Naam van de constructor.
     * @param accessModifier De access modifier voor deze constructor.
     */
    public Constructor(final JavaAccessModifier accessModifier, final String naam) {
        super(accessModifier, naam);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
